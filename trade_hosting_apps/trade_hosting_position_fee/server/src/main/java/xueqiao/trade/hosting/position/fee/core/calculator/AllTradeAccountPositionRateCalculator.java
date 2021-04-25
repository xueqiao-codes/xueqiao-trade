package xueqiao.trade.hosting.position.fee.core.calculator;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.position.fee.core.api.HostingTradeAccountApi;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;

import java.util.List;

public class AllTradeAccountPositionRateCalculator extends AbstractTradeAccountPositionRateCalculator<Void> {

    /**
     * 1 更新上手源数据
     * 2 更新上手合约层面平铺数据
     */
    @Override
    public Void onCalculate(Void param) throws TException {
        /*
         * 1 更新所有资金账号上手源数据
         * */
        List<HostingTradeAccount> tradeAccountList = HostingTradeAccountApi.queryValidTradeAccounts();
        if (tradeAccountList == null) {
            return null;
        }
        for (HostingTradeAccount tradeAccount : tradeAccountList) {
            List<UpsidePositionRate> upsidePositionRateList = getUpsidePositionRateForSure(tradeAccount.getTradeAccountId());
            updateUpsidePositionRateList(tradeAccount.getTradeAccountId(), upsidePositionRateList);
        }

        /*
         * 2 更新所有操作账号上手合约层面平铺数据
         * */
        AllSubAccountContractPositionRateCalculator calculator = new AllSubAccountContractPositionRateCalculator();
        calculator.onCalculate(null);
        return null;
    }
}
