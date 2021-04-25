package xueqiao.trade.hosting.asset.event.handler;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.thriftapi.*;


public class MainTest {

    public static void main(String[] args) {
    }

    private static void settest() {
        long sledCommodityId = 100;
        long sledContractId = 1001;
        long subAccountId = 1;
        long orderId = 2001;
        long tradeId = 3001;
        String currency = "CNY";
        AssetCalculateConfig calculateConfig = getAssetCalculateConfig(sledCommodityId);

        // 买入成交
        AssetTradeDetail assetTradeDetail = new AssetTradeDetail();
        assetTradeDetail.setExecOrderId(orderId);
        assetTradeDetail.setSubAccountId(subAccountId);
        assetTradeDetail.setSledCommodityId(sledCommodityId);
        assetTradeDetail.setExecTradeId(tradeId);
        assetTradeDetail.setSledContractId(sledContractId);

        assetTradeDetail.setTradePrice(5.0);
        assetTradeDetail.setTradeVolume(10);
        assetTradeDetail.setExecTradeDirection(HostingExecTradeDirection.TRADE_BUY);

        assetTradeDetail.setConfig(calculateConfig);

        calculate(assetTradeDetail);

        // 卖出成交
        AssetTradeDetail assetTradeDetail1 = new AssetTradeDetail();
        assetTradeDetail1.setExecOrderId(orderId);
        assetTradeDetail1.setSubAccountId(subAccountId);
        assetTradeDetail1.setSledCommodityId(sledCommodityId);
        assetTradeDetail1.setExecTradeId(tradeId);
        assetTradeDetail1.setSledContractId(sledContractId);

        assetTradeDetail1.setExecOrderId(orderId + 1);
        assetTradeDetail1.setExecTradeId(tradeId + 1);

        assetTradeDetail1.setTradePrice(6.0);
        assetTradeDetail1.setTradeVolume(5);
        assetTradeDetail1.setExecTradeDirection(HostingExecTradeDirection.TRADE_SELL);
        assetTradeDetail1.setConfig(calculateConfig);

        calculate(assetTradeDetail1);


        // 买入成交合约2
        AssetTradeDetail assetTradeDetail2 = new AssetTradeDetail();
        assetTradeDetail2.setExecOrderId(orderId + 2);
        assetTradeDetail2.setSubAccountId(subAccountId);
        assetTradeDetail2.setSledCommodityId(sledCommodityId + 2);
        assetTradeDetail2.setExecTradeId(tradeId + 2);
        assetTradeDetail2.setSledContractId(sledContractId + 2);

        assetTradeDetail2.setTradePrice(6.0);
        assetTradeDetail2.setTradeVolume(90);
        assetTradeDetail2.setExecTradeDirection(HostingExecTradeDirection.TRADE_BUY);

        assetTradeDetail2.setConfig(getAssetCalculateConfig2(sledCommodityId + 2));

        calculate(assetTradeDetail2);

        try {
            printData(assetTradeDetail);
        } catch (ErrorInfo errorInfo) {
            errorInfo.printStackTrace();
        }
    }

    private static void printData(AssetTradeDetail assetTradeDetail) throws ErrorInfo {
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(assetTradeDetail.getSubAccountId());
        for (HostingSledContractPosition position : globalData.getHostingPositionMap().values()) {
            print(position);
        }
        for (HostingFund fund : globalData.getHostingFundMap().values()) {
            print(fund);
        }
    }

    private static void print(Object obj) {
        System.out.println(obj);
    }

    private static void calculate(AssetTradeDetail assetTradeDetail) {

    }

    private static AssetCalculateConfig getAssetCalculateConfig(long sledCommodityId) {
        AssetCalculateConfig calculateConfig = new AssetCalculateConfig();
        calculateConfig.setSledCommodityId(sledCommodityId);
        calculateConfig.setCurrency("CNY");
        calculateConfig.setContractSize(100);
        calculateConfig.setChargeUnit(1.0);
        calculateConfig.setOpenCloseFee(0.05);
        calculateConfig.setInitialMargin(0.3);
        calculateConfig.setSellInitialMargin(0.2);
        calculateConfig.setCommissionCalculateMode(CalculateMode.PERCENTAGE);
        calculateConfig.setMarginCalculateMode(CalculateMode.PERCENTAGE);
        return calculateConfig;
    }

    private static AssetCalculateConfig getAssetCalculateConfig2(long sledCommodityId) {
        AssetCalculateConfig calculateConfig = new AssetCalculateConfig();
        calculateConfig.setSledCommodityId(sledCommodityId);
        calculateConfig.setCurrency("USD");
        calculateConfig.setContractSize(200);
        calculateConfig.setChargeUnit(0.1);
        calculateConfig.setOpenCloseFee(0.5);
        calculateConfig.setInitialMargin(330);
        calculateConfig.setSellInitialMargin(220);
        calculateConfig.setCommissionCalculateMode(CalculateMode.QUOTA);
        calculateConfig.setMarginCalculateMode(CalculateMode.QUOTA);
        return calculateConfig;
    }


}
