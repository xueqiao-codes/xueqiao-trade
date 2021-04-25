/*
 * main_processor.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */
#include "base/app_log.h"
#include "main_processor.h"
#include "message_bus/interface/framework_topics.h"
#include "thrift/protocol/TDebugProtocol.h"
#include "protocol_util.h"
#include "trade_hosting_basic_errors_types.h"
#include "trade_hosting_storage_api.h"
#include "upside_effective_manager.h"

using namespace apache::thrift;
using namespace platform::comm;
using namespace soldier::message_bus;
using namespace soldier::svr_platform;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::events;
using namespace xueqiao::trade::hosting::storage::api;
using namespace xueqiao::trade::hosting::upside::entry;

const std::string TRADE_ACCOUNT_EVENT = "TradeAccountEvent";

MainProcessor::MainProcessor() {
}

MainProcessor::~MainProcessor() {
}

IMessageConsumer::StartUpMode MainProcessor::onStartUp() {
	return IMessageConsumer::StartUpMode::CLEAR_QUEUE_INIT;
}

IMessageConsumer::ConsumeResult MainProcessor::onConsumeMessage(
		const std::string& topic
		, const std::string& message) {
	try {
		if (topic == INIT_TOPIC) {
			return onInit();
		} else if (topic == TRADE_ACCOUNT_EVENT) {
			TradeAccountEvent event;
			ProtocolUtil::unserializeCompact(event
					, (const uint8_t*)message.data()
					, message.length());
			return onHandleTradeAccountEvent(event);
		}

		APPLOG_INFO("ignore event, topic={}", topic);
		return IMessageConsumer::ConsumeResult::CONSUME_FAILED_DROP;
	} catch (ErrorInfo& err) {
		APPLOG_ERROR("onConsumeMessage failed, topic={}, error_code={}, error_msg={}"
				, topic, err.errorCode, err.errorMsg);
		std::this_thread::sleep_for(std::chrono::seconds(1));
		return IMessageConsumer::ConsumeResult::CONSUME_RETRY;
	} catch (...) {
	    APPLOG_ERROR("onConsumeMessage, topic={} throws unkown error!");
	    std::this_thread::sleep_for(std::chrono::seconds(1));
	    return IMessageConsumer::ConsumeResult::CONSUME_RETRY;
	}
}

IMessageConsumer::ConsumeResult MainProcessor::onInit() {
	APPLOG_INFO("onInit......");
	std::vector<HostingTradeAccount> trade_accounts;
	HostingTradeAccountAPI::getAllTradeAccounts(trade_accounts);
	std::map<int64_t, bool> normal_trade_account;
	for (const auto& trade_account : trade_accounts) {
	    std::shared_ptr<HostingTradeAccount> shared_trade_account(new HostingTradeAccount(trade_account));
		doTradeAccountStateAction(shared_trade_account);
		if (trade_account.accountState == TradeAccountState::ACCOUNT_NORMAL) {
		    normal_trade_account[trade_account.tradeAccountId] = true;
		}
	}

	// 检测已经被清理的账号
	std::vector<SubProcessEntry> process_entries;
	SubProcessManager::Global().dumpProcessesInfo(process_entries);
	for (SubProcessEntry entry : process_entries) {
	    if (normal_trade_account.find(entry.trade_account_id) == normal_trade_account.end()) {
	        SubProcessManager::Global().removeSubProcess(entry.trade_account_id);
	        UpsideEffectiveManager::Global().removeTradeAccount(entry.trade_account_id);
	    }
	}

	return IMessageConsumer::ConsumeResult::CONSUME_OK;
}

void MainProcessor::doTradeAccountStateAction(const std::shared_ptr<HostingTradeAccount>& trade_account) {
	if (trade_account->accountState == TradeAccountState::ACCOUNT_NORMAL) {
		SubProcessManager::Global().addSubProcess(trade_account->tradeAccountId, trade_account);
		UpsideEffectiveManager::Global().addTradeAccount(trade_account->tradeAccountId);
	} else if(trade_account->accountState == TradeAccountState::ACCOUNT_REMOVED
	        || trade_account->accountState == TradeAccountState::ACCOUNT_DISABLED) {
		SubProcessManager::Global().removeSubProcess(trade_account->tradeAccountId);
		UpsideEffectiveManager::Global().removeTradeAccount(trade_account->tradeAccountId);
	} else {
		APPLOG_WARN("trade_account={} unkown account state ={}"
				, trade_account->tradeAccountId, trade_account->accountState);
	}
}

IMessageConsumer::ConsumeResult MainProcessor::onHandleTradeAccountEvent(const TradeAccountEvent& event) {
	if (event.type != TradeAccountEventType::TRADE_ACCOUNT_ALL_CLEARD && event.tradeAccountId <= 0) {
		APPLOG_WARN("onHandleTradeAccountEvent received unexpected tradeAccountId={}, event type={}"
		        , event.tradeAccountId
		        , event.type);
		return IMessageConsumer::ConsumeResult::CONSUME_FAILED_DROP;
	}

	APPLOG_INFO("onHandleTradeAccountEvent event={}", ThriftDebugString(event));

	std::shared_ptr<HostingTradeAccount> trade_account
	                    = HostingTradeAccountAPI::getTradeAccount(event.tradeAccountId);
	if (event.type == TradeAccountEventType::TRADE_ACCOUNT_ADDED) {
		SubProcessManager::Global().addSubProcess(event.tradeAccountId, trade_account);
		UpsideEffectiveManager::Global().addTradeAccount(event.tradeAccountId);
	} else if (event.type == TradeAccountEventType::TRADE_ACCOUNT_DELETED) {
	    if (!trade_account) {
	        SubProcessManager::Global().removeSubProcess(event.tradeAccountId);
	        UpsideEffectiveManager::Global().removeTradeAccount(event.tradeAccountId);
	    }
	} else if (event.type == TradeAccountEventType::TRADE_ACCOUNT_INFO_UPDATED) {
		SubProcessManager::Global().restartSubProcess(event.tradeAccountId, trade_account);
	} else if (event.type == TradeAccountEventType::TRADE_ACCOUNT_STATE_CHANGED) {
		if (!trade_account) {
			SubProcessManager::Global().removeSubProcess(event.tradeAccountId);
			UpsideEffectiveManager::Global().removeTradeAccount(event.tradeAccountId);
		} else {
			doTradeAccountStateAction(trade_account);
		}
	} else if (event.type == TradeAccountEventType::TRADE_ACCOUNT_ACCESS_ENTRY_UPDATE) {
		SubProcessManager::Global().restartSubProcess(event.tradeAccountId, trade_account);
	} else if (event.type == TradeAccountEventType::TRADE_ACCOUNT_ALL_CLEARD) {
	    APPLOG_WARN("[NOTICE]received trade account all cleared, clear all process...");
	    SubProcessManager::Global().clearAll();
	    UpsideEffectiveManager::Global().clearAll();
	}

	return IMessageConsumer::ConsumeResult::CONSUME_OK;
}

static void setTradeAccountInvalid(int64_t trade_account_id, HostingTradeAccountAPI::InvalidDescription desc) {
    try {
        HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_id, desc);
    } catch (ErrorInfo& e) {
        APPLOG_ERROR("setTradeAccountInvalid failed, trade_account_id={}, errorCode={}, errorMsg={}"
                , trade_account_id, e.errorCode, e.errorMsg);
    } catch (...) {
        APPLOG_ERROR("setTradeAccountInvalid failed, trade_account_id={}, unkown exception"
                , trade_account_id);
    }
}

void MainProcessor::onStartSubProcessBegin(int64_t trade_account_id) {
	APPLOG_INFO("onStartSubProcessBegin trade_account_id={}", trade_account_id);

	HostingTradeAccountAPI::InvalidDescription desc;
	desc.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STARTING;
	desc.invalid_reason = "进程正在启动";
	setTradeAccountInvalid(trade_account_id, desc);
}

void MainProcessor::onStartSubProcessFinished(int64_t trade_account_id, int pid) {
	APPLOG_INFO("onStartSubProcessFinished trade_account_id={}, pid={}"
			, trade_account_id, pid);
}

void MainProcessor::onStopSubProcessBegin(int64_t trade_account_id, int pid) {
	APPLOG_INFO("onStopSubProcessBegin trade_account_id={}, pid={}"
				, trade_account_id, pid);
}

void MainProcessor::onStopSubProcessFinished(int64_t trade_account_id) {
	APPLOG_INFO("onStopSubProcessFinished trade_account_id={}", trade_account_id);

	HostingTradeAccountAPI::InvalidDescription desc;
	desc.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STOPED;
	desc.invalid_reason = "进程停止运行";

	setTradeAccountInvalid(trade_account_id, desc);
}

void MainProcessor::onRestartSubProcessBegin(int64_t trade_account_id) {
	APPLOG_INFO("onRestartSubProcessBegin trade_account_id={}", trade_account_id);
}

void MainProcessor::onRestartSubProcessFinished(int64_t trade_account_id, int pid) {
	APPLOG_INFO("onRestartSubProcessFinished trade_account_id={}", trade_account_id);
}

void MainProcessor::onSubProcessExited(int64_t trade_account_id) {
	APPLOG_INFO("onSubProcessExited trade_account_id={}", trade_account_id);

	HostingTradeAccountAPI::InvalidDescription desc;
	desc.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STOPED;
	desc.invalid_reason = "进程停止运行";
	setTradeAccountInvalid(trade_account_id, desc);
}


