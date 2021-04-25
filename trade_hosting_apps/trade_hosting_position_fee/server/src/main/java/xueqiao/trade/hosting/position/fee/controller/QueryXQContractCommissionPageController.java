package xueqiao.trade.hosting.position.fee.controller;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledExchange;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.core.cache.SledContractCacheManager;
import xueqiao.trade.hosting.position.fee.core.cache.SledExchangeCacheManager;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQContractCommissionTable;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommission;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractCommissionPage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryXQContractCommissionPageController implements IController {

    private QueryXQPFeeOptions queryOptions;
    private IndexedPageOption pageOption;

    public QueryXQContractCommissionPageController(QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) {
        this.queryOptions = queryOptions;
        this.pageOption = pageOption;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        // check nothing
    }

    public XQContractCommissionPage doQuery() throws TException {
        XQContractCommissionPage page = new DBQueryHelper<XQContractCommissionPage>(PositionFeeDB.Global()) {
            @Override
            protected XQContractCommissionPage onQuery(Connection connection) throws Exception {
                return new XQContractCommissionTable(connection).query(queryOptions, pageOption);
            }
        }.query();

        if (page != null && page.getXqContractCommissionListSize() > 0) {
            List<Integer> commodityIds = new ArrayList<>();
            for (XQContractCommission xqContractCommission : page.getXqContractCommissionList()) {
                SledContract sledContract = SledContractCacheManager.getInstance().get((int) xqContractCommission.getContractInfo().getContractId());
                SledExchange sledExchange = SledExchangeCacheManager.getInstance().get(xqContractCommission.getContractInfo().getExchangeMic());

                xqContractCommission.getContractInfo().setContractEngName(sledContract.getContractEngName());
                xqContractCommission.getContractInfo().setContractCnName(sledContract.getContractCnName());
                xqContractCommission.getContractInfo().setExchangeAcronym(sledExchange.getAcronym());
                xqContractCommission.getContractInfo().setExchangeCnAcronym(sledExchange.getCnAcronym());
                commodityIds.add(sledContract.getSledCommodityId());
            }
            List<SledCommodity> commodityList = HostingContractApi.queryCommodity(commodityIds);
            Map<Integer, SledCommodity> commodityMap = new HashMap<>();
            if (commodityList != null && commodityList.size() > 0) {
                for (SledCommodity commodity : commodityList) {
                    commodityMap.put(commodity.getSledCommodityId(), commodity);
                }
                for (XQContractCommission xqContractCommission : page.getXqContractCommissionList()) {
                    SledCommodity commodity = commodityMap.get((int)xqContractCommission.getContractInfo().getCommodityId());
                    if (commodity != null) {
                        xqContractCommission.getContractInfo().setCommodityEngAcronym(commodity.getEngName());
                        xqContractCommission.getContractInfo().setCommodityCnAcronym(commodity.getCnName());
                    }
                }
            }
        }

        return page;
    }
}
