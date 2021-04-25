package xueqiao.trade.hosting.dealing.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingDealingFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("clearAll",1);
    putEntry("createUserDeal",2);
    putEntry("revokeDeal",3);
    putEntry("getOrder",4);
    putEntry("getOrderTrades",5);
    putEntry("getTradeRelatedLegs",6);
    putEntry("getRunningExecOrderIdByOrderRef",7);
    putEntry("getRunningExecOrderIdByOrderDealID",8);
    putEntry("getInDealingOrderPage",9);
    putEntry("createExecOrderId",10);
    putEntry("createExecTradeId",11);
    putEntry("createExecTradeLegId",12);
  }

  public static int getUniqueNumber(String functionName) {
    Integer number = sMapping.get(functionName);
    if (number == null) {
      return -1;    }
    return number.intValue();
  }

  private static void putEntry(String functionName, int uniqueNumber) {
    sMapping.put(functionName, uniqueNumber);
  }

}
