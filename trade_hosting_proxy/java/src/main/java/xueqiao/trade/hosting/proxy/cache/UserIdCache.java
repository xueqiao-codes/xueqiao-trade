package xueqiao.trade.hosting.proxy.cache;

import com.antiy.error_code.ErrorCodeOuter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.company.CompanyUser;
import xueqiao.company.CompanyUserPage;
import xueqiao.company.QueryCompanyUserOption;
import xueqiao.company.dao.client.CompanyDaoStub;
import xueqiao.hosting.machine.HostingRelatedInfo;
import xueqiao.hosting.machine.HostingRelatedInfoPageResult;
import xueqiao.hosting.machine.QueryHostingRelatedInfoOption;
import xueqiao.hosting.machine.dao.client.HostingMachineDaoStub;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.QueryHostingUserOption;
import xueqiao.trade.hosting.QueryHostingUserPage;
import xueqiao.trade.hosting.cloud.ao.client.TradeHostingCloudAoStub;

import java.util.concurrent.ExecutionException;


public class UserIdCache {
    private static UserIdCache ourInstance = new UserIdCache();

    public static UserIdCache getInstance() {
        return ourInstance;
    }

    private UserIdCache() {
    }

    private CacheLoader<String, Integer> hostingUserIdCache = new CacheLoader<String, Integer>() {
        @Override
        public Integer load(String s) throws Exception {
            String[] idStrs = StringUtils.split(s, "|");
            if (idStrs.length > 0) {
                long machineId = Long.valueOf(idStrs[0]);
                int subUserId = Integer.valueOf(idStrs[1]);
                QueryHostingRelatedInfoOption option = new QueryHostingRelatedInfoOption();
                option.setMachineId(machineId);
                IndexedPageOption pageOption = new IndexedPageOption();
                pageOption.setPageIndex(0);
                pageOption.setPageSize(1);
                HostingRelatedInfoPageResult page = new HostingMachineDaoStub()
                        .queryRelatedInfoPage((int) machineId, 2000, option, pageOption);

                if (page.getResultListSize() > 0) {
                    HostingRelatedInfo relatedInfo = page.getResultList().get(0);
                    int companyId = relatedInfo.getCompanyId();

                    TradeHostingCloudAoStub cloudAoStub = new TradeHostingCloudAoStub();
                    cloudAoStub.setPeerAddr(relatedInfo.getMachineInnerIP());
                    QueryHostingUserOption queryOption = new QueryHostingUserOption();
                    queryOption.setSubUserId(subUserId);
                    QueryHostingUserPage subUserPage = cloudAoStub.getHostingUserPage(queryOption, pageOption);
                    if (subUserPage.getResultListSize() > 0) {
                        HostingUser subUser = subUserPage.getResultList().get(0);
                        String userName = subUser.getLoginName();

                        CompanyDaoStub companyDaoStub = new CompanyDaoStub();
                        QueryCompanyUserOption companyUserOption = new QueryCompanyUserOption();
                        companyUserOption.setCompanyId(companyId);
                        companyUserOption.setUserName(userName);
                        CompanyUserPage companyUserPage = companyDaoStub.queryCompanyUser(companyUserOption, pageOption);
                        if (companyUserPage.getPageSize() > 0) {
                            CompanyUser companyUser = companyUserPage.getPage().get(0);
                            return companyUser.getUserId();
                        }
                    }
                }

            }
            throw new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "UserId cache key error.");
        }
    };

    private LoadingCache<String, Integer> loadingCache =
            CacheBuilder.newBuilder().build(hostingUserIdCache);

    public long getUserId(long machineId, int subUserId) throws ErrorInfo {
        try {
            return loadingCache.get(buildKey(machineId, subUserId));
        } catch (ExecutionException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "Get user id fail.");
        }
    }

    private String buildKey(long machineId, int subUserId) {
        return machineId + "|" + subUserId;
    }
}
