#
# -*- coding: utf-8 -*-
# Autogenerated by Thrift Compiler (0.9.1)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
import comm.ttypes
import page.ttypes
import xueqiao.trade.hosting.arbitrage.thriftapi.ttypes


from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None


class QueryXQOrderHisIndexItemOrderType:
  XQ_ORDER_CREATE_TIMESTAMP_ASC = 1
  XQ_ORDER_CREATE_TIMESTAMP_DESC = 2
  XQ_ORDER_END_TIMESTAMP_ASC = 3
  XQ_ORDER_END_TIMESTAMP_DESC = 4

  _VALUES_TO_NAMES = {
    1: "XQ_ORDER_CREATE_TIMESTAMP_ASC",
    2: "XQ_ORDER_CREATE_TIMESTAMP_DESC",
    3: "XQ_ORDER_END_TIMESTAMP_ASC",
    4: "XQ_ORDER_END_TIMESTAMP_DESC",
  }

  _NAMES_TO_VALUES = {
    "XQ_ORDER_CREATE_TIMESTAMP_ASC": 1,
    "XQ_ORDER_CREATE_TIMESTAMP_DESC": 2,
    "XQ_ORDER_END_TIMESTAMP_ASC": 3,
    "XQ_ORDER_END_TIMESTAMP_DESC": 4,
  }

class QueryXQTradeHisIndexItemOrderType:
  XQ_TRADE_CREATE_TIMESTAMP_ASC = 1
  XQ_TRADE_CREATE_TIMESTAMP_DESC = 2

  _VALUES_TO_NAMES = {
    1: "XQ_TRADE_CREATE_TIMESTAMP_ASC",
    2: "XQ_TRADE_CREATE_TIMESTAMP_DESC",
  }

  _NAMES_TO_VALUES = {
    "XQ_TRADE_CREATE_TIMESTAMP_ASC": 1,
    "XQ_TRADE_CREATE_TIMESTAMP_DESC": 2,
  }


class HostingXQOrderHisIndexItem:
  """
  Attributes:
   - subAccountId
   - subUserId
   - orderType
   - orderTarget
   - orderCreateTimestampMs
   - orderEndTimestampMs
   - orderId
   - createTimestampMs
   - lastmodifyTimestampMs
  """

  thrift_spec = (
    None, # 0
    (1, TType.I64, 'subAccountId', None, None, ), # 1
    (2, TType.I32, 'subUserId', None, None, ), # 2
    (3, TType.I32, 'orderType', None, None, ), # 3
    (4, TType.STRUCT, 'orderTarget', (xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget, xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget.thrift_spec), None, ), # 4
    (5, TType.I64, 'orderCreateTimestampMs', None, None, ), # 5
    (6, TType.I64, 'orderEndTimestampMs', None, None, ), # 6
    (7, TType.STRING, 'orderId', None, None, ), # 7
    None, # 8
    (9, TType.I64, 'createTimestampMs', None, None, ), # 9
    (10, TType.I64, 'lastmodifyTimestampMs', None, None, ), # 10
  )

  def __init__(self, subAccountId=None, subUserId=None, orderType=None, orderTarget=None, orderCreateTimestampMs=None, orderEndTimestampMs=None, orderId=None, createTimestampMs=None, lastmodifyTimestampMs=None,):
    self.subAccountId = subAccountId
    self.subUserId = subUserId
    self.orderType = orderType
    self.orderTarget = orderTarget
    self.orderCreateTimestampMs = orderCreateTimestampMs
    self.orderEndTimestampMs = orderEndTimestampMs
    self.orderId = orderId
    self.createTimestampMs = createTimestampMs
    self.lastmodifyTimestampMs = lastmodifyTimestampMs

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.I64:
          self.subAccountId = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I32:
          self.subUserId = iprot.readI32();
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.I32:
          self.orderType = iprot.readI32();
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.STRUCT:
          self.orderTarget = xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget()
          self.orderTarget.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 5:
        if ftype == TType.I64:
          self.orderCreateTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 6:
        if ftype == TType.I64:
          self.orderEndTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 7:
        if ftype == TType.STRING:
          self.orderId = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 9:
        if ftype == TType.I64:
          self.createTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 10:
        if ftype == TType.I64:
          self.lastmodifyTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('HostingXQOrderHisIndexItem')
    if self.subAccountId is not None:
      oprot.writeFieldBegin('subAccountId', TType.I64, 1)
      oprot.writeI64(self.subAccountId)
      oprot.writeFieldEnd()
    if self.subUserId is not None:
      oprot.writeFieldBegin('subUserId', TType.I32, 2)
      oprot.writeI32(self.subUserId)
      oprot.writeFieldEnd()
    if self.orderType is not None:
      oprot.writeFieldBegin('orderType', TType.I32, 3)
      oprot.writeI32(self.orderType)
      oprot.writeFieldEnd()
    if self.orderTarget is not None:
      oprot.writeFieldBegin('orderTarget', TType.STRUCT, 4)
      self.orderTarget.write(oprot)
      oprot.writeFieldEnd()
    if self.orderCreateTimestampMs is not None:
      oprot.writeFieldBegin('orderCreateTimestampMs', TType.I64, 5)
      oprot.writeI64(self.orderCreateTimestampMs)
      oprot.writeFieldEnd()
    if self.orderEndTimestampMs is not None:
      oprot.writeFieldBegin('orderEndTimestampMs', TType.I64, 6)
      oprot.writeI64(self.orderEndTimestampMs)
      oprot.writeFieldEnd()
    if self.orderId is not None:
      oprot.writeFieldBegin('orderId', TType.STRING, 7)
      oprot.writeString(self.orderId)
      oprot.writeFieldEnd()
    if self.createTimestampMs is not None:
      oprot.writeFieldBegin('createTimestampMs', TType.I64, 9)
      oprot.writeI64(self.createTimestampMs)
      oprot.writeFieldEnd()
    if self.lastmodifyTimestampMs is not None:
      oprot.writeFieldBegin('lastmodifyTimestampMs', TType.I64, 10)
      oprot.writeI64(self.lastmodifyTimestampMs)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class HostingXQTradeHisIndexItem:
  """
  Attributes:
   - subAccountId
   - subUserId
   - tradeTarget
   - tradeCreateTimestampMs
   - tradeId
   - orderId
   - createTimestampMs
   - lastmodifyTimestampMs
  """

  thrift_spec = (
    None, # 0
    (1, TType.I64, 'subAccountId', None, None, ), # 1
    (2, TType.I32, 'subUserId', None, None, ), # 2
    (3, TType.STRUCT, 'tradeTarget', (xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget, xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget.thrift_spec), None, ), # 3
    (4, TType.I64, 'tradeCreateTimestampMs', None, None, ), # 4
    (5, TType.I64, 'tradeId', None, None, ), # 5
    (6, TType.STRING, 'orderId', None, None, ), # 6
    None, # 7
    None, # 8
    (9, TType.I64, 'createTimestampMs', None, None, ), # 9
    (10, TType.I64, 'lastmodifyTimestampMs', None, None, ), # 10
  )

  def __init__(self, subAccountId=None, subUserId=None, tradeTarget=None, tradeCreateTimestampMs=None, tradeId=None, orderId=None, createTimestampMs=None, lastmodifyTimestampMs=None,):
    self.subAccountId = subAccountId
    self.subUserId = subUserId
    self.tradeTarget = tradeTarget
    self.tradeCreateTimestampMs = tradeCreateTimestampMs
    self.tradeId = tradeId
    self.orderId = orderId
    self.createTimestampMs = createTimestampMs
    self.lastmodifyTimestampMs = lastmodifyTimestampMs

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.I64:
          self.subAccountId = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I32:
          self.subUserId = iprot.readI32();
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.STRUCT:
          self.tradeTarget = xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget()
          self.tradeTarget.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.I64:
          self.tradeCreateTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 5:
        if ftype == TType.I64:
          self.tradeId = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 6:
        if ftype == TType.STRING:
          self.orderId = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 9:
        if ftype == TType.I64:
          self.createTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 10:
        if ftype == TType.I64:
          self.lastmodifyTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('HostingXQTradeHisIndexItem')
    if self.subAccountId is not None:
      oprot.writeFieldBegin('subAccountId', TType.I64, 1)
      oprot.writeI64(self.subAccountId)
      oprot.writeFieldEnd()
    if self.subUserId is not None:
      oprot.writeFieldBegin('subUserId', TType.I32, 2)
      oprot.writeI32(self.subUserId)
      oprot.writeFieldEnd()
    if self.tradeTarget is not None:
      oprot.writeFieldBegin('tradeTarget', TType.STRUCT, 3)
      self.tradeTarget.write(oprot)
      oprot.writeFieldEnd()
    if self.tradeCreateTimestampMs is not None:
      oprot.writeFieldBegin('tradeCreateTimestampMs', TType.I64, 4)
      oprot.writeI64(self.tradeCreateTimestampMs)
      oprot.writeFieldEnd()
    if self.tradeId is not None:
      oprot.writeFieldBegin('tradeId', TType.I64, 5)
      oprot.writeI64(self.tradeId)
      oprot.writeFieldEnd()
    if self.orderId is not None:
      oprot.writeFieldBegin('orderId', TType.STRING, 6)
      oprot.writeString(self.orderId)
      oprot.writeFieldEnd()
    if self.createTimestampMs is not None:
      oprot.writeFieldBegin('createTimestampMs', TType.I64, 9)
      oprot.writeI64(self.createTimestampMs)
      oprot.writeFieldEnd()
    if self.lastmodifyTimestampMs is not None:
      oprot.writeFieldBegin('lastmodifyTimestampMs', TType.I64, 10)
      oprot.writeI64(self.lastmodifyTimestampMs)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class QueryTimePeriod:
  """
  Attributes:
   - startTimestampMs
   - endTimestampMs
  """

  thrift_spec = (
    None, # 0
    (1, TType.I64, 'startTimestampMs', None, None, ), # 1
    (2, TType.I64, 'endTimestampMs', None, None, ), # 2
  )

  def __init__(self, startTimestampMs=None, endTimestampMs=None,):
    self.startTimestampMs = startTimestampMs
    self.endTimestampMs = endTimestampMs

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.I64:
          self.startTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I64:
          self.endTimestampMs = iprot.readI64();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('QueryTimePeriod')
    if self.startTimestampMs is not None:
      oprot.writeFieldBegin('startTimestampMs', TType.I64, 1)
      oprot.writeI64(self.startTimestampMs)
      oprot.writeFieldEnd()
    if self.endTimestampMs is not None:
      oprot.writeFieldBegin('endTimestampMs', TType.I64, 2)
      oprot.writeI64(self.endTimestampMs)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class QueryXQOrderHisIndexItemOption:
  """
  Attributes:
   - orderCreateTimePeriod
   - orderEndTimePeriod
   - subAccountIds
   - orderTargets
   - itemOrderType
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'orderCreateTimePeriod', (QueryTimePeriod, QueryTimePeriod.thrift_spec), None, ), # 1
    (2, TType.STRUCT, 'orderEndTimePeriod', (QueryTimePeriod, QueryTimePeriod.thrift_spec), None, ), # 2
    (3, TType.SET, 'subAccountIds', (TType.I64,None), None, ), # 3
    (4, TType.SET, 'orderTargets', (TType.STRUCT,(xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget, xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget.thrift_spec)), None, ), # 4
    (5, TType.I32, 'itemOrderType', None, None, ), # 5
  )

  def __init__(self, orderCreateTimePeriod=None, orderEndTimePeriod=None, subAccountIds=None, orderTargets=None, itemOrderType=None,):
    self.orderCreateTimePeriod = orderCreateTimePeriod
    self.orderEndTimePeriod = orderEndTimePeriod
    self.subAccountIds = subAccountIds
    self.orderTargets = orderTargets
    self.itemOrderType = itemOrderType

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRUCT:
          self.orderCreateTimePeriod = QueryTimePeriod()
          self.orderCreateTimePeriod.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.STRUCT:
          self.orderEndTimePeriod = QueryTimePeriod()
          self.orderEndTimePeriod.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.SET:
          self.subAccountIds = set()
          (_etype3, _size0) = iprot.readSetBegin()
          for _i4 in xrange(_size0):
            _elem5 = iprot.readI64();
            self.subAccountIds.add(_elem5)
          iprot.readSetEnd()
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.SET:
          self.orderTargets = set()
          (_etype9, _size6) = iprot.readSetBegin()
          for _i10 in xrange(_size6):
            _elem11 = xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget()
            _elem11.read(iprot)
            self.orderTargets.add(_elem11)
          iprot.readSetEnd()
        else:
          iprot.skip(ftype)
      elif fid == 5:
        if ftype == TType.I32:
          self.itemOrderType = iprot.readI32();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('QueryXQOrderHisIndexItemOption')
    if self.orderCreateTimePeriod is not None:
      oprot.writeFieldBegin('orderCreateTimePeriod', TType.STRUCT, 1)
      self.orderCreateTimePeriod.write(oprot)
      oprot.writeFieldEnd()
    if self.orderEndTimePeriod is not None:
      oprot.writeFieldBegin('orderEndTimePeriod', TType.STRUCT, 2)
      self.orderEndTimePeriod.write(oprot)
      oprot.writeFieldEnd()
    if self.subAccountIds is not None:
      oprot.writeFieldBegin('subAccountIds', TType.SET, 3)
      oprot.writeSetBegin(TType.I64, len(self.subAccountIds))
      for iter12 in self.subAccountIds:
        oprot.writeI64(iter12)
      oprot.writeSetEnd()
      oprot.writeFieldEnd()
    if self.orderTargets is not None:
      oprot.writeFieldBegin('orderTargets', TType.SET, 4)
      oprot.writeSetBegin(TType.STRUCT, len(self.orderTargets))
      for iter13 in self.orderTargets:
        iter13.write(oprot)
      oprot.writeSetEnd()
      oprot.writeFieldEnd()
    if self.itemOrderType is not None:
      oprot.writeFieldBegin('itemOrderType', TType.I32, 5)
      oprot.writeI32(self.itemOrderType)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class HostingXQOrderHisIndexPage:
  """
  Attributes:
   - totalNum
   - resultList
  """

  thrift_spec = (
    None, # 0
    (1, TType.I32, 'totalNum', None, None, ), # 1
    (2, TType.LIST, 'resultList', (TType.STRUCT,(HostingXQOrderHisIndexItem, HostingXQOrderHisIndexItem.thrift_spec)), None, ), # 2
  )

  def __init__(self, totalNum=None, resultList=None,):
    self.totalNum = totalNum
    self.resultList = resultList

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.I32:
          self.totalNum = iprot.readI32();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.LIST:
          self.resultList = []
          (_etype17, _size14) = iprot.readListBegin()
          for _i18 in xrange(_size14):
            _elem19 = HostingXQOrderHisIndexItem()
            _elem19.read(iprot)
            self.resultList.append(_elem19)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('HostingXQOrderHisIndexPage')
    if self.totalNum is not None:
      oprot.writeFieldBegin('totalNum', TType.I32, 1)
      oprot.writeI32(self.totalNum)
      oprot.writeFieldEnd()
    if self.resultList is not None:
      oprot.writeFieldBegin('resultList', TType.LIST, 2)
      oprot.writeListBegin(TType.STRUCT, len(self.resultList))
      for iter20 in self.resultList:
        iter20.write(oprot)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class QueryXQTradeHisIndexItemOption:
  """
  Attributes:
   - tradeCreateTimePeriod
   - subAccountIds
   - tradeTargets
   - itemOrderType
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'tradeCreateTimePeriod', (QueryTimePeriod, QueryTimePeriod.thrift_spec), None, ), # 1
    (2, TType.SET, 'subAccountIds', (TType.I64,None), None, ), # 2
    (3, TType.SET, 'tradeTargets', (TType.STRUCT,(xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget, xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget.thrift_spec)), None, ), # 3
    (4, TType.I32, 'itemOrderType', None, None, ), # 4
  )

  def __init__(self, tradeCreateTimePeriod=None, subAccountIds=None, tradeTargets=None, itemOrderType=None,):
    self.tradeCreateTimePeriod = tradeCreateTimePeriod
    self.subAccountIds = subAccountIds
    self.tradeTargets = tradeTargets
    self.itemOrderType = itemOrderType

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRUCT:
          self.tradeCreateTimePeriod = QueryTimePeriod()
          self.tradeCreateTimePeriod.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.SET:
          self.subAccountIds = set()
          (_etype24, _size21) = iprot.readSetBegin()
          for _i25 in xrange(_size21):
            _elem26 = iprot.readI64();
            self.subAccountIds.add(_elem26)
          iprot.readSetEnd()
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.SET:
          self.tradeTargets = set()
          (_etype30, _size27) = iprot.readSetBegin()
          for _i31 in xrange(_size27):
            _elem32 = xueqiao.trade.hosting.arbitrage.thriftapi.ttypes.HostingXQTarget()
            _elem32.read(iprot)
            self.tradeTargets.add(_elem32)
          iprot.readSetEnd()
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.I32:
          self.itemOrderType = iprot.readI32();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('QueryXQTradeHisIndexItemOption')
    if self.tradeCreateTimePeriod is not None:
      oprot.writeFieldBegin('tradeCreateTimePeriod', TType.STRUCT, 1)
      self.tradeCreateTimePeriod.write(oprot)
      oprot.writeFieldEnd()
    if self.subAccountIds is not None:
      oprot.writeFieldBegin('subAccountIds', TType.SET, 2)
      oprot.writeSetBegin(TType.I64, len(self.subAccountIds))
      for iter33 in self.subAccountIds:
        oprot.writeI64(iter33)
      oprot.writeSetEnd()
      oprot.writeFieldEnd()
    if self.tradeTargets is not None:
      oprot.writeFieldBegin('tradeTargets', TType.SET, 3)
      oprot.writeSetBegin(TType.STRUCT, len(self.tradeTargets))
      for iter34 in self.tradeTargets:
        iter34.write(oprot)
      oprot.writeSetEnd()
      oprot.writeFieldEnd()
    if self.itemOrderType is not None:
      oprot.writeFieldBegin('itemOrderType', TType.I32, 4)
      oprot.writeI32(self.itemOrderType)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class HostingXQTradeHisIndexPage:
  """
  Attributes:
   - totalNum
   - resultList
  """

  thrift_spec = (
    None, # 0
    (1, TType.I32, 'totalNum', None, None, ), # 1
    (2, TType.LIST, 'resultList', (TType.STRUCT,(HostingXQTradeHisIndexItem, HostingXQTradeHisIndexItem.thrift_spec)), None, ), # 2
  )

  def __init__(self, totalNum=None, resultList=None,):
    self.totalNum = totalNum
    self.resultList = resultList

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.I32:
          self.totalNum = iprot.readI32();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.LIST:
          self.resultList = []
          (_etype38, _size35) = iprot.readListBegin()
          for _i39 in xrange(_size35):
            _elem40 = HostingXQTradeHisIndexItem()
            _elem40.read(iprot)
            self.resultList.append(_elem40)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('HostingXQTradeHisIndexPage')
    if self.totalNum is not None:
      oprot.writeFieldBegin('totalNum', TType.I32, 1)
      oprot.writeI32(self.totalNum)
      oprot.writeFieldEnd()
    if self.resultList is not None:
      oprot.writeFieldBegin('resultList', TType.LIST, 2)
      oprot.writeListBegin(TType.STRUCT, len(self.resultList))
      for iter41 in self.resultList:
        iter41.write(oprot)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
