package xueqiao.trade.hosting.tradeaccount.data.server.impl;


import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFundHisItem;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountNetPositionSummary;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;
import xueqiao.trade.hosting.tradeaccount.data.core.TADataSynchonizerManager;
import xueqiao.trade.hosting.tradeaccount.data.server.TradeHostingTradeAccountDataAdaptor;
import xueqiao.trade.hosting.tradeaccount.data.storage.TADataDB;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.FundHisTable;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.FundNowTable;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.NetPositionSummaryTable;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.SettlementInfoTable;

import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class TradeHostingTradeAccountDataHandler extends TradeHostingTradeAccountDataAdaptor {
    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected void clearAll(TServiceCntl oCntl) throws ErrorInfo, TException {
        new DBTransactionHelper<Void>(TADataDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new FundHisTable(getConnection()).deleteAll();
                new FundNowTable(getConnection()).deleteAll();
                new NetPositionSummaryTable(getConnection()).deleteAll();
                new SettlementInfoTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

        TADataSynchonizerManager.Global().clearAll();
    }

    @Override
    protected List<TradeAccountFund> getNowFund(TServiceCntl oCntl, long tradeAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");

        return new DBQueryHelper<List<TradeAccountFund>>(TADataDB.Global()) {

            @Override
            protected List<TradeAccountFund> onQuery(Connection conn) throws Exception {
                return new FundNowTable(conn).getFunds(tradeAccountId);
            }
        }.query();
    }

    private static Date[] checkDatePeriod(String dateBeginStr, String dateEndStr, int maxPeriodDays) throws ErrorInfo {
        if (dateBeginStr == null || dateBeginStr.length() != 10) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "dateBegin format error");
        }
        if (dateEndStr == null || dateEndStr.length() != 10) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "dateEnd format error");
        }

        Date dateBegin;
        Date dateEnd;
        try {
            dateBegin = Date.valueOf(dateBeginStr);
            dateEnd = Date.valueOf(dateEndStr);
        } catch(IllegalArgumentException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "date has format error");
        }

        if (dateEnd.before(dateBegin)) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "dateEnd is before dateBegin");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBegin);
        calendar.add(Calendar.DATE, maxPeriodDays);

        if (dateEnd.after(calendar.getTime())) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "datePeriod is too large, maxPeriodDays is " + maxPeriodDays);
        }

        Date[] result = new Date[2];
        result[0] = dateBegin;
        result[1] = dateEnd;

        return result;

    }

    @Override
    protected List<TradeAccountFundHisItem> getHisFunds(TServiceCntl oCntl
            , long tradeAccountId
            , String fundDateBegin
            , String fundDateEnd) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
        Date[] fundDates = checkDatePeriod(fundDateBegin, fundDateEnd, 366);

        return new DBQueryHelper<List<TradeAccountFundHisItem>>(TADataDB.Global()) {
            @Override
            protected List<TradeAccountFundHisItem> onQuery(Connection conn) throws Exception {
                return new FundHisTable(conn).getFundHisItems(tradeAccountId, fundDates[0], fundDates[1]);
            }
        }.query();
    }

    @Override
    protected List<TradeAccountSettlementInfo> getSettlementInfos(TServiceCntl oCntl
            , long tradeAccountId
            , String settlementDateBegin
            , String settlementDateEnd) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
        Date[] settlementDates = checkDatePeriod(settlementDateBegin, settlementDateEnd, 31);
        return new DBQueryHelper<List<TradeAccountSettlementInfo>>(TADataDB.Global()) {

            @Override
            protected List<TradeAccountSettlementInfo> onQuery(Connection conn) throws Exception {
                return new SettlementInfoTable(conn).getSettlementInfos(tradeAccountId, settlementDates[0], settlementDates[1]);
            }

        }.query();
    }

    @Override
    protected List<TradeAccountNetPositionSummary> getNetPositionSummaries(TServiceCntl oCntl
            , long tradeAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");

        return new DBQueryHelper<List<TradeAccountNetPositionSummary>>(TADataDB.Global()) {
            @Override
            protected List<TradeAccountNetPositionSummary> onQuery(Connection conn) throws Exception {
                return new NetPositionSummaryTable(conn).getNetPositionSummaries(tradeAccountId);
            }
        }.query();
    }

    @Override
    public void destroy() {
    }

}
