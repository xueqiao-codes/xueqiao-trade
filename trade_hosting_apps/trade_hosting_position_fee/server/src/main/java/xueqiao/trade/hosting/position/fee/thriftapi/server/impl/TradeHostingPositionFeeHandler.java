package xueqiao.trade.hosting.position.fee.thriftapi.server.impl;

import java.util.Properties;

import org.apache.thrift.TException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.position.fee.controller.*;
import xueqiao.trade.hosting.position.fee.thriftapi.*;
import xueqiao.trade.hosting.position.fee.thriftapi.server.TradeHostingPositionFeeAdaptor;

public class TradeHostingPositionFeeHandler extends TradeHostingPositionFeeAdaptor {
    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    protected void clearAll(TServiceCntl oCntl) throws ErrorInfo, TException {
    }

    @Override
    protected void setGeneralMarginSetting(TServiceCntl oCntl, XQGeneralMarginSettings marginSettings) throws ErrorInfo, TException {
        SetGeneralMarginController controller = new SetGeneralMarginController(marginSettings);
        controller.checkParams();
        controller.doSet();
    }

    @Override
    protected void setGeneralCommissionSetting(TServiceCntl oCntl, XQGeneralCommissionSettings commissionSettings) throws ErrorInfo, TException {
        SetGeneralCommissionController controller = new SetGeneralCommissionController(commissionSettings);
        controller.checkParams();
        controller.doSet();
    }

    @Override
    protected void addSpecMarginSetting(TServiceCntl oCntl, XQSpecMarginSettings marginSettings) throws ErrorInfo, TException {
        AddSpecMarginController controller = new AddSpecMarginController(marginSettings);
        controller.checkParams();
        controller.doAdd();
    }

    @Override
    protected void addSpecCommissionSetting(TServiceCntl oCntl, XQSpecCommissionSettings commissionSettings) throws ErrorInfo, TException {
        AddSpecCommissionController controller = new AddSpecCommissionController(commissionSettings);
        controller.checkParams();
        controller.doAdd();
    }

    @Override
    protected void updateSpecMarginSetting(TServiceCntl oCntl, XQSpecMarginSettings marginSettings) throws ErrorInfo, TException {
        UpdateSpecMarginController controller = new UpdateSpecMarginController(marginSettings);
        controller.checkParams();
        controller.doUpdate();
    }

    @Override
    protected void updateSpecCommissionSetting(TServiceCntl oCntl, XQSpecCommissionSettings commissionSettings) throws ErrorInfo, TException {
        UpdateSpecCommissionController controller = new UpdateSpecCommissionController(commissionSettings);
        controller.checkParams();
        controller.doUpdate();
    }

    @Override
    protected void deleteSpecMarginSetting(TServiceCntl oCntl, long subAccountId, long commodityId) throws ErrorInfo, TException {
        DeleteSpecMarginSettingController controller = new DeleteSpecMarginSettingController(subAccountId, commodityId);
        controller.checkParams();
        controller.doDelete();
    }

    @Override
    protected void deleteSpecCommissionSetting(TServiceCntl oCntl, long subAccountId, long commodityId) throws ErrorInfo, TException {
        DeleteSpecCommissionSettingController controller = new DeleteSpecCommissionSettingController(subAccountId, commodityId);
        controller.checkParams();
        controller.doDelete();
    }

    @Override
    protected XQGeneralMarginSettings queryXQGeneralMarginSettings(TServiceCntl oCntl, long subAccountId) throws ErrorInfo, TException {
        QueryXQGeneralMarginSettingsController controller = new QueryXQGeneralMarginSettingsController(subAccountId);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected XQGeneralCommissionSettings queryXQGeneralCommissionSettings(TServiceCntl oCntl, long subAccountId) throws ErrorInfo, TException {
        QueryXQGeneralCommissionSettingsController controller = new QueryXQGeneralCommissionSettingsController(subAccountId);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected XQSpecMarginSettingPage queryXQSpecMarginSettingPage(TServiceCntl oCntl, QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryXQSpecMarginSettingPageController controller = new QueryXQSpecMarginSettingPageController(queryOptions, pageOption);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected XQSpecCommissionSettingPage queryXQSpecCommissionSettingPage(TServiceCntl oCntl, QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryXQSpecCommissionSettingPageController controller = new QueryXQSpecCommissionSettingPageController(queryOptions, pageOption);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected UpsideContractMarginPage queryUpsideContractMarginPage(TServiceCntl oCntl, QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryUpsideContractMarginPageController controller = new QueryUpsideContractMarginPageController(queryOptions, pageOption);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected UpsideContractCommissionPage queryUpsideContractCommissionPage(TServiceCntl oCntl, QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryUpsideContractCommissionPageController controller = new QueryUpsideContractCommissionPageController(queryOptions, pageOption);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected XQContractMarginPage queryXQContractMarginPage(TServiceCntl oCntl, QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryXQContractMarginPageController controller = new QueryXQContractMarginPageController(queryOptions, pageOption);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected XQContractCommissionPage queryXQContractCommissionPage(TServiceCntl oCntl, QueryXQPFeeOptions queryOptions, IndexedPageOption pageOption) throws ErrorInfo, TException {
        QueryXQContractCommissionPageController controller = new QueryXQContractCommissionPageController(queryOptions, pageOption);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    protected PositionFee queryPositionFee(TServiceCntl oCntl, long subAccountId, long contractId) throws ErrorInfo, TException {
        QueryPositionFeeController controller = new QueryPositionFeeController(subAccountId, contractId);
        controller.checkParams();
        return controller.doQuery();
    }

    @Override
    public void destroy() {
    }
}
