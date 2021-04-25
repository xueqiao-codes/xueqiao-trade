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
        STATUS_DESCRIPTIONS.put(ORDER_WAITING_VERIFY, "等待审核");
        STATUS_DESCRIPTIONS.put(ORDER_VERIFY_FAILED, "审核失败");
        STATUS_DESCRIPTIONS.put(ORDER_WAITING_SLED_SEND, "等待发送");
        STATUS_DESCRIPTIONS.put(ORDER_SLED_SEND_FAILED, "发送失败");
        STATUS_DESCRIPTIONS.put(ORDER_SLED_SEND_UNKOWN, "订单确认中");
        STATUS_DESCRIPTIONS.put(ORDER_SLED_ALLOC_REF_FINISHED, "订单发送中");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_REJECTED, "上手拒绝");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RECEIVED, "上手已接收");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RESTING, "已挂单");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_PARTFINISHED, "部分成交");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_FINISHED, "全部成交");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RECEIVED_WAITING_REVOKE, "上手已接收,等待撤单");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RESTING_WAITING_REVOKE, "已挂单,等待撤单");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE, "部分成交,等待撤单");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RECEIVED_REVOKE_SEND_UNKOWN, "上手已接收,撤单已发送");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_RESTING_REVOKE_SEND_UNKOWN, "已挂单,撤单已发送");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_PARTFINISHED_REVOKE_SEND_UNKOWN, "部分成交,撤单已发送");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_REVOKE_RECEIVED, "上手已接收撤单");
        STATUS_DESCRIPTIONS.put(ORDER_UPSIDE_DELETED, "已撤单");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_NOT_TRIGGER, "未触发");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_TRIGGEDED, "已触发");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_NOT_TRIGGER_WAITING_REVOKE, "未触发,等待撤单");
        STATUS_DESCRIPTIONS.put(ORDER_CONDITION_NOT_TRIGGER_REVOKE_SEND_UNKOWN, "未触发,撤单已发送");
        STATUS_DESCRIPTIONS.put(ORDER_EXPIRED, "订单已过期");
        
        ERROR_DESCRIPTIONS.put(ERROR_TRADE_ACCOUNT_NOT_EXISTED, "关联账号不存在");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "账号进程不可用");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_ORDERREF_EXPIRED, "订单引用过期");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME, "合约不在交易时间");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO, "无上手订单信息");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_NOT_EXISTED, "订单不存在");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG, "系统拒绝");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT, "交易账户正在初始化数据");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND, "无法找到对应的CTP合约信息");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED, "系统切分组合合约信息错误");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_REVOKE_TIMEOUT, "雪橇撤单超时");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO, "无交易账户信息(易胜9.0)");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_SELF_MATCH, "订单构成自成交");
        
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY, "非审核状态的订单");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_ALREADY_IN_REVOKING, "撤单进行中");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE, "订单已撤单");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE, "订单已终结，无法撤单");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_FAILED_USER_CANCELLED, "主动撤单");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT, "无关联资金账户");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND, "无合约映射");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE, "不支持的订单类型");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR, "系统错误");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED, "关联交易账号被禁用");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED, "账号类型下单不支持");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH, "合约映射过多");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR, "合约映射配置错误");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED, "映射合约代码错误");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS, "系统拒绝");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED, "系统计算交易时间失败");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN, "商品禁止交易");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN, "风控策略触发");
        
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER, "上手调用错误");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND, "上手未找到相应订单");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION, "平今持仓量不足");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION, "平昨持仓量不足");
        ERROR_DESCRIPTIONS.put(ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION, "平仓仓位不足");
    }
    
    public static String generateStatusMsg(
            HostingExecOrderStateValue currentState
            , int failedErrorCode
            , String statusAppendMsg) {
        StringBuilder builder = new StringBuilder(64);
        String statusMsg = STATUS_DESCRIPTIONS.get(currentState);
        if (StringUtils.isEmpty(statusMsg)) {
            builder.append("未知状态");
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
                builder.append("系统错误");
            }
        }
        
        return builder.toString();
    }
    
    public static String getErrorMsg(int failedErrorCode) {
        TradeHostingBasicErrorCode errorCodeEnum = TradeHostingBasicErrorCode.findByValue(failedErrorCode);
        if (errorCodeEnum != null && ERROR_DESCRIPTIONS.containsKey(errorCodeEnum)) {
            return ERROR_DESCRIPTIONS.get(errorCodeEnum);
        } 
        return "未知错误";
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
