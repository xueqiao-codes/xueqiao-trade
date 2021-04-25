/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef broker_TYPES_H
#define broker_TYPES_H

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <thrift/cxxfunctional.h>




struct BrokerAccessStatus {
  enum type {
    NEW = 0,
    EDIT = 1,
    VERIFIED_CORRECT = 2,
    SYNCHRONIZED = 3
  };
};

extern const std::map<int, const char*> _BrokerAccessStatus_VALUES_TO_NAMES;

struct BrokerAccessWorkingStatus {
  enum type {
    NOT_WORKING = 0,
    WORKING = 1
  };
};

extern const std::map<int, const char*> _BrokerAccessWorkingStatus_VALUES_TO_NAMES;

struct TechPlatformEnv {
  enum type {
    NONE = 0,
    REAL = 1,
    SIM = 2
  };
};

extern const std::map<int, const char*> _TechPlatformEnv_VALUES_TO_NAMES;

struct BrokerPlatform {
  enum type {
    NONE = 0,
    CTP = 1,
    ESUNNY = 2,
    SP = 3,
    ESUNNY_3 = 4
  };
};

extern const std::map<int, const char*> _BrokerPlatform_VALUES_TO_NAMES;

struct BrokerErrorCode {
  enum type {
    BROKER_NOT_FOUND = 1000,
    BROKER_ENG_NAME_EXIST = 1001,
    BROKER_CN_NAME_EXIST = 1002,
    BROKER_ACCESS_NOT_VERIFIED = 1003,
    BROKER_EXIST = 1004,
    BROKER_ACCESS_EXIST = 1005,
    BROKER_ACCESS_NOT_FOUND = 1006,
    BROKER_ACCESS_BROKERID_NO_CHANGE = 1007,
    BROKER_ACCESS_PLATFORM_NO_CHANGE = 1008,
    BROKER_ACCESS_NOT_WORKING = 1009,
    BROKER_ACCESS_WORKING = 1010,
    BROKER_ACCESS_ADDRESS_ERROR = 1011,
    BROKER_ACCESS_ADDRESS_PORT_ERROR = 1012,
    BROKER_ACCESS_TECH_PLATFORM_MUST_SET = 1013,
    BROKER_ACCESS_TRADE_ADDRESS_MUST_SET = 1014,
    BROKER_ACCESS_TECH_PLATFORM_ENV_MUST_SET = 1015,
    BROKER_ACCESS_MAPPING_FILE_MUST_SET = 1016,
    BROKER_ACCESS_CUSTOM_INFO_MUST_SET = 1017,
    BROKER_ACCESS_PLATFORM_ENV_NO_CHANGE = 1018,
    BROKER_ACCESS_CUSTOM_INFO_NO_CHANGE = 1019
  };
};

extern const std::map<int, const char*> _BrokerErrorCode_VALUES_TO_NAMES;

typedef struct _AccessAddress__isset {
  _AccessAddress__isset() : address(false), port(false) {}
  bool address;
  bool port;
} _AccessAddress__isset;

class AccessAddress {
 public:

  static const char* ascii_fingerprint; // = "5B2E301BB39FF8267F51DF4E0A3C43D2";
  static const uint8_t binary_fingerprint[16]; // = {0x5B,0x2E,0x30,0x1B,0xB3,0x9F,0xF8,0x26,0x7F,0x51,0xDF,0x4E,0x0A,0x3C,0x43,0xD2};

  AccessAddress() : address(), port(0) {
  }

  virtual ~AccessAddress() throw() {}

  std::string address;
  int32_t port;

  _AccessAddress__isset __isset;

  void __set_address(const std::string& val) {
    address = val;
    __isset.address = true;
  }

  void __set_port(const int32_t val) {
    port = val;
    __isset.port = true;
  }

  bool operator == (const AccessAddress & rhs) const
  {
    if (__isset.address != rhs.__isset.address)
      return false;
    else if (__isset.address && !(address == rhs.address))
      return false;
    if (__isset.port != rhs.__isset.port)
      return false;
    else if (__isset.port && !(port == rhs.port))
      return false;
    return true;
  }
  bool operator != (const AccessAddress &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const AccessAddress & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(AccessAddress &a, AccessAddress &b);

typedef struct _BrokerEntry__isset {
  _BrokerEntry__isset() : brokerId(false), engName(false), cnName(false), note(false), lastModityTimestamp(false), createTimestamp(false) {}
  bool brokerId;
  bool engName;
  bool cnName;
  bool note;
  bool lastModityTimestamp;
  bool createTimestamp;
} _BrokerEntry__isset;

class BrokerEntry {
 public:

  static const char* ascii_fingerprint; // = "1F3C5E2048F7CCE93BE90EC362CD6A05";
  static const uint8_t binary_fingerprint[16]; // = {0x1F,0x3C,0x5E,0x20,0x48,0xF7,0xCC,0xE9,0x3B,0xE9,0x0E,0xC3,0x62,0xCD,0x6A,0x05};

  BrokerEntry() : brokerId(0), engName(), cnName(), note(), lastModityTimestamp(0), createTimestamp(0) {
  }

  virtual ~BrokerEntry() throw() {}

  int32_t brokerId;
  std::string engName;
  std::string cnName;
  std::string note;
  int64_t lastModityTimestamp;
  int64_t createTimestamp;

  _BrokerEntry__isset __isset;

  void __set_brokerId(const int32_t val) {
    brokerId = val;
    __isset.brokerId = true;
  }

  void __set_engName(const std::string& val) {
    engName = val;
    __isset.engName = true;
  }

  void __set_cnName(const std::string& val) {
    cnName = val;
    __isset.cnName = true;
  }

  void __set_note(const std::string& val) {
    note = val;
    __isset.note = true;
  }

  void __set_lastModityTimestamp(const int64_t val) {
    lastModityTimestamp = val;
    __isset.lastModityTimestamp = true;
  }

  void __set_createTimestamp(const int64_t val) {
    createTimestamp = val;
    __isset.createTimestamp = true;
  }

  bool operator == (const BrokerEntry & rhs) const
  {
    if (__isset.brokerId != rhs.__isset.brokerId)
      return false;
    else if (__isset.brokerId && !(brokerId == rhs.brokerId))
      return false;
    if (__isset.engName != rhs.__isset.engName)
      return false;
    else if (__isset.engName && !(engName == rhs.engName))
      return false;
    if (__isset.cnName != rhs.__isset.cnName)
      return false;
    else if (__isset.cnName && !(cnName == rhs.cnName))
      return false;
    if (__isset.note != rhs.__isset.note)
      return false;
    else if (__isset.note && !(note == rhs.note))
      return false;
    if (__isset.lastModityTimestamp != rhs.__isset.lastModityTimestamp)
      return false;
    else if (__isset.lastModityTimestamp && !(lastModityTimestamp == rhs.lastModityTimestamp))
      return false;
    if (__isset.createTimestamp != rhs.__isset.createTimestamp)
      return false;
    else if (__isset.createTimestamp && !(createTimestamp == rhs.createTimestamp))
      return false;
    return true;
  }
  bool operator != (const BrokerEntry &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const BrokerEntry & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(BrokerEntry &a, BrokerEntry &b);

typedef struct _BrokerAccessEntry__isset {
  _BrokerAccessEntry__isset() : entryId(false), brokerId(false), platform(false), tradeAddresses(false), customInfoMap(false), status(false), lastModityTimestamp(false), createTimestamp(false), workingStatus(false), techPlatformEnv(false), accessName(false), quotaAddresses(false) {}
  bool entryId;
  bool brokerId;
  bool platform;
  bool tradeAddresses;
  bool customInfoMap;
  bool status;
  bool lastModityTimestamp;
  bool createTimestamp;
  bool workingStatus;
  bool techPlatformEnv;
  bool accessName;
  bool quotaAddresses;
} _BrokerAccessEntry__isset;

class BrokerAccessEntry {
 public:

  static const char* ascii_fingerprint; // = "B02E622F8C0C2B87567AEB9C5F33B325";
  static const uint8_t binary_fingerprint[16]; // = {0xB0,0x2E,0x62,0x2F,0x8C,0x0C,0x2B,0x87,0x56,0x7A,0xEB,0x9C,0x5F,0x33,0xB3,0x25};

  BrokerAccessEntry() : entryId(0), brokerId(0), platform((BrokerPlatform::type)0), status((BrokerAccessStatus::type)0), lastModityTimestamp(0), createTimestamp(0), workingStatus((BrokerAccessWorkingStatus::type)0), techPlatformEnv((TechPlatformEnv::type)0), accessName() {
  }

  virtual ~BrokerAccessEntry() throw() {}

  int32_t entryId;
  int32_t brokerId;
  BrokerPlatform::type platform;
  std::vector<AccessAddress>  tradeAddresses;
  std::map<std::string, std::string>  customInfoMap;
  BrokerAccessStatus::type status;
  int64_t lastModityTimestamp;
  int64_t createTimestamp;
  BrokerAccessWorkingStatus::type workingStatus;
  TechPlatformEnv::type techPlatformEnv;
  std::string accessName;
  std::vector<AccessAddress>  quotaAddresses;

  _BrokerAccessEntry__isset __isset;

  void __set_entryId(const int32_t val) {
    entryId = val;
    __isset.entryId = true;
  }

  void __set_brokerId(const int32_t val) {
    brokerId = val;
    __isset.brokerId = true;
  }

  void __set_platform(const BrokerPlatform::type val) {
    platform = val;
    __isset.platform = true;
  }

  void __set_tradeAddresses(const std::vector<AccessAddress> & val) {
    tradeAddresses = val;
    __isset.tradeAddresses = true;
  }

  void __set_customInfoMap(const std::map<std::string, std::string> & val) {
    customInfoMap = val;
    __isset.customInfoMap = true;
  }

  void __set_status(const BrokerAccessStatus::type val) {
    status = val;
    __isset.status = true;
  }

  void __set_lastModityTimestamp(const int64_t val) {
    lastModityTimestamp = val;
    __isset.lastModityTimestamp = true;
  }

  void __set_createTimestamp(const int64_t val) {
    createTimestamp = val;
    __isset.createTimestamp = true;
  }

  void __set_workingStatus(const BrokerAccessWorkingStatus::type val) {
    workingStatus = val;
    __isset.workingStatus = true;
  }

  void __set_techPlatformEnv(const TechPlatformEnv::type val) {
    techPlatformEnv = val;
    __isset.techPlatformEnv = true;
  }

  void __set_accessName(const std::string& val) {
    accessName = val;
    __isset.accessName = true;
  }

  void __set_quotaAddresses(const std::vector<AccessAddress> & val) {
    quotaAddresses = val;
    __isset.quotaAddresses = true;
  }

  bool operator == (const BrokerAccessEntry & rhs) const
  {
    if (__isset.entryId != rhs.__isset.entryId)
      return false;
    else if (__isset.entryId && !(entryId == rhs.entryId))
      return false;
    if (__isset.brokerId != rhs.__isset.brokerId)
      return false;
    else if (__isset.brokerId && !(brokerId == rhs.brokerId))
      return false;
    if (__isset.platform != rhs.__isset.platform)
      return false;
    else if (__isset.platform && !(platform == rhs.platform))
      return false;
    if (__isset.tradeAddresses != rhs.__isset.tradeAddresses)
      return false;
    else if (__isset.tradeAddresses && !(tradeAddresses == rhs.tradeAddresses))
      return false;
    if (__isset.customInfoMap != rhs.__isset.customInfoMap)
      return false;
    else if (__isset.customInfoMap && !(customInfoMap == rhs.customInfoMap))
      return false;
    if (__isset.status != rhs.__isset.status)
      return false;
    else if (__isset.status && !(status == rhs.status))
      return false;
    if (__isset.lastModityTimestamp != rhs.__isset.lastModityTimestamp)
      return false;
    else if (__isset.lastModityTimestamp && !(lastModityTimestamp == rhs.lastModityTimestamp))
      return false;
    if (__isset.createTimestamp != rhs.__isset.createTimestamp)
      return false;
    else if (__isset.createTimestamp && !(createTimestamp == rhs.createTimestamp))
      return false;
    if (__isset.workingStatus != rhs.__isset.workingStatus)
      return false;
    else if (__isset.workingStatus && !(workingStatus == rhs.workingStatus))
      return false;
    if (__isset.techPlatformEnv != rhs.__isset.techPlatformEnv)
      return false;
    else if (__isset.techPlatformEnv && !(techPlatformEnv == rhs.techPlatformEnv))
      return false;
    if (__isset.accessName != rhs.__isset.accessName)
      return false;
    else if (__isset.accessName && !(accessName == rhs.accessName))
      return false;
    if (__isset.quotaAddresses != rhs.__isset.quotaAddresses)
      return false;
    else if (__isset.quotaAddresses && !(quotaAddresses == rhs.quotaAddresses))
      return false;
    return true;
  }
  bool operator != (const BrokerAccessEntry &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const BrokerAccessEntry & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(BrokerAccessEntry &a, BrokerAccessEntry &b);



#endif
