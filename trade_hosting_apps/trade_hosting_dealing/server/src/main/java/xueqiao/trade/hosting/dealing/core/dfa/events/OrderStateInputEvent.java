package xueqiao.trade.hosting.dealing.core.dfa.events;

import java.util.HashSet;
import java.util.Set;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.CTPCombOffsetFlagType;
import xueqiao.trade.hosting.CTPOrderInputExt;
import xueqiao.trade.hosting.CTPOrderRef;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderRef;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.dfa.DFAEvent;
import xueqiao.trade.hosting.dealing.core.dfa.InputAction;
import xueqiao.trade.hosting.dealing.core.dfa.TransferCode;
import xueqiao.trade.hosting.upside.entry.client.TradeHostingUpsideEntryStub;
import xueqiao.trade.hosting.upside.entry.helper.UpsideEntryStubBuilder;

import static xueqiao.trade.hosting.HostingExecOrderStateValue.*;

/**
 *  状态构成的事件， 到达一个状态，就构成一个事件驱动
 */
public class OrderStateInputEvent extends DFAEvent {
    private static Set<HostingExecOrderStateValue> NEED_HANDLE_STATE_SET 
            = new HashSet<HostingExecOrderStateValue>();
            
    static {
        NEED_HANDLE_STATE_SET.add(ORDER_WAITING_SLED_SEND);
        NEED_HANDLE_STATE_SET.add(ORDER_SLED_ALLOC_REF_FINISHED);
        NEED_HANDLE_STATE_SET.add(ORDER_UPSIDE_RECEIVED_WAITING_REVOKE);
        NEED_HANDLE_STATE_SET.add(ORDER_UPSIDE_RESTING_WAITING_REVOKE);
        NEED_HANDLE_STATE_SET.add(ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE);
        NEED_HANDLE_STATE_SET.add(ORDER_CONDITION_NOT_TRIGGER_WAITING_REVOKE);
        
        NEED_HANDLE_STATE_SET.add(ORDER_UPSIDE_REJECTED);
    }
    
    public OrderStateInputEvent() {
    }
    
    @Override
    public String getType() {
        return getClass().getSimpleName();
    }
    
    public boolean needHandleState(HostingExecOrderStateValue state) {
        return NEED_HANDLE_STATE_SET.contains(state);
    }
    
    class AllocOrderRefInputAction extends InputAction {
        public AllocOrderRefInputAction(HostingExecOrder inputOrder) {
            super(inputOrder);
        }

        @Override
        protected void run() {
            outputOrder = new HostingExecOrder();
            outputOrder.setStateInfo(new HostingExecOrderStateInfo());
            
            // 审核通过至开始订单发送过程超过15s时间
            if (System.currentTimeMillis() > inputOrder.getVerifyTimestampMs() + 15000) {
                transferCode = TransferCode.SLED_SYSTEM_REJECT_SEND;
                outputOrder.getStateInfo().setFailedErrorCode(
                        TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG.getValue());
                return ;
            }
            
            TradeHostingUpsideEntryStub stub = UpsideEntryStubBuilder.fromOrder(inputOrder);
            try {
                HostingExecOrderRef orderRef = stub.allocOrderRef();
                outputOrder.setUpsideOrderRef(orderRef);
                transferCode = TransferCode.SLED_CALL_ALLOCREF_SUCCESS;
            } catch (ErrorInfo e) {
                AppLog.e("ErrorInfo errorCode=" + e.getErrorCode() + ", errorMsg=" + e.getErrorMsg(), e);
                outputOrder.getStateInfo().setFailedErrorCode(e.getErrorCode());
                transferCode = TransferCode.SLED_CALL_ALLOCREF_FAILED;
            } catch (TTransportException e) {
                AppLog.e("type=" + e.getType() + ", msg=" + e.getMessage(), e);
                if (e.getType() == TTransportException.NOT_OPEN) {
                    outputOrder.getStateInfo().setFailedErrorCode(
                            TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID.getValue());
                } else {
                    outputOrder.getStateInfo().setFailedErrorCode(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue());
                }
                transferCode = TransferCode.SLED_CALL_ALLOCREF_FAILED;
            } catch (TException e) {
                AppLog.e(e.getMessage(), e);
                outputOrder.getStateInfo().setFailedErrorCode(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue());
                transferCode = TransferCode.SLED_CALL_ALLOCREF_FAILED;
            }
        }
    }
    
    class OrderInsertInputAction extends InputAction {
        public OrderInsertInputAction(HostingExecOrder inputOrder) {
            super(inputOrder);
        }

        @Override
        protected void run() {
            TradeHostingUpsideEntryStub stub = UpsideEntryStubBuilder.fromOrder(inputOrder);
            int failedErrorCode = -1;
            
            try {
                stub.orderInsert(inputOrder);
                transferCode = TransferCode.SLED_CALL_ORDERINSERT_SUCCESS;
            } catch (ErrorInfo e) {
                AppLog.e("ErrorInfo errorCode=" + e.getErrorCode() + ", errorMsg=" + e.getErrorMsg(), e);
                
                if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_ORDERREF_EXPIRED.getValue()) {
                    transferCode = TransferCode.SLED_CALL_ORDERINSERT_FAILED_ORDERREF_EXPIRED;
                } else if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_SELF_MATCH.getValue()) {
                	transferCode = TransferCode.SLED_CALL_ORDERINSERT_FAILED_SELF_MATCH;
                } else if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED.getValue()) {
                    transferCode = TransferCode.SLED_CALL_ORDERINSERT_FAILED_CONTRACT_ERROR;
                } else if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND.getValue()) {
                    transferCode = TransferCode.SLED_CALL_ORDERINSERT_FAILED_CONTRACT_ERROR;
                }  else {
                    transferCode = TransferCode.SLED_CALL_ORDERINSERT_FAILED_OTHER;
                }
                failedErrorCode = e.getErrorCode();

                if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_DUPLICATE_SEND.getValue()) {
                    // 上手进程检测到重复发送，可能订单已经发送
                    failedErrorCode = 0;
                    transferCode = TransferCode.SLED_CALL_ORDERINSERT_UNKOWN;
                }
                
            } catch (TTransportException e) {
            	AppLog.e("type=" + e.getType() + ", msg=" + e.getMessage(), e);
                if (e.getType() == TTransportException.NOT_OPEN) {
                    transferCode = TransferCode.SLED_CALL_ORDERINSERT_FAILED_CONNECT;
                    failedErrorCode = TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID.getValue();
                } else {
                    transferCode = TransferCode.SLED_CALL_ORDERINSERT_UNKOWN;
                }
            } catch (TException e) {
                AppLog.e(e.getMessage(), e);
                transferCode = TransferCode.SLED_CALL_ORDERINSERT_UNKOWN;
            }
            
            if (failedErrorCode > 0) {
                outputOrder = new HostingExecOrder();
                outputOrder.setStateInfo(new HostingExecOrderStateInfo());
                outputOrder.getStateInfo().setFailedErrorCode(failedErrorCode);
            }
        }

    }
    
    public class OrderDeleteInputAction extends InputAction {
        public OrderDeleteInputAction(HostingExecOrder inputOrder) {
            super(inputOrder);
        }

        @Override
        protected void run() {
            TradeHostingUpsideEntryStub stub = UpsideEntryStubBuilder.fromOrder(inputOrder);
            outputOrder = new HostingExecOrder();
            try {
            	outputOrder.setRevokeInfo(new HostingExecOrderRevokeInfo());
            	outputOrder.getRevokeInfo().setLastRevokeTimestampMs(System.currentTimeMillis());
            	outputOrder.getRevokeInfo().setLastRevokeFailedErrorCode(0);
            	outputOrder.getRevokeInfo().setLastRevokeUpsideErrorCode(0);
            	outputOrder.getRevokeInfo().setLastRevokeUpsideRejectReason("");
                stub.orderDelete(inputOrder);
                transferCode = TransferCode.SLED_CALL_ORDERDELETE_SUCCESS;
            } catch (ErrorInfo e) {
                AppLog.e("ErrorInfo errorCode=" + e.getErrorCode() + ", errorMsg=" + e.getErrorMsg(), e);
                transferCode = TransferCode.SLED_CALL_ORDERDELETE_FAILED;
                outputOrder.getRevokeInfo().setLastRevokeFailedErrorCode(e.getErrorCode());
            } catch (TTransportException e) {
            	AppLog.e("type=" + e.getType() + ", msg=" + e.getMessage(), e);
                if (e.getType() == TTransportException.NOT_OPEN) {
                    transferCode = TransferCode.SLED_CALL_ORDERDELETE_FAILED;
                    outputOrder.getRevokeInfo().setLastRevokeFailedErrorCode(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue());
                } else {
                    transferCode = TransferCode.SLED_CALL_ORDERDELETE_UNKOWN;
                }
            } catch (TException e) {
            	AppLog.e(e.getMessage(), e);
                transferCode = TransferCode.SLED_CALL_ORDERDELETE_UNKOWN;
            }
        }
    }
    
    public class ModifyOrderExtCombOffsetFlagInputAction extends InputAction {
		public ModifyOrderExtCombOffsetFlagInputAction(HostingExecOrder inputOrder) {
			super(inputOrder);
		}

		@Override
		protected void run() {
			if (inputOrder.getAccountSummary().getTechPlatform() != BrokerTechPlatform.TECH_CTP) {
				return ;
			}
			
			outputOrder = new HostingExecOrder();
			outputOrder.setUpsideOrderRef(new HostingExecOrderRef());
			outputOrder.getUpsideOrderRef().setCtpRef(new CTPOrderRef());
			outputOrder.getUpsideOrderRef().getCtpRef().setFrontID(0);
			outputOrder.getUpsideOrderRef().getCtpRef().setSessionID(0);
			outputOrder.getUpsideOrderRef().getCtpRef().setOrderRef("");
			
			outputOrder.setOrderInputExt(new HostingExecOrderInputExt());
			outputOrder.getOrderInputExt().setCtpInputExt(new CTPOrderInputExt());
			outputOrder.getOrderInputExt().getCtpInputExt().setCombOffsetFlag(CTPCombOffsetFlagType.THOST_FTDC_OF_OPEN);
			
			transferCode = TransferCode.SLED_MODIFY_RETRY;
		}
    }

    @Override
    public InputAction getInputAction(HostingExecOrder inputOrder) {
        HostingExecOrderState currentState  = inputOrder.getStateInfo().getCurrentState();
        
        if (currentState.getValue() == ORDER_WAITING_SLED_SEND) {
            return new AllocOrderRefInputAction(inputOrder);
        }
        if (currentState.getValue() == ORDER_SLED_ALLOC_REF_FINISHED) {
            return new OrderInsertInputAction(inputOrder);
        }
        if (currentState.getValue() == ORDER_UPSIDE_RECEIVED_WAITING_REVOKE
            || currentState.getValue() == ORDER_UPSIDE_RESTING_WAITING_REVOKE
            || currentState.getValue() == ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE
            || currentState.getValue() == ORDER_CONDITION_NOT_TRIGGER_WAITING_REVOKE) {
            return new OrderDeleteInputAction(inputOrder);
        }
        if (currentState.getValue() == ORDER_UPSIDE_REJECTED 
        	&& (inputOrder.getStateInfo().getFailedErrorCode()== TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION.getValue()
        	|| inputOrder.getStateInfo().getFailedErrorCode()== TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION.getValue()
        	|| inputOrder.getStateInfo().getFailedErrorCode() == TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION.getValue())) {
        	return new ModifyOrderExtCombOffsetFlagInputAction(inputOrder);
        }
        return null;
    }

}
