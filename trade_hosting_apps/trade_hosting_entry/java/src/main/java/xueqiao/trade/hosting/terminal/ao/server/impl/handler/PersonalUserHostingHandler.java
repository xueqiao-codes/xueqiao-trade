package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.*;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingOrderRouteTreeApi;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountPage;
import xueqiao.trade.hosting.terminal.ao.trade_hosting_terminal_aoConstants;

import java.util.*;

public class PersonalUserHostingHandler extends HandlerBase {

    private IHostingOrderRouteTreeApi mOrderRouteTreeApi;
    private IHostingTradeAccountApi mTradeAccountApi;


    public PersonalUserHostingHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);

        mOrderRouteTreeApi = Globals.getInstance().queryInterfaceForSure(IHostingOrderRouteTreeApi.class);
        mTradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
    }

    public HostingOrderRouteTree getHostingOrderRouteTree(long subAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        checkHasRelatedAccount(subAccountId);
        HostingOrderRouteTree routeTree = mOrderRouteTreeApi.getTree(subAccountId);
        if (routeTree == null) {
            routeTree = new HostingOrderRouteTree();
            routeTree.setConfigVersion(0);
        }
        return routeTree;
    }

    public List<HostingTradeAccount> getTradeAccount(long subAccountId) throws ErrorInfo, TException {
        HostingOrderRouteTree routeTree = getHostingOrderRouteTree(subAccountId);
        AppLog.d("routeTree: " + routeTree);
        Set<Long> tradeAccountIds = getTradeAccountIds(routeTree);
        AppLog.d("tradeAccountIds: " + tradeAccountIds);
        List<HostingTradeAccount> resultTradeAccounts = new ArrayList<>();
        if (tradeAccountIds.size() <= 0) {
            return resultTradeAccounts;
        }

        for (long id : tradeAccountIds) {
            QueryTradeAccountOption apiQueryOption = new QueryTradeAccountOption();
            apiQueryOption.setTradeAccountId(id);
            PageOption pageOption = new PageOption();
            pageOption.setNeedTotalCount(false);
            pageOption.setPageIndex(0);
            pageOption.setPageSize(1);
            PageResult<HostingTradeAccount> tradeAccountPage
                    = mTradeAccountApi.queryTradeAccountPage(apiQueryOption, pageOption);
            if (tradeAccountPage.getPageList() != null && tradeAccountPage.getPageList().size() > 0) {
                HostingTradeAccount tradeAccount = tradeAccountPage.getPageList().get(0);
                tradeAccount.unsetLoginPassword();
                resultTradeAccounts.add(tradeAccount);
            }
        }
        return resultTradeAccounts;
    }

    private Set<Long> getTradeAccountIds(HostingOrderRouteTree routeTree) {
        Set<Long> tradeAccountIds = new HashSet<>();
        if (routeTree.getSubExchangeNodesSize() > 0) {
            Map<String, HostingOrderRouteExchangeNode> nodes = routeTree.getSubExchangeNodes();
            for (HostingOrderRouteExchangeNode node : nodes.values()) {
                if (node.getRelatedInfo() != null) {
                    tradeAccountIds.add(node.getRelatedInfo().getMainTradeAccountId());
                }
                if (node.getSubCommodityTypeNodesSize() > 0) {
                    Map<Short, HostingOrderRouteCommodityTypeNode> subCommodityTypeNodes = node.getSubCommodityTypeNodes();
                    for (HostingOrderRouteCommodityTypeNode typeNode : subCommodityTypeNodes.values()) {
                        if (typeNode.getRelatedInfo() != null) {
                            tradeAccountIds.add(typeNode.getRelatedInfo().getMainTradeAccountId());
                        }

                        if (typeNode.getSubCommodityCodeNodesSize() > 0) {
                            Map<String, HostingOrderRouteCommodityCodeNode> codeNodes = typeNode.getSubCommodityCodeNodes();
                            for (HostingOrderRouteCommodityCodeNode codeNode : codeNodes.values()) {
                                if (codeNode.getRelatedInfo() != null) {
                                    tradeAccountIds.add(codeNode.getRelatedInfo().getMainTradeAccountId());
                                }
                            }
                        }
                    }
                }
            }
        }
        return tradeAccountIds;
    }
}
