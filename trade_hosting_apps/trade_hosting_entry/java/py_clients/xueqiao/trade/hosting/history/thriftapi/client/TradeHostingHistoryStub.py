#
# -*- coding: utf-8 -*-
# Autogenerated by Thrift Compiler (0.9.1)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

import sys
import socket
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.transport.TTransport import TTransportException
from comm.ttypes import *
from page.ttypes import *
from xueqiao.trade.hosting.arbitrage.thriftapi.ttypes import *
from xueqiao.trade.hosting.history.thriftapi.ttypes import *
from xueqiao.trade.hosting.history.thriftapi import TradeHostingHistory

PYTHON_SUPPORT_DIR='/usr/local/soldier/route_agent'
if not (PYTHON_SUPPORT_DIR in sys.path):
  sys.path.append(PYTHON_SUPPORT_DIR)
from route_finder_python import *

class TradeHostingHistoryStub:
  def __init__(self):
    self.__peerAddr = None

  def setPeerAddr(self, peerAddr):
    self.__peerAddr = peerAddr

  def __getServiceAddr(self, methodName='', routeKey=0):
    if self.__peerAddr != None and self.__peerAddr !='':
      return self.__peerAddr
    return route_finder.GetRouteIp(TradeHostingHistory.TradeHostingHistory_SERVICE_KEY, methodName, routeKey)

  def clearAll(self, routeKey, timeout, ):
    platformArgs=PlatformArgs()
    frame = sys._getframe(1)
    code = frame.f_code
    platformArgs.sourceDesc = code.co_filename + '[' + code.co_name + ":" + str(code.co_firstlineno) + ']'
    platformArgs.sourceIp = socket.gethostbyname(socket.getfqdn())
    platformArgs.remoteAddress = self.__getServiceAddr('clearAll', routeKey)
    if platformArgs.remoteAddress == None or platformArgs.remoteAddress == '':
      raise Exception('No RouteIp is Found')
    platformArgs.remotePort = 10000 + TradeHostingHistory.TradeHostingHistory_SERVICE_KEY

    transport_socket = TSocket.TSocket(platformArgs.remoteAddress, platformArgs.remotePort)

    transport_socket.setTimeout(timeout)
    transport = TTransport.TFramedTransport(transport_socket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client=TradeHostingHistory.Client(protocol)
    try:
      transport.open()
      result = client.clearAll(platformArgs,)
      route_finder.UpdateCallInfo(TradeHostingHistory.TradeHostingHistory_SERVICE_KEY, 'clearAll', platformArgs.remoteAddress, 0)
      return result
    except TTransportException,t:
      route_finder.UpdateCallInfo(TradeHostingHistory.TradeHostingHistory_SERVICE_KEY, 'clearAll', platformArgs.remoteAddress, 1)
      raise t
    finally:
      transport.close()

  def getXQOrderHisIndexPage(self, routeKey, timeout, qryOption,pageOption,):
    platformArgs=PlatformArgs()
    frame = sys._getframe(1)
    code = frame.f_code
    platformArgs.sourceDesc = code.co_filename + '[' + code.co_name + ":" + str(code.co_firstlineno) + ']'
    platformArgs.sourceIp = socket.gethostbyname(socket.getfqdn())
    platformArgs.remoteAddress = self.__getServiceAddr('getXQOrderHisIndexPage', routeKey)
    if platformArgs.remoteAddress == None or platformArgs.remoteAddress == '':
      raise Exception('No RouteIp is Found')
    platformArgs.remotePort = 10000 + TradeHostingHistory.TradeHostingHistory_SERVICE_KEY

    transport_socket = TSocket.TSocket(platformArgs.remoteAddress, platformArgs.remotePort)

    transport_socket.setTimeout(timeout)
    transport = TTransport.TFramedTransport(transport_socket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client=TradeHostingHistory.Client(protocol)
    try:
      transport.open()
      result = client.getXQOrderHisIndexPage(platformArgs,qryOption,pageOption,)
      route_finder.UpdateCallInfo(TradeHostingHistory.TradeHostingHistory_SERVICE_KEY, 'getXQOrderHisIndexPage', platformArgs.remoteAddress, 0)
      return result
    except TTransportException,t:
      route_finder.UpdateCallInfo(TradeHostingHistory.TradeHostingHistory_SERVICE_KEY, 'getXQOrderHisIndexPage', platformArgs.remoteAddress, 1)
      raise t
    finally:
      transport.close()

  def getXQTradeHisIndexPage(self, routeKey, timeout, qryOption,pageOption,):
    platformArgs=PlatformArgs()
    frame = sys._getframe(1)
    code = frame.f_code
    platformArgs.sourceDesc = code.co_filename + '[' + code.co_name + ":" + str(code.co_firstlineno) + ']'
    platformArgs.sourceIp = socket.gethostbyname(socket.getfqdn())
    platformArgs.remoteAddress = self.__getServiceAddr('getXQTradeHisIndexPage', routeKey)
    if platformArgs.remoteAddress == None or platformArgs.remoteAddress == '':
      raise Exception('No RouteIp is Found')
    platformArgs.remotePort = 10000 + TradeHostingHistory.TradeHostingHistory_SERVICE_KEY

    transport_socket = TSocket.TSocket(platformArgs.remoteAddress, platformArgs.remotePort)

    transport_socket.setTimeout(timeout)
    transport = TTransport.TFramedTransport(transport_socket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client=TradeHostingHistory.Client(protocol)
    try:
      transport.open()
      result = client.getXQTradeHisIndexPage(platformArgs,qryOption,pageOption,)
      route_finder.UpdateCallInfo(TradeHostingHistory.TradeHostingHistory_SERVICE_KEY, 'getXQTradeHisIndexPage', platformArgs.remoteAddress, 0)
      return result
    except TTransportException,t:
      route_finder.UpdateCallInfo(TradeHostingHistory.TradeHostingHistory_SERVICE_KEY, 'getXQTradeHisIndexPage', platformArgs.remoteAddress, 1)
      raise t
    finally:
      transport.close()

