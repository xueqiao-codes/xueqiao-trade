package xueqiao.trade.hosting.risk.manager.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingRiskManagerFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("getAllSupportedItems",1);
    putEntry("getRiskRuleJointVersion",2);
    putEntry("getRiskRuleJoint",3);
    putEntry("batchSetSupportedItems",4);
    putEntry("batchSetTradedCommodityItems",5);
    putEntry("batchSetGlobalRules",6);
    putEntry("batchSetCommodityRules",7);
    putEntry("setRiskEnabled",8);
    putEntry("getRiskFrameDataInfo",9);
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
