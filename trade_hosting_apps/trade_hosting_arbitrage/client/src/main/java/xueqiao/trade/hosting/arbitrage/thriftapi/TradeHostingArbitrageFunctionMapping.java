package xueqiao.trade.hosting.arbitrage.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingArbitrageFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("createXQOrder",1);
    putEntry("cancelXQOrder",2);
    putEntry("suspendXQOrder",3);
    putEntry("resumeXQOrder",4);
    putEntry("getEffectXQOrderIndexPage",5);
    putEntry("getXQOrders",6);
    putEntry("getXQTrades",7);
    putEntry("getXQOrderWithTradeLists",8);
    putEntry("getXQOrderExecDetail",9);
    putEntry("clearAll",10);
    putEntry("filterXQTrades",11);
    putEntry("getXQTradeRelatedItems",12);
    putEntry("getSystemXQComposeLimitOrderSettings",13);
    putEntry("setSystemXQComposeLimitOrderSettings",14);
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
