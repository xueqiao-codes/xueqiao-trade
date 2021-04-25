package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.struct.ExecOrderOutSide;
import xueqiao.trade.hosting.asset.struct.OpenCloseTag;
import xueqiao.trade.hosting.asset.struct.PositionFundCalculateData;
import xueqiao.trade.hosting.asset.struct.FrozenPosition;
import xueqiao.trade.hosting.asset.thriftapi.*;

import java.math.BigDecimal;
import java.util.*;

public class AssetFrozenFundCalculator extends AssetBaseCalculator<PositionFundCalculateData> {

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_FROZEN_FUND_KEY;
    }

    public AssetFrozenFundCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public void onCalculate(PositionFundCalculateData data) throws TException {
        Preconditions.checkNotNull(data);
        Preconditions.checkArgument(data.getSledContractId() > 0);

        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(this.mAccountId);

        FrozenPosition fp = calculateFrozenFund(data.getSledContractId(), globalData, data.getCalculatePrice());
        HostingSledContractPosition hostingSledContractPosition = set2HostingPosition(data.getSledContractId(), globalData, fp);
        globalData.getHostingPositionMap().put(data.getSledContractId(), hostingSledContractPosition);

        sendPositionMsg(hostingSledContractPosition);
    }

    private HostingSledContractPosition set2HostingPosition(long sledContractId, AssetSubAccountGlobalData globalData, FrozenPosition fp) throws TException {
        HostingSledContractPosition hscp = globalData.getHostingSledContractPosition(sledContractId);
        hscp.getPositionFund().setFrozenCommission(fp.getFrozenCommission().doubleValue());

        BigDecimal totalFrozenMargin;
        BigDecimal sellFrozenMargin = fp.getShortFrozenMargin();
        BigDecimal buyFrozenMargin = fp.getLongFrozenMargin();
        if (hscp.getPositionVolume().getNetPosition() >= 0) {
            sellFrozenMargin = fp.getShortFrozenMargin().subtract(doubleToBigDecimal(hscp.getPositionFund().getUseMargin()));
        } else {
            buyFrozenMargin = fp.getLongFrozenMargin().subtract(doubleToBigDecimal(hscp.getPositionFund().getUseMargin()));
        }

        if (buyFrozenMargin.compareTo(sellFrozenMargin) >= 0) {
            totalFrozenMargin = buyFrozenMargin;
        } else {
            totalFrozenMargin = sellFrozenMargin;
        }
        hscp.getPositionFund().setFrozenMargin(totalFrozenMargin.doubleValue());
        return hscp;
    }

    /* 按冻结保证金计算规则计算 */

    private FrozenPosition calculateFrozenFund(long sledContractId, AssetSubAccountGlobalData globalData, BigDecimal calculatePrice) throws TException {

        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(this.mAccountId);
        AssetCalculateConfig calculateConfig = globalData.getAssetCalculateConfig(sledContractId);

        Map<Long, Map<Long, ExecOrderOutSide>> execOrderOutSideMap = globalData.getExecOrderOutSideMap();
        List<ExecOrderOutSide> orderOutSides = new ArrayList<>();
        for (Map<Long, ExecOrderOutSide> execOrderOutSide : execOrderOutSideMap.values()) {
            ExecOrderOutSide outSide = execOrderOutSide.get(sledContractId);
            if (outSide != null) {
                orderOutSides.add(outSide);
            }
        }

        Collections.sort(orderOutSides, new OutSideOrderComparator());

        long netPositionLeft = getNetPositionLeft(sledContractId, globalData);
        BigDecimal longFrozenMargin = BigDecimal.ZERO;
        BigDecimal shortFrozenMargin = BigDecimal.ZERO;
        long longPosition = 0;
        long shortPosition = 0;
        BigDecimal frozenCommission = BigDecimal.ZERO;
        for (ExecOrderOutSide outSide : orderOutSides) {
            if (outSide.getVolume() <= 0) {
                continue;
            }

            BigDecimal price = getPrice(sledContractId, calculatePrice, contractGlobal, outSide);
            HostingExecTradeDirection td = outSide.getTradeDirection();
            if (HostingExecTradeDirection.TRADE_BUY.equals(td)) {
                longFrozenMargin = longFrozenMargin.add(calculateMargin(calculateConfig, outSide.getVolume(), price));
                longPosition += outSide.getVolume();
            } else {
                shortFrozenMargin = shortFrozenMargin.add(calculateMargin(calculateConfig, outSide.getVolume(), price));
                shortPosition += outSide.getVolume();
            }

            if (netPositionLeft == 0) {
                frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, outSide.getVolume(), price, OpenCloseTag.OPEN);
            } else {
                if (netPositionLeft > 0) {
                    if (HostingExecTradeDirection.TRADE_BUY.equals(td)) {
                        frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, outSide.getVolume(), price, OpenCloseTag.OPEN);
                    } else {
                        if (Math.abs(netPositionLeft) >= outSide.getVolume()) {
                            frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, outSide.getVolume(), price, OpenCloseTag.CLOSE);
                            netPositionLeft = netPositionLeft - outSide.getVolume();
                        } else {
                            frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, (int) Math.abs(netPositionLeft), price, OpenCloseTag.CLOSE);
                            netPositionLeft = outSide.getVolume() - Math.abs(netPositionLeft);
                            frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, (int) Math.abs(netPositionLeft), price, OpenCloseTag.OPEN);
                            netPositionLeft = 0;
                        }
                    }
                } else {
                    if (HostingExecTradeDirection.TRADE_SELL.equals(td)) {
                        frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, outSide.getVolume(), price, OpenCloseTag.OPEN);
                    } else {
                        if (Math.abs(netPositionLeft) >= outSide.getVolume()) {
                            frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, outSide.getVolume(), price, OpenCloseTag.CLOSE);
                            netPositionLeft = 0 - (Math.abs(netPositionLeft) - outSide.getVolume());
                        } else {
                            frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, (int) Math.abs(netPositionLeft), price, OpenCloseTag.CLOSE);
                            netPositionLeft = outSide.getVolume() - Math.abs(netPositionLeft);
                            frozenCommission = addFrozenCommission(calculateConfig, frozenCommission, (int) Math.abs(netPositionLeft), price, OpenCloseTag.OPEN);
                            netPositionLeft = 0;
                        }
                    }
                }
            }
        }

        FrozenPosition frozenPosition = new FrozenPosition();
        frozenPosition.setCalculatePrice(calculatePrice);
        frozenPosition.setFrozenCommission(frozenCommission);
        frozenPosition.setLongFrozenMargin(longFrozenMargin);
        frozenPosition.setLongFrozenPosition(longPosition);
        frozenPosition.setShortFrozenMargin(shortFrozenMargin);
        frozenPosition.setShortFrozenPosition(shortPosition);
        frozenPosition.setSledCommodityId(calculateConfig.getSledCommodityId());
        frozenPosition.setSledContractId(sledContractId);

        return frozenPosition;
    }

    private BigDecimal addFrozenCommission(AssetCalculateConfig calculateConfig, BigDecimal frozenCommission, int volume, BigDecimal price, OpenCloseTag open) throws ErrorInfo {
        frozenCommission = frozenCommission.add(calculateCommission(calculateConfig, price, volume, open));
        return frozenCommission;
    }

    private long getNetPositionLeft(long sledContractId, AssetSubAccountGlobalData globalData) {
        long netPositionLeft = 0;
        Map<Long, HostingSledContractPosition> hpm = globalData.getHostingPositionMap();
        HostingSledContractPosition hp = hpm.get(sledContractId);
        if (hp != null) {
            netPositionLeft = hp.getPositionVolume().getNetPosition();
        }
        return netPositionLeft;
    }

    private BigDecimal getPrice(long sledContractId, BigDecimal calculatePrice, ContractGlobal contractGlobal, ExecOrderOutSide outSide) {
        // 如果该合约价格为0，需要使用提供的价格计算，否则直接计算
        BigDecimal price = doubleToBigDecimal(outSide.getPrice());
        if (Double.compare(0.0, outSide.getPrice()) == 0) {
            if (calculatePrice == null) {
                QuotationItem item = contractGlobal.getLatestQuotation(sledContractId);
                if (item != null) {
                    price = doubleToBigDecimal(item.getLastPrice());
                }
            } else {
                price = calculatePrice;
            }
        }
        return price;
    }

    private static class OutSideOrderComparator implements Comparator<ExecOrderOutSide> {

        @Override
        public int compare(ExecOrderOutSide outSide1, ExecOrderOutSide outSide2) {
            if (outSide1.getCreateTimestampMs() > outSide2.getCreateTimestampMs()) {
                return 1;
            } else if (outSide1.getCreateTimestampMs() < outSide2.getCreateTimestampMs()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
