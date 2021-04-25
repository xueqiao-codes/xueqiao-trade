package xueqiao.trade.hosting.position.statis.service.util;

import com.antiy.error_code.ErrorCodeInner;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraph;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeLeg;

import java.math.BigDecimal;
import java.util.Map;

public class ComposeUtil {
    /**
     * 计算组合差价
     */
    public static Double calculateComposePrice(StatComposeGraph composeGraph, Map<Long, Double> composeLegTotalPriceMap, int composeVolume) throws ErrorInfo {

        if (composeLegTotalPriceMap.isEmpty()) {
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "should called after method fetchOriginalContractPositionDataList()");
        }

        Expression formularExpression = new ExpressionBuilder(composeGraph.getFormular())
                .buildinUseBigDecimal(true)
                .variables(composeGraph.getLegKeySet()).build();

        Integer legQuantity;
        Double legTotalPrice;
        BigDecimal legAvergePrice;

        for (Map.Entry<Long, StatComposeLeg> kvEntry : composeGraph.getComposeLegQuantityMap().entrySet()) {
            // 计算组合腿的成交总数
            legQuantity = composeGraph.getComposeLegQuantityMap().get(kvEntry.getKey()).getQuantity();
            legQuantity *= composeVolume;

            if (legQuantity == null) {
                AppLog.e("calculateComposePrice ---- legQuantity is null ,kvEntry.getKey() : " + kvEntry.getKey());
                throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "compose leg quantity is null");
            }
            legTotalPrice = composeLegTotalPriceMap.get(kvEntry.getKey());
            if (legTotalPrice == null) {
                AppLog.e("calculateComposePrice ---- legTotalPrice is null ,kvEntry.getKey() : " + kvEntry.getKey());
                throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "compose leg total price is null");
            }

            legAvergePrice = new BigDecimal(legTotalPrice);
            legAvergePrice = legAvergePrice.divide(new BigDecimal(legQuantity), 16, BigDecimal.ROUND_HALF_UP);

            AppLog.d("calculateComposePrice ---- legTotalPrice : " + legTotalPrice + ", legQuantity : " + legQuantity + ", legAvergePrice : " + legAvergePrice.doubleValue());
            formularExpression.setVariable(kvEntry.getValue().variableName, legAvergePrice.doubleValue());
        }
        try {
            double result = formularExpression.evaluate();
            AppLog.d("calculateComposePrice ---- result : " + result);
            if (Double.isNaN(result)) {
                return null;
            }
            return result;
        } catch (Throwable e) {
            AppLog.d(e.getMessage(), e);
        }
        return null;
    }
}
