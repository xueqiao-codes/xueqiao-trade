package xueqiao.company.dao;


import java.util.HashMap;
import java.util.Map; 

public class CompanyDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("addCompany",1);
    putEntry("updateCompany",2);
    putEntry("queryCompanyPage",3);
    putEntry("addCompanyGroup",4);
    putEntry("updateCompanyGroup",5);
    putEntry("deleteCompanyGroup",6);
    putEntry("queryCompanyGroupPage",7);
    putEntry("addCompanyGroupAndSpec",8);
    putEntry("updateCompanyGroupSpec",9);
    putEntry("addCompanyUser",11);
    putEntry("updateCompanyUser",12);
    putEntry("queryCompanySpec",13);
    putEntry("addUser2Group",14);
    putEntry("removeGroupUser",15);
    putEntry("queryCompanyGroupSpec",16);
    putEntry("queryCompanyUser",17);
    putEntry("queryGroupUser",18);
    putEntry("updateGroupUser",19);
    putEntry("queryExpiredCompanyGroupSpec",20);
    putEntry("addCompanyUserEx",21);
    putEntry("updateCompanyUserPassword",22);
    putEntry("submitInitHosingTask",30);
    putEntry("doAfterInitHosting",31);
    putEntry("doUpgradeGroupSpec",32);
    putEntry("queryGroupUserEx",33);
    putEntry("getCollectiveCompany",34);
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
