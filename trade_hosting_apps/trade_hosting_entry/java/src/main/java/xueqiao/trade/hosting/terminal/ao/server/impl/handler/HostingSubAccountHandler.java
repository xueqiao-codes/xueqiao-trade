package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.HostingSubAccountRelatedItem;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.IHostingUserApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountOption;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;
import xueqiao.trade.hosting.terminal.ao.HostingSAWRUItemList;
import xueqiao.trade.hosting.terminal.ao.HostingSAWRUItemListPage;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.QueryHostingSAWRUItemListOption;

public class HostingSubAccountHandler extends HandlerBase {

    private IHostingSubAccountApi mSubAccountApi;
    private IHostingUserApi mUserApi;
    
    public HostingSubAccountHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
        
        mSubAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class);
        mUserApi = Globals.getInstance().queryInterfaceForSure(IHostingUserApi.class);
    }
    
    private void checkSubAccountName(String subAccountName) throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotBlank(subAccountName), "subAccountName should not be blank");
        ParameterChecker.check(StringUtils.trim(subAccountName).length() <= 60, "subAccountName length should <= 60");
    }
    
    public long createSubAccount(HostingSubAccount newSubAccount) throws ErrorInfo {
        permit(EHostingUserRole.AdminGroup);
        
        ParameterChecker.checkNotNull(newSubAccount, "newSubAccount should not be null");
        ParameterChecker.check(newSubAccount.isSetSubAccountName(), "newSubAccount's subAccountName should be set");
        checkSubAccountName(newSubAccount.getSubAccountName());
        
        HostingSubAccount opSubAccount = new HostingSubAccount();
        opSubAccount.setSubAccountId(mSubAccountApi.createSubAccountId());
        opSubAccount.setSubAccountName(StringUtils.trim(newSubAccount.getSubAccountName()));
        mSubAccountApi.createSubAccount(opSubAccount);
        
        return opSubAccount.getSubAccountId();
    }
    
    
    public void renameSubAccount(long subAccountId, String subAccountName) throws ErrorInfo {
        permit(EHostingUserRole.AdminGroup);
        
        ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        checkSubAccountName(subAccountName);
        
        mSubAccountApi.renameSubAccount(subAccountId, subAccountName);
    }
        
    public void assignSubAccountRelatedUsers(long subAccountId
            , Set<Integer> relatedSubUserIds, Set<Integer> unRelatedSubUserIds) throws  ErrorInfo {
        permit(EHostingUserRole.AdminGroup);
        
        ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        ParameterChecker.checkNotNull(relatedSubUserIds, "relatedSubUserIds should not be null");
        ParameterChecker.check(relatedSubUserIds.size() < 50, "relatedSubUserIds size should < 50");
        ParameterChecker.checkNotNull(unRelatedSubUserIds, "unRelatedSubUserIds should not be null");
        ParameterChecker.check(unRelatedSubUserIds.size() < 50, "unRelatedSubUserIds size should < 50");
        
        Set<Integer> batchSubUserIds = new HashSet<Integer>();
        batchSubUserIds.addAll(relatedSubUserIds);
        batchSubUserIds.addAll(unRelatedSubUserIds);
        ParameterChecker.check(!batchSubUserIds.isEmpty(), "relatedSubUserIds and unRelatedSubUserIds should not be null");
        
        QueryUserOption qryUserOption = new QueryUserOption();
        qryUserOption.setBatchSubUserIds(batchSubUserIds);
        PageResult<HostingUser> userPage = mUserApi.queryUserPage(qryUserOption
                , new PageOption().setPageIndex(0).setPageSize(Integer.MAX_VALUE));
        Map<Integer, HostingUser> userMapping = new HashMap<Integer, HostingUser>();
        for (HostingUser hostingUser : userPage.getPageList()) {
            userMapping.put(hostingUser.getSubUserId(), hostingUser);
        }
        
        Set<HostingUser> relatedUsers = new HashSet<HostingUser>();
        for (Integer relatedSubUserId : relatedSubUserIds) {
            HostingUser relatedUser = userMapping.get(relatedSubUserId);
            if (relatedUser == null) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue()
                        , "related user is not exist for " + relatedSubUserId);
            }
            relatedUsers.add(relatedUser);
        }
        
        Set<HostingUser> unRelatedUsers = new HashSet<HostingUser>();
        for (Integer unRelatedSubUserId : unRelatedSubUserIds) {
            HostingUser unRelatedUser = userMapping.get(unRelatedSubUserId);
            if (unRelatedUser == null) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue()
                        , "unRelated user is not exist for " + unRelatedSubUserId);
            }
            unRelatedUsers.add(unRelatedUser);
        }
        
        mSubAccountApi.assignSubAccountRelatedUsers(subAccountId, relatedUsers, unRelatedUsers);
    }
    
    public HostingSAWRUItemListPage getSAWRUTListPage(
            QueryHostingSAWRUItemListOption queryOption, IndexedPageOption pageOption) throws ErrorInfo {
        permit(EHostingUserRole.AdminGroup);
        
        ParameterChecker.checkNotNull(queryOption, "queryOption should not be null");
        PageOptionHelper.checkIndexedPageOption(pageOption, 50);
        
        QuerySubAccountOption apiOption = new QuerySubAccountOption();
        if (queryOption.isSetSubAccountId()) {
            apiOption.setSubAccountId(queryOption.getSubAccountId());
        }
        if (queryOption.isSetSubAccountNamePartical()) {
            apiOption.setSubAccountNamePartical(queryOption.getSubAccountNamePartical());
        }
        if (queryOption.isSetSubAccountNameWhole()) {
            apiOption.setSubAccountNameWhole(queryOption.getSubAccountNameWhole());
        }
        
        PageResult<HostingSubAccount> subAccountsPage 
            = mSubAccountApi.getSubAccounts(apiOption
                , new PageOption().setPageIndex(pageOption.getPageIndex())
                                  .setPageSize(pageOption.getPageSize())
                                  .setNeedTotalCount(pageOption.isNeedTotalCount()));
        
        Set<Long> batchSubAccountIds = new HashSet<Long>();
        for (HostingSubAccount subAccount : subAccountsPage.getPageList()) {
            batchSubAccountIds.add(subAccount.getSubAccountId());
        }
        Map<Long, List<HostingSubAccountRelatedItem>> 
                relatedItemMapping = mSubAccountApi.getSubAccountRelatedItemsBySubAccountIds(batchSubAccountIds);
        
        HostingSAWRUItemListPage pageResult = new HostingSAWRUItemListPage();
        pageResult.setTotalCount(subAccountsPage.getTotalCount());
        pageResult.setResultList(new ArrayList<HostingSAWRUItemList>());
        for (HostingSubAccount subAccount : subAccountsPage.getPageList()) {
            HostingSAWRUItemList sawruItemList = new HostingSAWRUItemList();
            sawruItemList.setSubAccount(subAccount);
            if (relatedItemMapping.containsKey(subAccount.getSubAccountId())) {
                sawruItemList.setRelatedItemList(relatedItemMapping.get(subAccount.getSubAccountId()));
            }
            pageResult.getResultList().add(sawruItemList);
        }
        return pageResult;
    }
    
    public Map<Long, List<HostingSubAccountRelatedItem>> getSARUTBySubAccountId(Set<Long> subAccountIds) throws ErrorInfo {
        permit(EHostingUserRole.AdminGroup);
        
        ParameterChecker.checkNotNull(subAccountIds, "subAccountIds should not be null");
        ParameterChecker.check(subAccountIds.size() > 0 && subAccountIds.size() <= 50, "subAccountIds'size should > 0 and <= 50");
        
        return mSubAccountApi.getSubAccountRelatedItemsBySubAccountIds(subAccountIds);
    }
    
    public Map<Integer, List<HostingSubAccountRelatedItem>> getSARUTBySubUserId(Set<Integer> subUserIds) throws ErrorInfo {
        ParameterChecker.checkNotNull(subUserIds, "subUserIds should not be null");
        ParameterChecker.check(subUserIds.size() > 0 && subUserIds.size() <= 50, "subUserId's should > 0 and <= 50");
        
        if (hasPermission(EHostingUserRole.AdminGroup)) {
            return mSubAccountApi.getSubAccountRelatedItemsBySubUserIds(subUserIds);
        }
        return mSubAccountApi.getSubAccountRelatedItemsBySubUserIds(
                new HashSet<Integer>(Arrays.asList(getSubUserId())));
    }
    
}
