package xueqiao.trade.hosting.storage.apis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.HostingSubAccountMoneyRecord;
import xueqiao.trade.hosting.HostingSubAccountRelatedItem;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountMoneyRecordOption;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountOption;

public interface IHostingSubAccountApi {
    public long createSubAccountId() throws ErrorInfo;
    public void createSubAccount(HostingSubAccount newSubAccount) throws ErrorInfo;
    public void renameSubAccount(long subAccountId, String subAccountName) throws ErrorInfo;
    
    public HostingSubAccount getSubAccount(long subAccountId) throws ErrorInfo;
    
    public void assignSubAccountRelatedUsers(
            long subAccountId
            , Set<HostingUser> relatedSubUsers
            , Set<HostingUser> unRelatedSubUsers) throws ErrorInfo;
    
    public void inSubAccountMoney(long subAccountId, long howMuch, String ticket) throws ErrorInfo;
    public void outSubAccountMoney(long subAccountId, long howMuch, String ticket) throws ErrorInfo;
    
    public PageResult<HostingSubAccountMoneyRecord> getSubAccountMoneyRecords(
            QuerySubAccountMoneyRecordOption qryOption
            , PageOption pageOption) throws ErrorInfo;
    
    public PageResult<HostingSubAccount> getSubAccounts(QuerySubAccountOption qryOption
            , PageOption pageOption) throws ErrorInfo;
    
    public Map<Long, List<HostingSubAccountRelatedItem>>
            getSubAccountRelatedItemsBySubAccountIds(Set<Long> subAccountIds) throws ErrorInfo;
    
    public Map<Integer, List<HostingSubAccountRelatedItem>> 
            getSubAccountRelatedItemsBySubUserIds(Set<Integer> subUserIds) throws ErrorInfo;
    
    public HostingSubAccountRelatedItem getSubAccountRelatedItem(long subAccountId, int subUserId) throws ErrorInfo;
    
    public List<HostingSubAccountRelatedItem> getAllSubAccountRelatedItems() throws ErrorInfo;
    
    public void clearAll() throws ErrorInfo;
}
