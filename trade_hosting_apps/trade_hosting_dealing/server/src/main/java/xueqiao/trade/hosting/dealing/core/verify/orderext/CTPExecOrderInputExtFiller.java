package xueqiao.trade.hosting.dealing.core.verify.orderext;

import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatform;
import xueqiao.trade.hosting.CTPContractSummary;
import xueqiao.trade.hosting.CTPOrderInputExt;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;

public class CTPExecOrderInputExtFiller extends ExecOrderInputExtFiller {
    public CTPExecOrderInputExtFiller() {
    }
    
    @Override
    public int doFill() throws Exception {
        CTPOrderInputExt ctpInputExt = new CTPOrderInputExt();
        ctpInputExt.setContractSummary(new CTPContractSummary());
        ctpInputExt.getContractSummary().setCtpExchangeCode(getCommodityMapping().getExchange());
        if (getCommodityMapping().getCommodityType().length() != 1) {
            return TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR.getValue();
        }
        ctpInputExt.getContractSummary().setCtpCommodityType(
                (short)(getCommodityMapping().getCommodityType().charAt(0)));
        ctpInputExt.getContractSummary().setCtpCommodityCode(getCommodityMapping().getCommodityCode());
        ctpInputExt.getContractSummary().setCtpContractCode(
        		getMappingContractResult().getCommonContract_().getTechPlatform_ContractCode_());
        getOutputOrderInputExt().setCtpInputExt(ctpInputExt);
        
        ctpInputExt.setMinVolume(1);
        
        return 0;
    }

    @Override
    protected TechPlatform getTechPlatform() {
        return TechPlatform.CTP;
    }

    @Override
    protected long getSledBrokerId() {
        return 0;
    }
    
    

}
