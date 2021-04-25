package xueqiao.trade.hosting.dealing.core.dfa;

import java.util.AbstractMap.SimpleEntry;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.CTPOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderDealInfo;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.OrderDealIDData;
import xueqiao.trade.hosting.dealing.core.OrderRefData;
import xueqiao.trade.hosting.dealing.core.sync.ISyncPolicy;
import xueqiao.trade.hosting.dealing.core.sync.OrderStateSyncPolicyMap;
import xueqiao.trade.hosting.dealing.core.sync.RevokeTimeoutTask;
import xueqiao.trade.hosting.dealing.core.sync.RevokeTimeoutTaskManager;
import xueqiao.trade.hosting.dealing.core.sync.SyncOrderStateTask;
import xueqiao.trade.hosting.dealing.core.sync.SyncOrderTaskManager;
import xueqiao.trade.hosting.dealing.core.sync.SyncOrderTradeListTask;
import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
import xueqiao.trade.hosting.dealing.storage.DealingStorageApiV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardType;
import xueqiao.trade.hosting.events.ExecOrderRunningEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
import xueqiao.trade.hosting.storage.dealing.HostingDealingHelper;

public class StateMachine {
    private HostingExecOrder originOrder;
    private HostingExecOrder newOrder;

    public StateMachine(HostingExecOrder originOrder) {
        this.originOrder = originOrder;
    }

    public HostingExecOrder getNewOrder() {
        return newOrder;
    }

    public void transfer(DFAEvent inputEvent) throws ErrorInfo {
        try {
            doTransfer(inputEvent);
        } catch (ErrorInfo e) {
            if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_NOT_EXISTED.getValue()) {
                newOrder = null;
                return;
            }

            if (e.getErrorCode() != TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()) {
                // 非系统异常，理论上不应该出现
                AppLog.e("doTransfer error ocuurs, execOrderId="  +  originOrder.getExecOrderId()
                        + ", inputEvent=" + inputEvent
                        +  ", errorCode=" + e.getErrorCode()
                        + ", errorMsg=" + e.getErrorMsg());
            }

            // 等待系统恢复
            throw e;
        }
    }

    private void doTransfer(DFAEvent inputEvent) throws ErrorInfo {
        new DBStepHelperWithMsg<Void>(DealingDBV2.Global()) {
            private HostingExecOrder updateOrder;
            private boolean needUpdate = false;
            
            private long startTimestampMs = 0;
            private long actionRunEscapedMs = 0;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                startTimestampMs = System.currentTimeMillis();
                InputAction inputAction = inputEvent.getInputAction(originOrder);
                if (inputAction == null) {
                    // 没有对应的注册行为
                    return ;
                }
                actionRunEscapedMs = System.currentTimeMillis() - startTimestampMs;
                
                inputAction.action();
                
                TransferCode transferCode = inputAction.getTransferCode();
                if (transferCode == null) {
                    // TODO 这个时候是属于代码异常
                    AppLog.e("InputAction=" + inputAction.getClass().getName() 
                            + " failed to get transfer code for inputEvent=" + inputEvent
                            + " , order=" + originOrder); 
                    return ;
                }
                if (transferCode == TransferCode.SLED_SKIP) {
                    return ;
                } 
                
                HostingExecOrderStateValue nextStateValue = 
                        Graph.INSTANCE.getNextState(originOrder.getStateInfo().getCurrentState().getValue(), transferCode);
                if (nextStateValue == null) {
                    return ;
                }

                newOrder = new HostingExecOrder(originOrder);
                
                HostingExecOrderState nextState = new HostingExecOrderState();
                nextState.setValue(nextStateValue);
                nextState.setTimestampMs(System.currentTimeMillis());
                
                updateOrder = new HostingExecOrder();
                updateOrder.setExecOrderId(originOrder.getExecOrderId());
                updateOrder.setStateInfo(new HostingExecOrderStateInfo());
                updateOrder.getStateInfo().setHistoryStates(originOrder.getStateInfo().getHistoryStates());
                updateOrder.getStateInfo().getHistoryStates().add(originOrder.getStateInfo().getCurrentState());
                updateOrder.getStateInfo().setCurrentState(nextState);
                updateOrder.getStateInfo().setFailedErrorCode(0);
                updateOrder.getStateInfo().setUpsideErrorCode(0);
                updateOrder.getStateInfo().setUpsideUsefulMsg("");
                
                HostingExecOrder actionOutputOrder = inputAction.getOutputOrder();
                if (actionOutputOrder != null) {
                    if (actionOutputOrder.isSetStateInfo()) {
                        if (actionOutputOrder.getStateInfo().isSetFailedErrorCode()) {
                            updateOrder.getStateInfo().setFailedErrorCode(
                                    actionOutputOrder.getStateInfo().getFailedErrorCode());
                        }
                        if (actionOutputOrder.getStateInfo().isSetUpsideUsefulMsg()) {
                            updateOrder.getStateInfo().setUpsideUsefulMsg(
                                    actionOutputOrder.getStateInfo().getUpsideUsefulMsg());
                        }
                        
                        if (actionOutputOrder.getStateInfo().isSetUpsideErrorCode()) {
                            updateOrder.getStateInfo().setUpsideErrorCode(
                                    actionOutputOrder.getStateInfo().getUpsideErrorCode());
                        }
                    }
                    
                    if (actionOutputOrder.isSetUpsideOrderRef()) {
                        updateOrder.setUpsideOrderRef(actionOutputOrder.getUpsideOrderRef());
                        newOrder.setUpsideOrderRef(actionOutputOrder.getUpsideOrderRef());
                    }
                    if (actionOutputOrder.isSetDealInfo()) {
                        updateOrder.setDealInfo(new HostingExecOrderDealInfo());
                        if (actionOutputOrder.getDealInfo().isSetDealId()) {
                            updateOrder.getDealInfo().setDealId(actionOutputOrder.getDealInfo().getDealId());
                            newOrder.getDealInfo().setDealId(actionOutputOrder.getDealInfo().getDealId());
                        }
                        if (actionOutputOrder.getDealInfo().isSetOrderInsertDateTime()) {
                            updateOrder.getDealInfo().setOrderInsertDateTime(actionOutputOrder.getDealInfo().getOrderInsertDateTime());
                            newOrder.getDealInfo().setOrderInsertDateTime(actionOutputOrder.getDealInfo().getOrderInsertDateTime());
                        }
                        if (actionOutputOrder.getDealInfo().isSetCtpDealInfo()) {
                            updateOrder.getDealInfo().setCtpDealInfo(actionOutputOrder.getDealInfo().getCtpDealInfo());
                            newOrder.getDealInfo().setCtpDealInfo(actionOutputOrder.getDealInfo().getCtpDealInfo());
                        }
                        if (actionOutputOrder.getDealInfo().isSetEsunny9DealInfo()) {
                        	updateOrder.getDealInfo().setEsunny9DealInfo(actionOutputOrder.getDealInfo().getEsunny9DealInfo());
                            newOrder.getDealInfo().setEsunny9DealInfo(actionOutputOrder.getDealInfo().getEsunny9DealInfo());
                        }
                    }
                    if (actionOutputOrder.isSetTradeSummary()) {
                        updateOrder.setTradeSummary(new HostingExecOrderTradeSummary());
                        if (actionOutputOrder.getTradeSummary().isSetUpsideTradeTotalVolume()) {
                            updateOrder.getTradeSummary().setUpsideTradeTotalVolume(
                                    actionOutputOrder.getTradeSummary().getUpsideTradeTotalVolume());
                            newOrder.getTradeSummary().setUpsideTradeTotalVolume(
                                    actionOutputOrder.getTradeSummary().getUpsideTradeTotalVolume());
                        }
                        if (actionOutputOrder.getTradeSummary().isSetUpsideTradeRestingVolume()) {
                            updateOrder.getTradeSummary().setUpsideTradeRestingVolume(
                                    actionOutputOrder.getTradeSummary().getUpsideTradeRestingVolume());
                            newOrder.getTradeSummary().setUpsideTradeRestingVolume(
                                    actionOutputOrder.getTradeSummary().getUpsideTradeRestingVolume());
                        }
                        if (actionOutputOrder.getTradeSummary().isSetUpsideTradeAveragePrice()) {
                            updateOrder.getTradeSummary().setUpsideTradeAveragePrice(
                                    actionOutputOrder.getTradeSummary().getUpsideTradeAveragePrice());
                            newOrder.getTradeSummary().setUpsideTradeAveragePrice(
                                    actionOutputOrder.getTradeSummary().getUpsideTradeAveragePrice());
                        }
                    }
                    if (actionOutputOrder.isSetNotifyStateHandleInfos()) {
                        updateOrder.setNotifyStateHandleInfos(actionOutputOrder.getNotifyStateHandleInfos());
                        newOrder.setNotifyStateHandleInfos(actionOutputOrder.getNotifyStateHandleInfos());
                    }
                    
                    if (actionOutputOrder.isSetRevokeInfo()) {
                    	updateOrder.setRevokeInfo(new HostingExecOrderRevokeInfo());
                    	if (actionOutputOrder.getRevokeInfo().isSetLastRevokeTimestampMs()) {
                    		updateOrder.getRevokeInfo().setLastRevokeTimestampMs(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeTimestampMs());
                            newOrder.getRevokeInfo().setLastRevokeTimestampMs(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeTimestampMs());
                    	}
                    	if (actionOutputOrder.getRevokeInfo().isSetLastRevokeFailedErrorCode()) {
                    		updateOrder.getRevokeInfo().setLastRevokeFailedErrorCode(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeFailedErrorCode());
                            newOrder.getRevokeInfo().setLastRevokeFailedErrorCode(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeFailedErrorCode());
                    	}
                    	if (actionOutputOrder.getRevokeInfo().isSetLastRevokeUpsideErrorCode()) {
                    		updateOrder.getRevokeInfo().setLastRevokeUpsideErrorCode(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeUpsideErrorCode());
                            newOrder.getRevokeInfo().setLastRevokeUpsideErrorCode(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeUpsideErrorCode());
                    	}
                    	if (actionOutputOrder.getRevokeInfo().isSetLastRevokeUpsideRejectReason()) {
                    		updateOrder.getRevokeInfo().setLastRevokeUpsideRejectReason(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeUpsideRejectReason());
                            newOrder.getRevokeInfo().setLastRevokeUpsideRejectReason(
                    				actionOutputOrder.getRevokeInfo().getLastRevokeUpsideRejectReason());
                    	}
                    }
                    if (actionOutputOrder.isSetOrderInputExt()) {
                    	updateOrder.setOrderInputExt(new HostingExecOrderInputExt());
                    	if (actionOutputOrder.getOrderInputExt().isSetCtpInputExt()) {
                    		updateOrder.getOrderInputExt().setCtpInputExt(new CTPOrderInputExt());
                    		if (!newOrder.getOrderInputExt().isSetCtpInputExt()) {
                                newOrder.getOrderInputExt().setCtpInputExt(new CTPOrderInputExt());
                    		}
                    		if (actionOutputOrder.getOrderInputExt().getCtpInputExt().isSetCombOffsetFlag()) {
                    			updateOrder.getOrderInputExt().getCtpInputExt().setCombOffsetFlag(
                    					actionOutputOrder.getOrderInputExt().getCtpInputExt().getCombOffsetFlag());
                                newOrder.getOrderInputExt().getCtpInputExt().setCombOffsetFlag(
                    					actionOutputOrder.getOrderInputExt().getCtpInputExt().getCombOffsetFlag());
                    		}
                    	}
                    }
                }
                
                updateOrder.setVersion(originOrder.getVersion() + 1);
                
                updateOrder.getStateInfo().setStatusMsg( 
                        HostingDealingHelper.generateStatusMsg(nextState.getValue()
                                    , updateOrder.getStateInfo().getFailedErrorCode()
                                    , updateOrder.getStateInfo().getUpsideUsefulMsg()));

                newOrder.setStateInfo(new HostingExecOrderStateInfo(updateOrder.getStateInfo()));
                newOrder.setVersion(updateOrder.getVersion());
                
                needUpdate = true;
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (!needUpdate) {
                    return ;
                }
                
                int r = new HostingExecOrderTableV2(getConnection()).updateOrder(updateOrder);
                if (r <= 0) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_NOT_EXISTED.getValue()
                            , "order not existed!");
                }
            }
            
            @Override
            protected void onUpdateFinished() throws Exception {
            	super.onUpdateFinished();
            	if (!needUpdate) {
            		return ;
            	}
            	
            	if(AppLog.infoEnabled()) {
            		StringBuilder performLogBuilder = new StringBuilder(256);
            		performLogBuilder.append("State machine execute for execOrderId=").append(originOrder.getExecOrderId())
            					.append(", fromState=").append(originOrder.getStateInfo().getCurrentState().getValue())
                                .append(", toState=").append(newOrder.getStateInfo().getCurrentState().getValue())
                                .append(", actionRunEscapedMs=").append(actionRunEscapedMs)
                                .append(", totalEscapedMs=").append(System.currentTimeMillis() - startTimestampMs);
            		AppLog.i(performLogBuilder.toString());
            	}

            	// 更新内存中所需的映射
            	if (updateOrder.isSetUpsideOrderRef()) {
                    OrderRefData.Global().put(originOrder.getAccountSummary()
                            , updateOrder.getUpsideOrderRef()
                            , originOrder.getExecOrderId());
                }
                if (updateOrder.isSetDealInfo() && updateOrder.getDealInfo().isSetDealId()) {
                    OrderDealIDData.Global().put(originOrder.getAccountSummary()
                            , updateOrder.getDealInfo().getDealId()
                            , originOrder.getExecOrderId());
                }

            	// 实际存储的内容要缩短
            	newOrder.setNotifyStateHandleInfos(DealingStorageApiV2.trimHandleInfos(newOrder.getNotifyStateHandleInfos()));
            	newOrder.getStateInfo().setHistoryStates(DealingStorageApiV2.trimHistoryStates(
            	        newOrder.getStateInfo().getHistoryStates()));

            	// 成交列表不平时，触发3s后同步成交列表，收到成交列表回报后，取消同步
            	if (newOrder.getTradeSummary().getTradeListTotalVolume()
                                < newOrder.getTradeSummary().getUpsideTradeTotalVolume()) {
            	    SyncOrderTaskManager.Global().addTask(
            	            new SyncOrderTradeListTask(newOrder.getExecOrderId()), 3000, 6000);
            	}
            	
            	// 订单状态同步策略，先取消原有状态同步时间策略，然后置换策略
            	RevokeTimeoutTaskManager.Global().cancelTask(new RevokeTimeoutTask(newOrder.getExecOrderId()));
            	SyncOrderTaskManager.Global().cancelTask(new SyncOrderStateTask(newOrder.getExecOrderId()));
            	ISyncPolicy newSyncPolicy = OrderStateSyncPolicyMap.Global().getSyncPolicy(
                        newOrder.getStateInfo().getCurrentState().getValue());
            	if (newSyncPolicy == null) {
            		return ;
            	}
            	newSyncPolicy.getTaskManager().addTask(newSyncPolicy.getSyncTask(newOrder.getExecOrderId())
            			, newSyncPolicy.getDelayMs()
            			, newSyncPolicy.getPeriodMs());
            }

            @Override
            public Void getResult() {
                return null;
            }
            
            @SuppressWarnings("rawtypes")
            @Override
            protected TBase prepareNotifyMessage() {
                if (!needUpdate) {
                    return null;
                }
                
                ExecOrderRunningEvent event = new ExecOrderRunningEvent();
                HostingExecOrder notifyOrder = new HostingExecOrder(newOrder);
                notifyOrder.getStateInfo().unsetHistoryStates();
                notifyOrder.unsetNotifyStateHandleInfos();
                event.setRunningOrder(notifyOrder);
                
                return event;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                if (!needUpdate) {
                    return null;
                }
                
                ExecOrderGuardEvent event = new ExecOrderGuardEvent();
                event.setGuardExecOrderId(originOrder.getExecOrderId());
                event.setGuardType(ExecOrderGuardType.GUARD_ORDER_RUNNING);
                
                return new SimpleEntry<>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();
    }
}
