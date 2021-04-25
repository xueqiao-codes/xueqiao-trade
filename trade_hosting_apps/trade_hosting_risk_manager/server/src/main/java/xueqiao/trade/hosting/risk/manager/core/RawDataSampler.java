package xueqiao.trade.hosting.risk.manager.core;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingFundPage;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPositionPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingSledContractPositionOption;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;
import xueqiao.trade.hosting.asset.thriftapi.helper.AssetStubFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 原始数据采样器
 */
public class RawDataSampler implements Runnable {

    private RawDataPool mDataPool;

    public RawDataSampler(RawDataPool dataPool) {
        this.mDataPool = dataPool;
    }

    private void onSample() throws Exception {
        long startTimestampMs = System.currentTimeMillis();

        TradeHostingAssetStub assetStub = AssetStubFactory.getStub();

        Set<Long> subAccountIds = new HashSet<>();
        subAccountIds.add(mDataPool.getContext().getSubAccountId());

        HostingFundPage fundPage = assetStub.getHostingSubAccountFund(
                new ReqHostingFundOption().setSubAccountIds(subAccountIds).setBaseCurrency(true));

        HostingFund baseCurrencyFund = null;
        if (fundPage != null && fundPage.getPageSize() > 0) {
            baseCurrencyFund = fundPage.getPage().get(0);
        }

        HostingSledContractPositionPage contractPositionPage
                = assetStub.getHostingSledContractPosition(new ReqHostingSledContractPositionOption()
                        .setSubAccountId(mDataPool.getContext().getSubAccountId())
                , new IndexedPageOption().setPageIndex(0).setPageSize(Integer.MAX_VALUE));
        Map<Long, Map<Long, HostingSledContractPosition>> commodityPositions = new HashMap<>();
        if (contractPositionPage.getPage() != null) {
            for (HostingSledContractPosition contractPosition : contractPositionPage.getPage()) {
                Map<Long, HostingSledContractPosition> contractPositions
                        = commodityPositions.get(contractPosition.getSledCommodityId());
                if (contractPositions == null) {
                    contractPositions = new HashMap<>();
                    commodityPositions.put(contractPosition.getSledCommodityId(), contractPositions);
                }

                contractPositions.put(contractPosition.getSledContractId(), contractPosition);
            }
        }

        if (AppLog.debugEnabled()) {
            AppLog.d("Sample Once Data subAccountId=" + mDataPool.getContext().getSubAccountId() +
                    ", timeEscaped=" + (System.currentTimeMillis() - startTimestampMs) + "ms");
        }

        mDataPool.update(baseCurrencyFund, commodityPositions);
    }

    @Override
    public void run() {
        try {
            onSample();

            for (IRawDataSampleListener listener : mDataPool.getListeners()) {
                try {
                    listener.onSampleProcess(mDataPool);
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                }
            }

        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        } finally {

            for (IRawDataSampleListener listener : mDataPool.getListeners()) {
                try {
                    listener.onSampleFinished(mDataPool);
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                }
            }
        }

    }
}
