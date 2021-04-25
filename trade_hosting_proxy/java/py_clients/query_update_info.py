#!/usr/bin/env python
# test for service TradeHostingProxy
import random
from xueqiao.trade.hosting.proxy.ttypes import *
from xueqiao.trade.hosting.proxy.constants import *
from xueqiao.trade.hosting.proxy.client.TradeHostingProxyStub import *

stub=TradeHostingProxyStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...


update_info_req = UpdateInfoReq()
update_info_req.companyId =1047
update_info_req.appKey = 'xueqiao_trade'
update_info_req.versionNum = VersionNum(majorVersionNum=1,minorVersionNum=0,buildVersionNum=1,reversionNum=2)
appversion= stub.queryUpdateInfo(routeKey=1,timeout=2000,req=update_info_req)
print(appversion)