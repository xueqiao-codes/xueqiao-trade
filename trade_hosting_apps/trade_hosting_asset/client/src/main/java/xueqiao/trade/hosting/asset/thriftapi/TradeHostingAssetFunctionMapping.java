package xueqiao.trade.hosting.asset.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingAssetFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("getHostingSledContractPosition",1);
    putEntry("getHostingSubAccountFund",2);
    putEntry("changeSubAccountFund",3);
    putEntry("setSubAccountCreditAmount",4);
    putEntry("getSettlementPositionDetail",5);
    putEntry("getHostingSubAccountMoneyRecord",6);
    putEntry("getAssetPositionTradeDetail",8);
    putEntry("getSettlementPositionTradeDetail",9);
    putEntry("getHostingPositionVolume",10);
    putEntry("getHostingPositionFund",11);
    putEntry("getSubAccountFundHistory",12);
    putEntry("deleteExpiredContractPosition",13);
    putEntry("getTradeAccountPositionTradeDetail",20);
    putEntry("getTradeAccountPosition",21);
    putEntry("assignPosition",22);
    putEntry("removeAllAssetData",99);
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
