package xueqiao.trade.hosting.position.statis;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingPositionStatisFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("clearAll",1);
    putEntry("contructCompose",2);
    putEntry("disassembleCompose",3);
    putEntry("batchClosePosition",4);
    putEntry("recoverClosedPosition",5);
    putEntry("assignPosition",6);
    putEntry("mergeToCompose",7);
    putEntry("deleteExpiredStatContractPosition",8);
    putEntry("queryStatPositionSummaryPage",10);
    putEntry("queryStatPositionItemPage",11);
    putEntry("queryCurrentDayStatClosedPositionPage",12);
    putEntry("queryStatClosedPositionDetail",13);
    putEntry("queryArchivedClosedPositionPage",14);
    putEntry("queryArchivedClosedPositionDetail",15);
    putEntry("queryStatPositionSummaryExPage",16);
    putEntry("queryStatPositionUnitPage",17);
    putEntry("queryHistoryClosedPositionPage",18);
    putEntry("queryHistoryClosedPositionDetail",19);
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
