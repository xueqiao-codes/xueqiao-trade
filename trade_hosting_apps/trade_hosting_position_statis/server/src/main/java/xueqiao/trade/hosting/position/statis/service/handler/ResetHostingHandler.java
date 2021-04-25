package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.core.cache.ICacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraphManager;
import xueqiao.trade.hosting.position.statis.core.cache.contract.StatContractCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCacheManager;
import xueqiao.trade.hosting.position.statis.core.quotation.StatQuotationHelper;
import xueqiao.trade.hosting.position.statis.core.thread.TaskThreadManager;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.*;

public class ResetHostingHandler {

    public void clearAll() throws ErrorInfo {
        /*
         * 清理数据库
         * */
        new DBTransactionHelper<Void>(PositionStatisDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new SourceAssignPositionTable(getConnection()).deleteAll();
                new SourceHostingXQTradeTable(getConnection()).deleteAll();
                new StatPositionSummaryTable(getConnection()).deleteAll();
                new StatPositionItemTable(getConnection()).deleteAll();
                new StatPositionUnitTable(getConnection()).deleteAll();
                new StatClosedPositionSummaryTable(getConnection()).deleteAll();
                new StatClosedPositionItemTable(getConnection()).deleteAll();
                new StatArchiveSummaryTable(getConnection()).deleteAll();
                new StatArchiveItemTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

        /*
         * 清理其他数据
         * */
        clearCacheData();
        unRegisterAllQuotations();
    }

    /**
     * 清除内存数据
     */
    private void clearCacheData() {
        StatComposeGraphManager.getInstance().clear();
        StatContractCacheManager.getInstance().clear();
        StatPositionCacheManager.getInstance().clear();
        StatQuoCombCacheManager.getInstance().clear();
        StatQuotationCacheManager.getInstance().clear();

        TaskThreadManager.getInstance().reset();
    }

    /**
     * 注册全部行情的监听
     */
    private void unRegisterAllQuotations() throws ErrorInfo {
        StatQuotationHelper.unRegisterAllQuotations();
    }
}
