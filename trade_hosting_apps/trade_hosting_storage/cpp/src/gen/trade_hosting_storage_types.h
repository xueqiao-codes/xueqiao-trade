/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef trade_hosting_storage_TYPES_H
#define trade_hosting_storage_TYPES_H

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <thrift/cxxfunctional.h>
#include "comm_types.h"
#include "trade_hosting_basic_types.h"
#include "broker_types.h"


namespace xueqiao { namespace trade { namespace hosting { namespace storage { namespace thriftapi {

typedef struct _TradeAccountInvalidDescription__isset {
  _TradeAccountInvalidDescription__isset() : invalidErrorCode(false), apiRetCode(false), invalidReason(false) {}
  bool invalidErrorCode;
  bool apiRetCode;
  bool invalidReason;
} _TradeAccountInvalidDescription__isset;

class TradeAccountInvalidDescription {
 public:

  static const char* ascii_fingerprint; // = "F274D66FD49A613E4D8C874F5A58A038";
  static const uint8_t binary_fingerprint[16]; // = {0xF2,0x74,0xD6,0x6F,0xD4,0x9A,0x61,0x3E,0x4D,0x8C,0x87,0x4F,0x5A,0x58,0xA0,0x38};

  TradeAccountInvalidDescription() : invalidErrorCode(0), apiRetCode(0), invalidReason() {
  }

  virtual ~TradeAccountInvalidDescription() throw() {}

  int32_t invalidErrorCode;
  int32_t apiRetCode;
  std::string invalidReason;

  _TradeAccountInvalidDescription__isset __isset;

  void __set_invalidErrorCode(const int32_t val) {
    invalidErrorCode = val;
    __isset.invalidErrorCode = true;
  }

  void __set_apiRetCode(const int32_t val) {
    apiRetCode = val;
    __isset.apiRetCode = true;
  }

  void __set_invalidReason(const std::string& val) {
    invalidReason = val;
    __isset.invalidReason = true;
  }

  bool operator == (const TradeAccountInvalidDescription & rhs) const
  {
    if (__isset.invalidErrorCode != rhs.__isset.invalidErrorCode)
      return false;
    else if (__isset.invalidErrorCode && !(invalidErrorCode == rhs.invalidErrorCode))
      return false;
    if (__isset.apiRetCode != rhs.__isset.apiRetCode)
      return false;
    else if (__isset.apiRetCode && !(apiRetCode == rhs.apiRetCode))
      return false;
    if (__isset.invalidReason != rhs.__isset.invalidReason)
      return false;
    else if (__isset.invalidReason && !(invalidReason == rhs.invalidReason))
      return false;
    return true;
  }
  bool operator != (const TradeAccountInvalidDescription &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const TradeAccountInvalidDescription & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(TradeAccountInvalidDescription &a, TradeAccountInvalidDescription &b);

typedef struct _UpdateConfigDescription__isset {
  _UpdateConfigDescription__isset() : configArea(false), configKey(false), configVersion(false), configContent(false), notifyEventClassName(false), notifyEventBinary(false) {}
  bool configArea;
  bool configKey;
  bool configVersion;
  bool configContent;
  bool notifyEventClassName;
  bool notifyEventBinary;
} _UpdateConfigDescription__isset;

class UpdateConfigDescription {
 public:

  static const char* ascii_fingerprint; // = "CB2E67103B69C59024C721D71893FCE5";
  static const uint8_t binary_fingerprint[16]; // = {0xCB,0x2E,0x67,0x10,0x3B,0x69,0xC5,0x90,0x24,0xC7,0x21,0xD7,0x18,0x93,0xFC,0xE5};

  UpdateConfigDescription() : configArea(), configKey(), configVersion(0), configContent(), notifyEventClassName(), notifyEventBinary() {
  }

  virtual ~UpdateConfigDescription() throw() {}

  std::string configArea;
  std::string configKey;
  int32_t configVersion;
  std::string configContent;
  std::string notifyEventClassName;
  std::string notifyEventBinary;

  _UpdateConfigDescription__isset __isset;

  void __set_configArea(const std::string& val) {
    configArea = val;
    __isset.configArea = true;
  }

  void __set_configKey(const std::string& val) {
    configKey = val;
    __isset.configKey = true;
  }

  void __set_configVersion(const int32_t val) {
    configVersion = val;
    __isset.configVersion = true;
  }

  void __set_configContent(const std::string& val) {
    configContent = val;
    __isset.configContent = true;
  }

  void __set_notifyEventClassName(const std::string& val) {
    notifyEventClassName = val;
    __isset.notifyEventClassName = true;
  }

  void __set_notifyEventBinary(const std::string& val) {
    notifyEventBinary = val;
    __isset.notifyEventBinary = true;
  }

  bool operator == (const UpdateConfigDescription & rhs) const
  {
    if (__isset.configArea != rhs.__isset.configArea)
      return false;
    else if (__isset.configArea && !(configArea == rhs.configArea))
      return false;
    if (__isset.configKey != rhs.__isset.configKey)
      return false;
    else if (__isset.configKey && !(configKey == rhs.configKey))
      return false;
    if (__isset.configVersion != rhs.__isset.configVersion)
      return false;
    else if (__isset.configVersion && !(configVersion == rhs.configVersion))
      return false;
    if (__isset.configContent != rhs.__isset.configContent)
      return false;
    else if (__isset.configContent && !(configContent == rhs.configContent))
      return false;
    if (__isset.notifyEventClassName != rhs.__isset.notifyEventClassName)
      return false;
    else if (__isset.notifyEventClassName && !(notifyEventClassName == rhs.notifyEventClassName))
      return false;
    if (__isset.notifyEventBinary != rhs.__isset.notifyEventBinary)
      return false;
    else if (__isset.notifyEventBinary && !(notifyEventBinary == rhs.notifyEventBinary))
      return false;
    return true;
  }
  bool operator != (const UpdateConfigDescription &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const UpdateConfigDescription & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(UpdateConfigDescription &a, UpdateConfigDescription &b);

}}}}} // namespace

#endif
