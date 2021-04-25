package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.tasknote.helper.TaskNoteStubFactory;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;
import xueqiao.trade.hosting.tasknote.thriftapi.QueryTaskNoteOption;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.QueryXQTradeLameTaskNotePageOption;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class HostingTaskNoteHandler extends HandlerBase  {
    public HostingTaskNoteHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    public HostingTaskNotePage getXQTradeLameTaskNotePage(QueryXQTradeLameTaskNotePageOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo {
        ParameterChecker.checkNotNull(qryOption, "qryOption should not be null");
        ParameterChecker.check(qryOption.isSetSubAccountIds() && !qryOption.getSubAccountIds().isEmpty()
                , "qryOption's subAccountIds should be set and not be empty");

        PageOptionHelper.checkIndexedPageOption(pageOption, 200);

        QueryTaskNoteOption apiQryOption = new QueryTaskNoteOption();
        apiQryOption.setNoteType(HostingTaskNoteType.XQ_TRADE_LAME);
        apiQryOption.setKey1(new HashSet<Long>());
        for (Long subAccountId : qryOption.getSubAccountIds()) {
            if (hasRelatedAccount(subAccountId)) {
                apiQryOption.getKey1().add(subAccountId);
            }
        }
        apiQryOption.setKey2(qryOption.getXqTradeIds());

        return ErrorInfoCallHelper.call(new Callable<HostingTaskNotePage>() {
            @Override
            public HostingTaskNotePage call() throws Exception {
                return TaskNoteStubFactory.getStub().getTaskNotePage(apiQryOption, pageOption);
            }
        });
    }

    public Map<Long, ErrorInfo> batchDeleteXQTradeLameTaskNotes(long subAccountId, Set<Long> xqTradeIds) throws ErrorInfo {
        ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        ParameterChecker.check(xqTradeIds != null && !xqTradeIds.isEmpty()
                , "xqTradeIds should be set and not be empty!");

        super.checkHasRelatedAccount(subAccountId);

        Map<Long, ErrorInfo> resultMap = new HashMap<>();
        for (Long xqTradeId : xqTradeIds) {
            try {
                ErrorInfoCallHelper.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        TaskNoteStubFactory.getStub().delTaskNote(
                                HostingTaskNoteType.XQ_TRADE_LAME
                                , new HostingTaskNoteKey().setKey1(subAccountId)
                                        .setKey2(xqTradeId)
                                        .setKey3("")
                        );
                        return null;
                    }
                });
            } catch (ErrorInfo e) {
                resultMap.put(xqTradeId, e);
            }
        }
        return resultMap;
    }
}
