package xueqiao.trade.hosting.position.fee.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingPositionFeeFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("clearAll",1);
    putEntry("setGeneralMarginSetting",2);
    putEntry("setGeneralCommissionSetting",3);
    putEntry("addSpecMarginSetting",4);
    putEntry("addSpecCommissionSetting",5);
    putEntry("updateSpecMarginSetting",6);
    putEntry("updateSpecCommissionSetting",7);
    putEntry("deleteSpecMarginSetting",8);
    putEntry("deleteSpecCommissionSetting",9);
    putEntry("queryXQGeneralMarginSettings",20);
    putEntry("queryXQGeneralCommissionSettings",21);
    putEntry("queryXQSpecMarginSettingPage",22);
    putEntry("queryXQSpecCommissionSettingPage",23);
    putEntry("queryUpsideContractMarginPage",24);
    putEntry("queryUpsideContractCommissionPage",25);
    putEntry("queryXQContractMarginPage",26);
    putEntry("queryXQContractCommissionPage",27);
    putEntry("queryPositionFee",28);
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
