package xueqiao.trade.hosting.dealing.core.verify.orderext;

import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatform;
import com.longsheng.xueqiao.contractconvertor.swig.SledContractToTechPlatformResult;
import xueqiao.trade.hosting.ESunny9ContractSummary;
import xueqiao.trade.hosting.ESunny9OrderInputExt;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;

public class ESunny9OrderInputExtFiller extends ExecOrderInputExtFiller {

	@Override
	protected TechPlatform getTechPlatform() {
		return TechPlatform.ESUNNY;
	}

	@Override
	protected long getSledBrokerId() {
		return 0;
	}

	@Override
	public int doFill() throws Exception {
		ESunny9OrderInputExt esunny9InputExt = new ESunny9OrderInputExt();
		esunny9InputExt.setContractSummary(new ESunny9ContractSummary());
		esunny9InputExt.getContractSummary().setEsunny9ExchangeNo(
				getCommodityMapping().getExchange());
		if (getCommodityMapping().getCommodityType().length() != 1) {
            return TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR.getValue();
        }
		esunny9InputExt.getContractSummary().setEsunny9CommodityType(
                (short)(getCommodityMapping().getCommodityType().charAt(0)));
		esunny9InputExt.getContractSummary().setEsunny9CommodityNo(
				getCommodityMapping().getCommodityCode());
		
		SledContractToTechPlatformResult mappingContractResult = super.getMappingContractResult();
		esunny9InputExt.getContractSummary().setEsunny9ContractNo(
				mappingContractResult.getCommonContract_().getTechPlatform_ContractCode_());
		if (mappingContractResult.getOtherCommonContractCount_() > 0) {
			esunny9InputExt.getContractSummary().setEsunny9ContractNo2(
					mappingContractResult.getOtherCommonContract_(0).getTechPlatform_ContractCode_());
		}
		
		getOutputOrderInputExt().setEsunny9InputExt(esunny9InputExt);
		return 0;
	}

}
