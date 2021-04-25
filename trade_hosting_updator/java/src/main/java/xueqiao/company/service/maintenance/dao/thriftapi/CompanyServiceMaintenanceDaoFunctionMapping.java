package xueqiao.company.service.maintenance.dao.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class CompanyServiceMaintenanceDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("addServiceMaintenance",1);
    putEntry("updateServiceMaintenance",2);
    putEntry("reqServiceMaintenance",3);
    putEntry("reqMaintenanceHistory",4);
    putEntry("addScheduleOperateDetail",5);
    putEntry("updateScheduleOperateDetail",6);
    putEntry("reqScheduleOperateDetail",7);
    putEntry("removeScheduleOperateDetail",8);
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
