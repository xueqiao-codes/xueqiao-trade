package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.client.TradeHostingPositionStatisStub;
import xueqiao.trade.hosting.position.statis.helper.PositionStatisStubFactory;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

import java.util.concurrent.Callable;

/**
 * 持仓统计模块
 */
public class HostingPositionStatisHandler extends HandlerBase {

    public HostingPositionStatisHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    public void contructCompose(StatContructComposeReq contructComposeReq) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(contructComposeReq, "contructComposeReq");
        ParameterChecker.check(contructComposeReq.getComposeGraphId() > 0, "composeGraphId should be larger than zero");
        ParameterChecker.check(contructComposeReq.getVolume() > 0, "volume should be larger than zero");
        ParameterChecker.check(contructComposeReq.getSubAccountId() > 0, "subAccountId should be larger than zero");
        ParameterChecker.check(contructComposeReq.isSetDiretion(), "direction should be set");
        ParameterChecker.check(contructComposeReq.isSetComposePrice(), "composePrice should be set");
        ParameterChecker.check(contructComposeReq.getComposeLegsSize() > 0, "composeLegs should not be null or empty");
        for (StatComposeLeg statComposeLeg : contructComposeReq.getComposeLegs()) {
            ParameterChecker.check(statComposeLeg.getSledContractId() > 0, "sledContractId should be larger than zero");
            ParameterChecker.check(statComposeLeg.getLegTradePrice() > 0, "legTradePrice should be larger than zero");
        }

        checkPermission(contructComposeReq.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().contructCompose(contructComposeReq);
                return null;
            }
        });
    }

    public void disassembleCompose(DisassembleComposePositionReq disassembleComposePositionReq) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(disassembleComposePositionReq, "disassembleComposePositionReq");
        ParameterChecker.check(disassembleComposePositionReq.getSubAccountId() > 0, "subAccountId should larger than zero");
        ParameterChecker.check(StringUtils.isNotBlank(disassembleComposePositionReq.getTargetKey()), "TargetKey should not be blank");
        ParameterChecker.check(disassembleComposePositionReq.isSetTargetType(), "targetType should be set");
        ParameterChecker.check(disassembleComposePositionReq.getPositionItemDataListSize() > 0, "positionItemDataList should not be null or empty");
        for (PositionItemData positionItemData : disassembleComposePositionReq.getPositionItemDataList()) {
            ParameterChecker.check(positionItemData.getPositionItemId() > 0, "positionItemId should larger than zero");
            ParameterChecker.check(positionItemData.getQuantity() > 0, "quantity should larger than zero");
        }

        checkPermission(disassembleComposePositionReq.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().disassembleCompose(disassembleComposePositionReq);
                return null;
            }
        });

    }

    public void batchClosePosition(BatchClosedPositionReq batchClosedPositionReq) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(batchClosedPositionReq, "batchClosedPositionReq");
        ParameterChecker.check(batchClosedPositionReq.getSubAccountId() > 0, "subAccountId should larger than zero");
        ParameterChecker.check(StringUtils.isNotBlank(batchClosedPositionReq.getTargetKey()), "targetKey should larger than zero");
        ParameterChecker.check(batchClosedPositionReq.isSetTargetType(), "targetType should be set");
        ParameterChecker.check(batchClosedPositionReq.getClosedPositionDetailItemsSize() > 0, "closedPositionDetailItems should larger than zero");
        for (ClosedPositionDetailItem item : batchClosedPositionReq.getClosedPositionDetailItems()) {
            ParameterChecker.check(item.getPositionItemId() > 0, "positionItemId should larger than zero");
            ParameterChecker.check(item.getClosedVolume() > 0, "closedVolume should larger than zero");
        }

        checkPermission(batchClosedPositionReq.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().batchClosePosition(batchClosedPositionReq);
                return null;
            }
        });
    }

    public void recoverClosedPosition(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subAccountId should larger than zero");
        ParameterChecker.check(StringUtils.isNotBlank(targetKey), "targetKey should not be blank");

        checkPermission(subAccountId);

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().recoverClosedPosition(subAccountId, targetKey, targetType);
                return null;
            }
        });
    }

    public void mergeToCompose(StatMergeToComposeReq mergeToComposeReq) throws ErrorInfo, TException {
        ParameterChecker.check(mergeToComposeReq != null, "mergeToComposeReq should not be empty");
        ParameterChecker.check(mergeToComposeReq.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(mergeToComposeReq.getComposeGraphId() > 0, "invalid subAccountId");
        ParameterChecker.check(mergeToComposeReq.isSetComposePrice(), "composePrice should be set");
        ParameterChecker.check(mergeToComposeReq.getVolume() > 0, "volume should be larger than 0");
        ParameterChecker.check(mergeToComposeReq.isSetDiretion(), "compose direction should be set");
        ParameterChecker.check(mergeToComposeReq.getMergeComposeLegDataList() != null && mergeToComposeReq.getMergeComposeLegDataListSize() > 0, "mergeComposeLegDataList should not be blank");
        for (MergeComposeLegData legData : mergeToComposeReq.getMergeComposeLegDataList()) {
            ParameterChecker.check(legData.isSetSledContractId(), "sledContractId should be set");
            ParameterChecker.check(legData.getQuantity() >= 0, "quantity should be equal to or larger than 0");
            if (legData.getQuantity() > 0) {
                ParameterChecker.check(legData.getPositionItemId() > 0, "positionItemId should be larger than 0 when quantity is larger than 0");
            }
        }

        checkPermission(mergeToComposeReq.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().mergeToCompose(mergeToComposeReq);
                return null;
            }
        });
    }

    public void deleteExpiredStatContractPosition(long subAccountId, long sledContractId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subAccountId should be larger than 0");
        ParameterChecker.check(sledContractId > 0, "sledContractId should be larger than 0");
        checkPermission(subAccountId);
        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().deleteExpiredStatContractPosition(subAccountId, sledContractId);
                return null;
            }
        });
    }

    public StatPositionSummaryPage queryStatPositionSummaryPage(QueryStatPositionSummaryOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatPositionSummaryPage>() {
            @Override
            public StatPositionSummaryPage call() throws Exception {
                return getStub().queryStatPositionSummaryPage(queryOption, pageOption);
            }
        });
    }

    public StatPositionItemPage queryStatPositionItemPage(QueryStatPositionItemOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatPositionItemPage>() {
            @Override
            public StatPositionItemPage call() throws Exception {
                return getStub().queryStatPositionItemPage(queryOption, pageOption);
            }
        });
    }

    public StatClosedPositionDateSummaryPage queryCurrentDayStatClosedPositionPage(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId >= 0, "subAccountId should be larger than zero");
        ParameterChecker.check(StringUtils.isNotBlank(targetKey), "targetKey should not be blank");

        checkPermission(subAccountId);

        return ErrorInfoCallHelper.call(new Callable<StatClosedPositionDateSummaryPage>() {
            @Override
            public StatClosedPositionDateSummaryPage call() throws Exception {
                return getStub().queryCurrentDayStatClosedPositionPage(subAccountId, targetKey, targetType);
            }
        });
    }

    public StatClosedPositionDetail queryStatClosedPositionDetail(QueryStatClosedPositionItemOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatClosedPositionDetail>() {
            @Override
            public StatClosedPositionDetail call() throws Exception {
                return getStub().queryStatClosedPositionDetail(queryOption, pageOption);
            }
        });
    }

    public StatClosedPositionDateSummaryPage queryArchivedClosedPositionPage(QueryStatClosedPositionDateSummaryOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatClosedPositionDateSummaryPage>() {
            @Override
            public StatClosedPositionDateSummaryPage call() throws Exception {
                return getStub().queryArchivedClosedPositionPage(queryOption, pageOption);
            }
        });
    }

    public StatClosedPositionDetail queryArchivedClosedPositionDetail(QueryStatArchiveItemOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatClosedPositionDetail>() {
            @Override
            public StatClosedPositionDetail call() throws Exception {
                return getStub().queryArchivedClosedPositionDetail(queryOption, pageOption);
            }
        });
    }

    public StatPositionSummaryExPage queryStatPositionSummaryExPage(QueryStatPositionSummaryOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatPositionSummaryExPage>() {
            @Override
            public StatPositionSummaryExPage call() throws Exception {
                return getStub().queryStatPositionSummaryExPage(queryOption, pageOption);
            }
        });
    }

    public StatPositionUnitPage queryStatPositionUnitPage(QueryStatPositionUnitOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        ParameterChecker.check(queryOption.isSetPositionItemId() && queryOption.getPositionItemId() > 0, "positionItemId should be set and larger than 0");

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatPositionUnitPage>() {
            @Override
            public StatPositionUnitPage call() throws Exception {
                return getStub().queryStatPositionUnitPage(queryOption, pageOption);
            }
        });
    }

    public StatClosedPositionDateSummaryPage queryHistoryClosedPositionPage(QueryHistoryClosedPositionOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        ParameterChecker.check(StringUtils.isNotBlank(queryOption.getTargetKey()), "invalid targetKey");
        ParameterChecker.check(queryOption.isSetTargetType(), "targetType should be set");
        ParameterChecker.check(queryOption.isSetClosedDateTimestampMs() && queryOption.getClosedDateTimestampMs() > 0, "invalid closedDateTimestampMs");

        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatClosedPositionDateSummaryPage>() {
            @Override
            public StatClosedPositionDateSummaryPage call() throws Exception {
                return getStub().queryHistoryClosedPositionPage(queryOption, pageOption);
            }
        });
    }

    public StatClosedPositionDetail queryHistoryClosedPositionDetail(QueryHistoryClosedPositionOption queryOption, IndexedPageOption pageOption) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(queryOption, "queryOption should be set");
        ParameterChecker.check(queryOption.isSetSubAccountId() && queryOption.getSubAccountId() > 0, "subAccountId should be set and larger than 0");
        ParameterChecker.check(StringUtils.isNotBlank(queryOption.getTargetKey()), "invalid targetKey");
        ParameterChecker.check(queryOption.isSetTargetType(), "targetType should be set");
        ParameterChecker.check(queryOption.isSetClosedDateTimestampMs() && queryOption.getClosedDateTimestampMs() > 0, "invalid closedDateTimestampMs");

        checkIndexedPageOption(pageOption);

        checkPermission(queryOption.getSubAccountId());

        return ErrorInfoCallHelper.call(new Callable<StatClosedPositionDetail>() {
            @Override
            public StatClosedPositionDetail call() throws Exception {
                return getStub().queryHistoryClosedPositionDetail(queryOption, pageOption);
            }
        });
    }

    private void checkIndexedPageOption(IndexedPageOption pageOption) throws ErrorInfo {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");
    }

    private void checkPermission(long subAccountId) throws ErrorInfo {
        checkHasRelatedAccount(subAccountId);
    }

    private TradeHostingPositionStatisStub getStub() {
        return PositionStatisStubFactory.getStub();
    }
}
