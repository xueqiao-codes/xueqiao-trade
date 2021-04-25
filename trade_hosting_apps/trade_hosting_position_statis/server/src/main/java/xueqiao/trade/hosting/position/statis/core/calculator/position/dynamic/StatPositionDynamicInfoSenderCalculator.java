package xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic;

import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.push.sdk.PushApi;
import xueqiao.quotation.push.sdk.UserMsg;
import xueqiao.trade.hosting.events.StatPositionDynamicInfoEvent;
import xueqiao.trade.hosting.position.statis.StatPositionDynamicInfo;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionDynamic;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.calculator.AbstractCalculator;
import xueqiao.trade.hosting.position.statis.core.factory.FactoryInstance;
import xueqiao.trade.hosting.position.statis.core.thread.TaskThreadManager;
import xueqiao.trade.hosting.push.sdk.impl.ProtocolUtil;

import java.util.Collection;

public class StatPositionDynamicInfoSenderCalculator extends AbstractCalculator<Void, Void> {

    private static final long TEN_SECONDS_MILLIS = 10 * 1000;

    public StatPositionDynamicInfoSenderCalculator() {
        super(TaskThreadManager.THREAD_KEY_POSITION_DYNAMIC_INFO_SENDER);
    }

    @Override
    public Void onCalculate(Void param) throws ErrorInfo {
        Collection<CachePositionDynamic> cachePositionDynamics = StatPositionCacheManager.getInstance().getCachePositionDynamicList();

//        AppLog.i("StatPositionDynamicInfoSenderCalculator ---- cachePositionDynamics.size() : " + cachePositionDynamics.size());

        if (cachePositionDynamics != null && cachePositionDynamics.size() > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            StatPositionDynamicInfo positionDynamicInfo;
            StringBuilder stringBuilder;
            for (CachePositionDynamic cachePositionDynamic : cachePositionDynamics) {
                /*
                 * 如果动态信息的计算在10秒内没有更新，则不推送
                 * */
                if (currentTimeMillis - cachePositionDynamic.getModifyTimestampMs() > TEN_SECONDS_MILLIS) {
//                    StatPositionCacheManager.getInstance().removeCachePositionDynamic(cachePositionDynamic);
                    continue;
                }
                positionDynamicInfo = CachePositionDynamic.convertToPositionDynamicInfo(cachePositionDynamic);
                sendPositionDynamicInfoMsg(positionDynamicInfo);

//                stringBuilder = new StringBuilder();
//                stringBuilder.append("sendPositionDynamicInfoMsg， subAccountId : ")
//                        .append(positionDynamicInfo.getSubAccountId())
//                        .append(", targetKey : ")
//                        .append(positionDynamicInfo.getTargetKey())
//                        .append(", targetType : ")
//                        .append(positionDynamicInfo.getTargetType());
//                AppLog.i(stringBuilder.toString());
            }
        }

        return null;
    }

    /**
     * 推送消息到客户端
     */
    private void sendPositionDynamicInfoMsg(StatPositionDynamicInfo positionDynamicInfo) {
        StatPositionDynamicInfoEvent event = new StatPositionDynamicInfoEvent();
        event.setSubAccountId(positionDynamicInfo.getSubAccountId());
        event.setTargetKey(positionDynamicInfo.getTargetKey());
        event.setTargetType(positionDynamicInfo.getTargetType());
        event.setPositionDynamicInfo(positionDynamicInfo);
        event.setEventCreateTimestampMs(System.currentTimeMillis());

        UserMsg msg = new UserMsg();
        msg.setMsgType(event.getClass().getSimpleName());
        msg.setMsgContent(ProtocolUtil.serialize(FactoryInstance.getInstance().getCompactFactory(), event).array());
        PushApi.sendMsgBySubAccountId(positionDynamicInfo.getSubAccountId(), msg);
    }
}
