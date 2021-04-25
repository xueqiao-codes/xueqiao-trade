package xueqiao.trade.hosting.storage.subaccount;

import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.HostingSubAccountMoneyRecord;
import xueqiao.trade.hosting.HostingSubAccountMoneyRecordDirection;
import xueqiao.trade.hosting.HostingSubAccountRelatedItem;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.events.SubAccountRelatedInfoChangedEvent;
import xueqiao.trade.hosting.events.SubAccountRelatedInfoChangedEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountMoneyRecordOption;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountOption;
import xueqiao.trade.hosting.storage.comm.HostingDB;
import xueqiao.trade.hosting.storage.comm.StorageApiStub;

public class HostingSubAccountApiImpl implements IHostingSubAccountApi {

    @Override
    public long createSubAccountId() throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return new StorageApiStub().createSubAccountId();
            }
        });
    }

    @Override
    public void createSubAccount(HostingSubAccount newSubAccount) throws ErrorInfo {
        ParameterChecker.checkInnerNotNull(newSubAccount);
        ParameterChecker.checkInnerArgument(newSubAccount.getSubAccountId() > 0);
        ParameterChecker.checkInnerArgument(newSubAccount.isSetSubAccountName()
                && StringUtils.isNotEmpty(newSubAccount.getSubAccountName()));
        
        new DBStepHelper<Void>(HostingDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                QuerySubAccountOption qryOption = new QuerySubAccountOption();
                qryOption.setSubAccountNameWhole(newSubAccount.getSubAccountName());
                
                PageResult<HostingSubAccount> subAccountNamePage = new HostingSubAccountTable(getConnection())
                    .getSubAccountPage(qryOption, new PageOption().setPageIndex(0).setPageSize(1));
                if (!subAccountNamePage.getPageList().isEmpty()) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_DUPLICATE_NAME.getValue()
                            , "Duplicate sub account name");
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                HostingSubAccount opSubAccount = new HostingSubAccount();
                opSubAccount.setSubAccountId(newSubAccount.getSubAccountId());
                opSubAccount.setSubAccountName(newSubAccount.getSubAccountName());
                new HostingSubAccountTable(getConnection()).createSubAccount(opSubAccount);
            }

            @Override
            public Void getResult() {
                return null;
            }
            
        }.execute();
    }

    @Override
    public void renameSubAccount(long subAccountId, String subAccountName) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(subAccountId > 0);
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(subAccountName));
        
        new DBTransactionHelper<Void>(HostingDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                HostingSubAccount updateSubAccount = new HostingSubAccountTable(getConnection()).getSubAccount(subAccountId, true);
                if (updateSubAccount == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_NOT_EXISTED.getValue()
                            , "Sub Account not existed!");
                }
                
                QuerySubAccountOption qryOption = new QuerySubAccountOption();
                qryOption.setSubAccountNameWhole(subAccountName);
                
                PageResult<HostingSubAccount> subAccountNamePage = new HostingSubAccountTable(getConnection())
                    .getSubAccountPage(qryOption, new PageOption().setPageIndex(0).setPageSize(1));
                if (!subAccountNamePage.getPageList().isEmpty()) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_DUPLICATE_NAME.getValue()
                            , "Duplicate sub account name");
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                HostingSubAccount opSubAccount = new HostingSubAccount();
                opSubAccount.setSubAccountId(subAccountId);
                opSubAccount.setSubAccountName(subAccountName);
                
                new HostingSubAccountTable(getConnection()).updateSubAccount(opSubAccount);
                new HostingSubAccountRelatedTable(getConnection()).updateSubAccountInfo(opSubAccount);
            }

            @Override
            public Void getResult() {
                return null;
            }
            
        }.execute();
        
    }

    @Override
    public void assignSubAccountRelatedUsers(long subAccountId
            , Set<HostingUser> relatedSubUsers
            , Set<HostingUser> unRelatedSubUsers) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(subAccountId > 0);
        ParameterChecker.checkInnerNotNull(relatedSubUsers);
        ParameterChecker.checkInnerNotNull(unRelatedSubUsers);
        
        if (relatedSubUsers.isEmpty() && unRelatedSubUsers.isEmpty()) {
            return ;
        }
        
        new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {
            private HostingSubAccount subAccount;
            private Set<HostingUser> addRelatedUsers;
            private Set<HostingUser> delRelatedUsers;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                subAccount = new HostingSubAccountTable(getConnection()).getSubAccount(subAccountId, true);
                if (subAccount == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_NOT_EXISTED.getValue()
                            , "Sub Account not existed!");
                }
                
                List<HostingSubAccountRelatedItem> existRelatedItems 
                    = new HostingSubAccountRelatedTable(getConnection()).getItemsBySubAccountId(subAccountId);
                Map<Integer, HostingSubAccountRelatedItem> userIndexRelatedItemMap
                    = new HashMap<Integer, HostingSubAccountRelatedItem>();
                for (HostingSubAccountRelatedItem relatedItem : existRelatedItems) {
                    userIndexRelatedItemMap.put(relatedItem.getSubUserId(), relatedItem);
                }
                
                addRelatedUsers = new HashSet<HostingUser>();
                delRelatedUsers = new HashSet<HostingUser>();
                for (HostingUser relatedSubUser : relatedSubUsers) {
                    if (userIndexRelatedItemMap.containsKey(relatedSubUser.getSubUserId())) {
                        continue;
                    }
                    addRelatedUsers.add(relatedSubUser);
                }
                for (HostingUser unRelatedSubUser : unRelatedSubUsers) {
                    if (!userIndexRelatedItemMap.containsKey(unRelatedSubUser.getSubUserId())) {
                        continue;
                    }
                    delRelatedUsers.add(unRelatedSubUser);
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                HostingSubAccountRelatedTable relatedTable = new HostingSubAccountRelatedTable(getConnection());
                for (HostingUser addRelatedUser : addRelatedUsers) {
                    HostingSubAccountRelatedItem opRelatedItem = new HostingSubAccountRelatedItem();
                    opRelatedItem.setSubAccountId(subAccountId);
                    opRelatedItem.setSubUserId(addRelatedUser.getSubUserId());
                    opRelatedItem.setRelatedTimestamp((int)(System.currentTimeMillis()/1000));
                    opRelatedItem.setSubAccountName(subAccount.getSubAccountName());
                    opRelatedItem.setSubUserLoginName(addRelatedUser.getLoginName());
                    opRelatedItem.setSubUserNickName(addRelatedUser.getNickName());
                    relatedTable.addRelatedItem(opRelatedItem);
                }
                for (HostingUser delRelatedUser : delRelatedUsers) {
                    relatedTable.deleteRelatedItem(subAccountId, delRelatedUser.getSubUserId());
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
            
            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                SubAccountRelatedInfoChangedEvent event = new SubAccountRelatedInfoChangedEvent();
                event.setType(SubAccountRelatedInfoChangedEventType.RELATED_INFO_CHANGED);
                event.setSubAccountId(subAccountId);
                return new SimpleEntry<TBase, IGuardPolicy>(event
                        , new TimeoutGuardPolicy().setTimeoutSeconds(5));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
            
        }.execute();
    }

    @Override
    public void inSubAccountMoney(long subAccountId, long howMuch, String ticket) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(subAccountId > 0);
        ParameterChecker.checkInnerArgument(howMuch > 0);
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(ticket));
        
        new DBTransactionHelper<Void>(HostingDB.Global()) {
            private HostingSubAccount subAccount;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                subAccount = new HostingSubAccountTable(getConnection()).getSubAccount(subAccountId, true);
                if (subAccount == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_NOT_EXISTED.getValue()
                            , "Sub Account not existed!");
                }
                
                HostingSubAccountMoneyRecord ticketMR 
                    = new HostingSubAccountMRTable(getConnection()).getTicketMR(subAccountId, ticket);
                if (ticketMR != null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_OP_MONEY_DUPLICATE_TICKET.getValue()
                            , "Duplicate ticket!");
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                HostingSubAccountMoneyRecord opMR = new HostingSubAccountMoneyRecord();
                HostingSubAccount opSubAccount = new HostingSubAccount();
                
                opSubAccount.setSubAccountId(subAccountId);
                opSubAccount.setInAmount(subAccount.getInAmount() + howMuch);
                
                opMR.setSubAccountId(subAccountId);
                opMR.setRecordTimestamp((int)(System.currentTimeMillis()/1000));
                opMR.setDirection(HostingSubAccountMoneyRecordDirection.MONEY_IN);
                opMR.setHowMuch(howMuch);
                opMR.setInAmountBefore(subAccount.getInAmount());
                opMR.setInAmountAfter(opSubAccount.getInAmount());
                opMR.setOutAmountBefore(subAccount.getOutAmount());
                opMR.setOutAmountAfter(subAccount.getOutAmount());
                opMR.setTicket(ticket);
                
                new HostingSubAccountTable(getConnection()).updateSubAccount(opSubAccount);
                new HostingSubAccountMRTable(getConnection()).appendMR(opMR);
            }

            @Override
            public Void getResult() {
                return null;
            }
            
        }.execute();
        
    }

    @Override
    public void outSubAccountMoney(long subAccountId, long howMuch, String ticket) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(subAccountId > 0);
        ParameterChecker.checkInnerArgument(howMuch > 0);
        ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(ticket));
        
        new DBTransactionHelper<Void>(HostingDB.Global()) {
            private HostingSubAccount subAccount;
            
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                subAccount = new HostingSubAccountTable(getConnection()).getSubAccount(subAccountId, true);
                if (subAccount == null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_NOT_EXISTED.getValue()
                            , "Sub Account not existed!");
                }
                
                HostingSubAccountMoneyRecord ticketMR 
                    = new HostingSubAccountMRTable(getConnection()).getTicketMR(subAccountId, ticket);
                if (ticketMR != null) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_OP_MONEY_DUPLICATE_TICKET.getValue()
                            , "Duplicate ticket!");
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                HostingSubAccountMoneyRecord opMR = new HostingSubAccountMoneyRecord();
                HostingSubAccount opSubAccount = new HostingSubAccount();
                
                opSubAccount.setSubAccountId(subAccountId);
                opSubAccount.setOutAmount(subAccount.getOutAmount() + howMuch);
                
                opMR.setSubAccountId(subAccountId);
                opMR.setRecordTimestamp((int)(System.currentTimeMillis()/1000));
                opMR.setDirection(HostingSubAccountMoneyRecordDirection.MONEY_OUT);
                opMR.setHowMuch(howMuch);
                opMR.setInAmountBefore(subAccount.getInAmount());
                opMR.setInAmountAfter(subAccount.getInAmount());
                opMR.setOutAmountBefore(subAccount.getOutAmount());
                opMR.setOutAmountAfter(opSubAccount.getOutAmount());
                opMR.setTicket(ticket);
                
                new HostingSubAccountTable(getConnection()).updateSubAccount(opSubAccount);
                new HostingSubAccountMRTable(getConnection()).appendMR(opMR);
            }

            @Override
            public Void getResult() {
                return null;
            }
            
        }.execute();
    }

    @Override
    public PageResult<HostingSubAccountMoneyRecord> getSubAccountMoneyRecords(
            QuerySubAccountMoneyRecordOption qryOption, PageOption pageOption) throws ErrorInfo {
        ParameterChecker.checkInnerNotNull(pageOption);
        pageOption.checkValid();
        
        return new DBQueryHelper<PageResult<HostingSubAccountMoneyRecord>>(HostingDB.Global()) {
            @Override
            protected PageResult<HostingSubAccountMoneyRecord> onQuery(Connection conn) throws Exception {
                return new HostingSubAccountMRTable(conn).getMRPage(qryOption, pageOption);
            }
        }.query();
    }

    @Override
    public PageResult<HostingSubAccount> getSubAccounts(QuerySubAccountOption qryOption, PageOption pageOption)
            throws ErrorInfo {
        ParameterChecker.checkInnerNotNull(pageOption);
        pageOption.checkValid();
        return new DBQueryHelper<PageResult<HostingSubAccount>>(HostingDB.Global()) {
            @Override
            protected PageResult<HostingSubAccount> onQuery(Connection conn) throws Exception {
                return new HostingSubAccountTable(conn).getSubAccountPage(qryOption, pageOption);
            }
        }.query();
    }

    @Override
    public Map<Long, List<HostingSubAccountRelatedItem>> getSubAccountRelatedItemsBySubAccountIds(
            Set<Long> subAccountIds) throws ErrorInfo {
        if (subAccountIds == null || subAccountIds.isEmpty()) {
            return new HashMap<Long, List<HostingSubAccountRelatedItem>>();
        }
        
        return new DBQueryHelper<Map<Long, List<HostingSubAccountRelatedItem>>>(HostingDB.Global()) {
            @Override
            protected Map<Long, List<HostingSubAccountRelatedItem>> onQuery(Connection conn) throws Exception {
                return new HostingSubAccountRelatedTable(conn).getItemsBySubAccountIds(subAccountIds);
            }
        }.query();
    }

    @Override
    public Map<Integer, List<HostingSubAccountRelatedItem>> getSubAccountRelatedItemsBySubUserIds(
            Set<Integer> subUserIds) throws ErrorInfo {
        if(subUserIds == null || subUserIds.isEmpty()) {
            return new HashMap<Integer, List<HostingSubAccountRelatedItem>>();
        }
        
        return new DBQueryHelper<Map<Integer, List<HostingSubAccountRelatedItem>>>(HostingDB.Global()) {
            @Override
            protected Map<Integer, List<HostingSubAccountRelatedItem>> onQuery(Connection conn) throws Exception {
                return new HostingSubAccountRelatedTable(conn).getItemsBySubUserIds(subUserIds);
            }
        }.query();
    }

    @Override
    public HostingSubAccountRelatedItem getSubAccountRelatedItem(long subAccountId, int subUserId) throws ErrorInfo {
        return new DBQueryHelper<HostingSubAccountRelatedItem>(HostingDB.Global()) {
            @Override
            protected HostingSubAccountRelatedItem onQuery(Connection conn) throws Exception {
                return new HostingSubAccountRelatedTable(conn).getItem(subAccountId, subUserId);
            }
        }.query();
    }

    @Override
    public HostingSubAccount getSubAccount(long subAccountId) throws ErrorInfo {
        return new DBQueryHelper<HostingSubAccount>(HostingDB.Global()) {
            @Override
            protected HostingSubAccount onQuery(Connection conn) throws Exception {
                return new HostingSubAccountTable(conn).getSubAccount(subAccountId);
            }
        }.query();
    }

    @Override
    public void clearAll() throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingSubAccountRelatedTable(getConnection()).deleteAll();
                new HostingSubAccountMRTable(getConnection()).deleteAll();
                new HostingSubAccountTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                SubAccountRelatedInfoChangedEvent event = new SubAccountRelatedInfoChangedEvent();
                event.setType(SubAccountRelatedInfoChangedEventType.RELATED_INFO_ALL_CLEARED);
                return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(10));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
            
        }.execute();
    }

    @Override
    public List<HostingSubAccountRelatedItem> getAllSubAccountRelatedItems() throws ErrorInfo {
        return new DBQueryHelper<List<HostingSubAccountRelatedItem>>(HostingDB.Global()) {
            @Override
            protected List<HostingSubAccountRelatedItem> onQuery(Connection conn) throws Exception {
                return new HostingSubAccountRelatedTable(conn).getAll();
            }
            
        }.query();
    }

}
