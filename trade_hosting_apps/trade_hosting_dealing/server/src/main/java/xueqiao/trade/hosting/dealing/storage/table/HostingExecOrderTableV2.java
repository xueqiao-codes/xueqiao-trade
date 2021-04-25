package xueqiao.trade.hosting.dealing.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.CTPCombHedgeFlagType;
import xueqiao.trade.hosting.CTPCombOffsetFlagType;
import xueqiao.trade.hosting.CTPContractSummary;
import xueqiao.trade.hosting.CTPDealID;
import xueqiao.trade.hosting.CTPOrderInputExt;
import xueqiao.trade.hosting.CTPOrderRef;
import xueqiao.trade.hosting.CTPTradeDirection;
import xueqiao.trade.hosting.ESunny3ContractSummary;
import xueqiao.trade.hosting.ESunny3DealID;
import xueqiao.trade.hosting.ESunny3OrderInputExt;
import xueqiao.trade.hosting.ESunny3OrderRef;
import xueqiao.trade.hosting.ESunny9ContractSummary;
import xueqiao.trade.hosting.ESunny9DealID;
import xueqiao.trade.hosting.ESunny9OrderInputExt;
import xueqiao.trade.hosting.ESunny9OrderRef;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderDealCTPInfo;
import xueqiao.trade.hosting.HostingExecOrderDealESunny9Info;
import xueqiao.trade.hosting.HostingExecOrderDealID;
import xueqiao.trade.hosting.HostingExecOrderDealInfo;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderRef;
import xueqiao.trade.hosting.HostingExecOrderRevokeInfo;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingUpsideNotifyStateHandleInfo;
import xueqiao.trade.hosting.dealing.storage.DealingStorageConstants;
import xueqiao.trade.hosting.dealing.storage.HostingDealingDBUtils;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *  报单历史列表
 * @author wangli
 *
 */
public class HostingExecOrderTableV2 extends TableHelper <HostingExecOrder> implements IDBTable {
	public HostingExecOrderTableV2(Connection conn) {
		super(conn);
	}
	
	public HostingExecOrder getContractSummaryWithTradeDirection(long execOrderId) throws SQLException {
	    SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
	    queryBuilder.addFields("Fexec_order_id"
	            , "Fsled_contract_id"
	            , "Fsled_contract_code"
	            , "Fsled_commodity_id"
	            , "Fsled_commodity_type"
	            , "Fsled_commodity_code"
	            , "Fsled_exchange_mic"
	            , "Fsled_contract_related_legs"
	            , "Ftrade_direction");
	    queryBuilder.addTables(getTableName());
	    queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_order_id=?", execOrderId);
	    
	    return new QueryRunner().query(getConnection(), queryBuilder.getItemsSql()
	            , new ResultSetHandler<HostingExecOrder>() {
	        @Override
	        public HostingExecOrder handle(ResultSet rs) throws SQLException {
	            if (rs.next()) {
	                HostingExecOrder execOrder = new HostingExecOrder();
	                execOrder.setContractSummary(HostingDealingDBUtils.contractSummaryFromRs(rs, true));
	                execOrder.setOrderDetail(new HostingExecOrderDetail());
	                execOrder.getOrderDetail().setTradeDirection(
	                        HostingExecOrderTradeDirection.findByValue(rs.getByte("Ftrade_direction")));
	                return execOrder;
	            }
	            return null;
	        }
	    }, queryBuilder.getParameterList());
	}
	
	public HostingExecOrder getOrder(long execOrderId) throws SQLException {
	    return getOrder(execOrderId, false);
	}

	public List<HostingExecOrder> batchGetOrders(Set<Long> execOrderIds) throws SQLException {
		Preconditions.checkNotNull(execOrderIds);

		if (execOrderIds == null || execOrderIds.isEmpty()) {
			return new ArrayList<>();
		}

		SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
		queryBuilder.addInFieldCondition(ConditionType.AND, "Fexec_order_id", execOrderIds);
		return super.getItemList(queryBuilder);
	}
	
	public HostingExecOrder getOrder(long execOrderId, boolean forUpdate) throws SQLException {
	    SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
	    queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_order_id=?", execOrderId);
	    return super.getItem(queryBuilder, forUpdate);
	}
	
	public int addOrder(HostingExecOrder order) throws SQLException {
	    Preconditions.checkNotNull(order);
	    Preconditions.checkArgument(order.isSetExecOrderId() && order.getExecOrderId() > 0);
	    Preconditions.checkArgument(order.isSetSubUserId());
	    
	    PreparedFields fields = new PreparedFields();
	    fields.addLong("Fexec_order_id", order.getExecOrderId());
	   
	    try {
	        order2PreparedFields(order, fields);
	    } catch (Throwable e) {
	        throw new SQLException(e.getMessage(), e);
	    }
	    
	    if(order.isSetCreateTimestampMs()) {
	        fields.addLong("Fcreate_timestampms", order.getCreateTimestampMs());
	        fields.addLong("Flastmodify_timestampms", order.getCreateTimestampMs());
	    } else {
	        fields.addLong("Fcreate_timestampms", System.currentTimeMillis());
	        fields.addLong("Flastmodify_timestampms", System.currentTimeMillis());
	    }
	    
	    if (order.isSetVersion()) {
	        fields.addInt("Fversion", order.getVersion());
	    } else {
	        fields.addInt("Fversion", 1);
	    }
	    
	    if (order.isSetSource()) {
	        fields.addString("Fsource", order.getSource());
	    }
	    
	    return super.insert(fields);
	}
	
	public int updateOrder(HostingExecOrder order) throws SQLException {
	    Preconditions.checkNotNull(order);
	    Preconditions.checkArgument(order.isSetExecOrderId() && order.getExecOrderId() > 0);
	    
	    PreparedFields fields = new PreparedFields();
	    try {
            order2PreparedFields(order, fields);
        } catch (Throwable e) {
            throw new SQLException(e.getMessage(), e);
        }
	    fields.addLong("Flastmodify_timestampms", System.currentTimeMillis());
	    if (order.isSetVersion()) {
	        fields.addInt("Fversion", order.getVersion());
	    } else {
	        fields.addFieldExpression("Fversion=Fversion+1");
	    }
	    
	    return super.update(fields, "Fexec_order_id=?", order.getExecOrderId());
	}
	
	public int deleteOrder(long execOrderId) throws SQLException {
	    Preconditions.checkArgument(execOrderId > 0);
	    return super.delete("Fexec_order_id=?", execOrderId);
	}
	
	private void order2PreparedFields(HostingExecOrder order, PreparedFields fields) throws TException  {
	    if (order.isSetSubUserId()) {
	        fields.addInt("Fsub_user_id", order.getSubUserId());
	    }
	    if (order.isSetSubAccountId()) {
	        fields.addLong("Fsub_account_id", order.getSubAccountId());
	    }
	    
	    if (order.isSetContractSummary()) {
            HostingDealingDBUtils.contractSummary2PrepraredFields(order.getContractSummary(), fields, true);
        }
        
        if (order.isSetAccountSummary()) {
            HostingDealingDBUtils.accountSummary2PreparedFields(order.getAccountSummary(), fields);
        }
        
        if (order.isSetOrderDetail()) {
           HostingDealingDBUtils.orderDetail2PreparedFields(order.getOrderDetail(), fields);
        }
	    
        if (order.isSetUpsideOrderRef()) {
            if (order.getUpsideOrderRef().isSetCtpRef()) {
                CTPOrderRef ctpRef = order.getUpsideOrderRef().getCtpRef();
                if (ctpRef.isSetFrontID()) {
                    fields.addInt("Fctp_front_id", ctpRef.getFrontID());
                }
                if (ctpRef.isSetSessionID()) {
                    fields.addInt("Fctp_session_id", ctpRef.getSessionID());
                }
                if (ctpRef.isSetOrderRef()) {
                    fields.addString("Fctp_order_ref", ctpRef.getOrderRef());
                }
            }
            if (order.getUpsideOrderRef().isSetEsunny3Ref()) {
                ESunny3OrderRef esunny3Ref = order.getUpsideOrderRef().getEsunny3Ref();
                if (esunny3Ref.isSetSaveString()) {
                    fields.addString("Fesunny3_save_string", esunny3Ref.getSaveString());
                }
            }
            if (order.getUpsideOrderRef().isSetEsunny9Ref()) {
            	ESunny9OrderRef esunny9Ref = order.getUpsideOrderRef().getEsunny9Ref();
            	if (esunny9Ref.isSetRefString()) {
            		fields.addString("Fesunny9_ref_string", esunny9Ref.getRefString());
            	}
            }
        }
        
        if (order.isSetOrderInputExt()) {
            if (order.getOrderInputExt().isSetCtpInputExt()) {
                CTPOrderInputExt ctpInputExt = order.getOrderInputExt().getCtpInputExt();
                if (ctpInputExt.isSetContractSummary()) {
                    if (ctpInputExt.getContractSummary().isSetCtpExchangeCode()) {
                        fields.addString("Fctp_exchange_code", ctpInputExt.getContractSummary().getCtpExchangeCode());
                    }
                    if (ctpInputExt.getContractSummary().isSetCtpCommodityType()) {
                        fields.addShort("Fctp_commodity_type", ctpInputExt.getContractSummary().getCtpCommodityType());
                    }
                    if (ctpInputExt.getContractSummary().isSetCtpCommodityCode()) {
                        fields.addString("Fctp_commodity_code", ctpInputExt.getContractSummary().getCtpCommodityCode());
                    }
                    if (ctpInputExt.getContractSummary().isSetCtpContractCode()) {
                        fields.addString("Fctp_contract_code", ctpInputExt.getContractSummary().getCtpContractCode());
                    }
                }
                if (ctpInputExt.isSetCombOffsetFlag()) {
                    fields.addByte("Fctp_comboffset_flag", (byte)ctpInputExt.getCombOffsetFlag().getValue());
                }
                if (ctpInputExt.isSetMinVolume()) {
                    fields.addInt("Fctp_min_volume", ctpInputExt.getMinVolume());
                }
                if (ctpInputExt.isSetCombHedgeFlag()) {
                    fields.addByte("Fctp_combhedge_flag", (byte)ctpInputExt.getCombHedgeFlag().getValue());
                }
                if (ctpInputExt.isSetTradeDirection()) {
                    fields.addByte("Fctp_input_trade_direction", (byte)ctpInputExt.getTradeDirection().getValue());
                }
            }
            if (order.getOrderInputExt().isSetEsunny3InputExt()) {
                ESunny3OrderInputExt esunny3InputExt = order.getOrderInputExt().getEsunny3InputExt();
                if (esunny3InputExt.isSetContractSummary()) {
                    if (esunny3InputExt.getContractSummary().isSetEsunny3ExchangeCode()) {
                        fields.addString("Fesunny3_exchange_code", esunny3InputExt.getContractSummary().getEsunny3ExchangeCode());
                    }
                    if (esunny3InputExt.getContractSummary().isSetEsunny3CommodityType()) {
                        fields.addShort("Fesunny3_commodity_type", esunny3InputExt.getContractSummary().getEsunny3CommodityType());
                    }
                    if (esunny3InputExt.getContractSummary().isSetEsunny3CommodityCode()) {
                        fields.addString("Fesunny3_commodity_code", esunny3InputExt.getContractSummary().getEsunny3CommodityCode());
                    }
                    if (esunny3InputExt.getContractSummary().isSetEsunny3ContractCode()) {
                        fields.addString("Fesunny3_contract_code", esunny3InputExt.getContractSummary().getEsunny3ContractCode());
                    }
                }
            }
            if (order.getOrderInputExt().isSetEsunny9InputExt()) {
            	ESunny9OrderInputExt esunny9InputExt = order.getOrderInputExt().getEsunny9InputExt();
            	if (esunny9InputExt.isSetContractSummary()) {
            		if (esunny9InputExt.getContractSummary().isSetEsunny9ExchangeNo()) {
            			fields.addString("Fesunny9_exchange_no", esunny9InputExt.getContractSummary().getEsunny9ExchangeNo());
            		}
            		if (esunny9InputExt.getContractSummary().isSetEsunny9CommodityType()) {
            			fields.addShort("Fesunny9_commodity_type", esunny9InputExt.getContractSummary().getEsunny9CommodityType());
            		}
            		if (esunny9InputExt.getContractSummary().isSetEsunny9CommodityNo()) {
            			fields.addString("Fesunny9_commodity_no", esunny9InputExt.getContractSummary().getEsunny9CommodityNo());
            		}
            		if (esunny9InputExt.getContractSummary().isSetEsunny9ContractNo()) {
            			fields.addString("Fesunny9_contract_no", esunny9InputExt.getContractSummary().getEsunny9ContractNo());
            		}
            		if (esunny9InputExt.getContractSummary().isSetEsunny9ContractNo2()) {
            			fields.addString("Fesunny9_contract_no2", esunny9InputExt.getContractSummary().getEsunny9ContractNo2());
            		}
            	}
            }
        }
        
        if (order.isSetStateInfo()) {
            if (order.getStateInfo().isSetCurrentState()) {
                HostingExecOrderState currentState = order.getStateInfo().getCurrentState();
                if (currentState.isSetValue()) {
                    fields.addShort("Fcurrent_state_value", (short)currentState.getValue().getValue());
                }
                if (currentState.isSetTimestampMs()) {
                    fields.addLong("Fcurrent_state_timestampms", currentState.getTimestampMs());
                }
            }
            if (order.getStateInfo().isSetHistoryStates()) {
                List<HostingExecOrderState> historyStates = new LinkedList<HostingExecOrderState>(order.getStateInfo().getHistoryStates());
                String historyStatesStr = ThriftExtJsonUtils.listToJsonTBase(historyStates);
                while(historyStatesStr.length() >= DealingStorageConstants.MAX_STATE_HISTORYS_FIELD_LENGTH) {
                    historyStates.remove(0);
                    historyStatesStr = ThriftExtJsonUtils.listToJsonTBase(historyStates);
                }
                fields.addString("Fhistory_states", historyStatesStr);
            }
            if (order.getStateInfo().isSetStatusMsg()) {
                fields.addString("Fstatus_msg", StringUtils.left(order.getStateInfo().getStatusMsg(), 255));
            }
            if (order.getStateInfo().isSetFailedErrorCode()) {
                fields.addInt("Ffailed_error_code", order.getStateInfo().getFailedErrorCode());
            }
            if (order.getStateInfo().isSetFailedErrorCode()) {
                fields.addInt("Fupside_error_code", order.getStateInfo().getUpsideErrorCode());
            }
            if (order.getStateInfo().isSetUpsideUsefulMsg()) {
                fields.addString("Fupside_useful_msg", StringUtils.left(order.getStateInfo().getUpsideUsefulMsg(), 255));
            }
        }
        
        if (order.isSetRevokeInfo()) {
            if (order.getRevokeInfo().isSetLastRevokeTimestampMs()) {
                fields.addLong("Flast_revoke_timestampms", order.getRevokeInfo().getLastRevokeTimestampMs());
            }
            if (order.getRevokeInfo().isSetLastRevokeFailedErrorCode()) {
                fields.addLong("Flast_revoke_failed_code", order.getRevokeInfo().getLastRevokeFailedErrorCode());
            }
            if (order.getRevokeInfo().isSetLastRevokeUpsideErrorCode()) {
                fields.addLong("Flast_revoke_upside_error_code", order.getRevokeInfo().getLastRevokeUpsideErrorCode());
            }
            if (order.getRevokeInfo().isSetLastRevokeUpsideRejectReason()) {
                fields.addString("Flast_revoke_upside_reject_reason"
                        , StringUtils.left(order.getRevokeInfo().getLastRevokeUpsideRejectReason(), 255));
            }
        }
        
        if (order.isSetDealInfo()) {
            if (order.getDealInfo().isSetDealId()) {
                if (order.getDealInfo().getDealId().isSetCtpDealId()) {
                    CTPDealID ctpDealId = order.getDealInfo().getDealId().getCtpDealId();
                    if (ctpDealId.isSetExchangeId()) {
                        fields.addString("Fctp_exchange_id", ctpDealId.getExchangeId());
                    }
                    if (ctpDealId.isSetOrderSysId()) {
                        fields.addString("Fctp_order_sys_id", ctpDealId.getOrderSysId());
                    }
                }
                if (order.getDealInfo().getDealId().isSetEsunny3DealId()) {
                    ESunny3DealID esunny3DealId = order.getDealInfo().getDealId().getEsunny3DealId();
                    if (esunny3DealId.isSetOrderId()) {
                        fields.addInt("Fesunny3_order_id", esunny3DealId.getOrderId());
                    }
                }
                if (order.getDealInfo().getDealId().isSetEsunny9DealId()) {
                	ESunny9DealID esunny9DealId = order.getDealInfo().getDealId().getEsunny9DealId();
                	if (esunny9DealId.isSetOrderNo()) {
                		fields.addString("Fesunny9_order_no", esunny9DealId.getOrderNo());
                	}
                }
            }
            if (order.getDealInfo().isSetOrderInsertDateTime()) {
                fields.addString("Forder_insert_datetime", order.getDealInfo().getOrderInsertDateTime());
            }
            if (order.getDealInfo().isSetCtpDealInfo()) {
                if (order.getDealInfo().getCtpDealInfo().isSetTradeDirection()) {
                    fields.addByte("Fctp_deal_trade_direction"
                            , (byte)order.getDealInfo().getCtpDealInfo().getTradeDirection().getValue());
                }
                if (order.getDealInfo().getCtpDealInfo().isSetOffsetFlag()) {
                    fields.addByte("Fctp_deal_offset_flag"
                            , (byte)order.getDealInfo().getCtpDealInfo().getOffsetFlag().getValue());
                }
            }
            if (order.getDealInfo().isSetEsunny9DealInfo()) {
            	if (order.getDealInfo().getEsunny9DealInfo().isSetServerFlag()) {
            		fields.addByte("Fesunny9_server_flag", order.getDealInfo().getEsunny9DealInfo().getServerFlag());
            	}
            	if (order.getDealInfo().getEsunny9DealInfo().isSetIsAddOne()) {
            		fields.addByte("Fesunny9_is_add_one", order.getDealInfo().getEsunny9DealInfo().getIsAddOne());
            	}
            }
        }
        
        if (order.isSetTradeSummary()) {
            HostingDealingDBUtils.tradeSummary2PreparedFields(order.getTradeSummary(), fields);
        }
       
        if (order.isSetNotifyStateHandleInfos()) {
            List<HostingUpsideNotifyStateHandleInfo> stateHandleInfos 
                    = new LinkedList<HostingUpsideNotifyStateHandleInfo>(order.getNotifyStateHandleInfos());
            String stateHandlerInfosStr = ThriftExtJsonUtils.listToJsonTBase(stateHandleInfos);
            while(stateHandlerInfosStr.length() >= DealingStorageConstants.MAX_STATE_HANDLEINFOS_FIELD_LENGTH) {
                stateHandleInfos.remove(0);
                stateHandlerInfosStr = ThriftExtJsonUtils.listToJsonTBase(stateHandleInfos);
            }
            fields.addString("Fnotify_state_handle_infos", stateHandlerInfosStr);
        }
        
        if (order.isSetRelateExecOrderId()) {
            fields.addLong("Frelate_exec_order_id", order.getRelateExecOrderId());
        }
        if (order.isSetTtlTimestampMs()) {
            fields.addLong("Fttl_timestampms", order.getTtlTimestampMs());
        }
        if (order.isSetVerifyTimestampMs()) {
            fields.addLong("Fverify_timestampms", order.getVerifyTimestampMs());
        }
	}

	@Override
	public HostingExecOrder fromResultSet(ResultSet rs) throws Exception {
	    HostingExecOrder execOrder = new HostingExecOrder();
	    execOrder.setExecOrderId(rs.getLong("Fexec_order_id"));
	    execOrder.setSubUserId(rs.getInt("Fsub_user_id"));
	    execOrder.setSubAccountId(rs.getLong("Fsub_account_id"));
	    
	    execOrder.setContractSummary(HostingDealingDBUtils.contractSummaryFromRs(rs, true));
	    HostingExecOrderTradeAccountSummary accountSummary = HostingDealingDBUtils.accountSummaryFromRs(rs);
	    execOrder.setAccountSummary(accountSummary);
	    
	    execOrder.setOrderDetail(HostingDealingDBUtils.orderDetailFromRs(rs));
	    
	    HostingExecOrderRef upsideOrderRef = new HostingExecOrderRef();
	    HostingExecOrderInputExt orderInputExt = new HostingExecOrderInputExt();
	    HostingExecOrderDealInfo dealInfo = new HostingExecOrderDealInfo();
	    HostingExecOrderDealID dealID = new HostingExecOrderDealID();
	    if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
	        CTPOrderRef ctpRef = new CTPOrderRef();
	        ctpRef.setFrontID(rs.getInt("Fctp_front_id"));
	        ctpRef.setSessionID(rs.getInt("Fctp_session_id"));
	        ctpRef.setOrderRef(rs.getString("Fctp_order_ref"));
	        upsideOrderRef.setCtpRef(ctpRef);
	        
	        CTPOrderInputExt ctpInputExt = new CTPOrderInputExt();
	        CTPContractSummary ctpContractSummary = new CTPContractSummary();
	        ctpContractSummary.setCtpExchangeCode(rs.getString("Fctp_exchange_code"));
	        ctpContractSummary.setCtpCommodityType(rs.getShort("Fctp_commodity_type"));
	        ctpContractSummary.setCtpCommodityCode(rs.getString("Fctp_commodity_code"));
	        ctpContractSummary.setCtpContractCode(rs.getString("Fctp_contract_code"));
	        ctpInputExt.setContractSummary(ctpContractSummary);
	        ctpInputExt.setCombOffsetFlag(CTPCombOffsetFlagType.findByValue(rs.getByte("Fctp_comboffset_flag")));
	        ctpInputExt.setMinVolume(rs.getInt("Fctp_min_volume"));
	        ctpInputExt.setCombHedgeFlag(CTPCombHedgeFlagType.findByValue(rs.getByte("Fctp_combhedge_flag")));
	        ctpInputExt.setTradeDirection(CTPTradeDirection.findByValue(rs.getByte("Fctp_input_trade_direction")));
	        
	        orderInputExt.setCtpInputExt(ctpInputExt);
	        
	        CTPDealID ctpDealId = new CTPDealID();
	        ctpDealId.setExchangeId(rs.getString("Fctp_exchange_id"));
	        ctpDealId.setOrderSysId(rs.getString("Fctp_order_sys_id"));
	        dealID.setCtpDealId(ctpDealId);
	        
	        HostingExecOrderDealCTPInfo ctpDealInfo = new HostingExecOrderDealCTPInfo();
	        ctpDealInfo.setTradeDirection(CTPTradeDirection.findByValue(rs.getByte("Fctp_deal_trade_direction")));
	        ctpDealInfo.setOffsetFlag(CTPCombOffsetFlagType.findByValue(rs.getByte("Fctp_deal_offset_flag")));
	        
	        dealInfo.setCtpDealInfo(ctpDealInfo);
	    } else if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_3) {
	        ESunny3OrderRef esunny3Ref = new ESunny3OrderRef();
	        esunny3Ref.setSaveString(rs.getString("Fesunny3_save_string"));
	        upsideOrderRef.setEsunny3Ref(esunny3Ref);
	        
	        ESunny3OrderInputExt esunny3InputExt = new ESunny3OrderInputExt();
	        ESunny3ContractSummary esunny3ContractSummary = new ESunny3ContractSummary();
	        esunny3ContractSummary.setEsunny3ExchangeCode(rs.getString("Fesunny3_exchange_code"));
	        esunny3ContractSummary.setEsunny3CommodityType(rs.getShort("Fctp_commodity_type"));
	        esunny3ContractSummary.setEsunny3CommodityCode(rs.getString("Fesunny3_commodity_code"));
	        esunny3ContractSummary.setEsunny3ContractCode(rs.getString("Fesunny3_contract_code"));
	        esunny3InputExt.setContractSummary(esunny3ContractSummary);
	        
	        orderInputExt.setEsunny3InputExt(esunny3InputExt);
	        
	        ESunny3DealID esunny3DealId = new ESunny3DealID();
	        esunny3DealId.setOrderId(rs.getInt("Fesunny3_order_id"));
	        dealID.setEsunny3DealId(esunny3DealId);
	    } else if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_9) {
	    	ESunny9OrderRef esunny9Ref = new ESunny9OrderRef();
	    	esunny9Ref.setRefString(rs.getString("Fesunny9_ref_string"));
	    	upsideOrderRef.setEsunny9Ref(esunny9Ref);
	    	
	    	ESunny9OrderInputExt esunny9InputExt = new ESunny9OrderInputExt();
	    	ESunny9ContractSummary esunny9ContractSummary = new ESunny9ContractSummary();
	    	esunny9ContractSummary.setEsunny9ExchangeNo(rs.getString("Fesunny9_exchange_no"));
	    	esunny9ContractSummary.setEsunny9CommodityType(rs.getShort("Fesunny9_commodity_type"));
	    	esunny9ContractSummary.setEsunny9CommodityNo(rs.getString("Fesunny9_commodity_no"));
	    	esunny9ContractSummary.setEsunny9ContractNo(rs.getString("Fesunny9_contract_no"));
	    	esunny9ContractSummary.setEsunny9ContractNo2(rs.getString("Fesunny9_contract_no2"));
	    	esunny9InputExt.setContractSummary(esunny9ContractSummary);
	    	
	    	orderInputExt.setEsunny9InputExt(esunny9InputExt);
	    	
	    	ESunny9DealID esunny9DealId = new ESunny9DealID();
	    	esunny9DealId.setOrderNo(rs.getString("Fesunny9_order_no"));
	    	dealID.setEsunny9DealId(esunny9DealId);
	    	
	    	HostingExecOrderDealESunny9Info esunny9DealInfo = new HostingExecOrderDealESunny9Info();
	    	esunny9DealInfo.setServerFlag(rs.getByte("Fesunny9_server_flag"));
	    	esunny9DealInfo.setIsAddOne(rs.getByte("Fesunny9_is_add_one"));
	    	dealInfo.setEsunny9DealInfo(esunny9DealInfo);
	    }
	    dealInfo.setOrderInsertDateTime(rs.getString("Forder_insert_datetime"));
	    dealInfo.setDealId(dealID);
	    execOrder.setUpsideOrderRef(upsideOrderRef);
	    execOrder.setOrderInputExt(orderInputExt);
	    execOrder.setDealInfo(dealInfo);
	    
	    HostingExecOrderStateInfo stateInfo = new HostingExecOrderStateInfo();
	    HostingExecOrderState currentState = new HostingExecOrderState();
	    currentState.setValue(HostingExecOrderStateValue.findByValue(rs.getShort("Fcurrent_state_value")));
	    currentState.setTimestampMs(rs.getLong("Fcurrent_state_timestampms"));
	    stateInfo.setCurrentState(currentState);
	    stateInfo.setHistoryStates(
	            ThriftExtJsonUtils.listFromJsonTBase(
	                    rs.getString("Fhistory_states"), HostingExecOrderState.class));
	    stateInfo.setStatusMsg(rs.getString("Fstatus_msg"));
	    stateInfo.setFailedErrorCode(rs.getInt("Ffailed_error_code"));
	    stateInfo.setUpsideErrorCode(rs.getInt("Fupside_error_code"));
	    stateInfo.setUpsideUsefulMsg(rs.getString("Fupside_useful_msg"));
	    execOrder.setStateInfo(stateInfo);
	    
	    HostingExecOrderRevokeInfo revokeInfo = new HostingExecOrderRevokeInfo();
	    revokeInfo.setLastRevokeTimestampMs(rs.getLong("Flast_revoke_timestampms"));
	    revokeInfo.setLastRevokeFailedErrorCode(rs.getInt("Flast_revoke_failed_code"));
	    revokeInfo.setLastRevokeUpsideErrorCode(rs.getInt("Flast_revoke_upside_error_code"));
	    revokeInfo.setLastRevokeUpsideRejectReason(rs.getString("Flast_revoke_upside_reject_reason"));
	    execOrder.setRevokeInfo(revokeInfo);
	    
	    execOrder.setTradeSummary(HostingDealingDBUtils.tradeSummaryFromRs(rs));
	    
	    execOrder.setNotifyStateHandleInfos(
	            ThriftExtJsonUtils.listFromJsonTBase(rs.getString("Fnotify_state_handle_infos")
	                    , HostingUpsideNotifyStateHandleInfo.class));
	    execOrder.setRelateExecOrderId(rs.getLong("Frelate_exec_order_id"));
	    
	    execOrder.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
	    execOrder.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
	    execOrder.setVersion(rs.getInt("Fversion"));
	    execOrder.setSource(rs.getString("Fsource"));
	    execOrder.setTtlTimestampMs(rs.getLong("Fttl_timestampms"));
	    execOrder.setVerifyTimestampMs(rs.getLong("Fverify_timestampms"));
	    
		return execOrder;
	}

	@Override
	protected String getTableName() throws SQLException {
		return "torder";
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder sqlBuilder = new StringBuilder(128);
			sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
			          .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
			          // contractSummary
			          .append("Fsled_contract_id BIGINT NOT NULL DEFAULT 0,")
			          .append("Fsled_contract_code VARCHAR(32) NOT NULL DEFAULT '',")
			          .append("Fsled_commodity_id BIGINT NOT NULL DEFAULT 0,")
			          .append("Fsled_commodity_type SMALLINT NOT NULL DEFAULT 0,")
			          .append("Fsled_commodity_code VARCHAR(32) NOT NULL DEFAULT '',")
			          .append("Fsled_exchange_mic VARCHAR(14) NOT NULL DEFAULT '',")
			          .append("Fsled_contract_related_legs VARCHAR(1024) NOT NULL DEFAULT '',")
			          // accountSummary
			          .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fbroker_id INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Ftech_platform TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          // orderDetail
			          .append("Forder_type TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Flimit_price_isset TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Flimit_price DOUBLE NOT NULL DEFAULT 0.00,")
			          .append("Fquantity INT NOT NULL DEFAULT 0,")
			          .append("Ftrade_direction TINYINT UNSIGNED NOT NULL DEFAULT 127,")
			          .append("Forder_creator_type TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Forder_mode TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Feffective_datetime VARCHAR(24) NOT NULL DEFAULT '',")
			          .append("Forder_condition TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Forder_condition_price_isset TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Forder_condition_price DOUBLE NOT NULL DEFAULT 0.00,")
			          // upsideOrderRef
			          // ctpOrderRef
			          .append("Fctp_front_id INT NOT NULL DEFAULT 0,")
			          .append("Fctp_session_id INT NULL NOT NULL DEFAULT 0,")
			          .append("Fctp_order_ref VARCHAR(16) NOT NULL DEFAULT '',")
			          // esunny3OrderRef
			          .append("Fesunny3_save_string VARCHAR(48) NOT NULL DEFAULT '',")
			          // orderInputExt
			          // ctpInputExt
			          .append("Fctp_exchange_code VARCHAR(14) NOT NULL DEFAULT '',")
			          .append("Fctp_commodity_type SMALLINT NOT NULL DEFAULT 0,")
			          .append("Fctp_commodity_code VARCHAR(32) NOT NULL DEFAULT '',")
			          .append("Fctp_contract_code VARCHAR(32) NOT NULL DEFAULT '',")
			          .append("Fctp_comboffset_flag TINYINT NOT NULL DEFAULT 0,")
			          .append("Fctp_min_volume INT NOT NULL DEFAULT 0,")
			          .append("Fctp_combhedge_flag TINYINT NOT NULL DEFAULT 0,")
			          .append("Fctp_input_trade_direction TINYINT UNSIGNED NOT NULL DEFAULT 127,")
			          // esunny3InputExt
			          .append("Fesunny3_exchange_code VARCHAR(14) NOT NULL DEFAULT '',")
			          .append("Fesunny3_commodity_type SMALLINT NOT NULL DEFAULT 0,")
			          .append("Fesunny3_commodity_code VARCHAR(32) NOT NULL DEFAULT '',")
			          .append("Fesunny3_contract_code VARCHAR(32) NOT NULL DEFAULT '',")
			          // stateInfo
			          .append("Fcurrent_state_value SMALLINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fcurrent_state_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fhistory_states VARCHAR(").append(DealingStorageConstants.MAX_STATE_HISTORYS_FIELD_LENGTH).append(") NOT NULL DEFAULT '',")
			          .append("Fstatus_msg VARCHAR(256) NOT NULL DEFAULT '',")
			          .append("Ffailed_error_code INT NOT NULL DEFAULT 0,")
			          .append("Fupside_error_code INT NOT NULL DEFAULT 0,")
			          .append("Fupside_useful_msg VARCHAR(256) NOT NULL DEFAULT '',")
			          // revokeInfo
			          .append("Flast_revoke_timestampms BIGINT NOT NULL DEFAULT 0,")
			          .append("Flast_revoke_failed_code INT NOT NULL DEFAULT 0,")
			          .append("Flast_revoke_upside_error_code INT NOT NULL DEFAULT 0,")
			          .append("Flast_revoke_upside_reject_reason VARCHAR(256) NOT NULL DEFAULT '',")
			          // dealInfo
			          // dealID, ctpDealId
			          .append("Fctp_exchange_id VARCHAR(14) NOT NULL DEFAULT '',")
			          .append("Fctp_order_sys_id VARCHAR(24) NOT NULL DEFAULT '',")
			          .append("Fctp_deal_trade_direction TINYINT UNSIGNED NOT NULL DEFAULT 127,")
			          .append("Fctp_deal_offset_flag TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          // dealID, esunny3OrderId
			          .append("Fesunny3_order_id INT NOT NULL DEFAULT 0,")
			          // --
			          .append("Forder_insert_datetime VARCHAR(24) NOT NULL DEFAULT '',")
			          // tradeSummary
			          .append("Fupside_trade_total_volume INT NOT NULL DEFAULT 0,")
			          .append("Fupside_trade_resting_volume INT NOT NULL DEFAULT 0, ")
			          .append("Fupside_trade_average_price_isset TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fupside_trade_average_price DOUBLE NOT NULL DEFAULT 0.00,")
			          .append("Ftrade_list_total_volume INT NOT NULL DEFAULT 0,")
			          .append("Ftrade_list_average_price_isset TINYINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Ftrade_list_average_price DOUBLE NOT NULL DEFAULT 0.00,")
			          // notifyStateHandleInfos
			          .append("Fnotify_state_handle_infos VARCHAR(").append(DealingStorageConstants.MAX_STATE_HANDLEINFOS_FIELD_LENGTH).append(") NOT NULL DEFAULT '',")
			          .append("Frelate_exec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          //
			          .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fversion INT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fsource VARCHAR(128) NOT NULL DEFAULT '',")
			          .append("Fttl_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("Fverify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
			          .append("PRIMARY KEY(Fexec_order_id))ENGINE=InnoDB CHARACTER SET UTF8");
			operator.execute(sqlBuilder.toString());
		}
		if (3 == targetVersion) {
			if (!operator.colunmExist(getTableName(), "Fesunny9_exchange_no")) {
				StringBuilder alterSqlBuilder = new StringBuilder(128);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_exchange_no VARCHAR(14) NOT NULL DEFAULT '';");
        		operator.execute(alterSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fesunny9_commodity_type")) {
				StringBuilder alterSqlBuilder = new StringBuilder(128);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_commodity_type SMALLINT NOT NULL DEFAULT 0;");
        		operator.execute(alterSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fesunny9_commodity_no")) {
				StringBuilder alterSqlBuilder = new StringBuilder(128);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_commodity_no VARCHAR(32) NOT NULL DEFAULT '';");
        		operator.execute(alterSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fesunny9_contract_no")) {
				StringBuilder alterSqlBuilder = new StringBuilder(128);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_contract_no VARCHAR(32) NOT NULL DEFAULT '';");
        		operator.execute(alterSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fesunny9_contract_no2")) {
				StringBuilder alterSqlBuilder = new StringBuilder(128);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_contract_no2 VARCHAR(32) NOT NULL DEFAULT '';");
        		operator.execute(alterSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fesunny9_ref_string")) {
				StringBuilder alterSqlBuilder = new StringBuilder(128);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_ref_string VARCHAR(32) NOT NULL DEFAULT '';");
        		operator.execute(alterSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fesunny9_order_no")) {
				StringBuilder alterSqlBuilder = new StringBuilder(128);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_order_no VARCHAR(24) NOT NULL DEFAULT '';");
        		operator.execute(alterSqlBuilder.toString());
			}
		}
		if (4 == targetVersion) {
			if (!operator.colunmExist(getTableName(), "Fesunny9_server_flag")) {
				StringBuilder alterSqlBuilder = new StringBuilder(64);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        				.append(" ADD COLUMN Fesunny9_server_flag TINYINT NOT NULL DEFAULT 0;");
        		operator.execute(alterSqlBuilder.toString());
			}
			if (!operator.colunmExist(getTableName(), "Fesunny9_is_add_one")) {
				StringBuilder alterSqlBuilder = new StringBuilder(64);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        				.append(" ADD COLUMN Fesunny9_is_add_one TINYINT NOT NULL DEFAULT 0;");
        		operator.execute(alterSqlBuilder.toString());
			}
		}
		if (5 == targetVersion) {
		    if (!operator.colunmExist(getTableName(), "Fsub_account_id")) {
		        StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0;");
                operator.execute(alterSqlBuilder.toString());
		    }
		}
	}

}
