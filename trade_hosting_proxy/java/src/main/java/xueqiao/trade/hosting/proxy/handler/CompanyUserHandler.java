package xueqiao.trade.hosting.proxy.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.company.*;
import xueqiao.company.dao.client.CompanyDaoStub;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.TradeHostingErrorCode;
import xueqiao.trade.hosting.proxy.*;
import xueqiao.trade.hosting.proxy.HostingServiceStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyUserHandler {

    private CompanyDaoStub companyDaoStub;
    private int companyId;
    private int userId;
    private ProxyFakeLoginReq proxyFakeLoginReq;
    private String companyCode;

    public CompanyUserHandler() {
        companyDaoStub = new CompanyDaoStub();
    }

    public void checkParam(ProxyFakeLoginReq req) throws ErrorInfo {
        if (req == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "req should not be null");
        }
        if (StringUtils.isBlank(req.getUserName())) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "userName should not be blank");
        }
        if (!req.isSetLoginUserType() || LoginUserType.NORMAL_COMPANY_USER.equals(req.getLoginUserType())) {
            if (StringUtils.isBlank(req.getCompanyCode())) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "companyCode should not be blank");
            }
            if (StringUtils.isBlank(req.getPasswordMd5())) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "passwordMd5 should not be blank");
            }
        } else if (LoginUserType.XQ_COMPANY_PERSONAL_USER.equals(req.getLoginUserType())) {
            if (StringUtils.isBlank(req.getPasswordMd5()) && StringUtils.isBlank(req.getVerifyCode())) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "passwordMd5 should not be blank");
            }
        } else {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue(), "LoginUserType error.");
        }
    }

    public void checkCompanyUser(ProxyFakeLoginReq req) throws TException {
        IndexedPageOption pageOption = new IndexedPageOption();
        pageOption.setPageIndex(0).setPageSize(1);

        // query company

        CompanyEntry companyEntry;
        QueryCompanyOption queryOption = new QueryCompanyOption();
        if (LoginUserType.NORMAL_COMPANY_USER.equals(req.getLoginUserType()) || !req.isSetLoginUserType()) {
            queryOption.setCompanyCodeWhole(req.getCompanyCode());

            CompanyPageResult companyPageResult = companyDaoStub.queryCompanyPage(queryOption, pageOption);

            if (companyPageResult == null || companyPageResult.getResultListSize() <= 0) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue(), "companycode is not exist.");
            }
            companyEntry = companyPageResult.getResultList().get(0);
        } else {
            companyEntry = companyDaoStub.getCollectiveCompany();
        }

        // query company user
        QueryCompanyUserOption option = new QueryCompanyUserOption();
        option.setCompanyId(companyEntry.getCompanyId());
        option.setUserName(req.getUserName());
        CompanyUserPage companyUserPage = companyDaoStub.queryCompanyUser(option, pageOption);
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
        } else if (StringUtils.isNotBlank(req.getVerifyCode()) && new VerifyCodeHandler().isInvalidate(req.getUserName(), req.getVerifyCode(), false)) {
            throw new ErrorInfo(ProxyErrorCode.ERROR_VERIFY_CODE.getValue(), "verify code error");
        }

        companyId = companyEntry.getCompanyId();
        userId = companyUser.getUserId();
        proxyFakeLoginReq = req;
        this.companyCode = companyEntry.getCompanyCode();
    }

    public ProxyFakeLoginResp queryComapanyGroupsByGivenUser() throws TException {
        IndexedPageOption pageOption = new IndexedPageOption();
        pageOption.setPageIndex(0).setPageSize(Integer.MAX_VALUE);

        ProxyFakeLoginResp proxyFakeLoginResp = new ProxyFakeLoginResp();
        List<ProxyCompanyGroup> companyGroups = new ArrayList<>();
        proxyFakeLoginResp.setCompanyGroups(companyGroups);

        // query company groups by companyId
        QueryCompanyGroupOption queryOption = new QueryCompanyGroupOption();
        queryOption.setCompanyId(companyId);
        CompanyGroupPageResult companyGroupPageResult = companyDaoStub.queryCompanyGroupPage(queryOption, pageOption);
        if (companyGroupPageResult == null || companyGroupPageResult.getResultListSize() < 1) {
            // company groups empty
            return proxyFakeLoginResp;
        }
        Map<Integer, CompanyGroup> groupMap = new HashMap<>();
        for (CompanyGroup companyGroup : companyGroupPageResult.getResultList()) {
            groupMap.put(companyGroup.getGroupId(), companyGroup);
        }

        // query company group spec
        QueryCompanyGroupSpecOption queryCompanyGroupSpecOption = new QueryCompanyGroupSpecOption();
        CompanyGroupSpecPage companyGroupSpecPage = companyDaoStub.queryCompanyGroupSpec(queryCompanyGroupSpecOption, pageOption);
        if (companyGroupSpecPage == null || companyGroupSpecPage.getPageSize() < 1) {
            // company groups empty
            return proxyFakeLoginResp;
        }
        Map<Integer, CompanyGroupSpec> groupSpecMap = new HashMap<>();
        for (CompanyGroupSpec companyGroupSpec : companyGroupSpecPage.getPage()) {
            groupSpecMap.put(companyGroupSpec.getGroupId(), companyGroupSpec);
        }

        // query group user
        QueryGroupUserOption option = new QueryGroupUserOption();
        option.setCompanyId(companyId);
        option.setUserId(userId);
        GroupUserPage groupUserPage = companyDaoStub.queryGroupUser(option, pageOption);
        if (groupUserPage == null || groupUserPage.getPageSize() <= 0) {
            // groupUsers empty
            return proxyFakeLoginResp;
        }

        CompanyGroup companyGroup = null;
        ProxyCompanyGroup proxyCompanyGroup = null;
        for (GroupUser groupUser : groupUserPage.getPage()) {
            proxyCompanyGroup = new ProxyCompanyGroup();
            CompanyGroupSpec companyGroupSpec = groupSpecMap.get(groupUser.getGroupId());

            if (proxyFakeLoginReq.isSetTradeType()) {
                if (proxyFakeLoginReq.getTradeType().name().equals(companyGroupSpec.getServiceType().name())) {
                    companyGroup = groupMap.get(groupUser.getGroupId());
                    proxyCompanyGroup.setCompanyId(companyGroup.getCompanyId());
                    proxyCompanyGroup.setGroupId(companyGroup.getGroupId());
                    proxyCompanyGroup.setGroupCode(companyGroup.getGroupCode());
                    proxyCompanyGroup.setGroupName(companyGroup.getGroupName());
                    proxyCompanyGroup.setStatus(HostingServiceStatus.valueOf(companyGroupSpec.getHostingServiceStatus().name()));
                    companyGroups.add(proxyCompanyGroup);
                }
            } else {
                companyGroup = groupMap.get(groupUser.getGroupId());
                proxyCompanyGroup.setCompanyId(companyGroup.getCompanyId());
                proxyCompanyGroup.setGroupId(companyGroup.getGroupId());
                proxyCompanyGroup.setGroupCode(companyGroup.getGroupCode());
                proxyCompanyGroup.setGroupName(companyGroup.getGroupName());
                proxyCompanyGroup.setStatus(HostingServiceStatus.valueOf(companyGroupSpec.getHostingServiceStatus().name()));
                companyGroups.add(proxyCompanyGroup);
            }
        }

        proxyFakeLoginResp.setCompanyCode(companyCode);
        return proxyFakeLoginResp;
    }

    public void updateCompanyUserPassword(ProxyUpdatePasswordReq req) throws ErrorInfo {
        try {
//            // verify user info
//            QueryCompanyUserOption option = new QueryCompanyUserOption();
////            option.addToUserId(req.getUserId());
//            option.setCompanyId(req.getCompanyId());
//            option.setUserName(req.getUserName());
////            option.setPassword(req.getOldPassword());
//            IndexedPageOption pageOption = new IndexedPageOption();
//            pageOption.setNeedTotalCount(false).setPageIndex(0).setPageSize(1);
//            CompanyUserPage companyUserPage = companyDaoStub.queryCompanyUser(option, pageOption);
//            if (companyUserPage == null || companyUserPage.getPageSize() < 1) {
//                throw new ErrorInfo(TradeHostingErrorCode.ERROR_USER_NOT_EXISTED.getValue(), "user not exist");
//            }
//            CompanyUser originalUser = companyUserPage.getPage().get(0);
//
//            // update company user password
//            CompanyUser companyUser = new CompanyUser();
//            companyUser.setCompanyId(originalUser.getCompanyId());
//            companyUser.setUserId(originalUser.getUserId());
//            companyUser.setPassword(req.getNewPassword());
//            companyDaoStub.updateCompanyUser(companyUser);
            UpdateCompanyUserPasswordReq updateCompanyUserPasswordReq = new UpdateCompanyUserPasswordReq();
            updateCompanyUserPasswordReq.setCompanyId(req.getCompanyId());
            updateCompanyUserPasswordReq.setUserName(req.getUserName());
            updateCompanyUserPasswordReq.setOldPassword(req.getOldPassword());
            updateCompanyUserPasswordReq.setNewPassword(req.getNewPassword());
            companyDaoStub.updateCompanyUserPassword(updateCompanyUserPasswordReq);
        } catch (ErrorInfo errorInfo) {
            AppLog.e("CompanyUserHandler ---- updateCompanyUserPassword ---- errorInfo", errorInfo);
            throw errorInfo;
        } catch (Throwable throwable) {
            AppLog.e("CompanyUserHandler ---- updateCompanyUserPassword ---- throwable", throwable);
            throw new ErrorInfo(TradeHostingErrorCode.ERROR_SERVER_INNER.getValue(), "Server busy");
        }
    }
}
