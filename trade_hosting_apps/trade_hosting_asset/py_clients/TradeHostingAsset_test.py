#!/usr/bin/env python
# test for service TradeHostingAsset
import random
from xueqiao.trade.hosting.asset.thriftapi.ttypes import *
from xueqiao.trade.hosting.asset.thriftapi.constants import *
from xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub import *

stub=TradeHostingAssetStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...

