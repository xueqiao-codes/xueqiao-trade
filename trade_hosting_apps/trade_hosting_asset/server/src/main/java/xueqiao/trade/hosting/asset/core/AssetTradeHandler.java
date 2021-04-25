package xueqiao.trade.hosting.asset.core;

import com.antiy.error_code.ErrorCodeOuter;
import com.longsheng.xueqiao.contract.standard.thriftapi.*;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import net.qihoo.qconf.QconfException;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.*;
import xueqiao.trade.hosting.asset.event.handler.SledCurrency;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.asset.thriftapi.CalculateMode;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.fee.helper.PositionFeeStubFactory;
import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.MarginInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.PositionFee;
import xueqiao.trade.hosting.position.fee.thriftapi.client.TradeHostingPositionFeeStub;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssetTradeHandler {

    public static List<AssetTradeDetail> map2AssetTradeDetail(HostingExecTrade hostingExecTrade, String source, String sledOrderId) throws ErrorInfo {
        AppLog.d("HostingExecTrade: " + hostingExecTrade);
        List<AssetTradeDetail> list = new ArrayList<>();
        List<HostingExecTradeLeg> tradeLegs = structTradeLegs(hostingExecTrade);

        String sledOrderIdFixed = sledOrderId.replace("XQ|", "");

        for (int index = 0; index < tradeLegs.size(); index++) {

            HostingExecTradeLeg tradeLeg = tradeLegs.get(index);
            long sledContractId = tradeLeg.getLegContractSummary().getLegSledContractId();

            double tradePrice = tradeLeg.getTradeLegInfo().getLegTradePrice();
            HostingExecTradeDirection tradeDirection = tradeLeg.getTradeLegInfo().getLegUpsideTradeDirection();

            AssetTradeDetail assetTradeDetail = new AssetTradeDetail();
            assetTradeDetail.setExecTradeId(tradeLeg.getExecTradeLegId());
            assetTradeDetail.setSubAccountId(tradeLeg.getSubAccountId());
            assetTradeDetail.setSledContractId(sledContractId);
            assetTradeDetail.setExecOrderId(tradeLeg.getExecOrderId());
            assetTradeDetail.setTradePrice(tradePrice);
            assetTradeDetail.setExecTradeDirection(tradeDirection);
            assetTradeDetail.setTradeVolume(tradeLeg.getTradeLegInfo().getLegTradeVolume());
            assetTradeDetail.setSource(source);
            assetTradeDetail.setSubUserId(hostingExecTrade.getSubUserId());
            assetTradeDetail.setSledOrderId(sledOrderIdFixed);

            assetTradeDetail.setTradeTimestampMs(hostingExecTrade.getCreateTimestampMs());
            assetTradeDetail.setTradeAccountId(hostingExecTrade.getAccountSummary().getTradeAccountId());

            long sledCommodityId = tradeLeg.getLegContractSummary().getLegSledCommodityId();
            assetTradeDetail.setSledCommodityId(sledCommodityId);

            list.add(assetTradeDetail);
        }
        AppLog.d("AssetTradeDetail list: " + list);
        return list;
    }

    public static List<AssetTradeDetail> map2AssetTradeDetail(List<PositionAssigned> positonAssigneds) {

        AppLog.d("PositionAssigned: " + positonAssigneds);
        List<AssetTradeDetail> list = new ArrayList<>();

        for (PositionAssigned positionAssigned : positonAssigneds) {
            HostingExecTradeDirection tradeDirection = map2TradeDirection(positionAssigned.getPositionDirection());

            AssetTradeDetail assetTradeDetail = new AssetTradeDetail();
            assetTradeDetail.setSubAccountId(positionAssigned.getSubAccountId());
            assetTradeDetail.setSledContractId(positionAssigned.getSledContractId());
            assetTradeDetail.setTradePrice(positionAssigned.getPrice());
            assetTradeDetail.setExecTradeDirection(tradeDirection);
            assetTradeDetail.setTradeVolume(positionAssigned.getVolume());
            assetTradeDetail.setSource(TradeDetailSource.ASSIGN.name());
            assetTradeDetail.setExecTradeId(positionAssigned.getAssignId());
            assetTradeDetail.setSubUserId((int) positionAssigned.getAssignSubUserId());

            assetTradeDetail.setTradeTimestampMs(positionAssigned.getPositionTimestampMs());
            assetTradeDetail.setTradeAccountId(positionAssigned.getTradeAccountId());
            assetTradeDetail.setSledCommodityId(positionAssigned.getSledCommodityId());

            list.add(assetTradeDetail);
        }
        AppLog.d("AssetTradeDetail list: " + list);
        return list;
    }

    private static HostingExecTradeDirection map2TradeDirection(PositionDirection positionDirection) {
        if (PositionDirection.POSITION_BUY.equals(positionDirection)) {
            return HostingExecTradeDirection.TRADE_BUY;
        }
        if (PositionDirection.POSITION_SELL.equals(positionDirection)) {
            return HostingExecTradeDirection.TRADE_SELL;
        }
        throw new NullPointerException();
    }

    private static List<HostingExecTradeLeg> structTradeLegs(HostingExecTrade newTrade) throws ErrorInfo {
        List<HostingExecTradeLeg> tradeLegs;
        if (newTrade.getRelatedTradeLegIdsSize() == 1) {
            // 单腿成交，构造订单腿信息即可
            tradeLegs = new ArrayList<HostingExecTradeLeg>();
            HostingExecTradeLeg newTradeLeg = new HostingExecTradeLeg();
            newTradeLeg.setExecOrderId(newTrade.getExecOrderId());
            newTradeLeg.setExecTradeLegId(newTrade.getRelatedTradeLegIds().get(0));
            newTradeLeg.setRelatedExecTradeId(newTrade.getExecTradeId());
            newTradeLeg.setLegIndex((short) 0);
            newTradeLeg.setAccountSummary(newTrade.getAccountSummary());

            HostingExecOrderLegContractSummary legContractSummary = new HostingExecOrderLegContractSummary();
            legContractSummary.setLegSledExchangeMic(newTrade.getContractSummary().getSledExchangeMic());
            legContractSummary.setLegSledCommodityCode(newTrade.getContractSummary().getSledCommodityCode());
            legContractSummary.setLegSledCommodityId(newTrade.getContractSummary().getSledCommodityId());
            legContractSummary.setLegSledCommodityType(newTrade.getContractSummary().getSledCommodityType());
            legContractSummary.setLegSledContractCode(newTrade.getContractSummary().getSledContractCode());
            legContractSummary.setLegSledContractId(newTrade.getContractSummary().getSledContractId());
            newTradeLeg.setLegContractSummary(legContractSummary);

            HostingExecTradeLegInfo tradeLegInfo = new HostingExecTradeLegInfo();
            tradeLegInfo.setLegTradeVolume(newTrade.getTradeVolume());
            tradeLegInfo.setLegTradePrice(newTrade.getTradePrice());
            if (newTrade.getOrderTradeDirection() == HostingExecOrderTradeDirection.ORDER_BUY) {
                tradeLegInfo.setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_BUY);
            } else {
                tradeLegInfo.setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_SELL);
            }
            newTradeLeg.setTradeLegInfo(tradeLegInfo);
            newTradeLeg.setSubAccountId(newTrade.getSubAccountId());
            newTradeLeg.setSubUserId(newTrade.getSubUserId());
            tradeLegs.add(newTradeLeg);
        } else {
            IHostingDealingApi dealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
            tradeLegs = dealingApi.getTradeRelatedLegs(newTrade.getExecTradeId());
        }

        return tradeLegs;
    }


    public static SledCommodity getSledCommodity(long sledCommodityId) throws ErrorInfo {
        IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        ReqSledCommodityOption option = new ReqSledCommodityOption();
        option.addToSledCommodityIdList((int) sledCommodityId);
        SledCommodityPage page;
        try {
            page = api.getContractOnlineStub().reqSledCommodity(option, 0, 1);
        } catch (TException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue(), "Storage api request fail.");
        }
        if (page.getPageSize() == 0) {
            AppLog.e("sledCommodityId: " + sledCommodityId);
            throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_TRADE_DETAIL_COMMODITY_NOT_FOUND.getValue(), "trade detail commodity not found.");
        }
        return page.getPage().get(0);
    }

    public static long getSledCommodityId(long sledContractId) throws ErrorInfo {
        IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        SledContractPage page;
        ReqSledContractOption option = new ReqSledContractOption();
        option.addToSledContractIdList((int) sledContractId);
        try {
            page = api.getContractOnlineStub().reqSledContract(option, 0, 1);
        } catch (TException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue(), "Storage api request fail.");
        }
        if (page.getPageSize() == 0) {
            AppLog.i("option: " + option);
            throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_TRADE_DETAIL_CONTRACT_NOT_FOUND.getValue(), "trade detail contract not found.");
        }
        return page.getPage().get(0).getSledCommodityId();
    }

    public static SledCommodityConfig getSledCommodityConfig(SledCommodity sledCommodity) throws ErrorInfo {
        List<SledCommodityConfig> configList = sledCommodity.getSledCommodityConfig();
        if (configList.size() == 0) {
            throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_TRADE_DETAIL_COMMODITY_CONFIG_NOT_FOUND.getValue(), "trade detail commodity config not found.");
        }

        long now = System.currentTimeMillis() / 1000;
        SledCommodityConfig sledCommodityConfig = null;

        for (SledCommodityConfig config : configList) {
            if (now >= config.getActiveStartTimestamp() && now <= config.getActiveEndTimestamp()) {
                sledCommodityConfig = config;
                break;
            }
        }
        if (sledCommodityConfig == null) {
            throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_TRADE_DETAIL_COMMODITY_CONFIG_NOT_FOUND.getValue(), "trade detail commodity config not found.");
        }
        return sledCommodityConfig;
    }

}
