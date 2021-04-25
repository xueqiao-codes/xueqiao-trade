#!/usr/bin/env python
# test for service TradeHostingTerminalAo
import random
from xueqiao.trade.hosting.terminal.ao.ttypes import *
from xueqiao.trade.hosting.terminal.ao.constants import *
from xueqiao.trade.hosting.terminal.ao.client.TradeHostingTerminalAoStub import *

#reload(sys)
#sys.setdefaultencoding('utf-8')

stub = TradeHostingTerminalAoStub()
# dev_tiyan2
stub.setPeerAddr("dev-thosting1.xueqiao.host")

landingInfo = LandingInfo()
landingInfo.machineId = 1001
landingInfo.subUserId = 4
#sub_account_id = 1001


def setGeneralMarginSetting():
    marginDelta = MarginInfo();
    marginDelta.longMarginRatioByMoney = 0.03
    marginDelta.longMarginRatioByVolume = 200
    marginDelta.shortMarginRatioByMoney = 0.04
    marginDelta.shortMarginRatioByVolume = 300
    marginDelta.currency = "CNY"

    marginSettings = XQGeneralMarginSettings()
    marginSettings.subAccountId = 1000
    # type : 0 -> add, 1 -> sub
    marginSettings.type = 0
    marginSettings.marginDelta = marginDelta


    stub.setGeneralMarginSetting(0, 3000, landingInfo, marginSettings)
    print "done"
    pass

def setGeneralCommissionSetting():
    commissionDelta = CommissionInfo();
    commissionDelta.openRatioByMoney = 0.03
    commissionDelta.openRatioByVolume = 300
    commissionDelta.closeRatioByMoney = 0.01
    commissionDelta.closeRatioByVolume = 100
    commissionDelta.closeTodayRatioByMoney = 0.02
    commissionDelta.closeTodayRatioByVolume = 200
    commissionDelta.currency = "CNY"

    commissionSettings = XQGeneralCommissionSettings()
    commissionSettings.subAccountId = 1000
    # type : 0 -> add, 1 -> sub
    commissionSettings.type = 0
    commissionSettings.commissionDelta = commissionDelta


    stub.setGeneralCommissionSetting(0, 3000, landingInfo, commissionSettings)
    print "done"
    pass

def addSpecMarginSetting():
    marginDelta = MarginInfo();
    marginDelta.longMarginRatioByMoney = 0.03
    marginDelta.longMarginRatioByVolume = 200
    marginDelta.shortMarginRatioByMoney = 0.04
    marginDelta.shortMarginRatioByVolume = 300
    marginDelta.currency = "CNY"

    marginSettings = XQSpecMarginSettings()
    marginSettings.subAccountId = 1000
    marginSettings.type = 1
    marginSettings.commodityId = 10444
    marginSettings.marginDelta = marginDelta

    stub.addSpecMarginSetting(0, 3000, landingInfo, marginSettings)
    print "done"
    pass

def addSpecCommissionSetting():
    commissionDelta = CommissionInfo();
    commissionDelta.openRatioByMoney = 0.03
    commissionDelta.openRatioByVolume = 300
    commissionDelta.closeRatioByMoney = 0.01
    commissionDelta.closeRatioByVolume = 100
    commissionDelta.closeTodayRatioByMoney = 0.02
    commissionDelta.closeTodayRatioByVolume = 200
    commissionDelta.currency = "CNY"

    commissionSettings = XQSpecCommissionSettings()
    commissionSettings.subAccountId = 1000
    commissionSettings.type = 1
    commissionSettings.commodityId = 10444
    commissionSettings.commissionDelta = commissionDelta

    stub.addSpecCommissionSetting(0, 3000, landingInfo, commissionSettings)
    print "done"
    pass

def updateSpecMarginSetting():
    marginDelta = MarginInfo();
    marginDelta.longMarginRatioByMoney = 0.035
    marginDelta.longMarginRatioByVolume = 205
    marginDelta.shortMarginRatioByMoney = 0.045
    marginDelta.shortMarginRatioByVolume = 305
    marginDelta.currency = "CNY"

    marginSettings = XQSpecMarginSettings()
    marginSettings.subAccountId = 1000
    marginSettings.type = 1
    marginSettings.commodityId = 10444
    marginSettings.marginDelta = marginDelta

    stub.updateSpecMarginSetting(0, 3000, landingInfo, marginSettings)
    print "done"
    pass

def updateSpecCommissionSetting():
    commissionDelta = CommissionInfo();
    commissionDelta.openRatioByMoney = 0.036
    commissionDelta.openRatioByVolume = 306
    commissionDelta.closeRatioByMoney = 0.016
    commissionDelta.closeRatioByVolume = 106
    commissionDelta.closeTodayRatioByMoney = 0.026
    commissionDelta.closeTodayRatioByVolume = 206
    commissionDelta.currency = "CNY"

    commissionSettings = XQSpecCommissionSettings()
    commissionSettings.subAccountId = 1000
    commissionSettings.type = 1
    commissionSettings.commodityId = 10444
    commissionSettings.commissionDelta = commissionDelta

    stub.updateSpecCommissionSetting(0, 3000, landingInfo, commissionSettings)
    print "done"
    pass

def deleteSpecMarginSetting():
    subAccountId = 1000
    commodityId = 10444
    stub.deleteSpecMarginSetting(0, 3000, landingInfo, subAccountId, commodityId)
    print "done"
    pass

def deleteSpecCommissionSetting():
    subAccountId = 1000
    commodityId = 10444
    stub.deleteSpecCommissionSetting(0, 3000, landingInfo, subAccountId, commodityId)
    print "done"
    pass

def queryXQGeneralMarginSettings():
    subAccountId = 1000
    xqGeneralMarginSettings = stub.queryXQGeneralMarginSettings(0, 3000, landingInfo, subAccountId)
    print xqGeneralMarginSettings
    pass


def queryXQGeneralCommissionSettings():
    subAccountId = 1000
    xqGeneralCommissionSettings = stub.queryXQGeneralCommissionSettings(0, 3000, landingInfo, subAccountId)
    print xqGeneralCommissionSettings
    pass

def queryXQSpecMarginSettingPage():
    queryOptions = QueryXQSpecSettingOptions()
    queryOptions.subAccountId = 1000

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 1
    pageOption.needTotalCount = True
    print stub.queryXQSpecMarginSettingPage(0, 3000, landingInfo, queryOptions, pageOption)
    pass

def queryXQSpecCommissionSettingPage():
    queryOptions = QueryXQSpecSettingOptions()
    queryOptions.subAccountId = 1000

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 1
    pageOption.needTotalCount = True
    print stub.queryXQSpecCommissionSettingPage(0, 3000, landingInfo, queryOptions, pageOption)
    pass

def queryUpsideContractMarginPage():
    queryOptions = QueryUpsidePFeeOptions()
    queryOptions.subAccountId = 1000

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 1
    pageOption.needTotalCount = True
    print stub.queryUpsideContractMarginPage(0, 3000, landingInfo, queryOptions, pageOption)
    pass

def queryUpsideContractCommissionPage():
    queryOptions = QueryUpsidePFeeOptions()
    queryOptions.subAccountId = 1000

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 1
    pageOption.needTotalCount = True
    print stub.queryUpsideContractCommissionPage(0, 3000, landingInfo, queryOptions, pageOption)
    pass

def queryXQContractMarginPage():
    queryOptions = QueryXQPFeeOptions()
    queryOptions.subAccountId = 1000

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 1
    pageOption.needTotalCount = True
    print stub.queryXQContractMarginPage(0, 3000, landingInfo, queryOptions, pageOption)
    pass

def queryXQContractCommissionPage():
    queryOptions = QueryXQPFeeOptions()
    queryOptions.subAccountId = 1000

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 1
    pageOption.needTotalCount = True
    print stub.queryXQContractCommissionPage(0, 3000, landingInfo, queryOptions, pageOption)
    pass


# setGeneralMarginSetting()
# setGeneralCommissionSetting()
# addSpecMarginSetting()
# addSpecCommissionSetting()
# updateSpecMarginSetting()
# updateSpecCommissionSetting()
# deleteSpecMarginSetting()
# deleteSpecCommissionSetting()
queryXQGeneralMarginSettings()
queryXQGeneralCommissionSettings()
queryXQSpecMarginSettingPage()
queryXQSpecCommissionSettingPage()
queryUpsideContractMarginPage()
queryUpsideContractCommissionPage()
queryXQContractMarginPage()
queryXQContractCommissionPage()

