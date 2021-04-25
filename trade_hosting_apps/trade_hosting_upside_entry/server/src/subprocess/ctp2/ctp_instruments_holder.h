/*
 * ctp_instruments_holder.h
 *
 *  Created on: 2018年3月13日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_INSTRUMENTS_HOLDER_H_
#define SRC_SUBPROCESS_CTP_CTP_INSTRUMENTS_HOLDER_H_

#include <map>
#include <memory>
#include <mutex>
#include <vector>
#include "ThostFtdcUserApiStruct.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class CtpInstrumentsHolder {
public:
    bool isEmpty();
    void updateAllInstruments(const std::vector<CThostFtdcInstrumentField>& instruments);
    std::shared_ptr<CThostFtdcInstrumentField> getInstrument(const char* instrument_id);

private:
    std::mutex lock;
    std::map<std::string, std::shared_ptr<CThostFtdcInstrumentField>> instrument_fields;
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_CTP_CTP_INSTRUMENTS_HOLDER_H_ */
