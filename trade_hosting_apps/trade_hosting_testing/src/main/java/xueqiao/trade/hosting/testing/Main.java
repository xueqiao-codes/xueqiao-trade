package xueqiao.trade.hosting.testing;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingExecOrderCreatorType;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecOrderMode;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingExecOrderType;
import xueqiao.trade.hosting.events.ExecOrderGuardEvent;
import xueqiao.trade.hosting.events.ExecOrderGuardType;
import xueqiao.trade.hosting.events.TaskNoteGuardEvent;
import xueqiao.trade.hosting.events.TaskNoteGuardEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.quot.comb.sdk.QuotCombDispatcher;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;

public class Main {
    public static long addOrderTest(long sledContractId, double limitPrice) throws ErrorInfo {
//    	HostingExecOrderContractSummary contractSummary = new HostingExecOrderContractSummary();
//        contractSummary.setSledExchangeMic("SHFE");
//        contractSummary.setSledCommodityId(1);
//        contractSummary.setSledCommodityType((short)'F');
//        contractSummary.setSledCommodityCode("cu");
//        contractSummary.setSledContractId(1);
//        contractSummary.setSledContractCode("1806");
    
        HostingExecOrderDetail orderDetail = new HostingExecOrderDetail();
        orderDetail.setOrderType(HostingExecOrderType.ORDER_LIMIT_PRICE);
        orderDetail.setTradeDirection(HostingExecOrderTradeDirection.ORDER_BUY);
        orderDetail.setQuantity(3);
        orderDetail.setLimitPrice(limitPrice);
        orderDetail.setOrderCreatorType(HostingExecOrderCreatorType.ORDER_ARTIFICAL);
        orderDetail.setOrderMode(HostingExecOrderMode.ORDER_GFD);
    
        IHostingDealingApi dealingApi = Globals.getInstance().queryInterface(IHostingDealingApi.class);
    
        long orderId = dealingApi.createOrderId();
        dealingApi.createUserDeal(1, 1054, orderId, sledContractId, orderDetail, "TEST");
        return orderId;
    }
    
    public static void revokeOrderTest(long execOrderId) throws ErrorInfo {
    	IHostingDealingApi dealingApi = Globals.getInstance().queryInterface(IHostingDealingApi.class);
    	dealingApi.revokeDeal(execOrderId);
    }
    
    public static void guardTest() throws ErrorInfo {
//    	ExecOrderGuardEvent guardEvent = new ExecOrderGuardEvent();
//    	guardEvent.setGuardExecOrderId(1363);
//    	guardEvent.setGuardType(ExecOrderGuardType.GUARD_ORDER_CREATED);

        TaskNoteGuardEvent guardEvent = new TaskNoteGuardEvent();
        guardEvent.setGuardType(TaskNoteGuardEventType.GUARD_TASK_NOTE_DELETED);
        guardEvent.setNoteKey(new HostingTaskNoteKey().setKey1(1040).setKey2(21004).setKey3(""));
        guardEvent.setNoteType(HostingTaskNoteType.XQ_TRADE_LAME);
    	
    	HostingMessageContext.Global().getSenderAgent().prepareGuardMessage(guardEvent, new TimeoutGuardPolicy().setTimeoutSeconds(2));
    }
	
    public static void main(String[] args) throws Exception {
        AppLog.init("apps/trade_hosting_testing");
        try {
            StorageInitialzer.initApis();
            
            if (args.length >= 1) {
                if (args[0].equals("addOrder")) {
                    if (args.length >= 3) {
                        addOrderTest(Long.parseLong(args[1]), Double.parseDouble(args[2]));
                    } else {
                        System.err.println("please input contract id and limit price");
                    }
                } else if (args[0].equals("revokeOrder")) {
                    if (args.length >= 2) {
                        revokeOrderTest(Long.parseLong(args[1]));
                    } else {
                        System.err.println("please input order id");
                    }
                } else if (args[0].equals("dbPerformance")) {
                    if (args.length >= 0) {
                        DBPerformanceTest.startTest(Integer.parseInt(args[1]));
                    } else {
                        System.err.println("please input test count");
                    }
                } else if (args[0].equalsIgnoreCase("guardTest")) {
                    guardTest();
                }else if (args[0].equalsIgnoreCase("quotCombClientTest")) {
                    QuotCombClientTest.runTest();
                } else if (args[0].equalsIgnoreCase("quotCombDispatcherTest")) {
                    QuotCombDispatcherTest.runTest();
            	} else {
            		System.err.println("unkown command " + args[0]);
            	}
            } else {
            	System.err.println("please input command");
            }
            
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
