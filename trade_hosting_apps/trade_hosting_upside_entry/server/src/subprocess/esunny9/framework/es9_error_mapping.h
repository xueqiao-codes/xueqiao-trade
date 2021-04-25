/*
 * es9_error_mapping.h
 *
 *  Created on: 2018年4月14日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_ERROR_MAPPING_H_
#define SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_ERROR_MAPPING_H_

#include <string>
#include <string.h>
#include "iTapTradeAPIDataType.h"

namespace es9ext {
namespace framework {

const std::string es9ErrorMsg(const ITapTrade::TAPIINT32& errorCode);

} // end namespace framework
} // end namespace es9ext



#endif /* SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_ERROR_MAPPING_H_ */
