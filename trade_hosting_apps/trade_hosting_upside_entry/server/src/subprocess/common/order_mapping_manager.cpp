/*
 * order_mapping_manager.cpp
 *
 *  Created on: 2018年2月7日
 *      Author: wangli
 */

#include "order_mapping_manager.h"

using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

OrderMappingManager::OrderMappingManager(
        IOrderMappingBackend* backend
		, size_t max_cache_items)
	: backend_(backend)
	  , order_ref_cache_(max_cache_items)
	  , deal_id_cache_(max_cache_items) {
}

int64_t OrderMappingManager::getExecOrderId(const HostingExecOrderRef& order_ref) throw(ErrorInfo) {
	std::string order_ref_key = backend_->calulateKey(order_ref);
	if (order_ref_key.empty()) {
		return -1;
	}

	int64_t exec_order_id = -1;
	{
		order_ref_cache_lock_.lock();
		const int64_t* cache_value_ptr = order_ref_cache_.get(order_ref_key);
		if (cache_value_ptr) {
			exec_order_id = *cache_value_ptr;
		}
		order_ref_cache_lock_.unlock();
	}
	if (exec_order_id > 0) {
		return exec_order_id;
	}

	exec_order_id = backend_->getExecOrderId(order_ref);
	if (exec_order_id <= 0) {
		return -1;
	}

	{
		order_ref_cache_lock_.lock();
		order_ref_cache_.put(order_ref_key, exec_order_id);
		order_ref_cache_lock_.unlock();
	}

	return exec_order_id;
}

int64_t OrderMappingManager::getExecOrderId(const HostingExecOrderDealID& deal_id) throw(ErrorInfo) {
	std::string deal_id_key = backend_->calulateKey(deal_id);
	if (deal_id_key.empty()) {
		return -1;
	}

	int64_t exec_order_id = -1;
	{
		deal_id_cache_lock_.lock();
		const int64_t* cache_value_ptr = deal_id_cache_.get(deal_id_key);
		if (cache_value_ptr) {
			exec_order_id = *cache_value_ptr;
		}
		deal_id_cache_lock_.unlock();
	}
	if (exec_order_id > 0) {
		return exec_order_id;
	}

	exec_order_id = backend_->getExecOrderId(deal_id);
	if (exec_order_id < 0) {
		return -1;
	}

	{
		deal_id_cache_lock_.lock();
		deal_id_cache_.put(deal_id_key, exec_order_id);
		deal_id_cache_lock_.unlock();
	}

	return exec_order_id;
}

void OrderMappingManager::setExecOrderId(const HostingExecOrderRef& order_ref
			, const int64_t& exec_order_id) {
	if (exec_order_id < 0) {
		return ;
	}

	std::string order_ref_key = backend_->calulateKey(order_ref);
	if (order_ref_key.empty()) {
		return ;
	}

	{
		order_ref_cache_lock_.lock();
		order_ref_cache_.put(order_ref_key, exec_order_id);
		order_ref_cache_lock_.unlock();
	}
}

void OrderMappingManager::setExecOrderId(const HostingExecOrderDealID& deal_id, const int64_t& exec_order_id) {
	if (exec_order_id < 0) {
		return ;
	}

	std::string deal_id_key = backend_->calulateKey(deal_id);
	if (deal_id_key.empty()) {
		return ;
	}

	{
		deal_id_cache_lock_.lock();
		deal_id_cache_.put(deal_id_key, exec_order_id);
		deal_id_cache_lock_.unlock();
	}
}

