/*
 * subprocess_manager.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_MAINPROCESS_SUBPROCESS_MANAGER_H_
#define SRC_MAINPROCESS_SUBPROCESS_MANAGER_H_

#include <chrono>
#include <map>
#include <mutex>
#include <queue>
#include <stdint.h>
#include "base/thread_pool.h"
#include "base/sync_call.h"
#include "base/listener_container.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct SubProcessEntryTimeInfo {
	int32_t start_timestamp = 0;  // 进程启动的时间
	int32_t exited_timestamp = 0; // 进程推出的时间
};

struct SubProcessEntry {
	int64_t trade_account_id = 0;
	int pid = -1;  // 进程对应的PID
	std::vector<SubProcessEntryTimeInfo> time_infos;
	std::shared_ptr<HostingTradeAccount> account_info;
};

class ISubProcessListener {
public:
	virtual ~ISubProcessListener() = default;

	virtual void onStartSubProcessBegin(int64_t trade_account_id) {}
	virtual void onStartSubProcessFinished(int64_t trade_account_id, int pid) {}

	virtual void onStopSubProcessBegin(int64_t trade_account_id, int pid){}
	virtual void onStopSubProcessFinished(int64_t trade_account_id){}

	virtual void onRestartSubProcessBegin(int64_t trade_account_id){}
	virtual void onRestartSubProcessFinished(int64_t trade_account_id, int pid){}

	virtual void onSubProcessExited(int64_t trade_account_id) {}

protected:
	ISubProcessListener() = default;
};

class SubProcessManager : public soldier::base::ListenerContainer<ISubProcessListener> {
public:
	static SubProcessManager& Global();
	virtual ~SubProcessManager();

	void addSubProcess(int64_t trade_account_id, const std::shared_ptr<HostingTradeAccount>& account_info);
	void removeSubProcess(int64_t trade_account_id);
	void dumpProcessesInfo(std::vector<SubProcessEntry>& process_entries);
	void restartSubProcess(int64_t trade_account_id, const std::shared_ptr<HostingTradeAccount>& new_account_info);
	void checkSubProcesses();
	void clearAll();

	void retryProcesses();

private:
	SubProcessManager();

	void onCheckSubProcesses();
	void onAddSubProcess(int64_t trade_account_id, const std::shared_ptr<HostingTradeAccount>& account_info);
	void onRemoveSubProcess(int64_t trade_account_id);
	void onRestartSubProcess(int64_t trade_account_id, const std::shared_ptr<HostingTradeAccount>& new_account_info);
	void onDumpProcessInfo(soldier::base::SyncCall* sync_call, std::vector<SubProcessEntry>* process_entries);
	void onClearAll();

	void startSubProcess(const std::shared_ptr<SubProcessEntry>& process_entry);
	void stopSubProcess(const std::shared_ptr<SubProcessEntry>& process_entry);

	void onRetryProcesses();

private:
	const std::string exec_dir_;

	std::map<int64_t, std::shared_ptr<SubProcessEntry>> trade_processes_map_;
	std::map<int, std::shared_ptr<SubProcessEntry>>     pid_processed_map_;

	std::unique_ptr<soldier::base::TaskThread> working_thread_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_MAINPROCESS_SUBPROCESS_MANAGER_H_ */
