package xueqiao.trade.hosting.asset.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.asset.struct.PositionFundCalculateData;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;

import java.math.BigDecimal;
import java.util.*;

public class PositionFundJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            if (dataMap == null) {
                AppLog.e("StartJob execute, but dataMap is null");
                return;
            }
            long subAccountId;
            try {
                subAccountId = dataMap.getLong("subAccountId");
            } catch (Exception e) {
                AppLog.e(e.getMessage(), e);
                return;
            }

            AppLog.d("PositionFundJob for subAccountId=" + subAccountId);

            AssetBaseCalculator positionFundCalculator;
//            AssetBaseCalculator frozenFundCalculator;
            AssetBaseCalculator fundCalculator;
            try {
                positionFundCalculator = AssetCalculatorFactory.getInstance().getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_POSITION_FUND_KEY, subAccountId);
//                frozenFundCalculator = AssetCalculatorFactory.getInstance().getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_FROZEN_FUND_KEY, subAccountId);
                fundCalculator = AssetCalculatorFactory.getInstance().getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_FUND_KEY, subAccountId);

            } catch (ErrorInfo errorInfo) {
                AppLog.e(errorInfo.getMessage(), errorInfo);
                return;
            }

            AssetSubAccountGlobalData data = AssetGlobalDataFactory.getInstance().getAssetGlobalData(subAccountId);
            Map<Long, HostingSledContractPosition> map = data.getHostingPositionMap();
            if (map == null) {
                return;
            }
            Set<String> currencyList = new HashSet<>();
            for (HostingSledContractPosition position : map.values()) {
                QuotationItem quotation = AssetGlobalDataFactory.getInstance().getContractGlobalData(subAccountId).getLatestQuotation(position.getSledContractId());
                if (null == quotation) {
                    continue;
                }

                PositionFundCalculateData calculateData = new PositionFundCalculateData();
                calculateData.setSledContractId(position.getSledContractId());
                calculateData.setSledCommodityId(position.getSledCommodityId());
                calculateData.setCalculatePrice(new BigDecimal(Double.toString(quotation.getLastPrice())));
                positionFundCalculator.addTask(calculateData);
//                frozenFundCalculator.addTask(calculateData);
                currencyList.add(position.getCurrency());
            }
            for (String currency : currencyList) {
                FundCalculateData fundCalculateData = new FundCalculateData();
                fundCalculateData.setCurrency(currency);
                fundCalculateData.setSubAccountId(subAccountId);
                fundCalculator.addTask(fundCalculateData);
            }
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
        }
    }
}
