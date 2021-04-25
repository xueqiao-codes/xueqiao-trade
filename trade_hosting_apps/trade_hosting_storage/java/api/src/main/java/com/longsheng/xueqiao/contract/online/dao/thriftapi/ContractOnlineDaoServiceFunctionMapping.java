package com.longsheng.xueqiao.contract.online.dao.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class ContractOnlineDaoServiceFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("reqSledContract",1);
    putEntry("reqSledContractDetail",2);
    putEntry("reqCommodityMapping",11);
    putEntry("reqSledExchange",20);
    putEntry("reqSledCommodity",30);
    putEntry("reqContractVersion",40);
    putEntry("updateContractVersion",42);
    putEntry("removeContractVersion",43);
    putEntry("addDbLocking",50);
    putEntry("removeDbLocking",51);
    putEntry("reqDbLockingInfo",52);
    putEntry("reqSledTradeTime",67);
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
