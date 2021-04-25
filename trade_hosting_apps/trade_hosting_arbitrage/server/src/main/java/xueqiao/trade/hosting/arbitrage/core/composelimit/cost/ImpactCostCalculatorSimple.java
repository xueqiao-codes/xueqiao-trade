package xueqiao.trade.hosting.arbitrage.core.composelimit.cost;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.logger.AppLog;
import xueqiao.quotation.QuotationItem;

public class ImpactCostCalculatorSimple extends ImpactCostCalculatorBase {


    public ImpactCostCalculatorSimple(SledContractDetails contractDetails) {
        super(contractDetails);
    }

    @Override
    public void onReceivedQuotationItem(QuotationItem quotationItem) throws Exception {
        if (quotationItem != null && quotationItem.isSetVolumn() && quotationItem.getVolumn() > 0) {
            super.updateImpactCost(1.0 / quotationItem.getVolumn());
        }
    }
}
