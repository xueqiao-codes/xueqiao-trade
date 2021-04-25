#!/usr/bin/env python
# -*- coding: utf-8 -*-  
# test for service TradeHostingCloudAo
import random
import sys
from xueqiao.trade.hosting.cloud.ao.ttypes import *
from xueqiao.trade.hosting.cloud.ao.constants import *
from xueqiao.trade.hosting.cloud.ao.client.TradeHostingCloudAoStub import *
from page.ttypes import *


stub=TradeHostingCloudAoStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...

stub.setPeerAddr("172.27.212.120")

initReq = HostingInitReq()
 
initReq.machineId = 1001
initReq.adminLoginName = "wangli"
initReq.adminLoginPasswd = "123456"
initReq.hostingAES16Key="defd351cb91e1e03"
initReq.runningMode = HostingRunningMode.ALLDAY_HOSTING

     
print(stub.initHosting(0, 3000, initReq))

resetReq = HostingResetReq()
resetReq.machineId = 1022
resetReq.hostingAES16Key=initReq.hostingAES16Key
   
#print(stub.resetHosting(0, 3000, resetReq))

# print(stub.getHostingInfo(0, 3000))

# loginReq = LoginReq()
# loginReq.companyId = 1
# loginReq.loginUserName="wangli"
# loginReq.loginPasswordMd5="e10adc3949ba59abbe56e057f20f883e"
# 
# resp = stub.login(0, 3000, loginReq)
# 
# print(resp)
#  
#  
# checkSessionReq = CheckSessionReq()
# checkSessionReq.session = HostingSession()
# checkSessionReq.session.companyId = 1
# checkSessionReq.session.subUserId = 0
# checkSessionReq.session.token = resp.session.token
#   
# print(stub.checkSession(0, 3000, checkSessionReq))
# 
# logoutReq = LogoutReq()
# logoutReq.session = HostingSession()
# logoutReq.session.companyId = 1
# logoutReq.session.subUserId = 2
# logoutReq.session.token = resp.session.token
#  
# print(stub.logout(0, 3000, logoutReq))

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

updateUser = HostingUser()
updateUser.subUserId = 1
updateUser.email = "wangli-whu@163.com"
updateUser.phone = "027-88888888" 
#print(stub.updateHostingUser(0, 3000, updateUser))

#print(stub.disableHostingUser(0, 3000, 4))
#print(stub.enableHostingUser(0, 3000, 4))

qryOption = QueryHostingUserOption()

print(stub.getHostingUserPage(0, 3000, qryOption, pageOption))


