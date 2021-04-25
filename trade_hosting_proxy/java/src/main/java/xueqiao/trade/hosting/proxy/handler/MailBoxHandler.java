package xueqiao.trade.hosting.proxy.handler;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.file_storage.FileStorage;
import org.soldier.platform.file_storage.FileStorageException;
import org.soldier.platform.file_storage.FileStorageFactory;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.mailbox.user.message.dao.thriftapi.client.UserMessageDaoStub;
import xueqiao.mailbox.user.message.thriftapi.MarkReadOption;
import xueqiao.mailbox.user.message.thriftapi.ReqUserMessageOption;
import xueqiao.mailbox.user.message.thriftapi.UserMessage;
import xueqiao.mailbox.user.message.thriftapi.UserMessagePage;
import xueqiao.trade.hosting.proxy.ReqMailBoxMessageOption;
import xueqiao.trade.hosting.proxy.cache.UserIdCache;

import java.util.Set;

public class MailBoxHandler {

    private static final String FILE_STORAGE_KEY = "mailbox";

    public UserMessagePage queryMailBoxMessage(long machineId, int subUserId, ReqMailBoxMessageOption option, IndexedPageOption pageOption) throws TException {

        UserMessageDaoStub daoStub = new UserMessageDaoStub();
        ReqUserMessageOption reqMessageOption = new ReqUserMessageOption();
        reqMessageOption.setUserId(UserIdCache.getInstance().getUserId(machineId, subUserId));
        if (option != null) {
            if (option.isSetState()) {
                reqMessageOption.setState(option.getState());
            }
            if (option.isSetMessageId()) {
                reqMessageOption.setMessageId(option.getMessageId());
            }
            if (option.isSetStartTimestamp()) {
                reqMessageOption.setStartTimestamp(option.getStartTimestamp());
            }
            if (option.isSetEndTimstamp()) {
                reqMessageOption.setEndTimstamp(option.getEndTimstamp());
            }
        }
        FileStorage fileStorage = FileStorageFactory.getInstance().getFileStorage(FILE_STORAGE_KEY);
        UserMessagePage page = daoStub.reqUserMessage(reqMessageOption, pageOption);
        for (UserMessage message : page.getPage()) {
            FileStorage.URLMode rulMode = FileStorage.URLMode.HTTPS;
            try {
                message.setContentFileName(fileStorage.getUrl(message.getContentFileName(), rulMode));
            } catch (FileStorageException e) {
                AppLog.e(e.getMessage(), e);
            }
        }
        return page;
    }

    public boolean markMessageAsRead(long machineId, int subUserId, Set<Long> hostingMessageIds) throws TException {
        UserMessageDaoStub daoStub = new UserMessageDaoStub();
        MarkReadOption option = new MarkReadOption();
        option.setUserId(UserIdCache.getInstance().getUserId(machineId, subUserId));
        if (hostingMessageIds != null && hostingMessageIds.size() > 0) {
            option.setMessageIds(hostingMessageIds);
        }
        daoStub.markAsRead(option);
        return true;
    }
}
