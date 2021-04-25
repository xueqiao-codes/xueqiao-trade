/*
 * es9_qry_bill_handler.cpp
 *
 *  Created on: 2018年8月13日
 *      Author: wangli
 */
#include "es9_qry_bill_handler.h"

#include "base/code_defense.h"
#include "errorinfo_helper.h"

using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9QryBillHandler::Es9QryBillHandler(
        CallbackFunction callback
        , const std::string& user_no
        , const std::string& bill_date)
    : Es9RequestWithCallback(callback)
      , user_no_(user_no)
      , bill_date_(bill_date)
      , qry_bill_resp_(new Es9QryBillResp()){
}

int Es9QryBillHandler::onStart(ITapTrade::ITapTradeAPI* trader_api, ITapTrade::TAPIUINT32& session_id) {
    ITapTrade::TapAPIBillQryReq req;
    memset(&req, 0, sizeof(req));
    strncpy(req.UserNo, user_no_.c_str(), sizeof(ITapTrade::TAPISTR_20) - 1);
    req.BillType = ITapTrade::TAPI_BILL_DATE;
    strncpy(req.BillDate, bill_date_.c_str(), sizeof(ITapTrade::TAPIDATE) - 1);
    req.BillFileType = ITapTrade::TAPI_BILL_FILE_TXT;

    return trader_api->QryBill(&session_id, &req);
}

void Es9QryBillHandler::OnRspQryBill(ITapTrade::TAPIUINT32 sessionID
        , ITapTrade::TAPIINT32 errorCode
        , ITapTrade::TAPIYNFLAG isLast
        , const ITapTrade::TapAPIBillQryRsp *info) {
    if (info) {
        qry_bill_resp_->bill_content.append(info->BillText, info->BillLen);
    }
    qry_bill_resp_->setErrorCode(errorCode);
}
