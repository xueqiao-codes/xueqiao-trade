package xueqiao.trade.hosting.storage.dealing;

import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER_REVOKE_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER_WAITING_REVOKE;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_CONDITION_TRIGGEDED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_EXPIRED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_SLED_ALLOC_REF_FINISHED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_SLED_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_DELETED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_FINISHED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_REVOKE_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_REVOKE_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_WAITING_REVOKE;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_REJECTED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RESTING;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_REVOKE_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_WAITING_REVOKE;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_REVOKE_RECEIVED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_VERIFY_FAILED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_WAITING_SLED_SEND;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_WAITING_VERIFY;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_ALREADY_IN_REVOKING;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_FAILED_USER_CANCELLED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_NOT_EXISTED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REVOKE_TIMEOUT;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_SELF_MATCH;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_ORDERREF_EXPIRED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED;
import static xueqiao.trade.hosting.TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;

import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

public class HostingDealingHelper {
    
    private static Map<HostingExecOrderStateValue, String> STATUS_DESCRIPTIONS = 
            new HashMap<HostingExecOrderStateValue, String>();
    
    private static Map<TradeHostingBasicErrorCode, String> ERROR_DESCRIPTIONS =
            new HashMap<TradeHostingBasicErrorCode, String>();
    
    static {
        STATUS_DESCRIPTIONS.put(ORDER_WAITING_VERIFY, "????????????");
        STATUS_DESCRIPTIONS.put(ORDER_VERIFY_FAILED, "????????????");
        STATUS_DESCRIPTIONS.put(ORDER_WAITING_SLED_SEND, "????????????");
        STATUS_DESCRIPTIONS.put(ORDER_SLED_SEND_FAILED, "????????????");
        STATUS_DESCRIPTIONS.put(ORDER_SLED_SEND_UNKOWN, "???????????????");
        STATUS_DESCRIPTIONS.put(ORDER_SLED_ALLOC_REF_FINISHED, "???????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_REJECTED, "????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RECEIVED, "???????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RESTING, "?????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_PARTFINISHED, "????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_FINISHED, "????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RECEIVED_WAITING_REVOKE, "???????????????,????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RESTING_WAITING_REVOKE, "?????????,????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE, "????????????,????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RECEIVED_REVOKE_SEND_UNKOWN, "???????????????,???????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RESTING_REVOKE_SEND_UNKOWN, "?????????,???????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_PARTFINISHED_REVOKE_SEND_UNKOWN, "????????????,???????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_REVOKE_RECEIVED, "?????????????????????");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_DELETED, "?????????");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_NOT_TRIGGER, "?????????");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_TRIGGEDED, "?????????");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_NOT_TRIGGER_WAITING_REVOKE, "?????????,????????????");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_NOT_TRIGGER_REVOKE_SEND_UNKOWN, "?????????,???????????????");
        STATUS_DESCRIPTIONS.put(ORDER_EXPIRED, "???????????????");
        
        ERROR_DESCRIPTIONS.put(ERROR_TRADE_ACCOUNT_NOT_EXISTED, "?????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "?????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_ORDERREF_EXPIRED, "??????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME, "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO, "?????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_NOT_EXISTED, "???????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG, "????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT, "?????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND, "?????????????????????CTP????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED, "????????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_REVOKE_TIMEOUT, "??????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO, "?????????????????????(??????9.0)");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_SELF_MATCH, "?????????????????????");
        
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY, "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_ALREADY_IN_REVOKING, "???????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE, "???????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE, "??????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_FAILED_USER_CANCELLED, "????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT, "?????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND, "???????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE, "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR, "????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED, "???????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED, "???????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH, "??????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR, "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED, "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS, "????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED, "??????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN, "??????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN, "??????????????????");
        
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER, "??????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND, "???????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION, "?????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION, "?????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION, "??????????????????");
    }
    
    public static String generateStatusMsg(
            HostingExecOrderStateValue currentState
            , int failedErrorCode
            , String statusAppendMsg) {
        StringBuilder builder = new StringBuilder(64);
        String statusMsg = STATUS_DESCRIPTIONS.get(currentState);
        if (StringUtils.isEmpty(statusMsg)) {
            builder.append("????????????");
        } else {
            builder.append(statusMsg);
        }
        
        if (StringUtils.isNotBlank(statusAppendMsg)) {
            builder.append(",").append(statusAppendMsg);
            return builder.toString();  
        } 
        
        if (failedErrorCode != 0) {
            builder.append(",");
            
            TradeHostingBasicErrorCode errorCodeEnum = TradeHostingBasicErrorCode.findByValue(failedErrorCode);
            if (errorCodeEnum != null && ERROR_DESCRIPTIONS.containsKey(errorCodeEnum)) {
                builder.append(ERROR_DESCRIPTIONS.get(errorCodeEnum));
            } else {
                builder.append("????????????");
            }
        }
        
        return builder.toString();
    }
    
    public static String getErrorMsg(int failedErrorCode) {
        TradeHostingBasicErrorCode errorCodeEnum = TradeHostingBasicErrorCode.findByValue(failedErrorCode);
        if (errorCodeEnum != null && ERROR_DESCRIPTIONS.containsKey(errorCodeEnum)) {
            return ERROR_DESCRIPTIONS.get(errorCodeEnum);
        } 
        return "????????????";
    }
    
    public static HostingExecOrderContractSummary contractSummaryFromSledContractID(
            long sledContractId, IHostingContractApi contractApi) throws ErrorInfo {
        SledContractDetails contractDetails = contractApi.getContractDetailForSure(sledContractId);
        HostingExecOrderContractSummary contractSummary = new HostingExecOrderContractSummary();
        contractSummary.setSledExchangeMic(contractDetails.getSledCommodity().getExchangeMic());
        contractSummary.setSledCommodityId(contractDetails.getSledCommodity().getSledCommodityId());
        contractSummary.setSledCommodityType((short)contractDetails.getSledCommodity().getSledCommodityType().getValue());
        contractSummary.setSledCommodityCode(contractDetails.getSledCommodity().getSledCommodityCode());
        contractSummary.setSledContractId(sledContractId);
        contractSummary.setSledContractCode(contractDetails.getSledContract().getSledContractCode());
        return contractSummary;
    }
}
