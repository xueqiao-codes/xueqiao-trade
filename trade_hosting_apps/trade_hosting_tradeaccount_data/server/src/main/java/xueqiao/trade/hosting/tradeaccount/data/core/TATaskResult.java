package xueqiao.trade.hosting.tradeaccount.data.core;

public enum TATaskResult {
    TASK_SUCCESS,
    TASK_EXCEPTION_OCCURS,

    TASK_FUNDS_EMPTY,   // 资金记录为空

    TASK_SETTLEMENT_CONTENT_INVALID,  // 结算单还未出
    TASK_SETTLEMENT_CONTENT_NOT_SURE, // 空结算单内容不确定有效
    TASK_SETTLEMENT_CONTENT_EXISTED,;   // 结算单已经存在
}
