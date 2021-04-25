#include "base/app_log.h"
#include "TradeHostingStorage_variables.h"
#include "thrift/concurrency/PosixThreadFactory.h"
#include "thrift/protocol/TCompactProtocol.h"
#include "thrift/server/TNonblockingServer.h"
#include "thrift/transport/TServerSocket.h"
#include "thrift/TProcessor.h"
#include "TradeHostingStorage_handler.h"

int main(int argc, char* argv[]) { 
  soldier::base::AppLog::Init("service/trade_hosting_storage");
  boost::shared_ptr<apache::thrift::TProcessor> processor(
    new  ::xueqiao::trade::hosting::storage::thriftapi::TradeHostingStorageProcessor(boost::shared_ptr< ::xueqiao::trade::hosting::storage::thriftapi::TradeHostingStorageIf>(new  ::xueqiao::trade::hosting::storage::thriftapi::TradeHostingStorageHandler())));
  boost::shared_ptr<apache::thrift::protocol::TProtocolFactory>
    protocolFactory(new apache::thrift::protocol::TCompactProtocolFactory());
  boost::shared_ptr<apache::thrift::concurrency::ThreadManager> threadManager
    = apache::thrift::concurrency::ThreadManager::newSimpleThreadManager(20);
  boost::shared_ptr<apache::thrift::concurrency::PosixThreadFactory>
    threadFactory(new ::apache::thrift::concurrency::PosixThreadFactory());
  threadManager->threadFactory(threadFactory); 
  threadManager->start();
  int port = 10000 +  ::xueqiao::trade::hosting::storage::thriftapi::TradeHostingStorage_service_key;
  apache::thrift::server::TNonblockingServer server(processor, protocolFactory, port, threadManager);
  server.serve();
  return 0;
}
