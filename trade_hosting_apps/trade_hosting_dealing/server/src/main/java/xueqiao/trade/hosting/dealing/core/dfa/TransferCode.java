package xueqiao.trade.hosting.dealing.core.dfa;

public enum TransferCode {
    SLED_SKIP,  // 跳过
    
    SLED_CALL_ALLOCREF_SUCCESS,  // 分配订单引用成功
    SLED_CALL_ALLOCREF_FAILED,   // 分配订单引用失败
    
    SLED_MODIFY_RETRY,           // 订单被重新修正，并要求重试
    
    SLED_SYSTEM_REJECT_SEND,    // 雪橇系统拒绝发送
    SLED_CALL_ORDERINSERT_FAILED_OTHER,  // 下单失败
    SLED_CALL_ORDERINSERT_FAILED_ORDERREF_EXPIRED, // 订单引用过期导致下单失败
    SLED_CALL_ORDERINSERT_FAILED_CONTRACT_ERROR, // 下单失败合约错误
    SLED_CALL_ORDERINSERT_FAILED_SELF_MATCH,  // 自成交导致下单失败
    SLED_CALL_ORDERINSERT_FAILED_CONNECT, // 连接不到上手进程导致失败
    SLED_CALL_ORDERINSERT_UNKOWN,   // 下单发送结果未知， 调用一半出现调用异常，但是无返回结果
    SLED_CALL_ORDERINSERT_SUCCESS,  // 雪橇下单发送成功
    SLED_ORDERINSERT_FAILED_CALLBACK,  //  下单上手返回异常回调
    
    SLED_REVOKE_TIMEOUT,  // 雪橇撤单超时
    
    SLED_CALL_ORDERDELETE_FAILED,      // 撤单失败
    SLED_CALL_ORDERDELETE_UNKOWN,      // 撤单调用结果未知，调用一半出现调用异常，但是无返回结果
    SLED_CALL_ORDERDELETE_SUCCESS,     // 撤单发送成功
    SLED_ORDERDELETE_FAILED_CALLBACK,  // 撤单返回异常回调
    
    RECEIVED_ORDER_NOTFOUND_EVENT,     // 订单未找到事件
    RECEIVED_CONDITION_NOT_TRIGGERED_EVENT, // 条件单未触发
    RECEIVED_CONDITION_TRIGGERED_EVENT,     // 条件单已触发
    RECEIVED_UPSIDE_RECEIVED_EVENT,         // 上手已接收
    RECEIVED_ORDER_RESTING_EVENT,           // 已挂单
    RECEIVED_ORDER_CANCELLED_EVENT,         // 已撤单
    RECEIVED_ORDER_PARTFINISHED_EVENT,      // 订单部分成交
    RECEIVED_ORDER_FINISHED_EVENT,          // 订单全部完成
    RECEIVED_ORDER_REJECT_EVENT,            // 订单拒绝
    RECEIVED_ORDER_CANCEL_RECEIVED_EVENT,   // 上手接收撤单请求
    
   
    
}
