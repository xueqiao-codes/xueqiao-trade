package xueqiao.trade.hosting.asset.core;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.event.handler.PositionFeeHandler;
import xueqiao.trade.hosting.asset.storage.*;
import xueqiao.trade.hosting.asset.storage.account.sub.*;
import xueqiao.trade.hosting.asset.struct.ExecOrderOutSide;
import xueqiao.trade.hosting.asset.struct.FrozenPosition;
import xueqiao.trade.hosting.asset.struct.PreFund4Calculate;

import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/* 跟子账号相关的实时数据 */
public class AssetSubAccountGlobalData {

    private long subAccountId;

    // execOrderId: ExecOrderOutSide 如果是组合单，会包含多个合约的下单信息
    private Map<Long, Map<Long, ExecOrderOutSide>> execOrderOutSideMap = new ConcurrentHashMap<>();

    // 实时 合约：持仓信息
    private Map<Long, HostingSledContractPosition> hostingPositionMap = new ConcurrentHashMap<>();

    // 实时 币种：资金信息
    private Map<String, HostingFund> hostingFundMap = new ConcurrentHashMap<>();

    // 实时 基币：资金信息
    private Map<String, HostingFund> basicCurrencyFundMap = new ConcurrentHashMap<>();

    public Map<String, HostingFund> getHostingFundMap() {
        return hostingFundMap;
    }

    public Map<Long, HostingSledContractPosition> getHostingPositionMap() {
        return hostingPositionMap;
    }

    public void setHostingPositionMap(Map<Long, HostingSledContractPosition> map) {
        this.hostingPositionMap = map;
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    private Map<Long, AssetCalculateConfig> commodityCalculatorConfigMap = new ConcurrentHashMap<>();

    private Map<Long, FrozenPosition> frozenPositionMap = new ConcurrentHashMap<>();

    // sled contract id: 最新结算时间点
    private Map<Long, Long> contractSettlementTimestamp = new ConcurrentHashMap<>();

    // synchronized locks
    private byte[] outsideContractLock = new byte[0];
    private byte[] sledContractPositionLock = new byte[0];
    private byte[] assetCalculateConfigLock = new byte[0];

    public AssetSubAccountGlobalData(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public HostingSledContractPosition getHostingSledContractPosition(long sledContractId) throws TException {

        HostingSledContractPosition position = hostingPositionMap.get(sledContractId);
        if (position == null) {
            synchronized (sledContractPositionLock) {
                position = hostingPositionMap.get(sledContractId);
                if (position == null) {
                    position = new HostingSledContractPosition();
                    position.setSubAccountId(subAccountId);
                    position.setSledContractId(sledContractId);
                    long sledCommodityId = AssetGlobalDataFactory.getInstance().getContractGlobalData(subAccountId).getCommodityIdByContractId(sledContractId);
                    SledCommodity sledCommodity = AssetTradeHandler.getSledCommodity(sledCommodityId);
                    String currency = sledCommodity.getTradeCurrency();
                    position.setCurrency(currency);
                    HostingPositionVolume positionVolume = new HostingPositionVolume();
                    positionVolume.setSubAccountId(subAccountId);
                    positionVolume.setSledContractId(sledContractId);
                    positionVolume.setCurrency(currency);
                    position.setPositionVolume(positionVolume);
                    HostingPositionFund positionFund = new HostingPositionFund();
                    positionFund.setSubAccountId(subAccountId);
                    positionFund.setSledContractId(sledContractId);
                    positionFund.setCurrency(currency);
                    position.setPositionFund(positionFund);
                    hostingPositionMap.put(sledContractId, position);
                }
            }
        }
        return position;
    }

    public PreFund4Calculate getPreFund4Calculate(String currency) throws ErrorInfo {
//        PreFund4Calculate preFund4Calculate = preFund4CalculateMap.get(currency);
//        if (preFund4Calculate != null) {
//            if (preFund4Calculate.getRefreshTimestamp() - System.currentTimeMillis() < 60 * 1000) {
//                return preFund4Calculate;
//            }
//        }

        PreFund4Calculate preFund4Calculate = new DBQueryHelper<PreFund4Calculate>(AssetDB.Global()) {
            @Override
            protected PreFund4Calculate onQuery(Connection connection) throws Exception {
                PreSettlementFundDetailTable preFundTable = new PreSettlementFundDetailTable(connection);
                SettlementFundDetail preFund = preFundTable.query(currency, subAccountId);

                if (preFund == null) {
                    preFund = new SettlementFundDetail();
                }

                AssetSubAccountFundTable assetSubAccountFundTable = new AssetSubAccountFundTable(connection);
                PageOption pageOption = new PageOption();
                pageOption.setPageSize(Integer.MAX_VALUE);
                pageOption.setPageIndex(0);
                ReqSubAccountFundOption subAccountOption = new ReqSubAccountFundOption();
                subAccountOption.setSubAccountId(subAccountId);
                subAccountOption.setCurrency(currency);
                PageResult<HostingSubAccountFund> subAccountFundPage = assetSubAccountFundTable.query(subAccountOption, pageOption);

                double creditAmount = 0.0;
                if (subAccountFundPage.getPageList().size() > 0) {
                    creditAmount = subAccountFundPage.getPageList().get(0).getCreditAmount();
                }
                SubAccountHostingFundTable hostingFundTable = new SubAccountHostingFundTable(connection);
                HostingFund lastSaveFund = hostingFundTable.queryLatest(subAccountId, currency, false);
                if (lastSaveFund == null) {
                    lastSaveFund = new HostingFund();
                }

                AssetSubAccountMoneyRecordTable assetSubAccountMoneyRecordTable = new AssetSubAccountMoneyRecordTable(connection);

                ReqMoneyRecordOption option = new ReqMoneyRecordOption();
                option.setCurrency(currency);
                option.setSubAccountId(subAccountId);
                option.setStartTimestampMs(lastSaveFund.getCreateTimestampMs());

                PageResult<HostingSubAccountMoneyRecord> recordPage = assetSubAccountMoneyRecordTable.query(option, pageOption);

                BigDecimal depositAmount = BigDecimal.ZERO;
                BigDecimal withdrawAmount = BigDecimal.ZERO;
                for (HostingSubAccountMoneyRecord record : recordPage.getPageList()) {
                    if (HostingSubAccountMoneyRecordDirection.DEPOSIT.equals(record.getDirection())) {
                        depositAmount = depositAmount.add(doubleToBigDecimal(record.getTotalAmount()));
                    } else if (HostingSubAccountMoneyRecordDirection.WITHDRAW.equals(record.getDirection())) {
                        withdrawAmount = withdrawAmount.add(doubleToBigDecimal(record.getTotalAmount()));
                    }
                }

                PreFund4Calculate preFund4Calculate = new PreFund4Calculate();
                preFund4Calculate.setCurrency(currency);
                preFund4Calculate.setSubAccountId(subAccountId);
                preFund4Calculate.setPreFund(doubleToBigDecimal(preFund.getBalance()));
                preFund4Calculate.setCreditAmount(doubleToBigDecimal(creditAmount));
                preFund4Calculate.setDepositAmount(depositAmount);
                preFund4Calculate.setWithdrawAmount(withdrawAmount);
                preFund4Calculate.setRefreshTimestamp(System.currentTimeMillis());
                return preFund4Calculate;
            }
        }.query();

        return preFund4Calculate;
    }

    private BigDecimal doubleToBigDecimal(double x) {
        if (Double.isNaN(x)) {
            x = 0.0;
        }
        return new BigDecimal(Double.toString(x));
    }

    public AssetCalculateConfig getAssetCalculateConfig(long sledContractId) throws TException {
        return getAssetCalculateConfig(sledContractId, false);
    }

    public AssetCalculateConfig getAssetCalculateConfig(long sledContractId, boolean isUpdate) throws TException {
        AssetCalculateConfig assetCalculateConfig = commodityCalculatorConfigMap.get(sledContractId);
        if (assetCalculateConfig == null) {
            synchronized (assetCalculateConfigLock) {
                assetCalculateConfig = commodityCalculatorConfigMap.get(sledContractId);
                if (isUpdate || assetCalculateConfig == null) {
                    assetCalculateConfig = PositionFeeHandler.queryAssetCalculateConfig(subAccountId, sledContractId);
                    commodityCalculatorConfigMap.put(sledContractId, assetCalculateConfig);
                }
            }
        }
        return assetCalculateConfig;
    }

    public Map<Long, ExecOrderOutSide> getExecOrderOutSideMap(long execOrderId) {
        synchronized (outsideContractLock) {
            return execOrderOutSideMap.get(execOrderId);
        }
    }

    public void addExecOrderOutSideMap(long execOrderId, Map<Long, ExecOrderOutSide> map) {
        execOrderOutSideMap.put(execOrderId, map);
    }

    public synchronized void removeOutSideContract(long execOrderId) {
        execOrderOutSideMap.remove(execOrderId);
    }

    public Map<Long, Map<Long, ExecOrderOutSide>> getExecOrderOutSideMap() {
        return execOrderOutSideMap;
    }

    public synchronized void setSettlementTime(long sledContractId) throws TException {

        long now = System.currentTimeMillis();
        Long timestamp = contractSettlementTimestamp.get(sledContractId);
        if (timestamp == null || timestamp <= now) {
            addSettlementTask(sledContractId, now);
        }
    }

    public synchronized void setSettlementTime(long sledContractId, long delayMs) throws TException {

        long now = System.currentTimeMillis();
        Long timestamp = contractSettlementTimestamp.get(sledContractId);
        if (timestamp == null || timestamp == 0) {
            timestamp = now + delayMs;
            contractSettlementTimestamp.put(sledContractId, timestamp);
            AppLog.w("=== complete settlementTimestamp: " + sledContractId + "-" + timestamp);
            ExecutorHandler.calculateSettlement(sledContractId, subAccountId, delayMs, TimeUnit.MILLISECONDS);
        } else if (timestamp <= now) {
            addSettlementTask(sledContractId, now);
        }
    }

    private void addSettlementTask(long sledContractId, long now) throws TException {
        Long timestamp;
        timestamp = AssetGlobalDataFactory.getInstance().getContractGlobalData(subAccountId).getSettlementTime(sledContractId);
        if (timestamp > now) {
            contractSettlementTimestamp.put(sledContractId, timestamp);
            long delay = timestamp - now;
            AppLog.w("settlementTimestamp: " + sledContractId + "-" + timestamp);
            ExecutorHandler.calculateSettlement(sledContractId, subAccountId, delay, TimeUnit.MILLISECONDS);
        } else {
            AppLog.e("settlementTimestamp calculate error: " + sledContractId + "-" + timestamp);
        }
    }

    public Map<Long, FrozenPosition> getFrozenPositionMap() {
        return frozenPositionMap;
    }

    public Map<String, HostingFund> getBasicCurrencyFundMap() {
        return basicCurrencyFundMap;
    }
}
