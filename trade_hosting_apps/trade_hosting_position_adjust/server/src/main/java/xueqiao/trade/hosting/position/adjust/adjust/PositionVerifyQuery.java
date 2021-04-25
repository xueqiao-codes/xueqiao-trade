package xueqiao.trade.hosting.position.adjust.adjust;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;
import com.longsheng.xueqiao.contract.standard.thriftapi.*;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.asset.thriftapi.helper.AssetStubFactory;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.adjust.storage.*;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;
import xueqiao.trade.hosting.position.adjust.utils.DateTimeUtils;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountNetPositionSummary;
import xueqiao.trade.hosting.tradeaccount.data.client.TradeHostingTradeAccountDataStub;
import xueqiao.trade.hosting.tradeaccount.data.helper.TradeAccountDataStubFactory;

import java.sql.Connection;
import java.util.*;

public class PositionVerifyQuery {

    public PositionVerifyPage queryPositionVerify(ReqPositionVerifyOption option, IndexedPageOption pageOption) throws ErrorInfo {
        return new DBQueryHelper<PositionVerifyPage>(PositionAdjustDB.Global()) {
            @Override
            protected PositionVerifyPage onQuery(Connection connection) throws Exception {
                PositionVerifyTable verifyTable = new PositionVerifyTable(connection);
                return verifyTable.query(option, pageOption);
            }
        }.query();
    }

    public PositionDifferencePage queryPositionDifference(ReqPositionDifferenceOption option, IndexedPageOption pageOption) throws TException {
        Preconditions.checkNotNull(option);
        if (!option.isSetVerifyId()) {
            Preconditions.checkArgument(option.isSetTradeAccountId());
            Preconditions.checkArgument(option.getTradeAccountId() > 0);
            PositionDifferencePage page = new PositionDifferencePage();
            List<PositionDifference> differences = verifyTotalPositionDifference(option.getTradeAccountId());
            page.setPage(differences);
            page.setTotal(differences.size());
            return page;
        }

        return new DBQueryHelper<PositionDifferencePage>(PositionAdjustDB.Global()) {
            @Override
            protected PositionDifferencePage onQuery(Connection connection) throws Exception {
                PositionDifferenceTable differenceTable = new PositionDifferenceTable(connection);
                return differenceTable.query(option, pageOption);
            }
        }.query();

    }

    public DailyPositionDifferencePage queryDailyPositionDifference(ReqDailyPositionDifferenceOption option, IndexedPageOption pageOption) throws ErrorInfo {

        return new DBQueryHelper<DailyPositionDifferencePage>(PositionAdjustDB.Global()) {
            @Override
            protected DailyPositionDifferencePage onQuery(Connection connection) throws Exception {
                return new PositionDailyDifferenceTable(connection).query(option, pageOption);
            }
        }.query();
    }

    public List<PositionDifference> verifyTotalPositionDifference(long tradeAccountId) throws TException {
        Map<Long, Integer> map = getSledNetPositionMap(tradeAccountId);
        TradeHostingTradeAccountDataStub tradeAccountDataStub = TradeAccountDataStubFactory.getStub();
        List<TradeAccountNetPositionSummary> upsideNetPositions = tradeAccountDataStub.getNetPositionSummaries(tradeAccountId);
        AppLog.d("upsideNetPositions: " + upsideNetPositions);
        return getPositionDifference(map, upsideNetPositions, tradeAccountId);
    }

    public DailyPositionDifferencePage queryDailyPositionDifference(ReqDailyPositionDifferenceOption option) throws TException {
        long now = System.currentTimeMillis();
        Map<Long, DailyPositionDifference> map = verifyDailyPositionDifference(option.getTradeAccountId(), now);
        List<DailyPositionDifference> list = new ArrayList<>();
        list.addAll(map.values());
        DailyPositionDifferencePage page = new DailyPositionDifferencePage();
        page.setPage(list);
        page.setTotal(list.size());
        return page;
    }

    public Map<Long, DailyPositionDifference> verifyDailyPositionDifference(long tradeAccountId, long endTimestamp) throws TException {
        List<PrePositionDifference> prePositionDifferences = getPrePositionDifference(tradeAccountId);
        List<TradeAccountNetPositionSummary> upSideNetPositionSummaries = getUpSideNetPositions(tradeAccountId);
        return getDailyPositionDifference(tradeAccountId, prePositionDifferences, upSideNetPositionSummaries, endTimestamp);
    }

    private Map<Long, DailyPositionDifference> getDailyPositionDifference(long tradeAccountId,
                                                                          List<PrePositionDifference> prePositionDifferences,
                                                                          List<TradeAccountNetPositionSummary> upSideNetPositionSummaries,
                                                                          long endTimestamp) throws TException {
        // 1. 查询上手所有的合约总持仓
        // 2. 查询雪橇合约总持仓
        // 3. 对应合约：查询昨日上手持仓，计算当日合约持仓
        //             查询当日雪橇持仓
        // 4. 对比合约每日净持仓

        Map<Long, Integer> upSideNetPositionMap = getUpSideNetPositionMap(upSideNetPositionSummaries, tradeAccountId);
        Map<Long, DailyPositionDifference> dailyPositionDifferenceMap = new HashMap<>();
        long dateSec = getDateSec();
        if (prePositionDifferences != null)
            for (PrePositionDifference pre : prePositionDifferences) {
                long startTimestamp = pre.getDotTimestampMs();
                int upsideDailyPosition;
                Integer totalPosition = upSideNetPositionMap.get(pre.getSledContractId());
                if (totalPosition == null) {
                    totalPosition = 0;
                }
                long sledContractId = pre.getSledContractId();
                upsideDailyPosition = totalPosition - pre.getUpsideNetPosition();
                DailyPositionDifference difference = getDailyPositionDifference(tradeAccountId, endTimestamp, sledContractId, startTimestamp, upsideDailyPosition, dateSec);
                dailyPositionDifferenceMap.put(pre.getSledContractId(), difference);
            }

        for (long sledContractId : upSideNetPositionMap.keySet()) {
            DailyPositionDifference difference = dailyPositionDifferenceMap.get(sledContractId);
            if (difference == null) {
                long startTimestamp = 0;
                int upsideDailyPosition = upSideNetPositionMap.get(sledContractId);
                difference = getDailyPositionDifference(tradeAccountId, endTimestamp, sledContractId, startTimestamp, upsideDailyPosition, dateSec);

                dailyPositionDifferenceMap.put(sledContractId, difference);
            }
        }

        Map<Long, Integer> sledNetPositionMap = getSledNetPositionMap(tradeAccountId);
        for (long sledContractId : sledNetPositionMap.keySet()) {
            DailyPositionDifference difference = dailyPositionDifferenceMap.get(sledContractId);
            if (difference == null) {
                long startTimestamp = 0;
                int upsideDailyPosition = 0;
                difference = getDailyPositionDifference(tradeAccountId, endTimestamp, sledContractId, startTimestamp, upsideDailyPosition, dateSec);
                dailyPositionDifferenceMap.put(sledContractId, difference);
            }

        }

        return dailyPositionDifferenceMap;
    }

    private DailyPositionDifference getDailyPositionDifference(long tradeAccountId, long endTimestamp, long sledContractId, long startTimestamp, int upsideDailyPosition, long dateSec) throws TException {
        DailyPositionDifference difference = new DailyPositionDifference();
        ReqPositionManualInputOption option = new ReqPositionManualInputOption();
        option.setStartInputTimestampMs(startTimestamp);
        option.setTradeAccountId(tradeAccountId);
        option.setEndInputTimestampMs(endTimestamp);
        option.setSledContractId(sledContractId);
        int inputDailyPosition = getInputDailyPosition(option);

        ReqTradeAccountPositionTradeDetailOption reqSledOption = new ReqTradeAccountPositionTradeDetailOption();
        reqSledOption.setTradeAccountId(tradeAccountId);
        reqSledOption.setSledContractId(sledContractId);
        reqSledOption.setStartTradeTimestampMs(startTimestamp);
        reqSledOption.setEndTradeTimestampMs(endTimestamp);
        int sledDailyPosition = getDailySledNetPosition(reqSledOption);

        difference.setDateSec(dateSec);
        difference.setTradeAccountId(tradeAccountId);
        difference.setSledContractId(sledContractId);
        difference.setSledNetPosition(sledDailyPosition);
        difference.setUpsideNetPosition(upsideDailyPosition);
        difference.setInputNetPosition(inputDailyPosition);
        difference.setSumNetPosition(sledDailyPosition + inputDailyPosition);
        difference.setDotTimestampMs(endTimestamp);
        return difference;
    }

    private Map<Long, Integer> getUpSideNetPositionMap(List<TradeAccountNetPositionSummary> upSideNetPositionSummaries, long tradeAccountId) throws TException {
        Map<Long, Integer> upsideNetPositionMap = new HashMap<>();

        IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        IHostingTradeAccountApi tradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
        BrokerAccessEntry entry = tradeAccountApi.getBrokerAccessEntry(tradeAccountId);
        TechPlatformEnv env = TradeAccountValidate.mappingBrokerPlatformEnv(entry.getTechPlatformEnv());

        // handle XLME 3M
        Set<Long> commodity3MIds = getCommodity3MIds();
        List<TradeAccountNetPositionSummary> contract3MUpside = new ArrayList<>();

        for (TradeAccountNetPositionSummary summary : upSideNetPositionSummaries) {
            if (commodity3MIds.contains(summary.getSledCommodityId())) {
                contract3MUpside.add(summary);
                continue;
            }

            ReqSledContractOption reqSledContractOption = new ReqSledContractOption();
            reqSledContractOption.setSledCommodityId((int) summary.getSledCommodityId());
            reqSledContractOption.setSledContractCode(summary.getSledContractCode());
            reqSledContractOption.setPlatformEnv(env);
            SledContractPage page = api.getContractOnlineStub().reqSledContract(reqSledContractOption, 0, 1);
            if (page.getPageSize() == 0) {
                continue;
            }
            long sledContractId = page.getPage().get(0).getSledContractId();
            upsideNetPositionMap.put(sledContractId, (int) summary.getNetVolume());
        }

        for (TradeAccountNetPositionSummary summary : contract3MUpside) {
            long contract3MId = get3MContractId(summary.getSledCommodityId(), env);
            if (contract3MId > 0) {
                Integer xlmeNetPosition = upsideNetPositionMap.get(contract3MId);
                if (xlmeNetPosition == null) {
                    xlmeNetPosition = (int) summary.getNetVolume();

                } else {
                    xlmeNetPosition = xlmeNetPosition + (int) summary.getNetVolume();
                }
                upsideNetPositionMap.put(contract3MId, xlmeNetPosition);
            }
        }

        return upsideNetPositionMap;
    }

    private long getDateSec() {
        long now = System.currentTimeMillis();
        String dateStr = DateTimeUtils.getDateStringDatePart(now);
        return DateTimeUtils.getDateTimeSecDatePart(dateStr);
    }

    private int getInputDailyPosition(ReqPositionManualInputOption option) throws ErrorInfo {
        List<PositionManualInput> list = new DBQueryHelper<List<PositionManualInput>>(PositionAdjustDB.Global()) {
            @Override
            protected List<PositionManualInput> onQuery(Connection connection) throws Exception {
                return new PositionManualInputTable(connection).query(option);
            }
        }.query();
        int position = 0;
        for (PositionManualInput input : list) {
            if (PositionDirection.POSITION_SELL.equals(input.getPositionDirection())) {
                position = position - Math.abs(input.getVolume());
            } else {
                position = position + Math.abs(input.getVolume());
            }
        }

        return position;
    }

    private List<PrePositionDifference> getPrePositionDifference(long tradeAccountId) throws ErrorInfo {
        return new DBQueryHelper<List<PrePositionDifference>>(PositionAdjustDB.Global()) {
            @Override
            protected List<PrePositionDifference> onQuery(Connection connection) throws Exception {
                return new PrePositionDifferenceTable(connection).query(tradeAccountId);
            }
        }.query();
    }

    private List<TradeAccountNetPositionSummary> getUpSideNetPositions(long tradeAccountId) throws TException {
        TradeHostingTradeAccountDataStub tradeAccountDataStub = TradeAccountDataStubFactory.getStub();
        return tradeAccountDataStub.getNetPositionSummaries(tradeAccountId);
    }


    private List<PositionDifference> getPositionDifference(Map<Long, Integer> map, List<TradeAccountNetPositionSummary> upsideNetPositions, long tradeAccountId) throws TException {
        List<Long> upsideSledContracts = new ArrayList<>();
        Map<Long, PositionDifference> differences = new HashMap<>();
        IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        IHostingTradeAccountApi tradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
        BrokerAccessEntry entry = tradeAccountApi.getBrokerAccessEntry(tradeAccountId);
        TechPlatformEnv env = TradeAccountValidate.mappingBrokerPlatformEnv(entry.getTechPlatformEnv());

        List<TradeAccountNetPositionSummary> contract3MUpside = new ArrayList<>();
        Set<Long> commodity3MIds = getCommodity3MIds();

        for (TradeAccountNetPositionSummary summary : upsideNetPositions) {
            if (commodity3MIds.contains(summary.getSledCommodityId())) {
                contract3MUpside.add(summary);
                continue;
            }

            ReqSledContractOption reqSledContractOption = new ReqSledContractOption();
            reqSledContractOption.setSledCommodityId((int) summary.getSledCommodityId());
            reqSledContractOption.setSledContractCode(summary.getSledContractCode());
            reqSledContractOption.setPlatformEnv(env);

            SledContractPage page = api.getContractOnlineStub().reqSledContract(reqSledContractOption, 0, 1);
            if (page.getPageSize() == 0) {
                continue;
            }
            long sledContractId = page.getPage().get(0).getSledContractId();
            upsideSledContracts.add(sledContractId);
            Integer netPosition = map.get(sledContractId);
            if (netPosition == null) {
                netPosition = 0;
            }
            if (summary.getNetVolume() != netPosition) {
                PositionDifference difference = new PositionDifference();
                difference.setSledContractId(sledContractId);
                difference.setUpsideNetPosition((int) summary.getNetVolume());
                difference.setSledNetPosition(netPosition);
                difference.setTradeAccountId(tradeAccountId);
                differences.put(sledContractId, difference);
            }
        }

        for (long sledContractId : map.keySet()) {
            if (!upsideSledContracts.contains(sledContractId)) {
                Integer netPosition = map.get(sledContractId);
                if (netPosition != null && netPosition != 0) {
                    PositionDifference difference = new PositionDifference();
                    difference.setSledContractId(sledContractId);
                    difference.setUpsideNetPosition(0);
                    difference.setSledNetPosition(netPosition);
                    difference.setTradeAccountId(tradeAccountId);

                    differences.put(sledContractId, difference);

                }
            }
        }
        // add 3M
        List<PositionDifference> contract3MDifferences = handle3MContract(map, contract3MUpside, env);
        for (PositionDifference difference : contract3MDifferences) {
            AppLog.d("PositionDifference: " + difference);
            if (difference.getSledNetPosition() == difference.getUpsideNetPosition()) {
                differences.remove(difference.getSledContractId());
            } else {
                differences.put(difference.getSledContractId(), difference);
            }
        }
        List<PositionDifference> list = new ArrayList<>();
        if (differences.size() > 0) {
            list.addAll(differences.values());
        }
        return list;
    }

    private List<PositionDifference> handle3MContract(Map<Long, Integer> map, List<TradeAccountNetPositionSummary> summaries, TechPlatformEnv env) throws TException {
        // 查询 LME 3M 合约
        List<PositionDifference> differences = new ArrayList<>();
        Map<Long, PositionDifference> xlme3MDifferences = new HashMap<>();
        for (TradeAccountNetPositionSummary summary : summaries) {
            AppLog.d("TradeAccountNetPositionSummary: " + summary);
            long contract3MId = get3MContractId(summary.getSledCommodityId(), env);
            if (contract3MId > 0) {
                PositionDifference xlme3MDifference = xlme3MDifferences.get(contract3MId);
                AppLog.d("xlme3MDifference: " + xlme3MDifference);
                if (xlme3MDifference == null) {
                    xlme3MDifference = new PositionDifference();
                    xlme3MDifference.setSledContractId(contract3MId);
                    xlme3MDifference.setUpsideNetPosition((int) summary.getNetVolume());
                    xlme3MDifference.setTradeAccountId(summary.getTradeAccountId());

                    Integer netPosition = map.get(contract3MId);
                    if (netPosition == null) {
                        netPosition = 0;
                    }
                    xlme3MDifference.setSledNetPosition(netPosition);
                    xlme3MDifferences.put(contract3MId, xlme3MDifference);
                } else {
                    int existNetPosition = xlme3MDifference.getUpsideNetPosition();
                    xlme3MDifference.setUpsideNetPosition(existNetPosition + (int) summary.getNetVolume());
                }
            }
        }
        if (xlme3MDifferences.size() > 0) {
            differences.addAll(xlme3MDifferences.values());
        }
        return differences;
    }

    private Set<Long> getCommodity3MIds() throws TException {
        IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        ReqSledCommodityOption option = new ReqSledCommodityOption();
        option.setExchangeMic("XLME");
        option.setSledCommodityType(SledCommodityType.FUTURES);
        SledCommodityPage page = api.getContractOnlineStub().reqSledCommodity(option, 0, Integer.MAX_VALUE);
        Set<Long> commodityIds = new HashSet<>();
        for (SledCommodity sledCommodity : page.getPage()) {
            commodityIds.add((long) sledCommodity.getSledCommodityId());
        }
        return commodityIds;
    }

    private long get3MContractId(long sledCommodityId, TechPlatformEnv env) throws TException {
        IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        ReqSledContractOption option = new ReqSledContractOption();
        option.setPlatformEnv(env);
        option.setSledCommodityId((int) sledCommodityId);
        option.setSledContractCode("3M");
        SledContractPage page = api.getContractOnlineStub().reqSledContract(option, 0, 1);
        if (page.getPageSize() > 0) {
            return page.getPage().get(0).getSledContractId();
        }
        return 0;
    }

    private int getDailySledNetPosition(ReqTradeAccountPositionTradeDetailOption option) throws TException {
        IndexedPageOption pageOption = new IndexedPageOption();
        AssetTradeDetailPage detail = AssetStubFactory.getStub().getTradeAccountPositionTradeDetail(option, pageOption);
        int position = 0;
        if (detail.getPageSize() > 0) {
            for (AssetTradeDetail tradeDetail : detail.getPage()) {
                if (HostingExecTradeDirection.TRADE_SELL.equals(tradeDetail.getExecTradeDirection())) {
                    position = position - Math.abs(tradeDetail.getTradeVolume());
                } else {
                    position = position + Math.abs(tradeDetail.getTradeVolume());
                }
            }
        }

        return position;
    }

    private Map<Long, Integer> getSledNetPositionMap(long tradeAccountId) throws TException {
        ReqTradeAccountPositionOption option = new ReqTradeAccountPositionOption();
        option.setTradeAccountId(tradeAccountId);
        IndexedPageOption pageOption = new IndexedPageOption();
        TradeAccountPositionPage tradeAccountPositionPage = AssetStubFactory.getStub().getTradeAccountPosition(option, pageOption);
        TradeAccountPosition tradeAccountPosition;
        if (tradeAccountPositionPage.getPageSize() > 0) {
            tradeAccountPosition = tradeAccountPositionPage.getPage().get(0);
        } else {
            tradeAccountPosition = new TradeAccountPosition();
        }

        List<TradeAccountPositionTable> manualInputNetPositions = new DBQueryHelper<List<TradeAccountPositionTable>>(PositionAdjustDB.Global()) {
            @Override
            protected List<TradeAccountPositionTable> onQuery(Connection connection) throws Exception {
                return new NetPositionManualInputTable(connection).query(option);
            }
        }.query();

        Map<Long, Integer> map = tradeAccountPosition.getSledContractNetPositionMap();
        if (map == null) {
            map = new HashMap<>();
        }
        for (TradeAccountPositionTable manualInputPosition : manualInputNetPositions) {
            Integer netPosition = map.get(manualInputPosition.getSledContractId());
            int newNetPosition;
            if (netPosition == null) {
                newNetPosition = manualInputPosition.getNetPosition();
            } else {
                newNetPosition = netPosition + manualInputPosition.getNetPosition();
            }
            map.put(manualInputPosition.getSledContractId(), newNetPosition);
        }
        return map;
    }
}
