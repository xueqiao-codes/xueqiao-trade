package xueqiao.trade.hosting.storage.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingStorageFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("getTraddeAccount",1);
    putEntry("getBrokerAccessEntry",2);
    putEntry("setTradeAccountInvalid",3);
    putEntry("setTradeAccountActive",4);
    putEntry("getAllTradeAccounts",5);
    putEntry("getBrokerAccessEntryFromCloud",6);
    putEntry("createComposeGraphId",8);
    putEntry("createTradeAccountId",9);
    putEntry("createSubAccountId",13);
    putEntry("updateConfig",22);
    putEntry("getMachineId",24);
    putEntry("getHostingSession",25);
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
