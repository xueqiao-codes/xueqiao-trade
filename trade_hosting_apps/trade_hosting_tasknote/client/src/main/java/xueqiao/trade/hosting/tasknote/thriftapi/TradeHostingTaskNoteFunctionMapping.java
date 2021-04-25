package xueqiao.trade.hosting.tasknote.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingTaskNoteFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("getTaskNotePage",1);
    putEntry("delTaskNote",2);
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
