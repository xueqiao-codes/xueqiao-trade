package xueqiao.trade.hosting.terminal.ao;


import java.util.HashMap;
import java.util.Map; 

public class TradeHostingTerminalAoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("getHostingUserPage",4);
    putEntry("heartBeat",6);
    putEntry("logout",7);
    putEntry("getComposeViewDetails",8);
    putEntry("changeComposeViewPrecisionNumber",9);
    putEntry("createComposeGraph",10);
    putEntry("delComposeView",11);
    putEntry("getComposeViewDetailPage",12);
    putEntry("getSameComposeGraphsPage",13);
    putEntry("addComposeViewBySearch",14);
    putEntry("subscribeComposeViewQuotation",15);
    putEntry("unSubscribeComposeViewQuotation",16);
    putEntry("changeComposeViewAliasName",17);
    putEntry("getComposeGraphs",18);
    putEntry("addComposeViewByShare",19);
    putEntry("addTradeAccount",20);
    putEntry("disableTradeAccount",21);
    putEntry("getTradeAccountPage",22);
    putEntry("enableTradeAccount",23);
    putEntry("updateTradeAccountInfo",24);
    putEntry("rmTradeAccount",25);
    putEntry("getPersonalUserTradeAccount",26);
    putEntry("getHostingOrderRouteTree",27);
    putEntry("updateHostingOrderRouteTree",28);
    putEntry("getHostingOrderRouteTreeVersion",29);
    putEntry("getPersonalUserHostingOrderRouteTree",30);
    putEntry("createXQOrder",35);
    putEntry("batchSuspendXQOrders",36);
    putEntry("batchResumeXQOrders",37);
    putEntry("batchCancelXQOrders",38);
    putEntry("getEffectXQOrderWithTradeListPage",39);
    putEntry("getXQOrderWithTradeLists",40);
    putEntry("getXQOrderExecDetail",41);
    putEntry("getXQOrderHisPage",50);
    putEntry("getXQTradeHisPage",51);
    putEntry("getUserSetting",60);
    putEntry("updateUserSetting",61);
    putEntry("getUserSettingVersion",62);
    putEntry("getSAWRUTListPage",65);
    putEntry("getSARUTBySubAccountId",66);
    putEntry("getSARUTBySubUserId",67);
    putEntry("assignSubAccountRelatedUsers",68);
    putEntry("renameSubAccount",72);
    putEntry("createSubAccount",73);
    putEntry("getHostingSledContractPosition",78);
    putEntry("getHostingSubAccountFund",79);
    putEntry("changeSubAccountFund",80);
    putEntry("setSubAccountCreditAmount",81);
    putEntry("getAssetPositionTradeDetail",82);
    putEntry("getHostingSubAccountMoneyRecord",83);
    putEntry("getSubAccountFundHistory",84);
    putEntry("getSubAccountPositionHistory",85);
    putEntry("getSubAccountPositionHistoryTradeDetail",86);
    putEntry("deleteExpiredContractPosition",87);
    putEntry("getTradeAccountFundNow",90);
    putEntry("getTradeAccountFundHis",91);
    putEntry("getTradeAccountSettlementInfos",92);
    putEntry("getTradeAccountSettlementInfosWithRelatedTime",95);
    putEntry("getTradeAccountPositionTradeDetail",101);
    putEntry("reqPositionVerify",102);
    putEntry("reqPositionDifference",103);
    putEntry("manualInputPosition",104);
    putEntry("reqPositionUnassigned",105);
    putEntry("assignPosition",106);
    putEntry("reqPositionEditLock",107);
    putEntry("addPositionEditLock",108);
    putEntry("removePositionEditLock",109);
    putEntry("reqDailyPositionDifference",110);
    putEntry("updateDailyPositionDifferenceNote",111);
    putEntry("reqPositionAssigned",112);
    putEntry("contructCompose",120);
    putEntry("disassembleCompose",121);
    putEntry("batchClosePosition",122);
    putEntry("recoverClosedPosition",123);
    putEntry("mergeToCompose",124);
    putEntry("deleteExpiredStatContractPosition",125);
    putEntry("queryStatPositionSummaryPage",130);
    putEntry("queryStatPositionItemPage",131);
    putEntry("queryCurrentDayStatClosedPositionPage",132);
    putEntry("queryStatClosedPositionDetail",133);
    putEntry("queryArchivedClosedPositionPage",134);
    putEntry("queryArchivedClosedPositionDetail",135);
    putEntry("queryStatPositionSummaryExPage",136);
    putEntry("queryStatPositionUnitPage",137);
    putEntry("queryHistoryClosedPositionPage",138);
    putEntry("queryHistoryClosedPositionDetail",139);
    putEntry("getXQTradeLameTaskNotePage",150);
    putEntry("batchDeleteXQTradeLameTaskNotes",151);
    putEntry("queryMailBoxMessage",160);
    putEntry("markMessageAsRead",161);
    putEntry("getAllSupportedItems",170);
    putEntry("getRiskRuleJointVersion",171);
    putEntry("getRiskRuleJoint",172);
    putEntry("batchSetSupportedItems",173);
    putEntry("batchSetTradedCommodityItems",174);
    putEntry("batchSetGlobalRules",175);
    putEntry("batchSetCommodityRules",176);
    putEntry("setRiskEnabled",177);
    putEntry("getRiskFrameDataInfo",178);
    putEntry("setGeneralMarginSetting",190);
    putEntry("setGeneralCommissionSetting",191);
    putEntry("addSpecMarginSetting",192);
    putEntry("addSpecCommissionSetting",193);
    putEntry("updateSpecMarginSetting",194);
    putEntry("updateSpecCommissionSetting",195);
    putEntry("deleteSpecMarginSetting",196);
    putEntry("deleteSpecCommissionSetting",197);
    putEntry("queryXQGeneralMarginSettings",200);
    putEntry("queryXQGeneralCommissionSettings",201);
    putEntry("queryXQSpecMarginSettingPage",202);
    putEntry("queryXQSpecCommissionSettingPage",203);
    putEntry("queryUpsideContractMarginPage",204);
    putEntry("queryUpsideContractCommissionPage",205);
    putEntry("queryXQContractMarginPage",206);
    putEntry("queryXQContractCommissionPage",207);
    putEntry("addAssetAccountWorkingOrder",215);
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
