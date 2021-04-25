package xueqiao.trade.hosting.position.fee.core.api;

import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HostingTradeAccountApi {

    private static IHostingTradeAccountApi tradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);

    /**
     * 查询所有有效的资金账号
     */
    public static List<HostingTradeAccount> queryValidTradeAccounts() throws ErrorInfo {
        QueryTradeAccountOption queryOption = new QueryTradeAccountOption();
        Set<TradeAccountState> inStates = new HashSet<>();
        inStates.add(TradeAccountState.ACCOUNT_NORMAL);
        queryOption.setInStates(inStates);

        PageOption pageOption = new PageOption();
        pageOption.setPageIndex(0).setPageSize(Integer.MAX_VALUE);

        PageResult<HostingTradeAccount> pageResult = tradeAccountApi.queryTradeAccountPage(queryOption, pageOption);
        if (pageResult != null && pageResult.getPageList().size() > 0) {
            return pageResult.getPageList();
        }
        return null;
    }

    public static HostingTradeAccount queryTradeAccount(long tradeAccountId) throws ErrorInfo {
        return tradeAccountApi.getTradeAccount(tradeAccountId);
    }
}
