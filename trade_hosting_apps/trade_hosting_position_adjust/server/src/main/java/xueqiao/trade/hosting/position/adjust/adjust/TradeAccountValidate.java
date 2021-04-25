package xueqiao.trade.hosting.position.adjust.adjust;

import com.antiy.error_code.ErrorCodeOuter;
import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;
import com.longsheng.xueqiao.broker.thriftapi.BrokerPlatform;
import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatform;
import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatformEnv;
import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.tradeaccount.HostingTradeAccountApiImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TradeAccountValidate {

    public boolean validate(long tradeAccountId, String exchangeMic) throws ErrorInfo {
        IHostingTradeAccountApi api = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
        BrokerAccessEntry brokerAccessEntry = api.getBrokerAccessEntry(tradeAccountId);
        BrokerPlatform platform = brokerAccessEntry.getPlatform();
        TechPlatform techPlatform = mappingBrokerPlatform(platform);

        try {
            String techPlatformMicStrs = Qconf.getConf("/xueqiao/tech_platform/" + techPlatform.name().toUpperCase()).trim();
            List<String> exchangeMics = Arrays.asList(StringUtils.split(techPlatformMicStrs, ","));
            List<String> fixedMics = new ArrayList<>();
            for (String mic : exchangeMics) {
                String fixMic = mic.trim();
                fixedMics.add(fixMic);
            }
            if (fixedMics.contains(exchangeMic)) {
                return true;
            }
        } catch (QconfException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
        }
        return false;
    }

    private TechPlatform mappingBrokerPlatform(BrokerPlatform platform) throws ErrorInfo {
        if (platform == BrokerPlatform.CTP) {
            return TechPlatform.CTP;
        } else if (platform == BrokerPlatform.ESUNNY) {
            return TechPlatform.ESUNNY;
        } else if (platform == BrokerPlatform.ESUNNY_3) {
            return TechPlatform.ESUNNY_3;
        } else {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED.getValue()
                    , "broker technical platform is not supported now");
        }
    }

    public static TechPlatformEnv mappingBrokerPlatformEnv(com.longsheng.xueqiao.broker.thriftapi.TechPlatformEnv techPlatformEnv) throws ErrorInfo {
        if (com.longsheng.xueqiao.broker.thriftapi.TechPlatformEnv.REAL.equals(techPlatformEnv)) {
            return TechPlatformEnv.REAL;
        }
        if (com.longsheng.xueqiao.broker.thriftapi.TechPlatformEnv.SIM.equals(techPlatformEnv)) {
            return TechPlatformEnv.SIM;
        } else {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED.getValue()
                    , "broker technical platform env is not supported now");
        }
    }
}
