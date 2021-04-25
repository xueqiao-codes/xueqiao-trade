package xueqiao.trade.hosting.proxy;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingProxyFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("login",1);
    putEntry("fakeLogin",3);
    putEntry("updateCompanyUserPassword",5);
    putEntry("checkSession",20);
    putEntry("queryUpdateInfo",30);
    putEntry("queryMailBoxMessage",35);
    putEntry("markMessageAsRead",36);
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
