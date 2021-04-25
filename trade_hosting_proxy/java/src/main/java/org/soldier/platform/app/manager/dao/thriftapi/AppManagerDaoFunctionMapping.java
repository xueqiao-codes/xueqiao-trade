package org.soldier.platform.app.manager.dao.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class AppManagerDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("reqProject",1);
    putEntry("addProject",2);
    putEntry("updateProject",3);
    putEntry("removeProject",4);
    putEntry("reqApp",5);
    putEntry("addApp",6);
    putEntry("updateApp",7);
    putEntry("removeApp",8);
    putEntry("reqAppVersion",9);
    putEntry("addAppVersion",10);
    putEntry("updateAppVersion",11);
    putEntry("removeAppVersion",12);
    putEntry("reqServerAppSupport",13);
    putEntry("addServerAppSupport",14);
    putEntry("updateServerAppSupport",15);
    putEntry("removeServerAppSupport",16);
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
