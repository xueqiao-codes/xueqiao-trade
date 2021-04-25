/*
 * upside_entry_mainprocess.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#include "upside_entry_mainprocess.h"

#include <boost/filesystem.hpp>
#include <chrono>
#include <thread>
#include <signal.h>
#include <sys/wait.h>
#include <sstream>
#include <pthread.h>

#include <time.h>
#include "attr/attr_reporter_factory.h"
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/time_helper.h"
#include "errorinfo_helper.h"
#include "hosting_message_graph.h"
#include "main_processor.h"
#include "subprocess_manager.h"
#include "thrift/concurrency/PosixThreadFactory.h"
#include "thrift/protocol/TCompactProtocol.h"
#include "thrift/server/TNonblockingServer.h"
#include "thrift/transport/TServerSocket.h"
#include "thrift/transport/TBufferTransports.h"
#include "thrift/protocol/TDebugProtocol.h"
#include "thrift/TProcessor.h"
#include "TradeHostingUpsideEntry.h"
#include "TradeHostingUpsideEntry_variables.h"
#include "trade_hosting_basic_errors_types.h"

using namespace apache::thrift;
using namespace apache::thrift::concurrency;
using namespace apache::thrift::protocol;
using namespace apache::thrift::server;
using namespace apache::thrift::transport;

using namespace soldier::base;
using namespace soldier::message_bus;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::storage::api;
using namespace xueqiao::trade::hosting::framework;
using namespace xueqiao::trade::hosting::upside::entry;

static void onProcessSignal() {
    sigset_t waitset;
    sigemptyset(&waitset);
    sigaddset(&waitset, SIGCHLD);

    while (true)  {
        siginfo_t info;
        int rc = sigwaitinfo(&waitset, &info);
        if (rc != -1) {
            APPLOG_INFO("onProcessSignal fetch the signal {}", rc);
            SubProcessManager::Global().checkSubProcesses();
        } else {
            APPLOG_ERROR("onProcessSignal sigwaitinfo returned err: {}, {}", errno, strerror(errno));
        }
    }
}

static void onSubProcessCheckTimer() {
	while(true) {
		std::this_thread::sleep_for(std::chrono::seconds(10));
		SubProcessManager::Global().retryProcesses();
	}
}

UpsideEntryMainProcess::UpsideEntryMainProcess() {
}

bool UpsideEntryMainProcess::runLoop() {
    sigset_t bset, oset;
    sigemptyset(&bset);
    sigaddset(&bset, SIGCHLD);
    if (pthread_sigmask(SIG_BLOCK, &bset, &oset) != 0) {
        APPLOG_ERROR("!! Set pthread mask failed\n");
        return false;
    }

	AppLog::Init("apps/trade_hosting_upside_entry");
	// create directories for subprocess
	boost::filesystem::path running_directory("/data/trade_hosting_upside_entry/run");
	if (!boost::filesystem::exists(running_directory)) {
		if (!boost::filesystem::create_directories(running_directory)) {
			APPLOG_ERROR("create directories {} failed!", running_directory.generic_string());
			return false;
		}
	}

	std::shared_ptr<MainProcessor> main_processor(new MainProcessor());
	SubProcessManager::Global().addListener(main_processor);
	mainprocess_message_agent_ = MessageAgent::create("hosting_upside_entry_main"
			, main_processor
			, HostingMessageGraph::messageGraphHolder());
	CHECK(mainprocess_message_agent_);

	std::thread subprocess_check_timer_thread(&onSubProcessCheckTimer);
	subprocess_check_timer_thread.detach();

	std::thread signal_thread(&onProcessSignal);
	signal_thread.detach();

	soldier::attr::AttrReporterFactory::Global().minute()
	                .keep(soldier::attr::AttrReporterFactory::Global().minute().requireKey(
	                        "xueqiao.trade.hosting.process.keepalive",
	                        {{"processname", "trade_hosting_upside_entry"}}), 1);

	serve();
	return true;
}

class TradeHostingUpsideEntryHandler : public TradeHostingUpsideEntryIf {
public:
	virtual void getSubProcessInfos(std::vector<TSubProcessInfo> & _return, const PlatformArgs& platformArgs) {
		std::vector<SubProcessEntry> entries;
		SubProcessManager::Global().dumpProcessesInfo(entries);

		for (auto entry : entries) {
			TSubProcessInfo info;
			info.__set_pid(entry.pid);
			info.__set_tradeAccountId(entry.trade_account_id);

			for (auto entry_time_info : entry.time_infos) {
				TSubProcessTimeInfo time_info;
				time_info.__set_startTimestamp(entry_time_info.start_timestamp);
				time_info.__set_exitedTimestamp(entry_time_info.exited_timestamp);
				info.timeInfos.push_back(time_info);
			}
			info.__isset.timeInfos = true;
			_return.push_back(info);
		}

		APPLOG_INFO("getSubProcessInfos platformArgs={}", ThriftDebugString(platformArgs));
	}

	virtual void restartSubProcess(const PlatformArgs& platformArgs, const int64_t trade_account_id) {
		SubProcessManager::Global().restartSubProcess(trade_account_id, std::shared_ptr<HostingTradeAccount>());
	}

	virtual void allocOrderRef(HostingExecOrderRef& _return, const  PlatformArgs& platformArgs) {
		ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void orderInsert(const PlatformArgs& platformArgs, const  HostingExecOrder& insertOrder) {
		ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void orderDelete(const PlatformArgs& platformArgs, const HostingExecOrder& deleteOrder) {
		ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void syncOrderState(const PlatformArgs& platformArgs, const  HostingExecOrder& syncOrder) {
		ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void syncOrderTrades(const PlatformArgs& platformArgs, const  HostingExecOrder& syncOrder) {
		ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void syncOrderStateBatch(const  ::platform::comm::PlatformArgs& platformArgs, const TSyncOrderStateBatchReq& batchReq) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual int64_t getLastUpsideEffectiveTimestamp(const  ::platform::comm::PlatformArgs& platformArgs) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	    return 0;
	}

	virtual void sendUpsideHeartBeat(const  ::platform::comm::PlatformArgs& platformArgs) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void dumpPositionSummaries(std::vector< ::xueqiao::trade::hosting::upside::position::PositionSummary> & _return
	        , const  ::platform::comm::PlatformArgs& platformArgs) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void getFunds(std::vector<TFund> & _return
	        , const  ::platform::comm::PlatformArgs& platformArgs) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void getSettlementInfo(TSettlementInfo& _return
	        , const  ::platform::comm::PlatformArgs& platformArgs
	        , const std::string& settlementDate) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void getNetPositionSummaries(std::vector<TNetPositionSummary> & _return
	        , const  ::platform::comm::PlatformArgs& platformArgs) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void getPositionInfos(std::vector<TPositionInfo> & _return
	        , const  ::platform::comm::PlatformArgs& platformArgs) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}

	virtual void getPositionRateDetails(TPositionRateDetails& _return
	        , const  ::platform::comm::PlatformArgs& platformArgs) {
	    ErrorInfoHelper::throwNotSupportedError("in mainprocess");
	}
};


void UpsideEntryMainProcess::serve() {
	boost::shared_ptr<apache::thrift::TProcessor> processor(
			new TradeHostingUpsideEntryProcessor(boost::shared_ptr<TradeHostingUpsideEntryIf>(
					new  TradeHostingUpsideEntryHandler())));
	boost::shared_ptr<apache::thrift::protocol::TProtocolFactory>
	protocolFactory(new apache::thrift::protocol::TCompactProtocolFactory());
	boost::shared_ptr<apache::thrift::concurrency::ThreadManager> threadManager
		= apache::thrift::concurrency::ThreadManager::newSimpleThreadManager(2);
	boost::shared_ptr<apache::thrift::concurrency::PosixThreadFactory>
	threadFactory(new ::apache::thrift::concurrency::PosixThreadFactory());
	threadManager->threadFactory(threadFactory);
	threadManager->start();
	int port = 10000 +  ::xueqiao::trade::hosting::upside::entry::TradeHostingUpsideEntry_service_key;
	TNonblockingServer server(processor, protocolFactory, port, threadManager);
	server.serve();
}







