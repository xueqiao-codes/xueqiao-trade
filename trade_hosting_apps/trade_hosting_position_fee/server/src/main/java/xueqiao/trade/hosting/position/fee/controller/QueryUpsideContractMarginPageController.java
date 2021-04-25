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
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractMarginTable;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMargin;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMarginPage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUpsideContractMarginPageController implements IController {

    private QueryUpsidePFeeOptions queryOptions;
    private IndexedPageOption pageOption;

    public QueryUpsideContractMarginPageController(QueryUpsidePFeeOptions queryOptions, org.soldier.platform.page.IndexedPageOption pageOption) {
        this.queryOptions = queryOptions;
        this.pageOption = pageOption;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        // check nothing
    }

    public UpsideContractMarginPage doQuery() throws TException {
        UpsideContractMarginPage page = new DBQueryHelper<UpsideContractMarginPage>(PositionFeeDB.Global()) {
            @Override
            protected UpsideContractMarginPage onQuery(Connection connection) throws Exception {
                return new UpsideContractMarginTable(connection).query(queryOptions, pageOption);
            }
        }.query();

        if (page != null && page.getUpsideContractMarginListSize() > 0) {
            List<Integer> commodityIds = new ArrayList<>();
            for (UpsideContractMargin upsideContractMargin : page.getUpsideContractMarginList()) {
                SledContract sledContract = SledContractCacheManager.getInstance().get((int) upsideContractMargin.getContractInfo().getContractId());
                SledExchange sledExchange = SledExchangeCacheManager.getInstance().get(upsideContractMargin.getContractInfo().getExchangeMic());

                upsideContractMargin.getContractInfo().setContractEngName(sledContract.getContractEngName());
                upsideContractMargin.getContractInfo().setContractCnName(sledContract.getContractCnName());
                upsideContractMargin.getContractInfo().setExchangeAcronym(sledExchange.getAcronym());
                upsideContractMargin.getContractInfo().setExchangeCnAcronym(sledExchange.getCnAcronym());
                commodityIds.add(sledContract.getSledCommodityId());
            }
            List<SledCommodity> commodityList = HostingContractApi.queryCommodity(commodityIds);
            Map<Integer, SledCommodity> commodityMap = new HashMap<>();
            if (commodityList != null && commodityList.size() > 0) {
                for (SledCommodity commodity : commodityList) {
                    commodityMap.put(commodity.getSledCommodityId(), commodity);
                }
                for (UpsideContractMargin upsideContractMargin : page.getUpsideContractMarginList()) {
                    SledCommodity commodity = commodityMap.get((int)upsideContractMargin.getContractInfo().getCommodityId());
                    if (commodity != null) {
                        upsideContractMargin.getContractInfo().setCommodityEngAcronym(commodity.getEngName());
                        upsideContractMargin.getContractInfo().setCommodityCnAcronym(commodity.getCnName());
                    }
                }
            }
        }

        return page;
    }
}
