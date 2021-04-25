#!/usr/bin/env python
#coding=utf-8

# import random
# from xueqiao.trade.hosting.proxy.ttypes import *
# from xueqiao.trade.hosting.proxy.constants import *
from xueqiao.trade.hosting.proxy.client.TradeHostingProxyStub import *

stub=TradeHostingProxyStub()

updatePasswdReq = ProxyUpdatePasswordReq()
# updatePasswdReq.userId = 1169
updatePasswdReq.companyId=1187
updatePasswdReq.userName="ljq"
updatePasswdReq.oldPassword="ecWWKoCZ4vySRNaUxc/D7rotJZg048+DvLhySf0894GnTrW1VjABZwzD8d1JRkNfdsuStqkIUu9oBRKW9F6OhJM8HoTWFUADlw/virJRiDXoZ52repIlsATAQ0MqP/in04cVJ1GiPTdG9vFZhRqJ3BVTQ7FPPZkwJkqWPh3HWWo="
updatePasswdReq.newPassword="MBOwtXM5diRkpmqwn1lpFAOuaeKAHFNDNUev2y/vHnXE3/3beFqPJ16TowDi3/w0X7wApnuEAebwYbutZJpNEmz2m+JCTfIkQK4w8ef93l2s/WzxjLXMe6EoL7G4e+TexC3FfGBJZPejIi5lYyni3dEBrVizEvOio8NH0F0geMM=="

stub.updateCompanyUserPassword(0, 30000, updatePasswdReq)