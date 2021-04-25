package xueqiao.trade.hosting.quot.comb.thriftapi.server.impl;


import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.quot.comb.core.QuotCombSyncTopicsHandler;
import xueqiao.trade.hosting.quot.comb.thriftapi.server.TradeHostingQuotCombAdaptor;
import xueqiao.trade.hosting.quot.comb.thriftapi.SyncCombTopicsRequest;

public class TradeHostingQuotCombHandler extends TradeHostingQuotCombAdaptor {

    private QuotCombSyncTopicsHandler mHandler = new QuotCombSyncTopicsHandler();

    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void syncCombTopics(TServiceCntl oCntl, SyncCombTopicsRequest syncRequest)
            throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(syncRequest, "syncRequest should not be null");
        ParameterChecker.check(StringUtils.isNotEmpty(syncRequest.getConsumerKey())
                , "syncRequest's consumerKey should not be empty");

        mHandler.syncCombTopics(syncRequest);
    }


}
