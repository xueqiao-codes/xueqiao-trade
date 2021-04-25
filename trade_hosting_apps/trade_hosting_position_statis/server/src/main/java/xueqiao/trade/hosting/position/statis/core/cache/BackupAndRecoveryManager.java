package xueqiao.trade.hosting.position.statis.core.cache;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.StatPositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCacheManager;
import xueqiao.trade.hosting.position.statis.core.calculator.backup.QuotationBackupCalculator;
import xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic.StatComposePositionDynamicInfoCalculator;
import xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic.StatContractPositionDynamicInfoCalculator;
import xueqiao.trade.hosting.position.statis.core.quotation.StatQuotationHelper;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionHandler;

import java.util.List;

public class BackupAndRecoveryManager {

    private static BackupAndRecoveryManager ourInstance = new BackupAndRecoveryManager();

    public static BackupAndRecoveryManager getInstance() {
        return ourInstance;
    }

    private BackupAndRecoveryManager() {
    }

    /**
     * 恢复数据
     */
    public void recoveryQuotationCache() throws ErrorInfo {
        QuotationBackupCalculator.recoveryContractQuotation();
        QuotationBackupCalculator.recoveryComposeQuotation();
    }

    /**
     *
     * */
    public void initPositionDynamicInfo() throws ErrorInfo {
        List<StatPositionSummary> positionSummaryList = StatPositionHandler.queryAllStatPositionSummary();
        if (positionSummaryList == null || positionSummaryList.size() < 1) {
            return;
        }
        for (StatPositionSummary positionSummary : positionSummaryList) {
            if (positionSummary.getTargetType() == HostingXQTargetType.CONTRACT_TARGET) {
                StatQuotationCache quotationCache = StatQuotationCacheManager.getInstance().getLatestQuotation(positionSummary.getTargetKey());
                new StatContractPositionDynamicInfoCalculator(positionSummary.getSubAccountId(), positionSummary.getTargetKey(), positionSummary.getTargetType()).addTask(quotationCache);
            } else if (positionSummary.getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
                StatQuoCombCache quoCombCache = StatQuoCombCacheManager.getInstance().getLatestQuotation(positionSummary.getTargetKey());
                new StatComposePositionDynamicInfoCalculator(positionSummary.getSubAccountId(), positionSummary.getTargetKey(), positionSummary.getTargetType()).addTask(quoCombCache);
            }
        }
    }
}
