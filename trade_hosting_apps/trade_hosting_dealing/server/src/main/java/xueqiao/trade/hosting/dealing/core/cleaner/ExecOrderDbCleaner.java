package xueqiao.trade.hosting.dealing.core.cleaner;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderCleanIndexTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.storage.apis.structs.OrderCleanIndexEntry;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;

public class ExecOrderDbCleaner extends Thread  {
    public static final int DB_RECORD_RESERVE_TIMEMS = 3* 24 * 60 * 60 * 1000;

    public ExecOrderDbCleaner() {
    }

    public void startWorking() {
        this.setName("db_cleaner");
        this.setPriority(MIN_PRIORITY);
        this.setDaemon(true);
        this.start();
    }

    private void cleanDBOrders() throws Exception {
        long startTimestampMs = System.currentTimeMillis();

        final int pageIndex = 0;
        final int pageSize = 50;

        while (true) {
            PageOption pageOption = new PageOption();
            pageOption.setNeedTotalCount(false);
            pageOption.setPageIndex(pageIndex);
            pageOption.setPageSize(pageSize);

            PageResult<OrderCleanIndexEntry> indexEntriesPage
                    = new DBQueryHelper<PageResult<OrderCleanIndexEntry>>(DealingDBV2.Global()) {
                @Override
                protected PageResult<OrderCleanIndexEntry> onQuery(Connection conn) throws Exception {
                    return new HostingExecOrderCleanIndexTableV2(conn).getBeforeCleanTimestampMs(startTimestampMs, pageOption);
                }
            }.query();

            if (indexEntriesPage.getPageList() != null) {
                for (OrderCleanIndexEntry indexEntry : indexEntriesPage.getPageList()) {
                    doCleanDBOrder(indexEntry.getCleanTimestampMs(), indexEntry.getExecOrderId());
                }
                if (indexEntriesPage.getPageList().size() < pageSize) {
                    break;
                }
            } else {
                break;
            }
        }

        AppLog.w("[Notice]clean dbOrders once escaped=" + (System.currentTimeMillis() - startTimestampMs) + "ms");
    }

    private void doCleanDBOrder(long cleanTimestampMs, long execOrderId) throws ErrorInfo {
        new DBTransactionHelper<Void>(DealingDBV2.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingExecOrderTableV2(getConnection()).deleteOrder(execOrderId);
                new HostingExecOrderCleanIndexTableV2(getConnection()).delIndexEntry(cleanTimestampMs, execOrderId);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(5*60*1000); // 休眠一定要在前面,留给其他模块在系统重启时有足够的恢复时间
                cleanDBOrders();
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
                try { Thread.sleep(10000);} catch (InterruptedException e1) {}
            }
        }
    }

}
