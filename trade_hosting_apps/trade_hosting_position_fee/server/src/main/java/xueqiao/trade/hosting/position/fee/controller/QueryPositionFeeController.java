package xueqiao.trade.hosting.position.fee.controller;

import com.antiy.error_code.ErrorCodeInner;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.core.bean.XQDefaultPositionRate;
import xueqiao.trade.hosting.position.fee.core.cache.SledContractCacheManager;
import xueqiao.trade.hosting.position.fee.core.cache.XQDefaultPositionRateCacheManager;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQContractCommissionTable;
import xueqiao.trade.hosting.position.fee.storage.table.XQContractMarginTable;
import xueqiao.trade.hosting.position.fee.thriftapi.PositionFee;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommission;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractMargin;

import java.sql.Connection;

public class QueryPositionFeeController implements IController {

    private long subAccountId;
    private long contractId;

    public QueryPositionFeeController(long subAccountId, long contractId) {
        this.subAccountId = subAccountId;
        this.contractId = contractId;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        Precondition.check(subAccountId > 0, "invalid subAccountId");
        Precondition.check(contractId > 0, "invalid commodityId");
    }

    public PositionFee doQuery() throws ErrorInfo {
        return new DBQueryHelper<PositionFee>(PositionFeeDB.Global()) {
            @Override
            protected PositionFee onQuery(Connection connection) throws Exception {
                XQContractMargin xqMargin = new XQContractMarginTable(connection).query(subAccountId, contractId);
                XQContractCommission xqCommission = new XQContractCommissionTable(connection).query(subAccountId, contractId);
                PositionFee positionFee;
                if (xqMargin != null || xqCommission != null) {
                    positionFee = getPositionFee(xqMargin, xqCommission);
                } else {
                    positionFee = getDefaultPositionFee(contractId);
                }
                if (positionFee == null) {
                    throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "no invalid commodity config found in xueqiao system");
                }
                return positionFee;
            }
        }.query();
    }

    private PositionFee getPositionFee(XQContractMargin xqMargin, XQContractCommission xqCommission) {
        PositionFee positionFee = new PositionFee();
        positionFee.setSubAccountId(subAccountId);
        if (xqMargin != null) {
            positionFee.setContractId(xqMargin.getContractInfo().getContractId());
            positionFee.setMargin(xqMargin.getMargin());
        } else {
            AppLog.e("getPositionFee ---- query xqMargin fail, subAccountId : " + subAccountId + ", contractId : " + contractId);
        }
        if (xqCommission != null) {
            positionFee.setContractId(xqCommission.getContractInfo().getContractId());
            positionFee.setCommission(xqCommission.getCommission());
        } else {
            AppLog.e("getPositionFee ---- query xqCommission fail, subAccountId : " + subAccountId + ", contractId : " + contractId);
        }
        return positionFee;
    }

    private PositionFee getDefaultPositionFee(long contractId) throws ErrorInfo {
        SledContract sledContract = SledContractCacheManager.getInstance().get((int) contractId);
        XQDefaultPositionRate xqDefaultPositionRate = XQDefaultPositionRateCacheManager.getInstance().get(sledContract.getSledCommodityId());
        if (xqDefaultPositionRate == null) {
            return null;
        }
        PositionFee positionFee = new PositionFee();
        positionFee.setSubAccountId(subAccountId);
        positionFee.setContractId(contractId);
        positionFee.setMargin(xqDefaultPositionRate.getMarginInfo());
        positionFee.setCommission(xqDefaultPositionRate.getCommissionInfo());
        return positionFee;
    }
}
