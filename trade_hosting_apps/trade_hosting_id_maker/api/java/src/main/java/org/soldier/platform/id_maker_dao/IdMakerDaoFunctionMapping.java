package org.soldier.platform.id_maker_dao;


import java.util.HashMap;
import java.util.Map; 

public class IdMakerDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("allocIds",1);
    putEntry("registerType",2);
    putEntry("updateType",3);
    putEntry("getIdMakerInfoByType",4);
    putEntry("queryIdMakerTypeInfoList",5);
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
