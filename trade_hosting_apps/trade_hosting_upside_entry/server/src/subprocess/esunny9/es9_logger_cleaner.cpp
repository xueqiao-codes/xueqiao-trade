/*
 * es9_logger_cleaner.cpp
 *
 *  Created on: 2019年1月9日
 *      Author: wangli
 */
#include "es9_logger_cleaner.h"

#include <boost/filesystem.hpp>
#include "base/app_log.h"
#include "base/string_util.h"

using namespace soldier::base;
using namespace xueqiao::trade::hosting::upside::entry;


Es9LoggerCleaner::Es9LoggerCleaner(const std::string& directory, const std::string& prefix)
    : directory_(directory), prefix_(prefix) {
    work_thread_.reset(new TaskThread());
    work_thread_->postTask(&Es9LoggerCleaner::onStart, this);
}

Es9LoggerCleaner::~Es9LoggerCleaner() {
}

void Es9LoggerCleaner::onStart() {
    process();
    work_thread_->postTask(&Es9LoggerCleaner::onCheckOnce, this);
}

void Es9LoggerCleaner::onCheckOnce() {
    std::this_thread::sleep_for(std::chrono::hours(12));
    process();
    work_thread_->postTask(&Es9LoggerCleaner::onCheckOnce, this);
}

void Es9LoggerCleaner::process() {
    APPLOG_INFO("Es9LoggerCleaner process start, directory={}, prefix={} ...."
            , directory_, prefix_);

    // 列出要检查的文件列表
    boost::filesystem::path dir(directory_);
    if (!boost::filesystem::exists(dir)) {
        APPLOG_ERROR("{} is not existed for logger cleaner", directory_);
        return ;
    }

    std::vector<boost::filesystem::path> check_paths;
    for (auto dirIt = boost::filesystem::directory_iterator(dir)
            ; dirIt != boost::filesystem::directory_iterator(); dirIt++) {
        APPLOG_INFO("List path={}", dirIt->path().string());
        if (boost::filesystem::is_regular_file(dirIt->path())) {
            if (prefix_.empty() || StringUtil::startsWith(dirIt->path().filename().string(), prefix_)) {
                APPLOG_INFO("Check files add path={}", dirIt->path().string());
                check_paths.push_back(dirIt->path());
            }
        }
    }

    // 找出最近修改时间最大的文件组
    std::time_t max_lastwrite_timestamp = 0;
    std::map<std::string, bool> max_lastwrite_paths;

    for (auto& path : check_paths) {
        std::time_t write_time = boost::filesystem::last_write_time(path);
        if (write_time < max_lastwrite_timestamp) {
            continue;
        } else if (write_time == max_lastwrite_timestamp) {
            max_lastwrite_paths[path.string()] = true;
        } else {
            max_lastwrite_paths.clear();
            max_lastwrite_paths[path.string()] = true;
            max_lastwrite_timestamp = write_time;
        }
    }

    // 执行删除
    for (auto& path : check_paths) {
        if (max_lastwrite_paths.end() != max_lastwrite_paths.find(path.string())) {
            continue;
        }
        APPLOG_INFO("Delete file {}", path.string());

        boost::filesystem::remove(path);
    }

    APPLOG_INFO("Es9LoggerCleaner process finished ....");
}
