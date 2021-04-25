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
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractCommissionTable;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryUpsidePFeeOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommission;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommissionPage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUpsideContractCommissionPageController implements IController {

    private QueryUpsidePFeeOptions queryOptions;
    private IndexedPageOption pageOption;

    public QueryUpsideContractCommissionPageController(QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) {
        this.queryOptions = queryOptions;
        this.pageOption = pageOption;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        // check nothing
    }

    public UpsideContractCommissionPage doQuery() throws TException {
        UpsideContractCommissionPage page = new DBQueryHelper<UpsideContractCommissionPage>(PositionFeeDB.Global()) {
            @Override
            protected UpsideContractCommissionPage onQuery(Connection connection) throws Exception {
                return new UpsideContractCommissionTable(connection).query(queryOptions, pageOption);
            }
        }.query();

        if (page != null && page.getUpsideContractCommissionListSize() > 0) {
            List<Integer> commodityIds = new ArrayList<>();
            for (UpsideContractCommission upsideContractCommission : page.getUpsideContractCommissionList()) {
                SledContract sledContract = SledContractCacheManager.getInstance().get((int) upsideContractCommission.getContractInfo().getContractId());
                SledExchange sledExchange = SledExchangeCacheManager.getInstance().get(upsideContractCommission.getContractInfo().getExchangeMic());

                upsideContractCommission.getContractInfo().setContractEngName(sledContract.getContractEngName());
                upsideContractCommission.getContractInfo().setContractCnName(sledContract.getContractCnName());
                upsideContractCommission.getContractInfo().setExchangeAcronym(sledExchange.getAcronym());
                upsideContractCommission.getContractInfo().setExchangeCnAcronym(sledExchange.getCnAcronym());
                commodityIds.add(sledContract.getSledCommodityId());
            }
            List<SledCommodity> commodityList = HostingContractApi.queryCommodity(commodityIds);
            Map<Integer, SledCommodity> commodityMap = new HashMap<>();
            if (commodityList != null && commodityList.size() > 0) {
                for (SledCommodity commodity : commodityList) {
                    commodityMap.put(commodity.getSledCommodityId(), commodity);
                }
                for (UpsideContractCommission upsideContractCommission : page.getUpsideContractCommissionList()) {
                    SledCommodity commodity = commodityMap.get((int)upsideContractCommission.getContractInfo().getCommodityId());
                    if (commodity != null) {
                        upsideContractCommission.getContractInfo().setCommodityEngAcronym(commodity.getEngName());
                        upsideContractCommission.getContractInfo().setCommodityCnAcronym(commodity.getCnName());
                    }
                }
            }
        }

        return page;
    }
}
