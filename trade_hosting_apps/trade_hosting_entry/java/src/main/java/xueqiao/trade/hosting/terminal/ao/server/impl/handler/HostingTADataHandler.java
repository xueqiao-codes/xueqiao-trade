package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;
import com.longsheng.xueqiao.broker.thriftapi.BrokerPlatform;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.entry.core.CNYRateManager;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.terminal.ao.HostingTAFundCurrencyGroup;
import xueqiao.trade.hosting.terminal.ao.HostingTAFundHisItem;
import xueqiao.trade.hosting.terminal.ao.HostingTAFundItem;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFundHisItem;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;
import xueqiao.trade.hosting.tradeaccount.data.helper.TradeAccountDataStubFactory;

import java.sql.Date;
import java.util.*;

public class HostingTADataHandler extends HandlerBase {
    public HostingTADataHandler(TServiceCntl serviceCntl
            , LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    private HostingTAFundItem convert(List<TradeAccountFund> funds) throws ErrorInfo {
        if (funds == null || funds.isEmpty()) {
            return null;
        }

        HostingTAFundItem fundItem = new HostingTAFundItem();
        fundItem.setTradeAccountId(funds.get(0).getTradeAccountId());
        fundItem.setUpdateTimestampMs(funds.get(0).getUpdateTimestampMs());

        Map<String, HostingTAFundCurrencyGroup> groupFunds = new HashMap<>();
        for (TradeAccountFund fund : funds) {
            HostingTAFundCurrencyGroup groupFund = groupFunds.get(fund.getCurrencyNo().toUpperCase());
            if (groupFund == null) {
                groupFund = new HostingTAFundCurrencyGroup();
                groupFund.setCurrencyNo(fund.getCurrencyNo());
                groupFund.setGroupTotalFund(new TradeAccountFund(fund));
                groupFund.getGroupTotalFund().setCurrencyChannel("");

                groupFunds.put(fund.getCurrencyNo().toUpperCase(), groupFund);
            } else {
                TradeAccountFund groupTotalFund = groupFund.getGroupTotalFund();
                groupTotalFund.setCredit(groupTotalFund.getCredit() + fund.getCredit())
                        .setPreBalance(groupTotalFund.getPreBalance() + fund.getPreBalance())
                        .setDeposit(groupTotalFund.getDeposit() + fund.getDeposit())
                        .setWithdraw(groupTotalFund.getWithdraw() + fund.getWithdraw())
                        .setFrozenMargin(groupTotalFund.getFrozenMargin() + fund.getFrozenMargin())
                        .setFrozenCash(groupTotalFund.getFrozenCash() + fund.getFrozenCash())
                        .setCurrMargin(groupTotalFund.getCurrMargin() + fund.getCurrMargin())
                        .setCommission(groupTotalFund.getCommission() + fund.getCommission())
                        .setCloseProfit(groupTotalFund.getCloseProfit() + fund.getCloseProfit())
                        .setPositionProfit(groupTotalFund.getPositionProfit() + fund.getPositionProfit())
                        .setAvailable(groupTotalFund.getAvailable() + fund.getAvailable())
                        .setDynamicBenefit(groupTotalFund.getDynamicBenefit() + fund.getDynamicBenefit());
                if (0 == Double.compare(0.0, groupTotalFund.getDynamicBenefit())) {
                    groupTotalFund.unsetRiskRate();
                } else {
                    groupTotalFund.setRiskRate((100 * groupTotalFund.getCurrMargin()) / groupTotalFund.getDynamicBenefit());
                }
            }

            groupFund.addToItemFunds(fund);
        }
        fundItem.setGroupFunds(groupFunds);

        TradeAccountFund totalFund = new TradeAccountFund();
        totalFund.setCurrencyNo("CNY")
                .setCurrencyChannel("")
                .setUpdateTimestampMs(fundItem.getUpdateTimestampMs())
                .setCredit(0.0).setPreBalance(0.0).setWithdraw(0.0)
                .setFrozenMargin(0.0).setFrozenCash(0.0).setCurrMargin(0.0)
                .setCommission(0.0).setCloseProfit(0.0).setPositionProfit(0.0)
                .setAvailable(0.0).setDynamicBenefit(0.0);

        for (HostingTAFundCurrencyGroup groupFund : groupFunds.values()) {
            Double rate = 1.0;
            if (!"CNY".equalsIgnoreCase(groupFund.getCurrencyNo())) {
                rate = CNYRateManager.Global().getRate(groupFund.getCurrencyNo());
                if (rate == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                            , "System has not found CNY rate for " + groupFund.getCurrencyNo());
                }
            }
            totalFund.setCredit(totalFund.getCredit() + groupFund.getGroupTotalFund().getCredit() * rate)
                    .setPreBalance(totalFund.getPreBalance() + groupFund.getGroupTotalFund().getPreBalance() * rate)
                    .setDeposit(totalFund.getDeposit() + groupFund.getGroupTotalFund().getDeposit() * rate)
                    .setWithdraw(totalFund.getWithdraw() + groupFund.getGroupTotalFund().getWithdraw() * rate)
                    .setFrozenMargin(totalFund.getFrozenMargin() + groupFund.getGroupTotalFund().getFrozenMargin() * rate)
                    .setFrozenCash(totalFund.getFrozenCash() + groupFund.getGroupTotalFund().getFrozenCash() * rate)
                    .setCurrMargin(totalFund.getCurrMargin() + groupFund.getGroupTotalFund().getCurrMargin() * rate)
                    .setCommission(totalFund.getCommission() + groupFund.getGroupTotalFund().getCommission() * rate)
                    .setCloseProfit(totalFund.getCloseProfit() + groupFund.getGroupTotalFund().getCloseProfit() * rate)
                    .setPositionProfit(totalFund.getPositionProfit() + groupFund.getGroupTotalFund().getPositionProfit() * rate)
                    .setAvailable(totalFund.getAvailable() + groupFund.getGroupTotalFund().getAvailable() * rate)
                    .setDynamicBenefit(totalFund.getDynamicBenefit() + groupFund.getGroupTotalFund().getDynamicBenefit() * rate);
        }

        if (0 == Double.compare(0.0, totalFund.getDynamicBenefit())) {
            totalFund.unsetRiskRate();
        } else {
            totalFund.setRiskRate((100 * totalFund.getCurrMargin()) / totalFund.getDynamicBenefit());
        }
        fundItem.setTotalFund(totalFund);

        return fundItem;
    }

    public List<HostingTAFundItem> getFundNow(long tradeAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
        permit(EHostingUserRole.AdminGroup);

        List<HostingTAFundItem> resultList = new ArrayList<>();
        HostingTAFundItem fundItem = convert(
                TradeAccountDataStubFactory.getStub().getNowFund(tradeAccountId));
        if (fundItem != null) {
            resultList.add(fundItem);
        }
        return resultList;
    }

    public List<HostingTAFundHisItem> getFundHis(long tradeAccountId
            , String fundDateBegin, String fundDateEnd) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
        ParameterChecker.check(StringUtils.isNotBlank(fundDateBegin) && fundDateBegin.length() == 10
                , " fundDateBegin format error! should be YYYY-MM-DD");
        ParameterChecker.check(StringUtils.isNotBlank(fundDateEnd) && fundDateEnd.length() == 10
                , "fundDateEnd format error! should be YYYY-MM-DD");
        permit(EHostingUserRole.AdminGroup);

        // 接口会检测日期的格式和限制
        List<TradeAccountFundHisItem> hisItemList
                = TradeAccountDataStubFactory.getStub().getHisFunds(tradeAccountId, fundDateBegin, fundDateEnd);
        List<HostingTAFundHisItem> resultList = new ArrayList<>(hisItemList.size() + 1);
        for (TradeAccountFundHisItem hisItem : hisItemList) {
            HostingTAFundHisItem resultItem = new HostingTAFundHisItem();
            resultItem.setDate(hisItem.getDate());
            resultItem.setItem(convert(hisItem.getFunds()));
            resultList.add(resultItem);
        }
        return resultList;
    }

    public List<TradeAccountSettlementInfo> getSettlementInfos(long tradeAccountId
            , String settlementDateBegin, String settlementDateEnd) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
        ParameterChecker.check(StringUtils.isNotBlank(settlementDateBegin) && settlementDateBegin.length() == 10
                , "settlementDateBegin format error! should be YYYY-MM-DD");
        ParameterChecker.check(StringUtils.isNotBlank(settlementDateEnd) && settlementDateEnd.length() == 10
                , "settlementDateEnd format error! should be YYYY-MM-DD");
        permit(EHostingUserRole.AdminGroup);

        return TradeAccountDataStubFactory.getStub().getSettlementInfos(tradeAccountId
                , settlementDateBegin, settlementDateEnd);
    }
}
