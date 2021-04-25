package com.longsheng.xueqiao.goose.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class GooseAoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("sendVerifyCode",1);
    putEntry("verifySmsCode",2);
    putEntry("sendUserNotificationSms",5);
    putEntry("sendMaintenanceNotificationSms",6);
    putEntry("sendMailboxMessage",7);
    putEntry("verifySmsCodeForClear",8);
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
