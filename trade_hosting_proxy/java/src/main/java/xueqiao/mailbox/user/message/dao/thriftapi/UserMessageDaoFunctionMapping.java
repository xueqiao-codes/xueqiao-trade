package xueqiao.mailbox.user.message.dao.thriftapi;


import java.util.HashMap;
import java.util.Map; 

public class UserMessageDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("addUserMessage",1);
    putEntry("reqUserMessage",2);
    putEntry("markAsRead",3);
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
