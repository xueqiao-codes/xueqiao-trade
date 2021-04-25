/*
 * main.cpp
 *
 *  Created on: 2018年4月25日
 *      Author: 44385
 */

#include <execinfo.h>
#include <signal.h>
#include <boost/filesystem.hpp>
#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/os_helper.h"
#include "TradeHostingQuotDispatcher_variables.h"
#include "thrift/concurrency/PosixThreadFactory.h"
#include "thrift/protocol/TCompactProtocol.h"
#include "thrift/server/TThreadPoolServer.h"
#include "thrift/transport/TServerSocket.h"
#include "thrift/transport/TBufferTransports.h"
#include "thrift/TProcessor.h"
#include "quot_dispatcher_entry.h"
#include "quotation/subscribe/api/subscribe_system.h"

using namespace apache::thrift;
using namespace apache::thrift::concurrency;
using namespace apache::thrift::protocol;
using namespace apache::thrift::server;
using namespace apache::thrift::transport;
using namespace soldier::base;
using namespace xueqiao::quotation;
using namespace xueqiao::trade::hosting::quot::dispatcher;

const int MAX_STACK_FRAMES = 128;
static void sigCrashHandler(int sig)
{
    APPLOG_ERROR("crash signal number:{}", sig);
    void* array[MAX_STACK_FRAMES];
    signal(sig, SIG_DFL);
    size_t bt_size = backtrace(array, MAX_STACK_FRAMES);
    char** bt_strings = (char**)backtrace_symbols(array, bt_size);
    for (size_t index = 0; index < bt_size; ++index) {
        APPLOG_ERROR("{}, {}", index, bt_strings[index]);
    }
    free(bt_strings);
    AppLog::FlushLog();
}


int main(int argc, char* argv[]) {
    AppLog::Init("apps/trade_hosting_quot_dispatcher");

    signal(SIGSEGV, sigCrashHandler);
    signal(SIGFPE, sigCrashHandler);
    signal(SIGILL, sigCrashHandler);
    signal(SIGBUS, sigCrashHandler);
    signal(SIGABRT, sigCrashHandler);
    signal(SIGSYS, sigCrashHandler);

    std::string process_socket_file;
    process_socket_file.append("/data/run/service_")
            .append(boost::lexical_cast<std::string>(TradeHostingQuotDispatcher_service_key))
            .append(".sock");
    if (boost::filesystem::exists(process_socket_file)) {
        CHECK(boost::filesystem::remove(process_socket_file));
    }

    boost::shared_ptr<QuotDispatcherEntry> if_impl(new QuotDispatcherEntry());
    if (!if_impl->init()) {
        APPLOG_ERROR("QuotDispatcherEntry init failed!");
        return -1;
    }
    SubscribeSystem::Init(std::shared_ptr<ISubscribeCallback>(if_impl.get()));

    boost::shared_ptr<TProcessor> processor(new TradeHostingQuotDispatcherProcessor(if_impl));
    boost::shared_ptr<TProtocolFactory> protocol_factory(new TCompactProtocolFactory());
    boost::shared_ptr<ThreadManager> thread_manager = apache::thrift::concurrency::ThreadManager::newSimpleThreadManager(2);
    boost::shared_ptr<apache::thrift::concurrency::PosixThreadFactory> thread_factory(
            new ::apache::thrift::concurrency::PosixThreadFactory());
    thread_manager->threadFactory(thread_factory);
    thread_manager->start();

    boost::shared_ptr<TServerTransport> server_socket(new TServerSocket(process_socket_file));
    boost::shared_ptr<::apache::thrift::transport::TTransportFactory>
    transport_factory(new ::apache::thrift::transport::TFramedTransportFactory());

    TThreadPoolServer server(processor
            , server_socket
            , transport_factory
            , protocol_factory
            , thread_manager);
    server.serve();

    return 0;
}
