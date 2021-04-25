/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#include "TradeHostingQuotDispatcher.h"

namespace xueqiao { namespace trade { namespace hosting { namespace quot { namespace dispatcher {

uint32_t TradeHostingQuotDispatcher_syncTopics_args::read(::apache::thrift::protocol::TProtocol* iprot) {

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
        if (ftype == ::apache::thrift::protocol::T_STRUCT) {
          xfer += this->platformArgs.read(iprot);
          this->__isset.platformArgs = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRUCT) {
          xfer += this->syncRequest.read(iprot);
          this->__isset.syncRequest = true;
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

uint32_t TradeHostingQuotDispatcher_syncTopics_args::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("TradeHostingQuotDispatcher_syncTopics_args");

  xfer += oprot->writeFieldBegin("platformArgs", ::apache::thrift::protocol::T_STRUCT, 1);
  xfer += this->platformArgs.write(oprot);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("syncRequest", ::apache::thrift::protocol::T_STRUCT, 2);
  xfer += this->syncRequest.write(oprot);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

uint32_t TradeHostingQuotDispatcher_syncTopics_pargs::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("TradeHostingQuotDispatcher_syncTopics_pargs");

  xfer += oprot->writeFieldBegin("platformArgs", ::apache::thrift::protocol::T_STRUCT, 1);
  xfer += (*(this->platformArgs)).write(oprot);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("syncRequest", ::apache::thrift::protocol::T_STRUCT, 2);
  xfer += (*(this->syncRequest)).write(oprot);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

uint32_t TradeHostingQuotDispatcher_syncTopics_result::read(::apache::thrift::protocol::TProtocol* iprot) {

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
        if (ftype == ::apache::thrift::protocol::T_STRUCT) {
          xfer += this->err.read(iprot);
          this->__isset.err = true;
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

uint32_t TradeHostingQuotDispatcher_syncTopics_result::write(::apache::thrift::protocol::TProtocol* oprot) const {

  uint32_t xfer = 0;

  xfer += oprot->writeStructBegin("TradeHostingQuotDispatcher_syncTopics_result");

  if (this->__isset.err) {
    xfer += oprot->writeFieldBegin("err", ::apache::thrift::protocol::T_STRUCT, 1);
    xfer += this->err.write(oprot);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

uint32_t TradeHostingQuotDispatcher_syncTopics_presult::read(::apache::thrift::protocol::TProtocol* iprot) {

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
        if (ftype == ::apache::thrift::protocol::T_STRUCT) {
          xfer += this->err.read(iprot);
          this->__isset.err = true;
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

void TradeHostingQuotDispatcherClient::syncTopics(const  ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest)
{
  send_syncTopics(platformArgs, syncRequest);
  recv_syncTopics();
}

void TradeHostingQuotDispatcherClient::send_syncTopics(const  ::platform::comm::PlatformArgs& platformArgs, const SyncTopicsRequest& syncRequest)
{
  int32_t cseqid = 0;
  oprot_->writeMessageBegin("syncTopics", ::apache::thrift::protocol::T_CALL, cseqid);

  TradeHostingQuotDispatcher_syncTopics_pargs args;
  args.platformArgs = &platformArgs;
  args.syncRequest = &syncRequest;
  args.write(oprot_);

  oprot_->writeMessageEnd();
  oprot_->getTransport()->writeEnd();
  oprot_->getTransport()->flush();
}

void TradeHostingQuotDispatcherClient::recv_syncTopics()
{

  int32_t rseqid = 0;
  std::string fname;
  ::apache::thrift::protocol::TMessageType mtype;

  iprot_->readMessageBegin(fname, mtype, rseqid);
  if (mtype == ::apache::thrift::protocol::T_EXCEPTION) {
    ::apache::thrift::TApplicationException x;
    x.read(iprot_);
    iprot_->readMessageEnd();
    iprot_->getTransport()->readEnd();
    throw x;
  }
  if (mtype != ::apache::thrift::protocol::T_REPLY) {
    iprot_->skip(::apache::thrift::protocol::T_STRUCT);
    iprot_->readMessageEnd();
    iprot_->getTransport()->readEnd();
  }
  if (fname.compare("syncTopics") != 0) {
    iprot_->skip(::apache::thrift::protocol::T_STRUCT);
    iprot_->readMessageEnd();
    iprot_->getTransport()->readEnd();
  }
  TradeHostingQuotDispatcher_syncTopics_presult result;
  result.read(iprot_);
  iprot_->readMessageEnd();
  iprot_->getTransport()->readEnd();

  if (result.__isset.err) {
    throw result.err;
  }
  return;
}

bool TradeHostingQuotDispatcherProcessor::dispatchCall(::apache::thrift::protocol::TProtocol* iprot, ::apache::thrift::protocol::TProtocol* oprot, const std::string& fname, int32_t seqid, void* callContext) {
  ProcessMap::iterator pfn;
  pfn = processMap_.find(fname);
  if (pfn == processMap_.end()) {
    iprot->skip(::apache::thrift::protocol::T_STRUCT);
    iprot->readMessageEnd();
    iprot->getTransport()->readEnd();
    ::apache::thrift::TApplicationException x(::apache::thrift::TApplicationException::UNKNOWN_METHOD, "Invalid method name: '"+fname+"'");
    oprot->writeMessageBegin(fname, ::apache::thrift::protocol::T_EXCEPTION, seqid);
    x.write(oprot);
    oprot->writeMessageEnd();
    oprot->getTransport()->writeEnd();
    oprot->getTransport()->flush();
    return true;
  }
  (this->*(pfn->second))(seqid, iprot, oprot, callContext);
  return true;
}

void TradeHostingQuotDispatcherProcessor::process_syncTopics(int32_t seqid, ::apache::thrift::protocol::TProtocol* iprot, ::apache::thrift::protocol::TProtocol* oprot, void* callContext)
{
  void* ctx = NULL;
  if (this->eventHandler_.get() != NULL) {
    ctx = this->eventHandler_->getContext("TradeHostingQuotDispatcher.syncTopics", callContext);
  }
  ::apache::thrift::TProcessorContextFreer freer(this->eventHandler_.get(), ctx, "TradeHostingQuotDispatcher.syncTopics");

  if (this->eventHandler_.get() != NULL) {
    this->eventHandler_->preRead(ctx, "TradeHostingQuotDispatcher.syncTopics");
  }

  TradeHostingQuotDispatcher_syncTopics_args args;
  args.read(iprot);
  iprot->readMessageEnd();
  uint32_t bytes = iprot->getTransport()->readEnd();

  if (this->eventHandler_.get() != NULL) {
    this->eventHandler_->postRead(ctx, "TradeHostingQuotDispatcher.syncTopics", bytes);
  }

  TradeHostingQuotDispatcher_syncTopics_result result;
  try {
    iface_->syncTopics(args.platformArgs, args.syncRequest);
  } catch ( ::platform::comm::ErrorInfo &err) {
    result.err = err;
    result.__isset.err = true;
  } catch (const std::exception& e) {
    if (this->eventHandler_.get() != NULL) {
      this->eventHandler_->handlerError(ctx, "TradeHostingQuotDispatcher.syncTopics");
    }

    ::apache::thrift::TApplicationException x(e.what());
    oprot->writeMessageBegin("syncTopics", ::apache::thrift::protocol::T_EXCEPTION, seqid);
    x.write(oprot);
    oprot->writeMessageEnd();
    oprot->getTransport()->writeEnd();
    oprot->getTransport()->flush();
    return;
  }

  if (this->eventHandler_.get() != NULL) {
    this->eventHandler_->preWrite(ctx, "TradeHostingQuotDispatcher.syncTopics");
  }

  oprot->writeMessageBegin("syncTopics", ::apache::thrift::protocol::T_REPLY, seqid);
  result.write(oprot);
  oprot->writeMessageEnd();
  bytes = oprot->getTransport()->writeEnd();
  oprot->getTransport()->flush();

  if (this->eventHandler_.get() != NULL) {
    this->eventHandler_->postWrite(ctx, "TradeHostingQuotDispatcher.syncTopics", bytes);
  }
}

::boost::shared_ptr< ::apache::thrift::TProcessor > TradeHostingQuotDispatcherProcessorFactory::getProcessor(const ::apache::thrift::TConnectionInfo& connInfo) {
  ::apache::thrift::ReleaseHandler< TradeHostingQuotDispatcherIfFactory > cleanup(handlerFactory_);
  ::boost::shared_ptr< TradeHostingQuotDispatcherIf > handler(handlerFactory_->getHandler(connInfo), cleanup);
  ::boost::shared_ptr< ::apache::thrift::TProcessor > processor(new TradeHostingQuotDispatcherProcessor(handler));
  return processor;
}
}}}}} // namespace
