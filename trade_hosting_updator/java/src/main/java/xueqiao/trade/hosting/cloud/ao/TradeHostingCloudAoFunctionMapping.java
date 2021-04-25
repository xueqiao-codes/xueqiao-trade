package xueqiao.trade.hosting.cloud.ao;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingCloudAoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("initHosting",1);
    putEntry("getHostingInfo",2);
    putEntry("resetHosting",3);
    putEntry("registerHostingUser",7);
    putEntry("updateHostingUser",8);
    putEntry("disableHostingUser",9);
    putEntry("getHostingUserPage",10);
    putEntry("enableHostingUser",11);
    putEntry("login",20);
    putEntry("checkSession",21);
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
