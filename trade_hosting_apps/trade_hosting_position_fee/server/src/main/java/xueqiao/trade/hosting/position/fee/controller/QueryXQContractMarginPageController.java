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
import xueqiao.trade.hosting.position.fee.storage.table.XQContractMarginTable;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQPFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractMargin;
import xueqiao.trade.hosting.position.fee.thriftapi.XQContractMarginPage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryXQContractMarginPageController implements IController {

    private QueryXQPFeeOptions queryOptions;
    private IndexedPageOption pageOption;

    public QueryXQContractMarginPageController(QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) {
        this.queryOptions = queryOptions;
        this.pageOption = pageOption;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        // check nothing
    }

    public XQContractMarginPage doQuery() throws TException {
        XQContractMarginPage page = new DBQueryHelper<XQContractMarginPage>(PositionFeeDB.Global()) {
            @Override
            protected XQContractMarginPage onQuery(Connection connection) throws Exception {
                return new XQContractMarginTable(connection).query(queryOptions, pageOption);
            }
        }.query();

        if (page != null && page.getXqContractMarginListSize() > 0) {
            List<Integer> commodityIds = new ArrayList<>();
            for (XQContractMargin xqContractMargin : page.getXqContractMarginList()) {
                SledContract sledContract = SledContractCacheManager.getInstance().get((int) xqContractMargin.getContractInfo().getContractId());
                SledExchange sledExchange = SledExchangeCacheManager.getInstance().get(xqContractMargin.getContractInfo().getExchangeMic());

                xqContractMargin.getContractInfo().setContractEngName(sledContract.getContractEngName());
                xqContractMargin.getContractInfo().setContractCnName(sledContract.getContractCnName());
                xqContractMargin.getContractInfo().setExchangeAcronym(sledExchange.getAcronym());
                xqContractMargin.getContractInfo().setExchangeCnAcronym(sledExchange.getCnAcronym());
                commodityIds.add(sledContract.getSledCommodityId());
            }
            List<SledCommodity> commodityList = HostingContractApi.queryCommodity(commodityIds);
            Map<Integer, SledCommodity> commodityMap = new HashMap<>();
            if (commodityList != null && commodityList.size() > 0) {
                for (SledCommodity commodity : commodityList) {
                    commodityMap.put(commodity.getSledCommodityId(), commodity);
                }
                for (XQContractMargin xqContractMargin : page.getXqContractMarginList()) {
                    SledCommodity commodity = commodityMap.get((int)xqContractMargin.getContractInfo().getCommodityId());
                    if (commodity != null) {
                        xqContractMargin.getContractInfo().setCommodityEngAcronym(commodity.getEngName());
                        xqContractMargin.getContractInfo().setCommodityCnAcronym(commodity.getCnName());
                    }
                }
            }
        }

        return page;
    }
}
