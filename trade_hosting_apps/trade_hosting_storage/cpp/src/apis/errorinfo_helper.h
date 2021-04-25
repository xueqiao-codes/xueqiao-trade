/*
 * errorinfo_helper.h
 *
 *  Created on: 2018年2月5日
 *      Author: wangli
 */

#ifndef SRC_APIS_ERRORINFO_HELPER_H_
#define SRC_APIS_ERRORINFO_HELPER_H_

#include "comm_types.h"
#include "stub_base.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace storage {
namespace api {

class ErrorInfoHelper {
public:
	static void throwInnerError(const std::string& error_msg) throw(::platform::comm::ErrorInfo);
	static void throwNotSupportedError(const std::string& function_msg) throw(::platform::comm::ErrorInfo);
	static void throwError(int error_code, const std::string& error_msg) throw(::platform::comm::ErrorInfo);
	static void throwParamError(const std::string& error_msg) throw(::platform::comm::ErrorInfo);
};


} // end namespace api
} // end namespace storage
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#define CHECK_PARAM_ERRORINFO(condition) \
{ \
	if (!(condition)) { \
		::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwParamError(#condition); \
	} \
}


#define STUB_SYNC_INVOKE_NOARGS_ERRORINFO(stub, func_name) \
{ \
	try { \
		STUB_SYNC_INVOKE_NOARGS(stub, func_name); \
    } catch (::platform::comm::ErrorInfo& ei) { \
    	throw ei; \
	} catch (::apache::thrift::TException& et) { \
		::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwInnerError(et.what()); \
	}\
}

#define STUB_SYNC_INVOKE_ERRORINFO(stub, func_name, args...) \
{ \
	try { \
		STUB_SYNC_INVOKE(stub, func_name, ##args); \
    } catch (::platform::comm::ErrorInfo& ei) { \
    	throw ei; \
	} catch (::apache::thrift::TException& et) { \
		::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwInnerError(et.what()); \
	}\
}

#define STUB_SYNC_INVOKE_NOARGS_ERRORINFO_RETURN(stub, func_name) \
{ \
    try { \
        STUB_SYNC_INVOKE_NOARGS_RETURN(stub, func_name); \
    } catch (::platform::comm::ErrorInfo& ei) { \
        throw ei; \
    } catch (::apache::thrift::TException& et) { \
        ::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwInnerError(et.what()); \
    }\
}

#define STUB_SYNC_INVOKE_ERRORINFO_RETURN(stub, func_name, args...) \
{ \
    try { \
        STUB_SYNC_INVOKE_RETURN(stub, func_name, ##args); \
    } catch (::platform::comm::ErrorInfo& ei) { \
        throw ei; \
    } catch (::apache::thrift::TException& et) { \
        ::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwInnerError(et.what()); \
    }\
}




#endif /* SRC_APIS_ERRORINFO_HELPER_H_ */
