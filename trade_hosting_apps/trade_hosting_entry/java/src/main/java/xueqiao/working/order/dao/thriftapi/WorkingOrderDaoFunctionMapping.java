package xueqiao.working.order.dao.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class WorkingOrderDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("addWorkingOrderStorage",1);
    putEntry("updateWorkingOrderStorage",2);
    putEntry("reqWorkingOrderInfo",3);
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
