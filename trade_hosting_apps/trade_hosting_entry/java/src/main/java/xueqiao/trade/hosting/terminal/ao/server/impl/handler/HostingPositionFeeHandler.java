package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.fee.helper.PositionFeeStubFactory;
import xueqiao.trade.hosting.position.fee.thriftapi.*;
import xueqiao.trade.hosting.position.fee.thriftapi.client.TradeHostingPositionFeeStub;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

import java.util.concurrent.Callable;

public class HostingPositionFeeHandler extends HandlerBase {

    public HostingPositionFeeHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
    }

    public void setGeneralMarginSetting(XQGeneralMarginSettings marginSettings) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(marginSettings, "marginSettings");
        ParameterChecker.check(marginSettings.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(marginSettings.isSetType(), "type should be set");
        ParameterChecker.check(marginSettings.getMarginDelta() != null, "marginDelta should be set");

        checkPermission(marginSettings.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().setGeneralMarginSetting(marginSettings);
                return null;
            }
        });
    }

    public void setGeneralCommissionSetting(XQGeneralCommissionSettings commissionSettings) throws ErrorInfo, TException {
        ParameterChecker.checkNotNull(commissionSettings, "commissionSettings");
        ParameterChecker.check(commissionSettings.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(commissionSettings.isSetType(), "type should be set");
        ParameterChecker.check(commissionSettings.getCommissionDelta() != null, "commissionDelta should be set");

        checkPermission(commissionSettings.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().setGeneralCommissionSetting(commissionSettings);
                return null;
            }
        });
    }

    public void addSpecMarginSetting(XQSpecMarginSettings marginSettings) throws ErrorInfo, TException {
        ParameterChecker.check(marginSettings != null, "marginSettings should not be null");
        ParameterChecker.check(marginSettings.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(marginSettings.getCommodityId() > 0, "invalid commodityId");
        ParameterChecker.check(marginSettings.isSetType(), "type should be set");
        ParameterChecker.check(marginSettings.isSetMarginDelta(), "marginDelta should be set");

        checkPermission(marginSettings.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().addSpecMarginSetting(marginSettings);
                return null;
            }
        });
    }

    public void addSpecCommissionSetting(XQSpecCommissionSettings commissionSettings) throws ErrorInfo, TException {
        ParameterChecker.check(commissionSettings != null, "commissionSettings should not be null");
        ParameterChecker.check(commissionSettings.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(commissionSettings.getCommodityId() > 0, "invalid commodityId");
        ParameterChecker.check(commissionSettings.isSetType(), "type should be set");
        ParameterChecker.check(commissionSettings.isSetCommissionDelta(), "commissionDelta should be set");

        checkPermission(commissionSettings.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().addSpecCommissionSetting(commissionSettings);
                return null;
            }
        });
    }

    public void updateSpecMarginSetting(XQSpecMarginSettings marginSettings) throws ErrorInfo, TException {
        ParameterChecker.check(marginSettings != null, "marginSettings should not be null");
        ParameterChecker.check(marginSettings.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(marginSettings.getCommodityId() > 0, "invalid commodityId");

        checkPermission(marginSettings.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().updateSpecMarginSetting(marginSettings);
                return null;
            }
        });
    }

    public void updateSpecCommissionSetting(XQSpecCommissionSettings commissionSettings) throws ErrorInfo, TException {
        ParameterChecker.check(commissionSettings != null, "commissionSettings should not be null");
        ParameterChecker.check(commissionSettings.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(commissionSettings.getCommodityId() > 0, "invalid commodityId");

        checkPermission(commissionSettings.getSubAccountId());

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().updateSpecCommissionSetting(commissionSettings);
                return null;
            }
        });
    }

    public void deleteSpecMarginSetting(long subAccountId, long commodityId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "invalid subAccountId");
        ParameterChecker.check(commodityId > 0, "invalid commodityId");

        checkPermission(subAccountId);

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().deleteSpecMarginSetting(subAccountId, commodityId);
                return null;
            }
        });
    }

    public void deleteSpecCommissionSetting(long subAccountId, long commodityId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "invalid subAccountId");
        ParameterChecker.check(commodityId > 0, "invalid commodityId");

        checkPermission(subAccountId);

        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                getStub().deleteSpecCommissionSetting(subAccountId, commodityId);
                return null;
            }
        });
    }

    public XQGeneralMarginSettings queryXQGeneralMarginSettings(long subAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "invalid subAccountId");
        checkPermission(subAccountId);

        return ErrorInfoCallHelper.call(new Callable<XQGeneralMarginSettings>() {
            @Override
            public XQGeneralMarginSettings call() throws Exception {
                return getStub().queryXQGeneralMarginSettings(subAccountId);
            }
        });
    }

    public XQGeneralCommissionSettings queryXQGeneralCommissionSettings(long subAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "invalid subAccountId");
        checkPermission(subAccountId);

        return ErrorInfoCallHelper.call(new Callable<XQGeneralCommissionSettings>() {
            @Override
            public XQGeneralCommissionSettings call() throws Exception {
                return getStub().queryXQGeneralCommissionSettings(subAccountId);
            }
        });
    }

    public XQSpecMarginSettingPage queryXQSpecMarginSettingPage(QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
//        checkPermission(subAccountId);
        return ErrorInfoCallHelper.call(new Callable<XQSpecMarginSettingPage>() {
            @Override
            public XQSpecMarginSettingPage call() throws Exception {
                return getStub().queryXQSpecMarginSettingPage(queryOptions, pageOption);
            }
        });
    }

    public XQSpecCommissionSettingPage queryXQSpecCommissionSettingPage(QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
//        checkPermission(subAccountId);
        return ErrorInfoCallHelper.call(new Callable<XQSpecCommissionSettingPage>() {
            @Override
            public XQSpecCommissionSettingPage call() throws Exception {
                return getStub().queryXQSpecCommissionSettingPage(queryOptions, pageOption);
            }
        });
    }

    public UpsideContractMarginPage queryUpsideContractMarginPage(QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        //        checkPermission(subAccountId);
        return ErrorInfoCallHelper.call(new Callable<UpsideContractMarginPage>() {
            @Override
            public UpsideContractMarginPage call() throws Exception {
                return getStub().queryUpsideContractMarginPage(queryOptions, pageOption);
            }
        });
    }

    public UpsideContractCommissionPage queryUpsideContractCommissionPage(QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        //        checkPermission(subAccountId);
        return ErrorInfoCallHelper.call(new Callable<UpsideContractCommissionPage>() {
            @Override
            public UpsideContractCommissionPage call() throws Exception {
                return getStub().queryUpsideContractCommissionPage(queryOptions, pageOption);
            }
        });
    }

    public XQContractMarginPage queryXQContractMarginPage(QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        //        checkPermission(subAccountId);
        return ErrorInfoCallHelper.call(new Callable<XQContractMarginPage>() {
            @Override
            public XQContractMarginPage call() throws Exception {
                return getStub().queryXQContractMarginPage(queryOptions, pageOption);
            }
        });
    }

    public XQContractCommissionPage queryXQContractCommissionPage(QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        //        checkPermission(subAccountId);
        return ErrorInfoCallHelper.call(new Callable<XQContractCommissionPage>() {
            @Override
            public XQContractCommissionPage call() throws Exception {
                return getStub().queryXQContractCommissionPage(queryOptions, pageOption);
            }
        });
    }

    private void checkPermission(long subAccountId) throws ErrorInfo {
        checkHasRelatedAccount(subAccountId);
    }

    private TradeHostingPositionFeeStub getStub() {
        return PositionFeeStubFactory.getStub();
    }
}
