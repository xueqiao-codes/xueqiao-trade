package xueqiao.trade.hosting.dealing.core.revoke;

import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;

import java.util.HashMap;
import java.util.Map;

public class RevokeActionMap {
    private static final RevokeAction REJECT_FINISHED_ACTION
            = new RevokeAction(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE.getValue());
    private static final RevokeAction REJECT_DELETED_ACTION
            = new RevokeAction(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE.getValue());
    private static final RevokeAction REJECT_ALREADY_INREVOKING_ACTION
            = new RevokeAction(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_ALREADY_IN_REVOKING.getValue());

    private static final RevokeAction REJECT_USECANCEL_SEND_FAILED_ACTION
            = new RevokeAction(HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED
            , TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_FAILED_USER_CANCELLED.getValue());

    private static Map<Integer, RevokeAction> REVOKE_ACTION_MAPPING = new HashMap<>();

    private static void putRevokeAction(HostingExecOrderStateValue value, RevokeAction action) {
        REVOKE_ACTION_MAPPING.put(value.getValue(), action);
    }

    static {
        putRevokeAction(HostingExecOrderStateValue.ORDER_WAITING_VERIFY
                , new RevokeAction(HostingExecOrderStateValue.ORDER_VERIFY_FAILED
                        , TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_FAILED_USER_CANCELLED.getValue()));

        putRevokeAction(HostingExecOrderStateValue.ORDER_VERIFY_FAILED, REJECT_FINISHED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_WAITING_SLED_SEND
                , REJECT_USECANCEL_SEND_FAILED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_SLED_ALLOC_REF_FINISHED
                , REJECT_USECANCEL_SEND_FAILED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED, REJECT_FINISHED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_SLED_SEND_UNKOWN
                , new RevokeAction(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO.getValue()));

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_REJECTED, REJECT_FINISHED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED
                , new RevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_WAITING_REVOKE));

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RESTING
                , new RevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_WAITING_REVOKE));

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED
                , new RevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE));

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_FINISHED, REJECT_FINISHED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_WAITING_REVOKE
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_WAITING_REVOKE
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_WAITING_REVOKE
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_REVOKE_SEND_UNKOWN
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_REVOKE_SEND_UNKOWN
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_REVOKE_SEND_UNKOWN
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_REVOKE_RECEIVED
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_UPSIDE_DELETED, REJECT_DELETED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER
                , new RevokeAction(HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER_WAITING_REVOKE));

        putRevokeAction(HostingExecOrderStateValue.ORDER_CONDITION_TRIGGEDED, REJECT_FINISHED_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER_WAITING_REVOKE
                , REJECT_ALREADY_INREVOKING_ACTION);

        putRevokeAction(HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER_REVOKE_SEND_UNKOWN
                , REJECT_ALREADY_INREVOKING_ACTION);
    }

    public static RevokeAction get(HostingExecOrderStateValue stateValue) {
        return REVOKE_ACTION_MAPPING.get(stateValue.getValue());
    }
}
