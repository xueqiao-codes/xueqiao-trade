package xueqiao.trade.hosting.upside.entry;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingUpsideEntryFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("getSubProcessInfos",1);
    putEntry("restartSubProcess",2);
    putEntry("allocOrderRef",10);
    putEntry("orderInsert",11);
    putEntry("orderDelete",12);
    putEntry("syncOrderState",13);
    putEntry("syncOrderTrades",14);
    putEntry("syncOrderStateBatch",15);
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
