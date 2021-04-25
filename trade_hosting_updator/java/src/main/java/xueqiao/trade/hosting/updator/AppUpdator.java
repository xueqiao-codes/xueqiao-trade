package xueqiao.trade.hosting.updator;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;

import com.openshift.internal.restclient.model.DeploymentConfig;
import com.openshift.internal.restclient.model.ImageStream;
import com.openshift.internal.restclient.model.deploy.ImageChangeTrigger;
import com.openshift.internal.util.JBossDmrExtentions;
import com.openshift.restclient.images.DockerImageURI;
import com.openshift.restclient.model.IContainer;
import com.openshift.restclient.model.deploy.IDeploymentTrigger;

import xueqiao.company.service.maintenance.MaintenanceTimeSpan;
import xueqiao.company.service.maintenance.ServiceMaintenance;
import xueqiao.company.service.maintenance.dao.thriftapi.ReqServiceMaintenanceOption;
import xueqiao.company.service.maintenance.dao.thriftapi.ServiceMaintenancePage;
import xueqiao.company.service.maintenance.dao.thriftapi.client.CompanyServiceMaintenanceDaoStub;
import xueqiao.hosting.machine.HostingRelatedInfo;
import xueqiao.hosting.machine.HostingRelatedInfoPageResult;
import xueqiao.hosting.machine.QueryHostingRelatedInfoOption;
import xueqiao.hosting.machine.dao.client.HostingMachineDaoStub;
import xueqiao.trade.hosting.HostingInfo;
import xueqiao.trade.hosting.HostingStatus;
import xueqiao.trade.hosting.cloud.ao.client.TradeHostingCloudAoStub;

public class AppUpdator {
    private final static String HOSTING_DC_PREFIX = "trade-";
    private final static String THA_CONTAINER_NAME = "trade-hosting-apps";
    private final static String FTP_CONTAINER_NAME = "fast-thrift-proxy";
    private final static String ECA_CONTAINER_NAME = "errorcode-agent";
    
    private OCManager mOcManager;
    private IConfProvider mConfProvider;
    
    public AppUpdator(OCManager ocManager, IConfProvider confProvider) {
        this.mOcManager = ocManager;
        this.mConfProvider = confProvider;
    }
    
    private ImageStream getImageStream() {
        return mOcManager.getClient().get("ImageStream"
                , mConfProvider.getTradeHostingAppsISName()
                , mConfProvider.getTradeHostingAppsISNamespace());
    }
    
    public void runOnce() {
        ImageStream is = getImageStream();
        if (is == null) {
            AppLog.i("ImageStream is not exist!!!");
            return ;
        }
        
        Map<String, DeploymentConfig> dcList = mOcManager.getAllDC();
        for(DeploymentConfig dc : dcList.values()) {
            try {
                AppLog.i("checkUpdate for DeploymentConfig " + dc.getName() + " start");
                checkUpdate(dc, is);
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            } finally {
                AppLog.i("checkUpdate for DeploymentConfig " + dc.getName() + " end");
            }
        }
    }
    
    private boolean isInMtSpans(Set<MaintenanceTimeSpan> mtSpans) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        cal.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss");
        sdf.setCalendar(cal);
        
        AppLog.i("isInMtSpans for currentTime in New_York is " + sdf.format(cal.getTime()));
        for (MaintenanceTimeSpan mtSpan : mtSpans) {
            int currentHour = cal.get(Calendar.HOUR_OF_DAY);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (mtSpan == MaintenanceTimeSpan.WORKING_DAY) {
                // 纽约时间周日， 周一，周二，周三，周四 17:00-18:00
                if (dayOfWeek == Calendar.FRIDAY || dayOfWeek == Calendar.SATURDAY) {
                    // 纽约时间周五和周六没有工作日维护时间段
                    continue;
                }
                if (currentHour < 17 || currentHour >= 18) {
                    // 不在时间段
                    continue;
                }
                
                return true;
            } else if (mtSpan == MaintenanceTimeSpan.WEEKEND) {
                // 星期五17:00-周日11:00
                if (dayOfWeek == Calendar.FRIDAY) {
                    if (currentHour >= 17) {
                        return true;
                    }
                } else if (dayOfWeek == Calendar.SATURDAY) {
                    return true;
                } else if (dayOfWeek == Calendar.SUNDAY) {
                    if (currentHour <= 11) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void checkUpdate(DeploymentConfig dc, ImageStream is) throws Exception {
        UpdateChecker checker = new UpdateChecker(dc, is);
        checker.check();
        
        // 执行更新检查动作
        UpdateAction action = checker.getResult();
        AppLog.i("DeploymentConfig " + dc.getName() + ", " + action);
        if (action.getType() == UpdateAction.ActionType.NO_ACTION) {
            return ;
        }
        
        if (!action.isRollback() && mConfProvider.isNeedCheckMaintenanceTime()) {
            // 检查维护时间
            Set<MaintenanceTimeSpan> mtSpans = checker.getSm().getMaintenanceTimeSpans();
            if (!isInMtSpans(mtSpans)) {
                AppLog.i("Not in maintenance timespans");
                return ;
            }
        }
        
        // 更新镜像
        if (action.getThaImage() != null) {
            dc.getContainer(THA_CONTAINER_NAME).setImage(new DockerImageURI(action.getThaImage()));
        }
        if (action.getFtpImage() != null) {
            dc.getContainer(FTP_CONTAINER_NAME).setImage(new DockerImageURI(action.getFtpImage()));
        }
        if (action.getEcaImage() != null) {
            dc.getContainer(ECA_CONTAINER_NAME).setImage(new DockerImageURI(action.getEcaImage()));
        }
        mOcManager.updateDC(dc);
    }
    
    
    private static class UpdateAction {
        public static enum ActionType {
            NO_ACTION,
            DO_UPDATE,
        }
        
        private ActionType type = ActionType.NO_ACTION;
        private boolean isRollback = false;
        private String thaImage;
        private String ftpImage;
        private String ecaImage;
        
        public ActionType getType() {
            return type;
        }
        public void setType(ActionType type) {
            this.type = type;
        }
        
        public String getThaImage() {
            return thaImage;
        }
        public void setThaImage(String thaImage) {
            this.thaImage = thaImage;
        }
        
        public String getFtpImage() {
            return ftpImage;
        }
        public void setFtpImage(String ftpImage) {
            this.ftpImage = ftpImage;
        }
        
        public String getEcaImage() {
            return ecaImage;
        }
        public void setEcaImage(String ecaImage) {
            this.ecaImage = ecaImage;
        }
        
        public boolean isRollback() {
            return isRollback;
        }
        public void setRollback(boolean isRollback) {
            this.isRollback = isRollback;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("UpdateAction(")
              .append("type=").append(type)
              .append(", isRollback=").append(isRollback)
              .append(", thaImage=").append(thaImage)
              .append(", ftpImage=").append(ftpImage)
              .append(", ecaImage=").append(ecaImage)
              .append(")");
            return sb.toString();
        }
    }
    
    private class UpdateChecker {
        private DeploymentConfig dc;
        private ImageStream is;
        
        private ServiceMaintenance sm;
        private UpdateAction result = new UpdateAction();
        
        public UpdateChecker(DeploymentConfig dc, ImageStream is) {
            this.dc = dc;
            this.is = is;
        }
        
        public void check() throws Exception{
            if (!dc.getName().startsWith("trade-")) {
                return ;
            }
            
            checkAppsUpdated();
            checkFastThriftProxyUpdated();
            checkErrorcodeAgentUpdated();
        }
        
        public UpdateAction getResult() {
            return result;
        }
        
        public ServiceMaintenance getSm() {
            return sm;
        }
        
        private String getCreateTimestampStr(String tagName) {
            ModelNode tags = JBossDmrExtentions.get(is.getNode(), is.getPropertyKeys(), "status.tags");
            if (tags.getType() != ModelType.LIST || tagName == null) {
                return null;
            }

            List<ModelNode> tagWrappers = tags.asList();
            for (ModelNode tagWrapper : tagWrappers) {
                ModelNode tag = tagWrapper.get("tag");
                ModelNode items = tagWrapper.get("items");
                if (tag.asString().equals(tagName) && items.getType() == ModelType.LIST) {
                    for (ModelNode itemWrapper : items.asList()) {
                        ModelNode created = itemWrapper.get("created");
                        if (created != null) {
                            return created.asString();
                        }
                    }
                    break;
                }
            }
            return null;
        }
        
        private long getCreateTimestamp(String tagName) {
            String createTimeStr = getCreateTimestampStr(tagName);
            if (StringUtils.isEmpty(createTimeStr)) {
                return -1;
            }
            return ZonedDateTime.parse(createTimeStr).toEpochSecond();
        }
        
        private void checkAppsUpdated() throws Exception {
            String hostName = dc.getName().substring(HOSTING_DC_PREFIX.length());
            TradeHostingCloudAoStub cloudAoStub = new TradeHostingCloudAoStub();
            cloudAoStub.setPeerAddr(hostName + ".xueqiao.host");
            
            // 检查托管机状态
            HostingInfo hInfo = cloudAoStub.getHostingInfo();
            AppLog.i("HostingInfo for " + hostName + " is " + hInfo);
            if (hInfo.getStatus() != HostingStatus.NORMAL) {
                return ;
            }
            
            // 查看托管机关联公司信息
            HostingMachineDaoStub hmDaoStub = new HostingMachineDaoStub();
            HostingRelatedInfoPageResult relatedInfoPage 
                = hmDaoStub.queryRelatedInfoPage(new QueryHostingRelatedInfoOption().setMachineId(hInfo.getMachineId())
                    , new IndexedPageOption().setPageIndex(0).setPageSize(10));
            if (relatedInfoPage.getResultListSize() <= 0) {
                AppLog.w("No RelatedInfo found for machineId=" + hInfo.getMachineId() + ", found by " + hostName);
                return ;
            }
            if (relatedInfoPage.getResultListSize() > 1) {
                AppLog.w("More than one relatedInfo found for machineId=" + hInfo.getMachineId());
                return ;
            }
            
            HostingRelatedInfo relatedInfo = relatedInfoPage.getResultList().get(0);
            if (relatedInfo.getMachineId() != hInfo.getMachineId()) {
                AppLog.w("Found RelatedInfo But machineId=" + relatedInfo.getMachineId() 
                    + " for result is not equals to parameter machineId=" + hInfo.getMachineId()
                    + ", HostingMachineDaoStub has error!!!");
                return ;
            }
            
            AppLog.i("Related CompanyId=" + relatedInfo.getCompanyId() + " for " + hostName);
            
            // 查找关联公司部署的版本
            CompanyServiceMaintenanceDaoStub csmDaoStub = new CompanyServiceMaintenanceDaoStub();
            ReqServiceMaintenanceOption qryOption = new ReqServiceMaintenanceOption();
            qryOption.addToCompanyIds(relatedInfo.getCompanyId());
            ServiceMaintenancePage csmPage 
                = csmDaoStub.reqServiceMaintenance(qryOption, new IndexedPageOption().setPageIndex(0).setPageSize(10));
            if (csmPage.getPageSize() <= 0) {
                AppLog.w("No ServiceMaintenance found for companyId=" + relatedInfo.getCompanyId());
                return ;
            }
            if (csmPage.getPageSize() > 1) {
                AppLog.w("More than one ServiceMaintenance info found for companyId=" + relatedInfo.getCompanyId());
                return ;
            }
            
            sm = csmPage.getPage().get(0);
            if (sm.getCompanyId() != relatedInfo.getCompanyId()) {
                AppLog.w("Found ServiceMaintenance companyId=" + sm.getCompanyId() 
                    + " for result is not equals to parameter companyId=" + relatedInfo.getCompanyId()
                    + ", CompanyServiceMaintenanceDaoStub has error!!!");
                return ;
            }
            
            AppLog.i("Related company version tag is " + sm.getVersionTag() + " for " + hostName);
            String versionImageId = is.getImageId(sm.getVersionTag());
            if (StringUtils.isEmpty(versionImageId)) {
                AppLog.w("NO VersionImageId found, versionTag="
                            + sm.getVersionId() + " for ImageStream=" + mConfProvider.getTradeHostingAppsISName());
                return ;
            }
            
            IContainer container = dc.getContainer(THA_CONTAINER_NAME);
            String containerImageTag = container.getImage().getNameAndTag();
            
            String compareNameAndTag = null;
            String originTag = null;
            if (containerImageTag.startsWith(mConfProvider.getTradeHostingAppsISName() + "@sha")) {
                compareNameAndTag = mConfProvider.getTradeHostingAppsISName() + "@" + versionImageId;
                // 找到对应的Tag
                for (String tagName : is.getTagNames()) {
                    String tagImageId = is.getImageId(tagName);
                    if (containerImageTag.endsWith(tagImageId)) {
                        originTag = tagName;
                        break;
                    }
                }
            } else {
                originTag = container.getImage().getTag();
                compareNameAndTag = mConfProvider.getTradeHostingAppsISName() + ":" + sm.getVersionTag();
            }
            
            AppLog.i("Update check, originTag=" + originTag + " containerImageTag=" + containerImageTag
                    + ", versionTag=" + sm.getVersionTag() + " compareNameAndTag=" + compareNameAndTag);
            if (containerImageTag.equalsIgnoreCase(compareNameAndTag)) {
                AppLog.i("No need update apps for " + hostName + ", image not changed");
                return ;
            }
            
            // 检查是否存在ImageTrigger， 否则修改也无意义
            for (IDeploymentTrigger dt : dc.getTriggers()) {
                if (!"ImageChange".equalsIgnoreCase(dt.getType())) {
                    continue;
                }
                
                ImageChangeTrigger trigger = ImageChangeTrigger.class.cast(dt);
                if (trigger.isAutomatic() && trigger.getContainerNames().contains(THA_CONTAINER_NAME)) {
                    AppLog.i("No need update apps for " + hostName + ", trigger exist!");
                    return ;
                }
            }
            
            // 检查是回退还是升级
            long originImageCreateTimestamp = getCreateTimestamp(originTag);
            long versionImageCreateTimestamp = getCreateTimestamp(sm.getVersionTag());
            if (originImageCreateTimestamp > 0 
                    && versionImageCreateTimestamp > 0
                    && versionImageCreateTimestamp < originImageCreateTimestamp) {
                result.setRollback(true);
            }
            
            result.setType(UpdateAction.ActionType.DO_UPDATE);
            result.setThaImage("docker-registry.default.svc:5000/" 
                            + mConfProvider.getTradeHostingAppsISNamespace()
                            + "/" + mConfProvider.getTradeHostingAppsISName()
                            + ":" + sm.getVersionTag());
        }
        
        private void checkFastThriftProxyUpdated() throws Exception {
            String ftpImageConf = mConfProvider.getFastThriftProxyImage();
            if (StringUtils.isEmpty(ftpImageConf)) {
                return ;
            }
            IContainer container = dc.getContainer(FTP_CONTAINER_NAME);
            if (container == null) {
                return ;
            }
            if (ftpImageConf.equalsIgnoreCase(container.getImage().toString())) {
                return ;
            }
            result.setFtpImage(ftpImageConf);
            result.setType(UpdateAction.ActionType.DO_UPDATE);
        }
        
        private void checkErrorcodeAgentUpdated() throws Exception {
            String ecaImageConf = mConfProvider.getErrorCodeAgentImage();
            if (StringUtils.isEmpty(ecaImageConf)) {
                return ;
            }
            IContainer container = dc.getContainer(ECA_CONTAINER_NAME);
            if (container == null) {
                return ;
            }
            if (ecaImageConf.equalsIgnoreCase(container.getImage().toString())) {
                return ;
            }
            result.setEcaImage(ecaImageConf);
            result.setType(UpdateAction.ActionType.DO_UPDATE);
        }
    }
    
}
