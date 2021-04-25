#!/usr/bin/env python
# -*- coding: utf-8 -*-  
# test for service TradeHostingTerminalAo
import random
import sys
from xueqiao.trade.hosting.arbitrage.thriftapi.ttypes import *
from xueqiao.trade.hosting.position.statis.ttypes import *
from xueqiao.trade.hosting.ttypes import *
from xueqiao.trade.hosting.terminal.ao.ttypes import *
from xueqiao.trade.hosting.terminal.ao.constants import *
from xueqiao.trade.hosting.terminal.ao.client.TradeHostingTerminalAoStub import *

from page.ttypes import  *

reload(sys)
sys.setdefaultencoding('utf-8')

stub=TradeHostingTerminalAoStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...

stub.setPeerAddr("172.27.212.120")

landingInfo = LandingInfo()
landingInfo.machineId = 1001
landingInfo.subUserId = 58
# 
newUser = HostingUser()
newUser.loginName = "pengguangbo"
newUser.loginPasswd = "123456"
#newUser.email = "wangli-whu@163.com"
#newUser.phone = "18888888888"
#   
#print(stub.registerHostingUser(0, 3000, landingInfo, newUser));


pageOption = IndexedPageOption()
pageOption.needTotalCount = True
pageOption.pageIndex = 0
pageOption.pageSize = 20

# updateUser = HostingUser()
# updateUser.subUserId = 2
# updateUser.email = "wangli-whu@163.com"
# updateUser.phone = "027-88888888"
# 
# print(stub.updateHostingUser(0, 3000, landingInfo, updateUser))

#print(stub.disableHostingUser(0, 3000, landingInfo, 7))
#print(stub.enableHostingUser(0, 3000, landingInfo, 7))


newGraph = HostingComposeGraph()
newGraph.name="hsi1809-hsi1806"
newGraph.formular = "A-B"
   
ALeg = HostingComposeLeg()
ALeg.variableName = "A"
ALeg.sledContractId = 37254
ALeg.quantity=1
ALeg.legTradeDirection = HostingComposeLegTradeDirection.COMPOSE_LEG_BUY
    
BLeg = HostingComposeLeg()
BLeg.variableName="B"
BLeg.sledContractId = 37253
BLeg.quantity = 1
BLeg.legTradeDirection = HostingComposeLegTradeDirection.COMPOSE_LEG_SELL
  
newGraph.legs = {
   "A" : ALeg,
   "B" : BLeg
}
  
#print(stub.createComposeGraph(0, 3000, landingInfo, newGraph, "jd1810-2jd1808"))

#print(stub.changeComposeViewAliasName(0, 3000, landingInfo, 3142, "hsi1809-hsi1806"))

#print(stub.delComposeView(0, 3000, landingInfo, 3125))

#print(stub.getSameComposeGraphsPage(0, 3000, landingInfo, newGraph, pageOption))

#print(stub.addComposeViewBySearch(0, 3000, landingInfo, 3125, "cpntndmaqulvvofoauzuptepkcxujkleuyemwpxgbegywatvrxvmqbmgmpprem", "test"))

option = QueryHostingComposeViewDetailOption()
#option.aliasNamePartical = "123"
option.composeGraphId = 1225
#print(stub.getComposeViewDetailPage(0, 3000, landingInfo, option, pageOption))

#print(stub.unSubscribeComposeViewQuotation(0, 3000, landingInfo, 3142))

# updateGraph = HostingComposeGraph()
# updateGraph.composeGraphId = 1081
# updateGraph.name = "MTest(A-B)"
# 
# AUpdateLeg = HostingComposeLeg()
# AUpdateLeg.plusTick = 5
# AUpdateLeg.order = 1
# 
# BUpdateLeg = HostingComposeLeg()
# BUpdateLeg.order = 2
# 
# 
# updateGraph.legs = {
#     "A" : AUpdateLeg,
#     "B" : BUpdateLeg
# }
# 
# print(stub.updateComposeGraph(0, 3000, landingInfo, updateGraph))

  
#print(stub.getComposeGraphPage(0, 3000, landingInfo, option, 0, 50))
# 
# 
# # print(stub.subscribeComposeQuotation(0, 3000, landingInfo, 1081))
# print(stub.unSubscribeComposeQuotation(0, 3000, landingInfo, 1081))
# print(stub.rmComposeGraph(0, 3000, landingInfo, 1081))


newTradeAccount = HostingTradeAccount()
newTradeAccount.tradeBrokerId = 1147
newTradeAccount.tradeBrokerAccessId = 1172
newTradeAccount.loginUserName = "Q443854478"
newTradeAccount.loginPassword = "550455"
newTradeAccount.tradeAccountRemark = "王立易胜9.0模拟";
newTradeAccount.accountProperties = {
    "ESUNNY9_AUTHCODE" : "67EA896065459BECDFDB924B29CB7DF1946CED32E26C1EAC946CED32E26C1EAC946CED32E26C1EAC946CED32E26C1EAC5211AF9FEE541DDE41BCBAB68D525B0D111A0884D847D57163FF7F329FA574E7946CED32E26C1EAC946CED32E26C1EAC733827B0CE853869ABD9B8F170E14F8847D3EA0BF4E191F5D97B3DFE4CCB1F01842DD2B3EA2F4B20CAD19B8347719B7E20EA1FA7A3D1BFEFF22290F4B5C43E6C520ED5A40EC1D50ACDF342F46A92CCF87AEE6D73542C42EC17818349C7DEDAB0E4DB16977714F873D505029E27B3D57EB92D5BEDA0A710197EB67F94BB1892B30F58A3F211D9C3B3839BE2D73FD08DD776B9188654853DDA57675EBB7D6FBBFC"
}

updateTradeAccount = HostingTradeAccount()
updateTradeAccount.tradeAccountId = 2201
updateTradeAccount.loginUserName = "14"
updateTradeAccount.loginPassword = "123456"
#updateTradeAccount.tradeAccountRemark = "CTP_SIM_TEST"

#print(stub.addTradeAccount(0, 3000, landingInfo, newTradeAccount))
#print(stub.disableTradeAccount(0, 3000, landingInfo, 2201))
#print(stub.enableTradeAccount(0, 3000, landingInfo, 2201))
#print(stub.updateTradeAccountInfo(0, 3000, landingInfo, updateTradeAccount))
#print(stub.rmTradeAccount(0, 3000, landingInfo, 2201))


#print(stub.getHostingOrderRouteTreeVersion(0, 3000, landingInfo, landingInfo.subUserId))

orderRouteTree = HostingOrderRouteTree()
orderRouteTree.configVersion = 7

exchangeNode1 = HostingOrderRouteExchangeNode()
exchangeNode1.sledExchangeCode = "XDCE"
exchangeNode1.relatedInfo = HostingOrderRouteRelatedInfo()
exchangeNode1.relatedInfo.mainTradeAccountId = 1872

exchangeNode2 = HostingOrderRouteExchangeNode()
exchangeNode2.sledExchangeCode = "XSGE"
exchangeNode2.relatedInfo = HostingOrderRouteRelatedInfo()
exchangeNode2.relatedInfo.mainTradeAccountId = 1941

exchangeNode3 = HostingOrderRouteExchangeNode()
exchangeNode3.sledExchangeCode = "XZCE"
exchangeNode3.relatedInfo = HostingOrderRouteRelatedInfo()
exchangeNode3.relatedInfo.mainTradeAccountId = 1873


orderRouteTree.subExchangeNodes = {
    "XDCE": exchangeNode1,
    "XSGE": exchangeNode2,
    "XZCE": exchangeNode3
}

#print(stub.updateHostingOrderRouteTree(0, 3000, landingInfo, landingInfo.subUserId, orderRouteTree))
#print(stub.getHostingOrderRouteTree(0, 3000, landingInfo, landingInfo.subUserId))

userSetting = HostingUserSetting()
userSetting.version = 1
userSetting.content = "this is test"

#print(stub.updateUserSetting(0, 3000, landingInfo, "TEST", userSetting))
#print(stub.getUserSettingVersion(0, 3000, landingInfo, "TEST"))
#print(stub.getUserSetting(0, 3000, landingInfo, "123"))

qryOption = QueryHostingSAWRUItemListOption()
#print(stub.getSAWRUTListPage(0, 3000, landingInfo, qryOption, pageOption))

newSubAccount = HostingSubAccount()
newSubAccount.subAccountName = "王立资金"
#print(stub.createSubAccount(0, 3000, landingInfo, newSubAccount))

#print(stub.renameSubAccount(0, 3000, landingInfo, 1054, "王立资金"))

#print(stub.inSubAccountMoney(0, 3000, landingInfo, 1054, 100000000, "next_in_key"));
#print(stub.outSubAccountMoney(0, 3000, landingInfo, 1054, 50000000, "get_out_ley"));

#qryOption = QueryHostingSubAccountMoneyRecordOption()
#qryOption.subAccountId = 1054
#print(stub.getSubAccountMoneyRecordPage(0, 3000, landingInfo, qryOption, pageOption))

#print(stub.assignSubAccountRelatedUsers(0, 3000, landingInfo, 1051, set([1, 2, 3]), set([])))
#print(stub.getSARUTBySubAccountId(0, 3000, landingInfo, set([1051, 1054])))
#print(stub.getSARUTBySubUserId(0, 3000, landingInfo, set([1,2,3,4])))

def createXQContractLimitOrderTest():
    subAccountId = 1051
    xqOrderId = "1001_1051_123123123190_3"
    xqOrderType = HostingXQOrderType.XQ_ORDER_CONTRACT_LIMIT
    xqOrderTarget = HostingXQTarget()
    xqOrderTarget.targetType = HostingXQTargetType.CONTRACT_TARGET
    xqOrderTarget.targetKey = "35636"
    xqOrderDetail = HostingXQOrderDetail()
    xqOrderDetail.contractLimitOrderDetail = HostingXQContractLimitOrderDetail()
    xqOrderDetail.contractLimitOrderDetail.direction = HostingXQTradeDirection.XQ_BUY
    xqOrderDetail.contractLimitOrderDetail.limitPrice = 97.59
    xqOrderDetail.contractLimitOrderDetail.quantity = 5
    print(stub.createXQOrder(0, 3000, landingInfo, subAccountId, xqOrderId, xqOrderType, xqOrderTarget, xqOrderDetail))
    

def createXQComposeLimitOrderParallelTest():
    subAccountId = 1001
    xqOrderId = "1001_" + str(subAccountId) + "_" + str(landingInfo.subUserId) + "_6_2"
    
    xqOrderType = HostingXQOrderType.XQ_ORDER_COMPOSE_LIMIT
    xqOrderTarget = HostingXQTarget()
    xqOrderTarget.targetType =  HostingXQTargetType.COMPOSE_TARGET
    xqOrderTarget.targetKey = "1202"
    xqOrderDetail = HostingXQOrderDetail()
    xqOrderDetail.composeLimitOrderDetail = HostingXQComposeLimitOrderDetail()
    xqOrderDetail.composeLimitOrderDetail.direction = HostingXQTradeDirection.XQ_SELL
    xqOrderDetail.composeLimitOrderDetail.limitPrice = 70
    xqOrderDetail.composeLimitOrderDetail.execParams = HostingXQComposeLimitOrderExecParams()
    xqOrderDetail.composeLimitOrderDetail.execParams.execType = HostingXQComposeLimitOrderExecType.PARALLEL_LEG
    xqOrderDetail.composeLimitOrderDetail.execParams.execEveryQty = 2
    xqOrderDetail.composeLimitOrderDetail.quantity = 5
    xqOrderDetail.composeLimitOrderDetail.effectDate = HostingXQEffectDate()
    xqOrderDetail.composeLimitOrderDetail.effectDate.type = HostingXQEffectDateType.XQ_EFFECT_FOREVER
    
    print(stub.createXQOrder(0, 3000, landingInfo, subAccountId, xqOrderId, xqOrderType, xqOrderTarget, xqOrderDetail))

def createXQComposeLimitOrderLegByTest():
    subAccountId = 1001
    xqOrderId = "1001_" + str(subAccountId) + "_" + str(landingInfo.subUserId) + "_6_8"

    xqOrderType = HostingXQOrderType.XQ_ORDER_COMPOSE_LIMIT
    xqOrderTarget = HostingXQTarget()
    xqOrderTarget.targetType =  HostingXQTargetType.COMPOSE_TARGET
    xqOrderTarget.targetKey = "1202"
    xqOrderDetail = HostingXQOrderDetail()
    xqOrderDetail.composeLimitOrderDetail = HostingXQComposeLimitOrderDetail()
    xqOrderDetail.composeLimitOrderDetail.direction = HostingXQTradeDirection.XQ_BUY
    xqOrderDetail.composeLimitOrderDetail.limitPrice = 70
    xqOrderDetail.composeLimitOrderDetail.execParams = HostingXQComposeLimitOrderExecParams()
    xqOrderDetail.composeLimitOrderDetail.execParams.execType = HostingXQComposeLimitOrderExecType.LEG_BY_LEG
    xqOrderDetail.composeLimitOrderDetail.execParams.execEveryQty = 2
    xqOrderDetail.composeLimitOrderDetail.execParams.execLegByParams = HostingXQComposeLimitOrderFirstLegChooseParam()
    xqOrderDetail.composeLimitOrderDetail.execParams.execLegByParams.firstLegChooseParam \
        = HostingXQComposeLimitOrderFirstLegChooseParam()
    #xqOrderDetail.composeLimitOrderDetail.execParams.firstLegChooseParam.mode =
    xqOrderDetail.composeLimitOrderDetail.quantity = 5
    xqOrderDetail.composeLimitOrderDetail.effectDate = HostingXQEffectDate()
    xqOrderDetail.composeLimitOrderDetail.effectDate.type = HostingXQEffectDateType.XQ_EFFECT_FOREVER

    print(stub.createXQOrder(0, 3000, landingInfo, subAccountId, xqOrderId, xqOrderType, xqOrderTarget, xqOrderDetail))
    
def createXQConditionOrderTest():
    subAccountId = 1051
    xqOrderId = "1001_1051_1_123123123_3"
    xqOrderType = HostingXQOrderType.XQ_ORDER_CONDITION
    xqOrderTarget = HostingXQTarget()
    xqOrderTarget.targetType = HostingXQTargetType.CONTRACT_TARGET
    xqOrderTarget.targetKey = "35636"
    xqOrderDetail = HostingXQOrderDetail()
    xqOrderDetail.conditionOrderDetail = HostingXQConditionOrderDetail()
    xqOrderDetail.conditionOrderDetail.effectDate = HostingXQEffectDate()
    xqOrderDetail.conditionOrderDetail.effectDate.type = HostingXQEffectDateType.XQ_EFFECT_FOREVER
    xqOrderDetail.conditionOrderDetail.effectDate.startEffectTimestampS = 0
    xqOrderDetail.conditionOrderDetail.effectDate.endEffectTimestampS = 0
    xqOrderDetail.conditionOrderDetail.label = HostingXQConditionOrderLabel.LABEL_STOP_LOST_SELL
    condition1 = HostingXQCondition()
    condition1.conditionTrigger = HostingXQConditionTrigger()
    condition1.conditionTrigger.triggerPriceType = HostingXQConditionTriggerPriceType.XQ_LASTEST_PRICE
    condition1.conditionTrigger.triggerOperator = HostingXQConditionTriggerOperator.XQ_OP_LE
    condition1.conditionTrigger.conditionPrice = 97.76
    condition1.conditionAction = HostingXQConditionAction()
    condition1.conditionAction.orderType = HostingXQOrderType.XQ_ORDER_CONTRACT_LIMIT
    condition1.conditionAction.tradeDirection = HostingXQTradeDirection.XQ_SELL
    condition1.conditionAction.quantity = 10
    condition1.conditionAction.price = HostingXQConditionActionPrice()
    condition1.conditionAction.price.priceType = HostingXQConditionActionPriceType.ACTION_PRICE_OPPONENT
    
    #condition2 = HostingXQCondition()
    #condition2.conditionTrigger = HostingXQConditionTrigger()
    #condition2.conditionTrigger.conditionPriceType = HostingXQPriceType.XQ_LASTEST_PRICE
    #condition2.conditionTrigger.conditionOperator = HostingXQOperator.XQ_OP_GE
    #condition2.conditionTrigger.conditionPrice = 99.00
    #condition2.conditionAction = HostingXQConditionAction()
    #condition2.conditionAction.orderType = HostingXQOrderType.XQ_ORDER_CONTRACT_LIMIT
    #condition2.conditionAction.contractLimitOrderDetail = HostingXQContractLimitOrderDetail()
    #condition2.conditionAction.contractLimitOrderDetail.direction = HostingXQTradeDirection.XQ_SELL
    #condition2.conditionAction.contractLimitOrderDetail.quantity = 10
    #condition2.conditionAction.contractLimitOrderDetail.limitPrice = 99.00
    
    xqOrderDetail.conditionOrderDetail.conditions = [condition1]
    
    print(xqOrderDetail)
    print(stub.createXQOrder(0, 3000, landingInfo, subAccountId, xqOrderId, xqOrderType, xqOrderTarget, xqOrderDetail))
    
def createXQParkedOrderTest():
    subAccountId = 1051
    xqOrderId = "1001_1051_1_1231231231_3"
    xqOrderType = HostingXQOrderType.XQ_ORDER_CONTRACT_PARKED
    xqOrderTarget = HostingXQTarget()
    xqOrderTarget.targetType = HostingXQTargetType.CONTRACT_TARGET
    xqOrderTarget.targetKey = "35889"
    xqOrderDetail = HostingXQOrderDetail()
    xqOrderDetail.contractParkedOrderDetail = HostingXQContractParkedOrderDetail()
    xqOrderDetail.contractParkedOrderDetail.direction = HostingXQTradeDirection.XQ_BUY
    xqOrderDetail.contractParkedOrderDetail.quantity = 1
    xqOrderDetail.contractParkedOrderDetail.price = HostingXQOrderPrice()
    xqOrderDetail.contractParkedOrderDetail.price.priceType = HostingXQOrderPriceType.ACTION_PRICE_LIMIT
    xqOrderDetail.contractParkedOrderDetail.price.limitPrice = 97.99
    print(stub.createXQOrder(0, 3000, landingInfo, subAccountId, xqOrderId, xqOrderType, xqOrderTarget, xqOrderDetail))
    

def suspendXQOrderTest(orderId):
    print(stub.batchSuspendXQOrders(0, 3000, landingInfo, set([orderId])))
    
def startXQOrderTest(orderId):
    print(stub.batchResumeXQOrders(0, 3000, landingInfo, set([orderId])))
    
def cancelXQOrderTest(orderId):
    print(stub.batchCancelXQOrders(0, 3000, landingInfo, set([orderId])))
    

def getEffectXQOrderWithTradeListPageTest() :
    qryOption = QueryEffectXQOrderIndexOption()
    qryOption.subAccountIds = set([1051])
    print(stub.getEffectXQOrderWithTradeListPage(0, 3000, landingInfo, qryOption, pageOption))
    
def getXQOrderWithTradeListsTest():
    orderIds = set(["1001_1051_123123123123_20", "1001_1051_123123123123_21"])
    print(stub.getXQOrderWithTradeLists(0, 3000, landingInfo, orderIds))

def getXQOrderExecDetailTest():
    print(stub.getXQOrderExecDetail(0, 3000, landingInfo, "1001_1051_123123123123_21"))

# createXQParkedOrderTest()

def getTradeAccoundFundNowTest():
    print(stub.getTradeAccountFundNow(0, 3000, landingInfo, 1021))

# getTradeAccoundFundNowTest()

def getTradeAccountFundHisTest():
    print(stub.getTradeAccountFundHis(0, 3000, landingInfo, 1001, "2018-08-01", "2018-08-30"))

#getTradeAccountFundHisTest()

def getTradeAccountSettlementInfoTest():
    print(stub.getTradeAccountSettlementInfos(0, 3000, landingInfo, 1001, "2018-08-15", "2018-08-15"))

#getTradeAccountSettlementInfoTest()

#createXQComposeLimitOrderParallelTest()

createXQComposeLimitOrderLegByTest()