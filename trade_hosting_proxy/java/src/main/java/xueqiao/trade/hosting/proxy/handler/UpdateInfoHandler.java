package xueqiao.trade.hosting.proxy.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.platform.app.manager.dao.thriftapi.ReqAppOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqAppVersionOption;
import org.soldier.platform.app.manager.dao.thriftapi.ReqServerAppSupportOption;
import org.soldier.platform.app.manager.dao.thriftapi.client.AppManagerDaoStub;
import org.soldier.platform.app.manager.thriftapi.*;
import org.soldier.platform.file_storage.FileStorage;
import org.soldier.platform.file_storage.FileStorageException;
import org.soldier.platform.file_storage.FileStorageFactory;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.company.CompanyPageResult;
import xueqiao.company.QueryCompanyOption;
import xueqiao.company.dao.client.CompanyDaoStub;
import xueqiao.company.service.maintenance.ServiceMaintenance;
import xueqiao.company.service.maintenance.dao.thriftapi.ReqServiceMaintenanceOption;
import xueqiao.company.service.maintenance.dao.thriftapi.ServiceMaintenancePage;
import xueqiao.company.service.maintenance.dao.thriftapi.client.CompanyServiceMaintenanceDaoStub;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.TradeHostingErrorCode;
import xueqiao.trade.hosting.proxy.AppVersion;
import xueqiao.trade.hosting.proxy.ProxyErrorCode;
import xueqiao.trade.hosting.proxy.UpdateInfoReq;
import xueqiao.trade.hosting.proxy.UpdateType;

import java.util.List;

public class UpdateInfoHandler {

    public AppVersion reqUpdateInfo(UpdateInfoReq req) throws TException {
        if (req == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "req should not be null");
        }
        if (!req.isSetCompanyId() && !req.isSetCompanyCode()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "companyId or companyCode must set");
        }
        if (StringUtils.isBlank(req.getAppKey())) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "AppKey should not be blank");
        }
        if (!req.isSetVersionNum()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "VersionNum must set");
        }
        long companyId = 0;
        if (req.isSetCompanyId()) {
            companyId = req.getCompanyId();
        } else {
            QueryCompanyOption companyOption = new QueryCompanyOption();
            companyOption.setCompanyCodeWhole(req.getCompanyCode());
            CompanyPageResult companyPage = new CompanyDaoStub().queryCompanyPage(companyOption, new IndexedPageOption().setPageSize(1).setPageIndex(0));
            if (companyPage.getResultListSize() > 0) {
                companyId = companyPage.getResultList().get(0).getCompanyId();
            }
        }
        if (companyId == 0) {
            throw new ErrorInfo(TradeHostingErrorCode.HOSTING_MACHINE_NOT_FOUND.getValue(), "company not found.");
        }

        long serverVersionId = getServerVersionId(companyId);
        long appId = getAppId(req);
        AppVersion resultVersion = new AppVersion();
        ReqAppVersionOption versionOption = new ReqAppVersionOption();
        boolean isRestUpdateType = false;
        if (serverVersionId > 0) {
            ServerAppSupport support = getServerAppSupport(serverVersionId, appId);
            VersionNum minVersion = support.getMinSupportVersion();
            VersionNum maxVersion = support.getMaxSupportVersion();
            long minVersionNum = VersionNumberHandler.getTotalNumber(minVersion);
            long maxVersionNum = VersionNumberHandler.getTotalNumber(maxVersion);
            long nowVersionNum = VersionNumberHandler.getTotalNumber(VersionNumberHandler.map2DaoVersionNum(req.getVersionNum()));
            versionOption.setStartVersionNum(minVersion);
            versionOption.setEndVersionNum(maxVersion);
            if (nowVersionNum >= minVersionNum && nowVersionNum <= maxVersionNum) {
                isRestUpdateType = true;
            } else {
                resultVersion.setUpdateType(UpdateType.FORCE_UPDATE);
            }
        } else {
            resultVersion.setUpdateType(UpdateType.FORCE_UPDATE);
        }
        
        versionOption.setAppId(appId);
        versionOption.setVersionState(VersionState.ENABLE);
        AppManagerDaoStub stub = new AppManagerDaoStub();
        AppVersionPage versions = stub.reqAppVersion(versionOption, new IndexedPageOption().setPageIndex(0).setPageIndex(1));
        if (versions.getPageSize() == 0) {
            throw new ErrorInfo(ProxyErrorCode.ERROR_APP_SUPPORT_VERSION_NOT_FOUND.getValue(), "App support version not found.");
        }

        org.soldier.platform.app.manager.thriftapi.AppVersion appVersion = versions.getPage().get(0);
        if (isRestUpdateType) {
            org.soldier.platform.app.manager.thriftapi.UpdateType updateType = appVersion.getUpdateType();
            resultVersion.setUpdateType(UpdateType.findByValue(updateType.getValue()));
        }

        resultVersion.setAppId(appId);
        resultVersion.setAppKey(req.getAppKey());
        resultVersion.setVersionId(appVersion.getVersionId());
        resultVersion.setVersionKey(appVersion.getVersionKey());
        resultVersion.setDownloadUrlX32(getUrl(appVersion.getDownloadUrlX32()));
        resultVersion.setDownloadUrlX64(getUrl(appVersion.getDownloadUrlX64()));
        resultVersion.setUpdateNotes(appVersion.getUpdateNotes());
        resultVersion.setVersionNum(VersionNumberHandler.map2ProxyVersionNum(appVersion.getVersionNum()));
        resultVersion.setVersionNumTag(appVersion.getVersionNumTag());

        return resultVersion;
    }

    private String getUrl(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }
        try {
            return FileStorageFactory.getInstance().getFileStorage("software").getUrl(filePath, FileStorage.URLMode.HTTPS);
        } catch (FileStorageException e) {
            return "";
        }
    }

    private long getServerVersionId(long companyId) throws TException {
        ReqServiceMaintenanceOption option = new ReqServiceMaintenanceOption();
        option.addToCompanyIds(companyId);
        ServiceMaintenancePage page = new CompanyServiceMaintenanceDaoStub().reqServiceMaintenance(option, null);
        if (page.getPageSize() == 0) {
            throw new ErrorInfo(ProxyErrorCode.ERROR_COMPANY_SERVICE_MAINTENANCE_NOT_FOUND.getValue(), "Company service maintenance info not found.");
        }

        ServiceMaintenance serviceMaintenance = page.getPage().get(0);
        if (serviceMaintenance.isKeepLatest()) {
            return 0;
        } else {
            return serviceMaintenance.getVersionId();
        }
    }

    private long getAppId(UpdateInfoReq req) throws TException {
        AppManagerDaoStub stub = new AppManagerDaoStub();
        List<App> apps = stub.reqApp(new ReqAppOption().setAppKey(req.getAppKey()));
        if (apps.size() == 0) {
            throw new ErrorInfo(ProxyErrorCode.ERROR_APP_NOT_FOUND.getValue(), "App info not found.");
        }
        return apps.get(0).getAppId();
    }

    private ServerAppSupport getServerAppSupport(long serverVersionId, long appId) throws TException {
        AppManagerDaoStub stub = new AppManagerDaoStub();
        ReqServerAppSupportOption serverSupportOption = new ReqServerAppSupportOption();
        serverSupportOption.addToServerVersionIds(serverVersionId);
        serverSupportOption.setSupportClientAppId(appId);
        List<ServerAppSupport> supports = stub.reqServerAppSupport(serverSupportOption);
        if (supports.size() == 0) {
            throw new ErrorInfo(ProxyErrorCode.ERROR_APP_NOT_SUPPORT.getValue(), "App support not found.");
        }
        return supports.get(0);
    }
}
