package xueqiao.trade.hosting.position.fee.core.calculator;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;

import java.util.List;

public class OneTradeAccountPositionRateCalculator extends AbstractTradeAccountPositionRateCalculator<Long> {

    @Override
    public Void onCalculate(Long tradeAccountId) throws TException {
        List<UpsidePositionRate> upsidePositionRateList = getUpsidePositionRateForSure(tradeAccountId);
        updateUpsidePositionRateList(tradeAccountId, upsidePositionRateList);
        return null;
    }
}
