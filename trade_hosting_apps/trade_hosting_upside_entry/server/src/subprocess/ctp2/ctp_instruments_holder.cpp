/*
 * ctp_instruments_holder.cpp
 *
 *  Created on: 2018年3月13日
 *      Author: wangli
 */
#include "ctp_instruments_holder.h"

#include "base/app_log.h"

using namespace xueqiao::trade::hosting::upside::entry;

bool CtpInstrumentsHolder::isEmpty() {
    std::unique_lock<std::mutex> auto_lock(lock);
    return instrument_fields.empty();
}

void CtpInstrumentsHolder::updateAllInstruments(const std::vector<CThostFtdcInstrumentField>& instruments) {
    std::unique_lock<std::mutex> auto_lock(lock);

    instrument_fields.clear();
    for (auto it = instruments.begin(); it != instruments.end(); ++it) {
        std::shared_ptr<CThostFtdcInstrumentField> field_value(new CThostFtdcInstrumentField(*it));
        instrument_fields[it->InstrumentID] = field_value;
        APPLOG_DEBUG("instrument {} -> ExchangeID={}, ProductClass={}, ProductID={}, InstrumentID={}", it->InstrumentID
                , field_value->ExchangeID, field_value->ProductClass, field_value->ProductID
                , field_value->InstrumentID);
    }
    APPLOG_INFO("updateAllInstruments instruments.size={}", instruments.size());
}

std::shared_ptr<CThostFtdcInstrumentField> CtpInstrumentsHolder::getInstrument(const char* instrument_id) {
    if (instrument_id == nullptr) {
        return std::shared_ptr<CThostFtdcInstrumentField>();
    }

    lock.lock();
    auto it = instrument_fields.find(instrument_id);
    if (it == instrument_fields.end()) {
        lock.unlock();
        return std::shared_ptr<CThostFtdcInstrumentField>();
    }
    std::shared_ptr<CThostFtdcInstrumentField> result = it->second;
    lock.unlock();
    return result;
}



