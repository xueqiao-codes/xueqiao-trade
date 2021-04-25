#!/usr/bin/env python
# test for service TradeHostingProxy
import random
from xueqiao.trade.hosting.proxy.ttypes import *
from xueqiao.trade.hosting.proxy.constants import *
from xueqiao.trade.hosting.proxy.client.TradeHostingProxyStub import *

stub=TradeHostingProxyStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...

loginReq = ProxyLoginReq()
loginReq.companyCode = "XUEQIAO"
loginReq.userName = "pengguangbo"
loginReq.passwordMd5 = "e10adc3949ba59abbe56e057f20f883e"
loginReq.companyGroupCode = "DEV2"
 
print(stub.login(0, 3000, loginReq))

session=HostingSession()
session.machineId = 1001
session.subUserId = 1
session.token="utpdftxingscitytggtchgpvmmwmzjczqrgclwixjblmztetjmsguirrjubrwykk"

print(stub.checkSession(0, 3000, session))


