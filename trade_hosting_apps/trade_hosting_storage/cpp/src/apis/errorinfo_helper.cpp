/*
 * errorinfo_helper.cpp
 *
 *  Created on: 2018年2月5日
 *      Author: wangli
 */

#include "errorinfo_helper.h"

#include "trade_hosting_basic_errors_types.h"

using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::storage::api;

void ErrorInfoHelper::throwInnerError(const std::string& error_msg) throw(ErrorInfo) {
	ErrorInfo err;
	err.__set_errorCode(TradeHostingBasicErrorCode::ERROR_SERVER_INNER);
	err.__set_errorMsg("server inner error!" + error_msg);
	throw err;
}

void ErrorInfoHelper::throwNotSupportedError(const std::string& error_msg) throw(ErrorInfo) {
	ErrorInfo err;
	err.__set_errorCode(TradeHostingBasicErrorCode::ERROR_NOT_SUPPORTED);
	err.__set_errorMsg("not supported because " + error_msg);
	throw err;
}

void ErrorInfoHelper::throwError(int error_code, const std::string& error_msg) throw(ErrorInfo) {
	ErrorInfo err;
	err.__set_errorCode(error_code);
	err.__set_errorMsg(error_msg);
	throw err;
}

void ErrorInfoHelper::throwParamError(const std::string& error_msg) throw(::platform::comm::ErrorInfo) {
	ErrorInfo err;
	err.__set_errorCode(TradeHostingBasicErrorCode::ERROR_PARAMETER);
	err.__set_errorMsg(error_msg);
	throw err;
}

