package xueqiao.trade.hosting.position.fee.core.calculator;

import org.apache.thrift.TException;

public class OneSubAccountContractPositionRateCalculator extends AbstractContractPositionRateCalculator<Void> {

    private long subAccountId;

    public OneSubAccountContractPositionRateCalculator(Long subAccountId) {
        super(subAccountId);
        this.subAccountId = subAccountId;
    }

    @Override
    public Void onCalculate(Void param) throws TException {
        updateOneSubAccountContractPositionRate(subAccountId);
        return null;
    }
}
