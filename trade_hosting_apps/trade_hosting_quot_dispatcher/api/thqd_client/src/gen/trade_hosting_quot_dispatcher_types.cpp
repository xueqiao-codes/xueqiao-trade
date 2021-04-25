/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#include "trade_hosting_quot_dispatcher_types.h"

#include <algorithm>

namespace xueqiao { namespace trade { namespace hosting { namespace quot { namespace dispatcher {

const char* SyncQuotTopic::ascii_fingerprint = "D0297FC5011701BD87898CC36146A565";
const uint8_t SyncQuotTopic::binary_fingerprint[16] = {0xD0,0x29,0x7F,0xC5,0x01,0x17,0x01,0xBD,0x87,0x89,0x8C,0xC3,0x61,0x46,0xA5,0x65};

uint32_t SyncQuotTopic::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->platform);
          this->__isset.platform = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->contractSymbol);
          this->__isset.contractSymbol = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t SyncQuotTopic::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("SyncQuotTopic");

  if (this->__isset.platform) {
    xfer += oprot->writeFieldBegin("platform", ::apache::thrift::protocol::T_STRING, 1);
    xfer += oprot->writeString(this->platform);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.contractSymbol) {
    xfer += oprot->writeFieldBegin("contractSymbol", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->contractSymbol);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(SyncQuotTopic &a, SyncQuotTopic &b) {
  using ::std::swap;
  swap(a.platform, b.platform);
  swap(a.contractSymbol, b.contractSymbol);
  swap(a.__isset, b.__isset);
}

const char* SyncTopicsRequest::ascii_fingerprint = "B33D7BC9DE434DAA5357867455C5BB4A";
const uint8_t SyncTopicsRequest::binary_fingerprint[16] = {0xB3,0x3D,0x7B,0xC9,0xDE,0x43,0x4D,0xAA,0x53,0x57,0x86,0x74,0x55,0xC5,0xBB,0x4A};

uint32_t SyncTopicsRequest::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->consumerKey);
          this->__isset.consumerKey = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_LIST) {
          {
            this->quotTopics.clear();
            uint32_t _size0;
            ::apache::thrift::protocol::TType _etype3;
            xfer += iprot->readListBegin(_etype3, _size0);
            this->quotTopics.resize(_size0);
            uint32_t _i4;
            for (_i4 = 0; _i4 < _size0; ++_i4)
            {
              xfer += this->quotTopics[_i4].read(iprot);
            }
            xfer += iprot->readListEnd();
          }
          this->__isset.quotTopics = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t SyncTopicsRequest::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("SyncTopicsRequest");

  if (this->__isset.consumerKey) {
    xfer += oprot->writeFieldBegin("consumerKey", ::apache::thrift::protocol::T_STRING, 1);
    xfer += oprot->writeString(this->consumerKey);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.quotTopics) {
    xfer += oprot->writeFieldBegin("quotTopics", ::apache::thrift::protocol::T_LIST, 2);
    {
      xfer += oprot->writeListBegin(::apache::thrift::protocol::T_STRUCT, static_cast<uint32_t>(this->quotTopics.size()));
      std::vector<SyncQuotTopic> ::const_iterator _iter5;
      for (_iter5 = this->quotTopics.begin(); _iter5 != this->quotTopics.end(); ++_iter5)
      {
        xfer += (*_iter5).write(oprot);
      }
      xfer += oprot->writeListEnd();
    }
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(SyncTopicsRequest &a, SyncTopicsRequest &b) {
  using ::std::swap;
  swap(a.consumerKey, b.consumerKey);
  swap(a.quotTopics, b.quotTopics);
  swap(a.__isset, b.__isset);
}

}}}}} // namespace
