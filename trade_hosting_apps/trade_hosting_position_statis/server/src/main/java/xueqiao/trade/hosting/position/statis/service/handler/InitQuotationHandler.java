package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.StatPositionSummary;
import xueqiao.trade.hosting.position.statis.core.quotation.StatQuotationHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class InitQuotationHandler {

    /**
     * 初始化行情监听
     * 应用启动时，发起已有持仓的行情监听
     */
    public static void initQuotationListener() throws ErrorInfo {
        List<StatPositionSummary> positionSummaryList = StatPositionHandler.queryAllStatPositionSummary();
        if (positionSummaryList == null || positionSummaryList.size() < 1) {
            return;
        }
        for (StatPositionSummary positionSummary : positionSummaryList) {
            StatQuotationHelper.register(positionSummary.getSubAccountId(), positionSummary.getTargetKey(), positionSummary.getTargetType());
        }
    }
}
