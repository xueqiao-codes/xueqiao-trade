package xueqiao.trade.hosting.dealing.storage;

import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.HostingUpsideNotifyStateHandleInfo;
import xueqiao.trade.hosting.dealing.storage.data.ExecOrderIndexEntryV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderCleanIndexTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderInDealingTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecTradeLegTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecTradeTableV2;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DealingStorageApiV2 {

    public static HostingExecOrder getOrder(long execOrderId) throws ErrorInfo {
        return new DBQueryHelper<HostingExecOrder>(DealingDBV2.Global()) {

            @Override
            protected HostingExecOrder onQuery(Connection conn) throws Exception {
                return new HostingExecOrderTableV2(conn).getOrder(execOrderId);
            }
        }.query();
    }

    public static List<HostingExecTrade> getOrderTrades(long execOrderId) throws ErrorInfo {
        return new DBQueryHelper<List<HostingExecTrade>>(DealingDBV2.Global()) {

            @Override
            protected List<HostingExecTrade> onQuery(Connection conn) throws Exception {
                return new HostingExecTradeTableV2(conn).getTradeList(execOrderId);
            }
        }.query();
    }

    public static PageResult<ExecOrderIndexEntryV2> getIndexEntries(PageOption pageOption) throws ErrorInfo {
        return new DBQueryHelper<PageResult<ExecOrderIndexEntryV2>>(DealingDBV2.Global()) {

            @Override
            protected PageResult<ExecOrderIndexEntryV2> onQuery(Connection conn) throws Exception {
                return new HostingExecOrderInDealingTableV2(conn).getIndexEntries(pageOption);
            }
        }.query();
    }

    public static List<HostingExecOrder> batchGetOrders(Set<Long> execOrderIds) throws ErrorInfo{
        return new DBQueryHelper<List<HostingExecOrder>>(DealingDBV2.Global()) {

            @Override
            protected List<HostingExecOrder> onQuery(Connection conn) throws Exception {
                return new HostingExecOrderTableV2(conn).batchGetOrders(execOrderIds);
            }
        }.query();
    }

    public static List<HostingExecTradeLeg> getRelatedTradeLegs(long execTradeId) throws ErrorInfo {

        return new DBQueryHelper<List<HostingExecTradeLeg>>(DealingDBV2.Global()) {

            @Override
            protected List<HostingExecTradeLeg> onQuery(Connection conn) throws Exception {
                return new HostingExecTradeLegTableV2(conn).getTradeRelatedLegs(execTradeId);
            }
        }.query();

    }

    public static PageResult<HostingExecOrder> getInDealingOrders(PageOption pageOption) throws ErrorInfo {
        Preconditions.checkNotNull(pageOption);

        PageResult<ExecOrderIndexEntryV2> indexEntries = DealingStorageApiV2.getIndexEntries(pageOption);
        PageResult<HostingExecOrder> pageResult = new PageResult<>();
        pageResult.setTotalCount(indexEntries.getTotalCount());

        if (indexEntries.getPageList() == null || indexEntries.getPageList().isEmpty()) {
            pageResult.setPageList(new ArrayList<>());
            return pageResult;
        }

        Set<Long> execOrderIds = new HashSet<>();
        for (ExecOrderIndexEntryV2 indexEntry : indexEntries.getPageList()) {
            execOrderIds.add(indexEntry.getExecOrderId());
        }

        pageResult.setPageList(DealingStorageApiV2.batchGetOrders(execOrderIds));

        return pageResult;
    }

    public static void cleanAll() throws ErrorInfo {
        new DBTransactionHelper<Void>(DealingDBV2.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingExecOrderTableV2(getConnection()).deleteAll();
                new HostingExecTradeTableV2(getConnection()).deleteAll();
                new HostingExecTradeLegTableV2(getConnection()).deleteAll();
                new HostingExecOrderInDealingTableV2(getConnection()).deleteAll();
                new HostingExecOrderCleanIndexTableV2(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    public static List<HostingUpsideNotifyStateHandleInfo>
        trimHandleInfos(List<HostingUpsideNotifyStateHandleInfo> originhandleInfos) throws TException {
        if (originhandleInfos == null) {
            return null;
        }

        List<HostingUpsideNotifyStateHandleInfo> stateHandleInfos = new LinkedList<>(originhandleInfos);
        String stateHandlerInfosStr = ThriftExtJsonUtils.listToJsonTBase(stateHandleInfos);
        while(stateHandlerInfosStr.length() >= DealingStorageConstants.MAX_STATE_HANDLEINFOS_FIELD_LENGTH) {
            stateHandleInfos.remove(0);
            stateHandlerInfosStr = ThriftExtJsonUtils.listToJsonTBase(stateHandleInfos);
        }
        return stateHandleInfos;
    }

    public static List<HostingExecOrderState> trimHistoryStates(List<HostingExecOrderState> originHistoryStates)
            throws TException {
        if (originHistoryStates == null) {
            return null;
        }

        List<HostingExecOrderState> historyStates = new LinkedList<HostingExecOrderState>(originHistoryStates);
        String historyStatesStr = ThriftExtJsonUtils.listToJsonTBase(historyStates);
        while(historyStatesStr.length() >= DealingStorageConstants.MAX_STATE_HISTORYS_FIELD_LENGTH) {
            historyStates.remove(0);
            historyStatesStr = ThriftExtJsonUtils.listToJsonTBase(historyStates);
        }
        return historyStates;
    }

}
