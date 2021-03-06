/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef TradeHostingQuotDispatcher_H
#define TradeHostingQuotDispatcher_H

#include <thrift/TDispatchProcessor.h>
#include "trade_hosting_quot_dispatcher_types.h"

namespace xueqiao { namespace trade { namespace hosting { namespace quot { namespace dispatcher {

class TradeHostingQuotDispatcherIf {
 public:
  virtual ~TradeHostingQuotDispatcherIf() {}
  virtual void syncTopics(const  ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest) = 0;
};

class TradeHostingQuotDispatcherIfFactory {
 public:
  typedef TradeHostingQuotDispatcherIf Handler;

  virtual ~TradeHostingQuotDispatcherIfFactory() {}

  virtual TradeHostingQuotDispatcherIf* getHandler(const ::apache::thrift::TConnectionInfo& connInfo) = 0;
  virtual void releaseHandler(TradeHostingQuotDispatcherIf* /* handler */) = 0;
};

class TradeHostingQuotDispatcherIfSingletonFactory : virtual public TradeHostingQuotDispatcherIfFactory {
 public:
  TradeHostingQuotDispatcherIfSingletonFactory(const boost::shared_ptr<TradeHostingQuotDispatcherIf>& iface) : iface_(iface) {}
  virtual ~TradeHostingQuotDispatcherIfSingletonFactory() {}

  virtual TradeHostingQuotDispatcherIf* getHandler(const ::apache::thrift::TConnectionInfo&) {
    return iface_.get();
  }
  virtual void releaseHandler(TradeHostingQuotDispatcherIf* /* handler */) {}

 protected:
  boost::shared_ptr<TradeHostingQuotDispatcherIf> iface_;
};

class TradeHostingQuotDispatcherNull : virtual public TradeHostingQuotDispatcherIf {
 public:
  virtual ~TradeHostingQuotDispatcherNull() {}
  void syncTopics(const  ::platform::comm::PlatformArgs& /* platformArgs */, const SyncTopicsRequest& /* syncRequest */) {
    return;
  }
};

typedef struct _TradeHostingQuotDispatcher_syncTopics_args__isset {
  _TradeHostingQuotDispatcher_syncTopics_args__isset() : platformArgs(false), syncRequest(false) {}
  bool platformArgs;
  bool syncRequest;
} _TradeHostingQuotDispatcher_syncTopics_args__isset;

class TradeHostingQuotDispatcher_syncTopics_args {
 public:

  TradeHostingQuotDispatcher_syncTopics_args() {
  }

  virtual ~TradeHostingQuotDispatcher_syncTopics_args() throw() {}

   ::platform::comm::PlatformArgs platformArgs;
  SyncTopicsRequest syncRequest;

  _TradeHostingQuotDispatcher_syncTopics_args__isset __isset;

  void __set_platformArgs(const  ::platform::comm::PlatformArgs& val) {
    platformArgs = val;
  }

  void __set_syncRequest(const SyncTopicsRequest& val) {
    syncRequest = val;
  }

  bool operator == (const TradeHostingQuotDispatcher_syncTopics_args & rhs) const
  {
    if (!(platformArgs == rhs.platformArgs))
      return false;
    if (!(syncRequest == rhs.syncRequest))
      return false;
    return true;
  }
  bool operator != (const TradeHostingQuotDispatcher_syncTopics_args &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const TradeHostingQuotDispatcher_syncTopics_args & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};


class TradeHostingQuotDispatcher_syncTopics_pargs {
 public:


  virtual ~TradeHostingQuotDispatcher_syncTopics_pargs() throw() {}

  const  ::platform::comm::PlatformArgs* platformArgs;
  const SyncTopicsRequest* syncRequest;

  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

typedef struct _TradeHostingQuotDispatcher_syncTopics_result__isset {
  _TradeHostingQuotDispatcher_syncTopics_result__isset() : err(false) {}
  bool err;
} _TradeHostingQuotDispatcher_syncTopics_result__isset;

class TradeHostingQuotDispatcher_syncTopics_result {
 public:

  TradeHostingQuotDispatcher_syncTopics_result() {
  }

  virtual ~TradeHostingQuotDispatcher_syncTopics_result() throw() {}

   ::platform::comm::ErrorInfo err;

  _TradeHostingQuotDispatcher_syncTopics_result__isset __isset;

  void __set_err(const  ::platform::comm::ErrorInfo& val) {
    err = val;
  }

  bool operator == (const TradeHostingQuotDispatcher_syncTopics_result & rhs) const
  {
    if (!(err == rhs.err))
      return false;
    return true;
  }
  bool operator != (const TradeHostingQuotDispatcher_syncTopics_result &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const TradeHostingQuotDispatcher_syncTopics_result & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

typedef struct _TradeHostingQuotDispatcher_syncTopics_presult__isset {
  _TradeHostingQuotDispatcher_syncTopics_presult__isset() : err(false) {}
  bool err;
} _TradeHostingQuotDispatcher_syncTopics_presult__isset;

class TradeHostingQuotDispatcher_syncTopics_presult {
 public:


  virtual ~TradeHostingQuotDispatcher_syncTopics_presult() throw() {}

   ::platform::comm::ErrorInfo err;

  _TradeHostingQuotDispatcher_syncTopics_presult__isset __isset;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);

};

class TradeHostingQuotDispatcherClient : virtual public TradeHostingQuotDispatcherIf {
 public:
  TradeHostingQuotDispatcherClient(boost::shared_ptr< ::apache::thrift::protocol::TProtocol> prot) :
    piprot_(prot),
    poprot_(prot) {
    iprot_ = prot.get();
    oprot_ = prot.get();
  }
  TradeHostingQuotDispatcherClient(boost::shared_ptr< ::apache::thrift::protocol::TProtocol> iprot, boost::shared_ptr< ::apache::thrift::protocol::TProtocol> oprot) :
    piprot_(iprot),
    poprot_(oprot) {
    iprot_ = iprot.get();
    oprot_ = oprot.get();
  }
  boost::shared_ptr< ::apache::thrift::protocol::TProtocol> getInputProtocol() {
    return piprot_;
  }
  boost::shared_ptr< ::apache::thrift::protocol::TProtocol> getOutputProtocol() {
    return poprot_;
  }
  void syncTopics(const  ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest);
  void send_syncTopics(const  ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest);
  void recv_syncTopics();
 protected:
  boost::shared_ptr< ::apache::thrift::protocol::TProtocol> piprot_;
  boost::shared_ptr< ::apache::thrift::protocol::TProtocol> poprot_;
  ::apache::thrift::protocol::TProtocol* iprot_;
  ::apache::thrift::protocol::TProtocol* oprot_;
};

class TradeHostingQuotDispatcherProcessor : public ::apache::thrift::TDispatchProcessor {
 protected:
  boost::shared_ptr<TradeHostingQuotDispatcherIf> iface_;
  virtual bool dispatchCall(::apache::thrift::protocol::TProtocol* iprot, ::apache::thrift::protocol::TProtocol* oprot, const std::string& fname, int32_t seqid, void* callContext);
 private:
  typedef  void (TradeHostingQuotDispatcherProcessor::*ProcessFunction)(int32_t, ::apache::thrift::protocol::TProtocol*, ::apache::thrift::protocol::TProtocol*, void*);
  typedef std::map<std::string, ProcessFunction> ProcessMap;
  ProcessMap processMap_;
  void process_syncTopics(int32_t seqid, ::apache::thrift::protocol::TProtocol* iprot, ::apache::thrift::protocol::TProtocol* oprot, void* callContext);
 public:
  TradeHostingQuotDispatcherProcessor(boost::shared_ptr<TradeHostingQuotDispatcherIf> iface) :
    iface_(iface) {
    processMap_["syncTopics"] = &TradeHostingQuotDispatcherProcessor::process_syncTopics;
  }

  virtual ~TradeHostingQuotDispatcherProcessor() {}
};

class TradeHostingQuotDispatcherProcessorFactory : public ::apache::thrift::TProcessorFactory {
 public:
  TradeHostingQuotDispatcherProcessorFactory(const ::boost::shared_ptr< TradeHostingQuotDispatcherIfFactory >& handlerFactory) :
      handlerFactory_(handlerFactory) {}

  ::boost::shared_ptr< ::apache::thrift::TProcessor > getProcessor(const ::apache::thrift::TConnectionInfo& connInfo);

 protected:
  ::boost::shared_ptr< TradeHostingQuotDispatcherIfFactory > handlerFactory_;
};

class TradeHostingQuotDispatcherMultiface : virtual public TradeHostingQuotDispatcherIf {
 public:
  TradeHostingQuotDispatcherMultiface(std::vector<boost::shared_ptr<TradeHostingQuotDispatcherIf> >& ifaces) : ifaces_(ifaces) {
  }
  virtual ~TradeHostingQuotDispatcherMultiface() {}
 protected:
  std::vector<boost::shared_ptr<TradeHostingQuotDispatcherIf> > ifaces_;
  TradeHostingQuotDispatcherMultiface() {}
  void add(boost::shared_ptr<TradeHostingQuotDispatcherIf> iface) {
    ifaces_.push_back(iface);
  }
 public:
  void syncTopics(const  ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest) {
    size_t sz = ifaces_.size();
    size_t i = 0;
    for (; i < (sz - 1); ++i) {
      ifaces_[i]->syncTopics(platformArgs, syncRequest);
    }
    ifaces_[i]->syncTopics(platformArgs, syncRequest);
  }

};

}}}}} // namespace

#endif
