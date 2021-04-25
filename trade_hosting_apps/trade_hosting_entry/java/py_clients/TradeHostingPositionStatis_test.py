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

def deleteExpiredStatContractPosition():
    sledContractId = 36106
    subAccountId = 1000
    stub.deleteExpiredStatContractPosition(0, 3000, landingInfo, subAccountId, sledContractId)
    print "done"
    pass

def contructComposeTest():
    contructComposeReq = StatContructComposeReq()
    contructComposeReq.composeGraphId = 1201
    contructComposeReq.composePrice = 225.3
    contructComposeReq.diretion = 1
    contructComposeReq.subAccountId = 1001
    contructComposeReq.volume = 1
    leg1 = StatComposeLeg()
    leg1.sledContractId = 35588
    leg1.legTradePrice = 234.2
    leg2 = StatComposeLeg()
    leg2.sledContractId = 42907
    leg2.legTradePrice = 412.7
    contructComposeReq.composeLegs = [leg1, leg2]
    stub.contructCompose(0, 3000, landingInfo, contructComposeReq)
    print "done"
    pass


def disassembleComposeTest():
    disassembleComposePositionReq = DisassembleComposePositionReq()
    disassembleComposePositionReq.subAccountId = 1001
    disassembleComposePositionReq.targetKey = "1201"
    disassembleComposePositionReq.targetType = HostingXQTargetType.COMPOSE_TARGET
    data1 = PositionItemData()
    data1.positionItemId = 4401
    data1.quantity = 2
    disassembleComposePositionReq.positionItemDataList = [data1]

    print disassembleComposePositionReq

    stub.disassembleCompose(0, 3000, landingInfo, disassembleComposePositionReq)
    print "done"
    pass


def batchClosePositionTest():
    batchClosedPositionReq = BatchClosedPositionReq()

    batchClosedPositionReq.subAccountId = 1001
    batchClosedPositionReq.targetKey = "35588"
    batchClosedPositionReq.targetType = 1

    data1 = ClosedPositionDetailItem()
    data1.positionItemId = 4612
    data1.closedVolume = 1

    data2 = ClosedPositionDetailItem()
    data2.positionItemId = 4803
    data2.closedVolume = 1

    batchClosedPositionReq.closedPositionDetailItems = [data1, data2]

    stub.batchClosePosition(0, 3000, landingInfo, batchClosedPositionReq)
    print "done"
    pass


def recoverClosedPositionTest():
    stub.recoverClosedPosition(0, 3000, landingInfo, 1001, "1201", 2)
    print "done"
    pass


def queryStatPositionSummaryPageTest():
    queryOption = QueryStatPositionSummaryOption()
    queryOption.subAccountId = 1001
    queryOption.targetType = 1
    queryOption.targetKey = "35588"

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 50
    pageOption.needTotalCount = True

    print stub.queryStatPositionSummaryPage(0, 3000, landingInfo, queryOption, pageOption)
    pass


def queryStatPositionItemPageTest():
    queryOption = QueryStatPositionItemOption()
    queryOption.subAccountId = 1001
    queryOption.targetKey = "35588"
    #queryOption.positionItemId = 2802

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 50
    pageOption.needTotalCount = True

    print stub.queryStatPositionItemPage(0, 3000, landingInfo, queryOption, pageOption)
    pass


def queryCurrentDayStatClosedPositionPage():
    print stub.queryCurrentDayStatClosedPositionPage(0, 3000, landingInfo, 1001, "35588", 1)
    pass


def queryStatClosedPositionDetail():
    queryOption = QueryStatClosedPositionItemOption()
    queryOption.subAccountId = 1001
    queryOption.targetKey = "35588"

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 50
    pageOption.needTotalCount = True

    print stub.queryStatClosedPositionDetail(0, 3000, landingInfo, queryOption, pageOption)
    pass


def queryArchivedClosedPositionPage():
    queryOption = QueryStatClosedPositionDateSummaryOption()
    queryOption.subAccountId = 1001
    queryOption.targetKey = "35588"

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 50
    pageOption.needTotalCount = True

    print stub.queryArchivedClosedPositionPage(0, 3000, landingInfo, queryOption, pageOption)
    pass


def queryArchivedClosedPositionDetail():
    queryOption = QueryStatArchiveItemOption()
    queryOption.subAccountId = 1001
    queryOption.targetKey = "35588"
    queryOption.archivedDateTimestampMs = 1536768000000
    queryOption.archiveStartDateTimestampMs = 1535817500009  #1535817500005
    queryOption.archiveEndDateTimestampMs = 1535817600009

    pageOption = IndexedPageOption()
    pageOption.pageIndex = 0
    pageOption.pageSize = 50
    pageOption.needTotalCount = True
    print stub.queryArchivedClosedPositionDetail(0, 3000, landingInfo, queryOption, pageOption)
    pass


#Test
#contructComposeTest()
#disassembleComposeTest()
#batchClosePositionTest()
#recoverClosedPositionTest()

##### query
#queryStatPositionSummaryPageTest()
#queryStatPositionItemPageTest()
#queryCurrentDayStatClosedPositionPage()
#queryStatClosedPositionDetail()
#queryArchivedClosedPositionPage()
#queryArchivedClosedPositionDetail()
deleteExpiredStatContractPosition()