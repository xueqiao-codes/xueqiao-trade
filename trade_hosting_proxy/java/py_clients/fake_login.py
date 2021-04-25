#!/usr/bin/env python
#coding=utf-8

import random
from xueqiao.trade.hosting.proxy.ttypes import *
from xueqiao.trade.hosting.proxy.constants import *
from xueqiao.trade.hosting.proxy.client.TradeHostingProxyStub import *

stub=TradeHostingProxyStub()

fakeLoginReq = ProxyFakeLoginReq()
fakeLoginReq.companyCode = "xiaoming"
fakeLoginReq.userName = "bb"
fakeLoginReq.passwordMd5 = "a61771b2d795dc417c02378fa5f344f0"
# fakeLoginReq.tradeType = 2

print(stub.fakeLogin(0, 3000, fakeLoginReq))