package xueqiao.trade.hosting.position.adjust.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingPositionAdjustFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("manualInputPosition",1);
    putEntry("reqManualInputPosition",3);
    putEntry("reqPositionUnassigned",4);
    putEntry("reqPositionAssigned",5);
    putEntry("assignPosition",6);
    putEntry("reqPositionEditLock",7);
    putEntry("addPositionEditLock",8);
    putEntry("removePositionEditLock",9);
    putEntry("reqPositionVerify",10);
    putEntry("reqPositionDifference",11);
    putEntry("reqSettlementTimeRelateSledReqTime",12);
    putEntry("reqDailyPositionDifference",13);
    putEntry("updateDailyPositionDifferenceNote",14);
    putEntry("clearAll",99);
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
