#
# -*- coding: utf-8 -*-
# Autogenerated by Thrift Compiler (0.9.1)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
from ttypes import *
from thrift.Thrift import TProcessor
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None


TradeHostingTaskNote_SERVICE_KEY=721
class Iface:
  def getTaskNotePage(self, platformArgs, qryOption, pageOption):
    """
    查询Notes

    Parameters:
     - platformArgs
     - qryOption
     - pageOption
    """
    pass

  def delTaskNote(self, platformArgs, noteType, noteKey):
    """
    按照Key删除Note

    Parameters:
     - platformArgs
     - noteType
     - noteKey
    """
    pass


class Client(Iface):
  def __init__(self, iprot, oprot=None):
    self._iprot = self._oprot = iprot
    if oprot is not None:
      self._oprot = oprot
    self._seqid = 0

  def getTaskNotePage(self, platformArgs, qryOption, pageOption):
    """
    查询Notes

    Parameters:
     - platformArgs
     - qryOption
     - pageOption
    """
    self.send_getTaskNotePage(platformArgs, qryOption, pageOption)
    return self.recv_getTaskNotePage()

  def send_getTaskNotePage(self, platformArgs, qryOption, pageOption):
    self._oprot.writeMessageBegin('getTaskNotePage', TMessageType.CALL, self._seqid)
    args = getTaskNotePage_args()
    args.platformArgs = platformArgs
    args.qryOption = qryOption
    args.pageOption = pageOption
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_getTaskNotePage(self):
    (fname, mtype, rseqid) = self._iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(self._iprot)
      self._iprot.readMessageEnd()
      raise x
    result = getTaskNotePage_result()
    result.read(self._iprot)
    self._iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    if result.err is not None:
      raise result.err
    raise TApplicationException(TApplicationException.MISSING_RESULT, "getTaskNotePage failed: unknown result");

  def delTaskNote(self, platformArgs, noteType, noteKey):
    """
    按照Key删除Note

    Parameters:
     - platformArgs
     - noteType
     - noteKey
    """
    self.send_delTaskNote(platformArgs, noteType, noteKey)
    self.recv_delTaskNote()

  def send_delTaskNote(self, platformArgs, noteType, noteKey):
    self._oprot.writeMessageBegin('delTaskNote', TMessageType.CALL, self._seqid)
    args = delTaskNote_args()
    args.platformArgs = platformArgs
    args.noteType = noteType
    args.noteKey = noteKey
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_delTaskNote(self):
    (fname, mtype, rseqid) = self._iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(self._iprot)
      self._iprot.readMessageEnd()
      raise x
    result = delTaskNote_result()
    result.read(self._iprot)
    self._iprot.readMessageEnd()
    if result.err is not None:
      raise result.err
    return


class Processor(Iface, TProcessor):
  def __init__(self, handler):
    self._handler = handler
    self._processMap = {}
    self._processMap["getTaskNotePage"] = Processor.process_getTaskNotePage
    self._processMap["delTaskNote"] = Processor.process_delTaskNote

  def process(self, iprot, oprot):
    (name, type, seqid) = iprot.readMessageBegin()
    if name not in self._processMap:
      iprot.skip(TType.STRUCT)
      iprot.readMessageEnd()
      x = TApplicationException(TApplicationException.UNKNOWN_METHOD, 'Unknown function %s' % (name))
      oprot.writeMessageBegin(name, TMessageType.EXCEPTION, seqid)
      x.write(oprot)
      oprot.writeMessageEnd()
      oprot.trans.flush()
      return
    else:
      self._processMap[name](self, seqid, iprot, oprot)
    return True

  def process_getTaskNotePage(self, seqid, iprot, oprot):
    args = getTaskNotePage_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = getTaskNotePage_result()
    try:
      result.success = self._handler.getTaskNotePage(args.platformArgs, args.qryOption, args.pageOption)
    except comm.ttypes.ErrorInfo, err:
      result.err = err
    oprot.writeMessageBegin("getTaskNotePage", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()

  def process_delTaskNote(self, seqid, iprot, oprot):
    args = delTaskNote_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = delTaskNote_result()
    try:
      self._handler.delTaskNote(args.platformArgs, args.noteType, args.noteKey)
    except comm.ttypes.ErrorInfo, err:
      result.err = err
    oprot.writeMessageBegin("delTaskNote", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()


# HELPER FUNCTIONS AND STRUCTURES

class getTaskNotePage_args:
  """
  Attributes:
   - platformArgs
   - qryOption
   - pageOption
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'platformArgs', (comm.ttypes.PlatformArgs, comm.ttypes.PlatformArgs.thrift_spec), None, ), # 1
    (2, TType.STRUCT, 'qryOption', (QueryTaskNoteOption, QueryTaskNoteOption.thrift_spec), None, ), # 2
    (3, TType.STRUCT, 'pageOption', (page.ttypes.IndexedPageOption, page.ttypes.IndexedPageOption.thrift_spec), None, ), # 3
  )

  def __init__(self, platformArgs=None, qryOption=None, pageOption=None,):
    self.platformArgs = platformArgs
    self.qryOption = qryOption
    self.pageOption = pageOption

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
          self.platformArgs = comm.ttypes.PlatformArgs()
          self.platformArgs.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.STRUCT:
          self.qryOption = QueryTaskNoteOption()
          self.qryOption.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.STRUCT:
          self.pageOption = page.ttypes.IndexedPageOption()
          self.pageOption.read(iprot)
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
    oprot.writeStructBegin('getTaskNotePage_args')
    if self.platformArgs is not None:
      oprot.writeFieldBegin('platformArgs', TType.STRUCT, 1)
      self.platformArgs.write(oprot)
      oprot.writeFieldEnd()
    if self.qryOption is not None:
      oprot.writeFieldBegin('qryOption', TType.STRUCT, 2)
      self.qryOption.write(oprot)
      oprot.writeFieldEnd()
    if self.pageOption is not None:
      oprot.writeFieldBegin('pageOption', TType.STRUCT, 3)
      self.pageOption.write(oprot)
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

class getTaskNotePage_result:
  """
  Attributes:
   - success
   - err
  """

  thrift_spec = (
    (0, TType.STRUCT, 'success', (HostingTaskNotePage, HostingTaskNotePage.thrift_spec), None, ), # 0
    (1, TType.STRUCT, 'err', (comm.ttypes.ErrorInfo, comm.ttypes.ErrorInfo.thrift_spec), None, ), # 1
  )

  def __init__(self, success=None, err=None,):
    self.success = success
    self.err = err

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.STRUCT:
          self.success = HostingTaskNotePage()
          self.success.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 1:
        if ftype == TType.STRUCT:
          self.err = comm.ttypes.ErrorInfo()
          self.err.read(iprot)
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
    oprot.writeStructBegin('getTaskNotePage_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.STRUCT, 0)
      self.success.write(oprot)
      oprot.writeFieldEnd()
    if self.err is not None:
      oprot.writeFieldBegin('err', TType.STRUCT, 1)
      self.err.write(oprot)
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

class delTaskNote_args:
  """
  Attributes:
   - platformArgs
   - noteType
   - noteKey
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'platformArgs', (comm.ttypes.PlatformArgs, comm.ttypes.PlatformArgs.thrift_spec), None, ), # 1
    (2, TType.I32, 'noteType', None, None, ), # 2
    (3, TType.STRUCT, 'noteKey', (HostingTaskNoteKey, HostingTaskNoteKey.thrift_spec), None, ), # 3
  )

  def __init__(self, platformArgs=None, noteType=None, noteKey=None,):
    self.platformArgs = platformArgs
    self.noteType = noteType
    self.noteKey = noteKey

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
          self.platformArgs = comm.ttypes.PlatformArgs()
          self.platformArgs.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I32:
          self.noteType = iprot.readI32();
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.STRUCT:
          self.noteKey = HostingTaskNoteKey()
          self.noteKey.read(iprot)
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
    oprot.writeStructBegin('delTaskNote_args')
    if self.platformArgs is not None:
      oprot.writeFieldBegin('platformArgs', TType.STRUCT, 1)
      self.platformArgs.write(oprot)
      oprot.writeFieldEnd()
    if self.noteType is not None:
      oprot.writeFieldBegin('noteType', TType.I32, 2)
      oprot.writeI32(self.noteType)
      oprot.writeFieldEnd()
    if self.noteKey is not None:
      oprot.writeFieldBegin('noteKey', TType.STRUCT, 3)
      self.noteKey.write(oprot)
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

class delTaskNote_result:
  """
  Attributes:
   - err
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'err', (comm.ttypes.ErrorInfo, comm.ttypes.ErrorInfo.thrift_spec), None, ), # 1
  )

  def __init__(self, err=None,):
    self.err = err

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
          self.err = comm.ttypes.ErrorInfo()
          self.err.read(iprot)
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
    oprot.writeStructBegin('delTaskNote_result')
    if self.err is not None:
      oprot.writeFieldBegin('err', TType.STRUCT, 1)
      self.err.write(oprot)
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
