package xueqiao.trade.hosting.cloud.ao.server.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.Md5;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.HostingInfo;
import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.HostingUserState;
import xueqiao.trade.hosting.QueryHostingUserOption;
import xueqiao.trade.hosting.QueryHostingUserPage;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.cloud.ao.HostingInitReq;
import xueqiao.trade.hosting.cloud.ao.HostingInitResp;
import xueqiao.trade.hosting.cloud.ao.HostingResetReq;
import xueqiao.trade.hosting.cloud.ao.LoginReq;
import xueqiao.trade.hosting.cloud.ao.LoginResp;
import xueqiao.trade.hosting.cloud.ao.RegisterHostingUserResp;
import xueqiao.trade.hosting.cloud.ao.server.TradeHostingCloudAoAdaptor;
import xueqiao.trade.hosting.entry.core.SessionValidation;
import xueqiao.trade.hosting.entry.core.TokenUtils;
import xueqiao.trade.hosting.entry.core.UserValidation;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.IHostingUserApi;
import xueqiao.trade.hosting.storage.apis.structs.HostingInitInfo;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;
import xueqiao.trade.hosting.terminal.ao.server.impl.handler.HostingUserHandler;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class TradeHostingCloudAoHandler extends TradeHostingCloudAoAdaptor {

    private final static File BUILD_VERSION_FILE = new File("/build_version");

    private IHostingManageApi mManageApi;
    private IHostingUserApi mUserApi;
    private IHostingSessionApi mSessionApi;

    @Override
    public int InitApp(Properties props) {
        mManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
        mUserApi = Globals.getInstance().queryInterfaceForSure(IHostingUserApi.class);
        mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);

        return 0;
    }

    @Override
    public void destroy() {
    }
    
    public void checkHostingAESKey(String hostingAES16Key) throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotBlank(hostingAES16Key)
                , "AES16Key should not be null or empty");
        ParameterChecker.check(16 == hostingAES16Key.length()
                , "AES16Key length should be 16");
    }

    @Override
    protected HostingInitResp initHosting(TServiceCntl oCntl, HostingInitReq req)
            throws ErrorInfo, TException {
        ParameterChecker.check(req != null, "req should not be null");
        ParameterChecker.check(req.getRunningMode() != null, "runningMode should not be null");
        checkHostingAESKey(req.getHostingAES16Key());
        if (req.isSetAdminLoginName()) {
            UserValidation.checkLoginName(req.getAdminLoginName());
            UserValidation.checkLoginPasswd(req.getAdminLoginPasswd());
        }

        IHostingManageApi manageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);

        HostingInitInfo info = new HostingInitInfo();
        info.setMachineId(req.getMachineId());
        info.setHostingAES16Key(req.getHostingAES16Key().trim());
        if (req.isSetAdminLoginName()) {
            info.setAdminLoginName(req.getAdminLoginName().trim());
            info.setAdminLoginPasswd(req.getAdminLoginPasswd().trim());
        }
        info.setRunningMode(req.getRunningMode());

        manageApi.initHosting(info);

        HostingInitResp resp = new HostingInitResp();
        resp.setInfo(getHostingInfo(oCntl));

        return resp;
    }

    @Override
    protected HostingInfo getHostingInfo(TServiceCntl oCntl)
            throws ErrorInfo, TException {
        HostingInfo hostingInfo =  mManageApi.getHostingInfo(true);
        if (BUILD_VERSION_FILE.exists() && BUILD_VERSION_FILE.isFile()) {
            try {
                hostingInfo.setBuildVersion(
                        FileUtils.readFileToString(BUILD_VERSION_FILE, "UTF-8").trim());
            } catch (Throwable e) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                        , "Failed to read buildVersion file");
            }
        } else {
            hostingInfo.setBuildVersion("");
        }
        return hostingInfo;
    }

    @Override
    protected void resetHosting(TServiceCntl oCntl, HostingResetReq req) throws ErrorInfo, TException {
        ParameterChecker.check(req != null, "req should not be null");
        ParameterChecker.check(req.getMachineId() > 0, "req's machineId should > 0");
        checkHostingAESKey(req.getHostingAES16Key());
        mManageApi.resetHosting(req.getMachineId(), req.getHostingAES16Key());
    }

    private void checkMachineId(long machineId) throws ErrorInfo {
        if (machineId != mManageApi.getMachineId()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.MACHINEID_ERROR.getValue()
                    , "companyId is not equals to hosting effective companyId");
        }
    }

    @Override
    protected LoginResp login(TServiceCntl oCntl, LoginReq req)
            throws ErrorInfo, TException {
        ParameterChecker.check(req != null, "req should not be null");
        checkMachineId(req.getMachineId());
        UserValidation.checkLoginName(req.getLoginUserName());
        SessionValidation.checkLoginPasswordMd5(req.getLoginPasswordMd5());

        QueryUserOption option = new QueryUserOption();
        option.setLoginNameWhole(req.getLoginUserName());

        PageResult<HostingUser> userList = mUserApi.queryUserPage(option, new PageOption().setPageSize(1));
        if (userList.getPageList().isEmpty()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_NOT_EXISTED.getValue()
                    , req.getLoginUserName() + " is not existed!");
        }
        HostingUser loginUser = userList.getPageList().get(0);
        if (loginUser.getUserState() != HostingUserState.USER_NORMAL) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_DISABLED.getValue()
                    , "user login forbidden");
        }
        if (!req.getLoginPasswordMd5().trim().equalsIgnoreCase(Md5.toMD5(loginUser.getLoginPasswd()))) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_USER_PASSWORD.getValue()
                    , "password error!");
        }

        HostingSession session = new HostingSession();
        session.setMachineId(req.getMachineId());
        session.setSubUserId(loginUser.getSubUserId());
        session.setLoginIP(oCntl.getPlatformArgs().getXForwardAddress());
        session.setToken(TokenUtils.generateToken());

        SessionEntry entry = mSessionApi.getSession(loginUser.getSubUserId());
        if (entry == null) {
            mSessionApi.insertSession(session);
        } else {
            mSessionApi.updateSession(session);
        }

        LoginResp resp = new LoginResp();
        resp.setSession(session);
        resp.setHostingProxyPort(8069);
        // Java目前无法准确的知道系统的ns时钟，先大致近似
        resp.setHostingTimens(System.currentTimeMillis() *1000000 + (RandomUtils.nextLong()%1000000));
        resp.setRunningMode(mManageApi.getRunningMode());
        loginUser.unsetLoginPasswd();
        resp.setLoginUserInfo(loginUser);

        return resp;
    }

    @Override
    protected boolean checkSession(TServiceCntl oCntl, HostingSession session) throws ErrorInfo, TException {
        SessionValidation.checkHostingSession(session);
        checkMachineId(session.getMachineId());

        SessionEntry sEntry = mSessionApi.getSession(session.getSubUserId());
        if (sEntry == null || !session.getToken().equals(sEntry.getToken())) {
            return false;
        }
        return true;
    }

    @Override
    protected RegisterHostingUserResp registerHostingUser(TServiceCntl oCntl, HostingUser newUser)
            throws ErrorInfo, TException {
        return new HostingUserHandler(oCntl, null).registerHostingUser(newUser);
    }

    @Override
    protected void updateHostingUser(TServiceCntl oCntl, HostingUser updateUser) throws ErrorInfo, TException {
        new HostingUserHandler(oCntl, null).updateHostingUser(updateUser);
    }

    @Override
    protected void disableHostingUser(TServiceCntl oCntl, int subUserId) throws ErrorInfo, TException {
        new HostingUserHandler(oCntl, null).disableHostingUser(subUserId);
    }

    @Override
    protected QueryHostingUserPage getHostingUserPage(TServiceCntl oCntl
            , QueryHostingUserOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        return new HostingUserHandler(oCntl, null).getHostingUserPage(queryOption, pageOption);
    }

    @Override
    protected void enableHostingUser(TServiceCntl oCntl, int subUserId) throws ErrorInfo, TException {
        new HostingUserHandler(oCntl, null).enableHostingUser(subUserId);
    }

}
