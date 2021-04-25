package xueqiao.trade.hosting.asset.core;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSubAccountFundTable;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund;
import xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundOption;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HostingSubAccountGlobal {
    private static HostingSubAccountGlobal ourInstance = new HostingSubAccountGlobal();

    public static HostingSubAccountGlobal getInstance() {
        return ourInstance;
    }

    private HostingSubAccountGlobal() {
        api = Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class);
    }

    private long lastTimestamp = 0L;
    private final static long TIME_SPAN = 60000;
    private IHostingSubAccountApi api;

    private Map<Long, HostingSubAccount> subAccountMap = new ConcurrentHashMap<>();

    private Map<String, HostingSubAccountFund> subAccountFundMap = new ConcurrentHashMap<>();

    public List<HostingSubAccount> getAllSubAccount() throws ErrorInfo {
        checkRefresh();
        List<HostingSubAccount> list = new ArrayList<>();
        list.addAll(subAccountMap.values());
        return list;
    }

    private String genSubAccountFundKey(long subAccountId, String currency) {
        StringBuilder builder = new StringBuilder();
        builder.append(subAccountId);
        builder.append("|");
        builder.append(currency);
        return builder.toString();
    }

    public HostingSubAccountFund getHostingSubAccountFund(long subAccountId, String currency) throws ErrorInfo {
        checkRefresh();
        return subAccountFundMap.get(genSubAccountFundKey(subAccountId, currency));

    }

    private void checkRefresh() throws ErrorInfo {
        long nowTimestamp = System.currentTimeMillis();
        boolean isFresh = false;
        if (nowTimestamp - lastTimestamp > TIME_SPAN) {
            isFresh = true;
            lastTimestamp = nowTimestamp;
        }

        if (isFresh) {
            subAccountMap.clear();
            subAccountFundMap.clear();
            PageOption pageOption = new PageOption();
            pageOption.setPageIndex(0);
            pageOption.setPageSize(500);
            PageResult<HostingSubAccount> subAccountPage = api.getSubAccounts(null, pageOption);
            AppLog.d("subAccountPage: " + subAccountPage.getTotalCount() + " subAccountPage: " + subAccountPage.getPageList());

            PageResult<HostingSubAccountFund> hostingSubAccountFundPage = new DBQueryHelper<PageResult<HostingSubAccountFund>>(AssetDB.Global()) {
                @Override
                protected PageResult<HostingSubAccountFund> onQuery(Connection connection) throws Exception {
                    AssetSubAccountFundTable assetSubAccountFundTable = new AssetSubAccountFundTable(connection);
                    ReqSubAccountFundOption option = new ReqSubAccountFundOption();
                    return assetSubAccountFundTable.query(option, pageOption);
                }
            }.query();


            for (HostingSubAccount subAccount : subAccountPage.getPageList()) {
                subAccountMap.put(subAccount.getSubAccountId(), subAccount);
                for (HostingSubAccountFund fund : hostingSubAccountFundPage.getPageList()) {
                    if (fund.getSubAccountId() == subAccount.getSubAccountId()) {
                        subAccountFundMap.put(genSubAccountFundKey(subAccount.getSubAccountId(), fund.getCurrency()), fund);
                    }
                }
            }

            if (AppLog.debugEnabled()) {
                AppLog.d("HostingSubAccount: " + subAccountMap);
                AppLog.d("subAccountFundMap: " + subAccountFundMap);
            }
        }
    }

    public HostingSubAccount getSubAccount(long subAccountId) throws ErrorInfo {
        checkRefresh();
        return subAccountMap.get(subAccountId);
    }
}
