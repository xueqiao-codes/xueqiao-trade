/*
 * upside_entry_subprocess.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#include "upside_entry_subprocess.h"

#include <execinfo.h>
#include <signal.h>
#include <boost/filesystem.hpp>
#include <boost/lexical_cast.hpp>
#include <sys/time.h>
#include <sys/resource.h>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/os_helper.h"
#include "thrift/concurrency/PosixThreadFactory.h"
#include "thrift/protocol/TCompactProtocol.h"
#include "thrift/server/TThreadPoolServer.h"
#include "thrift/transport/TServerSocket.h"
#include "thrift/transport/TBufferTransports.h"
#include "thrift/TProcessor.h"
#include "es9_upside_entry_handler.h"
#include "trade_hosting_storage_api.h"
#include "effective_reporter.h"

using namespace apache::thrift;
using namespace apache::thrift::concurrency;
using namespace apache::thrift::protocol;
using namespace apache::thrift::server;
using namespace apache::thrift::transport;
using namespace soldier::base;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;

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


UpsideEntrySubProcess::UpsideEntrySubProcess(int64_t trade_account_id)
	: trade_account_id_(trade_account_id) {
}

bool UpsideEntrySubProcess::runLoop() {
	AppLog::Init("apps/trade_hosting_upside_entry/subprocess/"
			+ boost::lexical_cast<std::string>(trade_account_id_));

	setpriority(PRIO_PROCESS, getpid(), 20);

	signal(SIGSEGV, sigCrashHandler);
	signal(SIGFPE, sigCrashHandler);
	signal(SIGILL, sigCrashHandler);
	signal(SIGBUS, sigCrashHandler);
	signal(SIGABRT, sigCrashHandler);
	signal(SIGSYS, sigCrashHandler);

	std::string process_socket_file;
	process_socket_file.append("/data/trade_hosting_upside_entry/run/")
					   .append(boost::lexical_cast<std::string>(trade_account_id_))
					   .append(".sock");
	if (boost::filesystem::exists(process_socket_file)) {
		CHECK(boost::filesystem::remove(process_socket_file));
	}

	boost::shared_ptr<TradeHostingUpsideEntryIf> if_impl = prepareIf();
	if (!if_impl) {
	    // 初始化失败并不立即退出，休眠一段时间后再退出，让主进程拉起
	    std::this_thread::sleep_for(std::chrono::seconds(30));
		return false;
	}

	boost::shared_ptr<TProcessor> processor(new TradeHostingUpsideEntryProcessor(if_impl));
	boost::shared_ptr<TProtocolFactory> protocol_factory(new TCompactProtocolFactory());
	boost::shared_ptr<ThreadManager> thread_manager = apache::thrift::concurrency::ThreadManager::newSimpleThreadManager(6);
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
	return true;
}

boost::shared_ptr<TradeHostingUpsideEntryIf> UpsideEntrySubProcess::prepareIf() const {
	try {
		std::shared_ptr<HostingTradeAccount> trade_account = getTradeAccount();
		if (!trade_account) {
			APPLOG_ERROR("failed to find account info for {}", trade_account_id_);
			return boost::shared_ptr<TradeHostingUpsideEntryIf>();
		}

		std::unique_ptr<HostingUpsideEntryHandler> if_impl(allocIfImpl(getTradeAccount()));
		if (!if_impl) {
			return boost::shared_ptr<TradeHostingUpsideEntryIf>();
		}

		std::shared_ptr<BrokerAccessEntry> broker_access_entry(getBrokerAccessEntry(trade_account));
		if (!broker_access_entry) {
			APPLOG_ERROR("faild to access entry for {}, brokerId={} brokerAccessId={}"
				, trade_account_id_, trade_account->tradeBrokerId
				, trade_account->tradeBrokerAccessId);
			return boost::shared_ptr<TradeHostingUpsideEntryIf>();
		}

		if (!if_impl->init(broker_access_entry)) {
			APPLOG_ERROR("failed to init account {}", trade_account_id_);
			return boost::shared_ptr<TradeHostingUpsideEntryIf>();
		}

		EffectiveReporter::Global().setEffective();

		HostingTradeAccountAPI::InvalidDescription description;
		description.invalid_reason = "账号接入初始化完成，连接劵商中";
		description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_CONNECTING;
		HostingTradeAccountAPI::setTradeAccountInvalid(trade_account->tradeAccountId, description);

		return boost::shared_ptr<TradeHostingUpsideEntryIf>(if_impl.release());
	} catch (::platform::comm::ErrorInfo& ei) {
		APPLOG_ERROR("prepareIf throws ErrorInfo, error_code={}, error_msg={}"
				, ei.errorCode, ei.errorMsg);
		return boost::shared_ptr<TradeHostingUpsideEntryIf>();
	}
}

std::shared_ptr<BrokerAccessEntry> UpsideEntrySubProcess::getBrokerAccessEntry(
			const std::shared_ptr<HostingTradeAccount>& trade_account) const {
	return HostingTradeAccountAPI::getBrokerAccessEntry(trade_account->tradeAccountId);
}

std::shared_ptr<HostingTradeAccount> UpsideEntrySubProcess::getTradeAccount() const {
	return HostingTradeAccountAPI::getTradeAccount(trade_account_id_);
}

HostingUpsideEntryHandler* UpsideEntrySubProcess::allocIfImpl(
		const std::shared_ptr<HostingTradeAccount>& trade_account) const {
	if (!trade_account) {
		return nullptr;
	}

	if (trade_account->brokerTechPlatform == BrokerTechPlatform::TECH_ESUNNY_9) {
	    return new Es9UpsideEntryHandler(trade_account);
	}

	APPLOG_ERROR("failed to found implemention for platform {}", trade_account->brokerTechPlatform);
	return nullptr;
}


