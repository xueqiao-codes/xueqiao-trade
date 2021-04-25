package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingUser;
import xueqiao.trade.hosting.QueryHostingUserOption;
import xueqiao.trade.hosting.QueryHostingUserPage;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.cloud.ao.RegisterHostingUserResp;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.entry.core.UserValidation;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.IHostingUserApi;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryUserOption;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.trade_hosting_terminal_aoConstants;

public class HostingUserHandler extends HandlerBase {

    private IHostingUserApi mUserApi;
    private IHostingSessionApi mSessionApi;
    private IHostingManageApi mManageApi;
    
    public HostingUserHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
        mUserApi = Globals.getInstance().queryInterfaceForSure(IHostingUserApi.class);
        mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
        mManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
    }
    
    public RegisterHostingUserResp registerHostingUser(HostingUser newUser) throws ErrorInfo {
        ParameterChecker.check(newUser != null, "newUser should not be null");
        
        HostingUser operateHostingUser = new HostingUser();
        UserValidation.checkLoginName(newUser.getLoginName());
        operateHostingUser.setLoginName(newUser.getLoginName().trim());
        
        UserValidation.checkLoginPasswd(newUser.getLoginPasswd());
        operateHostingUser.setLoginPasswd(newUser.getLoginPasswd().trim());
        
        if (newUser.isSetEmail()) {
            UserValidation.checkEmail(newUser.getEmail());
            operateHostingUser.setEmail(newUser.getEmail().trim());
        }
        if (newUser.isSetPhone()) {
            UserValidation.checkPhone(newUser.getPhone());
            operateHostingUser.setPhone(newUser.getPhone().trim());
        }
        if (newUser.isSetNickName()) {
            UserValidation.checkNickName(newUser.getNickName());
            operateHostingUser.setNickName(newUser.getNickName().trim());
        }
        
        if (!newUser.isSetUserRoleValue()) {
            operateHostingUser.setUserRoleValue((short)EHostingUserRole.TraderGroup.getValue());
        } else {
            UserValidation.checkNewUserRoleValue(newUser.getUserRoleValue());
            operateHostingUser.setUserRoleValue(newUser.getUserRoleValue());
        }
        
        int newOperateSubUserId = mUserApi.addUser(operateHostingUser);
        
        RegisterHostingUserResp resp = new RegisterHostingUserResp();
        resp.setMachineId(mManageApi.getMachineId());
        resp.setSubUserId(newOperateSubUserId);
        return resp;
    }
    
    public void updateHostingUser(HostingUser updateUser) throws ErrorInfo {
        ParameterChecker.check(updateUser.getSubUserId() > 0, "subUserId should > 0");
        
        HostingUser operateHostingUser = new HostingUser();
        operateHostingUser.setSubUserId(updateUser.getSubUserId());
        if (updateUser.isSetLoginPasswd()) {
            UserValidation.checkLoginPasswd(updateUser.getLoginPasswd());
            operateHostingUser.setLoginPasswd(updateUser.getLoginPasswd().trim());
        }
        if (updateUser.isSetEmail()) {
            UserValidation.checkEmail(updateUser.getEmail());
            operateHostingUser.setEmail(updateUser.getEmail().trim());
        }
        if (updateUser.isSetPhone()) {
            UserValidation.checkPhone(updateUser.getPhone());
            operateHostingUser.setPhone(updateUser.getPhone().trim());
        }
        if (updateUser.isSetNickName()) {
            UserValidation.checkNickName(updateUser.getNickName());
            operateHostingUser.setNickName(updateUser.getNickName().trim());
        }
        if (updateUser.isSetUserRoleValue()) {
            operateHostingUser.setUserRoleValue(updateUser.getUserRoleValue());
        }
        
        mUserApi.updateUser(operateHostingUser);
    }
    
    public void disableHostingUser(int subUserId) throws ErrorInfo {
        ParameterChecker.check(subUserId > 0, "subUserId should > 0");
        
        if (getSubUserId() == subUserId) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.USER_FORBIDDEN_ERROR.getValue()
                    , "forbidden to disable user self");
        }
        
        mUserApi.disableUser(subUserId);
        try {
            mSessionApi.deleteSession(subUserId);
        } catch (Throwable e) {
        }
    }
    
    public void enableHostingUser(int subUserId) throws ErrorInfo {
        ParameterChecker.check(subUserId > 0, "subUserId should > 0");
        
        mUserApi.enableUser(subUserId);
    }
    
    public QueryHostingUserPage getHostingUserPage(
            QueryHostingUserOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo {
        PageOptionHelper.checkIndexedPageOption(pageOption
                , trade_hosting_terminal_aoConstants.MAX_QUERY_USER_PAGESIZE);
        
        QueryUserOption apiQueryOption = new QueryUserOption();
        if (queryOption != null) {
            if (queryOption.isSetSubUserId()) {
                apiQueryOption.setSubUserId(queryOption.getSubUserId());
            }
            if (queryOption.isSetLoginNamePartical()) {
                apiQueryOption.setLoginNamePartical(queryOption.getLoginNamePartical());
            }
            if (queryOption.isSetNickNamePartical()) {
                apiQueryOption.setNickNamePartical(queryOption.getNickNamePartical());
            }
            if (queryOption.isSetLoginNameWhole()) {
                ParameterChecker.check(StringUtils.isNotEmpty(queryOption.getLoginNameWhole())
                        , "queryOption's loginName should not be empty");
                apiQueryOption.setLoginNameWhole(queryOption.getLoginNameWhole());
            }
            if (queryOption.isSetOrderType()) {
                apiQueryOption.setOrder(queryOption.getOrderType());
            }
        }
        
        PageResult<HostingUser> pageList = mUserApi.queryUserPage(apiQueryOption, PageOptionHelper.toApiPageOption(pageOption));
        QueryHostingUserPage resultPage = new QueryHostingUserPage();
        resultPage.setTotalCount(pageList.getTotalCount());
        List<HostingUser> resultUserList = new ArrayList<HostingUser>();
        for (HostingUser user : pageList.getPageList()) {
            user.unsetLoginPasswd();
            resultUserList.add(user);
        }
        resultPage.setResultList(resultUserList);
        
        return resultPage;
    }
    
}
