package xueqiao.trade.hosting.proxy.server.impl;

import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import xueqiao.company.*;
import xueqiao.company.dao.client.CompanyDaoStub;
import xueqiao.hosting.machine.HostingMachine;
import xueqiao.hosting.machine.HostingMachinePageResult;
import xueqiao.hosting.machine.HostingRelatedInfo;
import xueqiao.hosting.machine.HostingRelatedInfoPageResult;
import xueqiao.hosting.machine.QueryHostingMachineOption;
import xueqiao.hosting.machine.QueryHostingRelatedInfoOption;
import xueqiao.hosting.machine.dao.client.HostingMachineDaoStub;
import xueqiao.mailbox.user.message.thriftapi.UserMessagePage;
import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.TradeHostingErrorCode;
import xueqiao.trade.hosting.cloud.ao.client.TradeHostingCloudAoStub;
import xueqiao.trade.hosting.proxy.*;
import xueqiao.trade.hosting.proxy.handler.CompanyUserHandler;
import xueqiao.trade.hosting.proxy.handler.MailBoxHandler;
import xueqiao.trade.hosting.proxy.handler.UpdateInfoHandler;
import xueqiao.trade.hosting.proxy.handler.VerifyCodeHandler;
import xueqiao.trade.hosting.proxy.server.TradeHostingProxyAdaptor;

public class TradeHostingProxyHandler extends TradeHostingProxyAdaptor {

    @Override
    public int InitApp(Properties props) {
        return 0;
    }

    @Override
    public void destroy() {
    }

    @Override
    protected ProxyLoginResp login(TServiceCntl oCntl, ProxyLoginReq req) throws ErrorInfo, TException {
        if (req == null) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "req should not be null");
        }
        if (StringUtils.isBlank(req.getCompanyCode())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "req's companyCode should not be blank");
        }
        if (StringUtils.isBlank(req.getUserName())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "req's userName should not be blank");
        }
        if (StringUtils.isBlank(req.getPasswordMd5()) && StringUtils.isBlank(req.getVerifyCode())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "req's passwordMd5 or Verify code should not be blank");
        }
        if (StringUtils.isBlank(req.getCompanyGroupCode())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "req's companyGroupCode should not be blank");
        }


        CompanyDaoStub companyStub = new CompanyDaoStub();
        HostingMachineDaoStub machineStub = new HostingMachineDaoStub();

        try {
            CompanyEntry companyEntry = getCompanyEntry(req, companyStub);
            String loginPasswordMd5 = getLoginPasswordMd5(req, companyStub, companyEntry);
            CompanyGroup loginCompanyGroup = getCompanyGroup(req, companyStub, companyEntry);
            HostingRelatedInfo relatedInfo = getHostingRelatedInfo(machineStub, loginCompanyGroup);
            String loginUserName = req.getUserName().trim();

            xueqiao.trade.hosting.cloud.ao.LoginReq cloudLoginReq = new xueqiao.trade.hosting.cloud.ao.LoginReq();
            cloudLoginReq.setMachineId(relatedInfo.getMachineId());
            cloudLoginReq.setLoginUserName(loginUserName);
            cloudLoginReq.setLoginPasswordMd5(loginPasswordMd5.trim());

            TradeHostingCloudAoStub cloudAoStub = new TradeHostingCloudAoStub();
            cloudAoStub.setPeerAddr(relatedInfo.getMachineInnerIP());
            xueqiao.trade.hosting.cloud.ao.LoginResp cloudLoginResp
                    = cloudAoStub.login((int) relatedInfo.getMachineId(), 2000, cloudLoginReq);

            ProxyLoginResp resultResp = new ProxyLoginResp();
            resultResp.setHostingServerIP(relatedInfo.getMachineOuterIP());
            resultResp.setHostingProxyPort(cloudLoginResp.getHostingProxyPort());
            resultResp.setHostingTimens(cloudLoginResp.getHostingTimens());
            resultResp.setHostingSession(cloudLoginResp.getSession());
            resultResp.setHostingRunningMode(cloudLoginResp.getRunningMode());
            resultResp.setCompanyId(loginCompanyGroup.getCompanyId());
            resultResp.setCompanyGroupId(loginCompanyGroup.getGroupId());
            resultResp.setLoginUserInfo(cloudLoginResp.getLoginUserInfo());

            return resultResp;
        } catch (ErrorInfo ei) {
            throw ei;
        } catch (Throwable et) {
            AppLog.e(et.getMessage(), et);
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server busy!");
        }
    }

    private HostingRelatedInfo getHostingRelatedInfo(HostingMachineDaoStub machineStub, CompanyGroup loginCompanyGroup) throws TException {
        HostingRelatedInfoPageResult relatedResult = machineStub.queryRelatedInfoPage(RandomUtils.nextInt(), 2000
                , new QueryHostingRelatedInfoOption()
                        .setCompanyId(loginCompanyGroup.getCompanyId())
                        .setCompanyGroupId(loginCompanyGroup.getGroupId())
                , new IndexedPageOption().setPageIndex(0).setPageSize(1));
        if (relatedResult.getResultListSize() <= 0) {
            throw new ErrorInfo(TradeHostingErrorCode.HOSTING_MACHINE_NOT_FOUND.getValue()
                    , "company group has no related info, no hosting machine");
        }

        return relatedResult.getResultList().get(0);
    }

    private CompanyGroup getCompanyGroup(ProxyLoginReq req, CompanyDaoStub companyStub, CompanyEntry companyEntry) throws TException {
        CompanyGroupPageResult groupResult = companyStub.queryCompanyGroupPage(RandomUtils.nextInt(), 2000
                , new QueryCompanyGroupOption()
                        .setCompanyId(companyEntry.getCompanyId())
                        .setGroupCodeWhole(req.getCompanyGroupCode().trim())
                , new IndexedPageOption().setPageIndex(0).setPageSize(1));
        if (groupResult.getResultListSize() <= 0) {
            throw new ErrorInfo(TradeHostingErrorCode.HOSTING_MACHINE_NOT_FOUND.getValue()
                    , "company group is not existed, no hosting machine");
        }
        return groupResult.getResultList().get(0);
    }

    private CompanyEntry getCompanyEntry(ProxyLoginReq req, CompanyDaoStub companyStub) throws TException {
        CompanyPageResult companyResults = companyStub.queryCompanyPage(RandomUtils.nextInt(), 2000
                , new QueryCompanyOption().setCompanyCodeWhole(req.getCompanyCode().trim())
                , new IndexedPageOption().setPageIndex(0).setPageSize(1));
        if (companyResults.getResultListSize() <= 0) {
            throw new ErrorInfo(TradeHostingErrorCode.HOSTING_MACHINE_NOT_FOUND.getValue()
                    , "company is not existed, no hosting machine");
        }
        return companyResults.getResultList().get(0);
    }

    private String getLoginPasswordMd5(ProxyLoginReq req, CompanyDaoStub companyStub, CompanyEntry companyEntry) throws TException {
        // query company user
        QueryCompanyUserOption option = new QueryCompanyUserOption();
        option.setCompanyId(companyEntry.getCompanyId());
        option.setUserName(req.getUserName());
        IndexedPageOption pageOption = new IndexedPageOption();
        pageOption.setPageSize(1);
        pageOption.setPageIndex(0);
        CompanyUserPage companyUserPage = companyStub.queryCompanyUser(option, pageOption);
        if (companyUserPage == null || companyUserPage.getPageSize() <= 0) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue(), "companyUser is not exist.");
        }
        CompanyUser companyUser = companyUserPage.getPage().get(0);

        /*
         * verify password
         * 直接获取回来的就已经是md5
         * */
//        String passwdMD5 = MD5Util.crypto(companyUser.getPassword());
        AppLog.i("CompanyUserHandler ---- checkCompanyUser ---- companyUser.getPassword() : " + companyUser.getPassword() + ", req.getPasswordMd5() : " + req.getPasswordMd5());
        if (StringUtils.isNotBlank(req.getPasswordMd5()) && !req.getPasswordMd5().equals(companyUser.getPassword())) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_PASSWORD.getValue(), "password error");
        } else if (StringUtils.isBlank(req.getPasswordMd5())
                && StringUtils.isNotBlank(req.getVerifyCode())
                && new VerifyCodeHandler().isInvalidate(req.getUserName(), req.getVerifyCode())) {
            throw new ErrorInfo(ProxyErrorCode.ERROR_VERIFY_CODE.getValue(), "verify code error");
        }

        return companyUser.getPassword();
    }

    @Override
    protected ProxyFakeLoginResp fakeLogin(TServiceCntl oCntl, ProxyFakeLoginReq req) throws ErrorInfo, TException {
        CompanyUserHandler handler = new CompanyUserHandler();
        handler.checkParam(req);
        handler.checkCompanyUser(req);
        return handler.queryComapanyGroupsByGivenUser();
    }

    @Override
    protected void updateCompanyUserPassword(TServiceCntl oCntl, ProxyUpdatePasswordReq req) throws ErrorInfo, TException {
        if (req == null) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue(), "req should not be null");
        }
        if (req.isSetCompanyId() && req.getCompanyId() < 1) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue(), "companyId invalid");
        }
        if (StringUtils.isBlank(req.getUserName())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue(), "userName should not be blank");
        }
        if (StringUtils.isBlank(req.getOldPassword())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue(), "oldPassword should not be blank");
        }
        if (StringUtils.isBlank(req.getNewPassword())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue(), "newPassword should not be blank");
        }
        new CompanyUserHandler().updateCompanyUserPassword(req);
    }

    @Override
    protected boolean checkSession(TServiceCntl oCntl, HostingSession session) throws ErrorInfo, TException {
        if (session == null) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "session should not be null");
        }
        if (session.getMachineId() <= 0) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "session's companyId should not <= 0");
        }
        if (session.getSubUserId() <= 0) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "session's subUserId should not <= 0");
        }
        if (StringUtils.isEmpty(session.getToken())) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "session's token should not be empty");
        }

        try {
            HostingMachinePageResult machineResult
                    = new HostingMachineDaoStub().queryHostingMachinePage((int) session.getMachineId(), 2000
                    , new QueryHostingMachineOption().setMachineId(session.getMachineId())
                    , new IndexedPageOption().setPageIndex(0).setPageSize(1));
            if (machineResult.getResultListSize() <= 0) {
                return false;
            }

            HostingMachine checkMachine = machineResult.getResultList().get(0);

            TradeHostingCloudAoStub cloudAoStub = new TradeHostingCloudAoStub();
            cloudAoStub.setPeerAddr(checkMachine.getMachineInnerIP());
            return cloudAoStub.checkSession((int) checkMachine.getMachineId(), 2000, session);
        } catch (ErrorInfo ei) {
            throw ei;
        } catch (Throwable et) {
            AppLog.e(et.getMessage(), et);
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server busy!");

        }
    }

    @Override
    protected AppVersion queryUpdateInfo(TServiceCntl oCntl, UpdateInfoReq req) throws ErrorInfo, TException {
        return new UpdateInfoHandler().reqUpdateInfo(req);
    }

    @Override
    protected UserMessagePage queryMailBoxMessage(TServiceCntl oCntl, HostingSession session, ReqMailBoxMessageOption option, IndexedPageOption pageOption) throws ErrorInfo, TException {
        if (pageOption == null) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "pageOption must set.");
        }
        if (pageOption.getPageSize() <= 0 || pageOption.getPageSize() > 50) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "PageSize must 1-50.");
        }
        if (pageOption.getPageIndex() < 0) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_PARAMETER.getValue()
                    , "PageIndex must >=0.");
        }
        validateSession(oCntl, session);
        return new MailBoxHandler().queryMailBoxMessage(session.getMachineId(), session.getSubUserId(), option, pageOption);
    }

    private void validateSession(TServiceCntl oCntl, HostingSession session) throws TException {
        boolean validate = this.checkSession(oCntl, session);
        if (!validate) {
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_USER_SESSION.getValue(), "Session error");
        }
    }

    @Override
    protected boolean markMessageAsRead(TServiceCntl oCntl, HostingSession session, Set<Long> hostingMessageIds) throws ErrorInfo, TException {
        validateSession(oCntl, session);
        return new MailBoxHandler().markMessageAsRead(session.getMachineId(), session.getSubUserId(), hostingMessageIds);
    }
}
