package xueqiao.trade.hosting.tradeaccount.data;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingTradeAccountDataFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("clearAll",1);
    putEntry("getNowFund",2);
    putEntry("getHisFunds",3);
    putEntry("getSettlementInfos",4);
    putEntry("getNetPositionSummaries",5);
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
