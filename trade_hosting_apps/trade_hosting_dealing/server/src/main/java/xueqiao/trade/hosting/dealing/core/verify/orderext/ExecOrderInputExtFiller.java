package xueqiao.trade.hosting.dealing.core.verify.orderext;

import com.longsheng.xueqiao.contract.online.dao.thriftapi.client.ContractOnlineDaoServiceStub;
import com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMapping;
import com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledCommodityOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityType;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatform;
import com.longsheng.xueqiao.contractconvertor.swig.ContractConvertor;
import com.longsheng.xueqiao.contractconvertor.swig.SledContractToTechPlatformArgs;
import com.longsheng.xueqiao.contractconvertor.swig.SledContractToTechPlatformResult;
import com.longsheng.xueqiao.contractconvertor.util.ObjectToString;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ExecOrderInputExtFiller {
    
    private HostingExecOrder mInputOrder;
    private HostingExecOrderInputExt mOutputOrderInputExt;
    
    private List<HostingExecOrderLegContractSummary> mOutputLegContractSummary;
    
    private ContractOnlineDaoServiceStub mConstractOnlineStub;
    
    private CommodityMapping mCommodityMapping;
    private SledContractToTechPlatformResult mMappingContractResult;
    
    protected ExecOrderInputExtFiller() {
    }
    
    public void setInputOrder(HostingExecOrder inputOrder) {
        this.mInputOrder = inputOrder;
    }
    
    public void setOutputOrderInputExt(HostingExecOrderInputExt outputOrderInputExt) {
        this.mOutputOrderInputExt = outputOrderInputExt;
    }
    
    public List<HostingExecOrderLegContractSummary> getOutputLegContractSummary() {
        return mOutputLegContractSummary;
    }

    public void setOutputLegContractSummary(List<HostingExecOrderLegContractSummary> mOutputLegContractSummary) {
        this.mOutputLegContractSummary = mOutputLegContractSummary;
    }

    public void setContractOnlineStub(ContractOnlineDaoServiceStub stub) {
        this.mConstractOnlineStub = stub;
    }
    
    protected HostingExecOrder getInputOrder() {
        return mInputOrder;
    }
    
    protected HostingExecOrderInputExt getOutputOrderInputExt() {
        return mOutputOrderInputExt;
    }
    
    protected ContractOnlineDaoServiceStub getContractOnlineStub() {
        return mConstractOnlineStub;
    }
    
    protected abstract TechPlatform getTechPlatform();
    protected abstract long getSledBrokerId();
    
    protected Map<Integer, SledContract> reqSledContracts(List<Integer> sledContractIds) throws ErrorInfo, TException {
        Map<Integer, SledContract> resultMap = new HashMap<Integer, SledContract>();
        ReqSledContractOption contractOption = new ReqSledContractOption();
        contractOption.setSledContractIdList(sledContractIds);
        contractOption.setNeedTotalCount(false);
        SledContractPage contractPage = getContractOnlineStub().reqSledContract(contractOption, 0, sledContractIds.size());
        if (contractPage.getPageSize() > 0) {
            for (SledContract c : contractPage.getPage()) {
                resultMap.put(c.getSledContractId(), c);
            }
        }
        return resultMap;
    }
    
    private SledContract reqSledContract(int sledContractId) throws ErrorInfo, TException {
        return reqSledContracts(Arrays.asList(sledContractId)).get(sledContractId);
    }
    
    protected Map<Integer, SledCommodity> reqSledCommodities(List<Integer> sledCommodityIds) throws ErrorInfo, TException {
        Map<Integer, SledCommodity> resultMap = new HashMap<Integer, SledCommodity>();
        ReqSledCommodityOption commodityOption = new ReqSledCommodityOption();
        commodityOption.setSledCommodityIdList(sledCommodityIds);
        commodityOption.setNeedTotalCount(false);
        SledCommodityPage commodityPage 
            = getContractOnlineStub().reqSledCommodity(commodityOption, 0, 3*sledCommodityIds.size());
        if (commodityPage.getPageSize() > 0) {
            for (SledCommodity c : commodityPage.getPage()) {
                resultMap.put(c.getSledCommodityId(), c);
            }
        }
        return resultMap;
    }
    
    private CommodityMappingPage reqCommodityMappingPage(List<Integer> sledCommodityIds) throws ErrorInfo, TException {
        ReqCommodityMappingOption mappingOption = new ReqCommodityMappingOption();
        mappingOption.setSledCommodityIdList(sledCommodityIds);
        mappingOption.setTechPlatform(getTechPlatform());
        mappingOption.setBrokerEntryId((int)getSledBrokerId());
        mappingOption.setNeedTotalCount(false);
        return getContractOnlineStub().reqCommodityMapping(mappingOption, 0, 3 * sledCommodityIds.size());
    }
    
    private Map<Integer, CommodityMapping> reqCommodityMappings(List<Integer> sledCommodityIds) throws ErrorInfo, TException {
        CommodityMappingPage mappingPage = reqCommodityMappingPage(sledCommodityIds);
        Map<Integer, CommodityMapping> resultMap = new HashMap<Integer, CommodityMapping>();
        if (mappingPage.getPageSize() > 0) {
            for (CommodityMapping m : mappingPage.getPage()) {
                resultMap.put(m.getSledCommodityId(), m);
            }
        }
        return resultMap;
    }
    
    private SledContractToTechPlatformResult processMappingContactCode() throws ErrorInfo, TException {
        SledContractToTechPlatformArgs args = new SledContractToTechPlatformArgs();
        
        com.longsheng.xueqiao.contractconvertor.swig.SledContract convertorContract 
            = new com.longsheng.xueqiao.contractconvertor.swig.SledContract();
        
        com.longsheng.xueqiao.contractconvertor.swig.SledBaseContract convertorBaseContract 
            = new com.longsheng.xueqiao.contractconvertor.swig.SledBaseContract();
        convertorBaseContract.setExchangeMic_(getInputOrder().getContractSummary().getSledExchangeMic());
        convertorBaseContract.setSledCommodityCode_(getInputOrder().getContractSummary().getSledCommodityCode());
        convertorBaseContract.setSledContractCode_(getInputOrder().getContractSummary().getSledContractCode());
        convertorBaseContract.setSledCommodityType_(
                com.longsheng.xueqiao.contractconvertor.swig.SledCommodityType.swigToEnum(
                        getInputOrder().getContractSummary().getSledCommodityType()));
        
        convertorContract.setSledBaseContract_(convertorBaseContract);
        
        com.longsheng.xueqiao.contractconvertor.swig.CommodityMap convertorCommodityMap
                = new com.longsheng.xueqiao.contractconvertor.swig.CommodityMap();
        convertorCommodityMap.setTechPlatform_(
                com.longsheng.xueqiao.contractconvertor.swig.TechPlatform.swigToEnum(getTechPlatform().getValue()));
        convertorCommodityMap.setExchangeMic_(getInputOrder().getContractSummary().getSledExchangeMic());
        convertorCommodityMap.setSledCommodityType_(
                com.longsheng.xueqiao.contractconvertor.swig.SledCommodityType.swigToEnum(
                        getInputOrder().getContractSummary().getSledCommodityType()));
        convertorCommodityMap.setSledCommodityCode_(getInputOrder().getContractSummary().getSledCommodityCode());
        convertorCommodityMap.setTechPlatform_Exchange_(getCommodityMapping().getExchange());
        convertorCommodityMap.setTechPlatform_CommodityType_(getCommodityMapping().getCommodityType());
        convertorCommodityMap.setTechPlatform_CommodityCode_(getCommodityMapping().getCommodityCode());
        
        args.setCommodityMap_(convertorCommodityMap);
        
        int sledCommodityType = getInputOrder().getContractSummary().getSledCommodityType();
        if (sledCommodityType  == SledCommodityType.FUTURES.getValue()) {
            // Do Nothing
        } else if (sledCommodityType == SledCommodityType.SPREAD_COMMODITY.getValue()
                || sledCommodityType == SledCommodityType.SPREAD_MONTH.getValue()) {
            SledContract mappingSledContract 
                = reqSledContract((int)getInputOrder().getContractSummary().getSledContractId());
            if (mappingSledContract == null) {
                AppLog.e("Contract not found for mappingContractId=" + getInputOrder().getContractSummary().getSledContractId());
                return null;
            }
            
            if (mappingSledContract.getRelateContractIdsSize() <= 0) {
                AppLog.e("No RelatedIds for mappingContractId=" + getInputOrder().getContractSummary().getSledContractId());
                return null;
            }
            
            Map<Integer, SledContract> relatedLegContracts = reqSledContracts(mappingSledContract.getRelateContractIds());
            Set<Integer> relatedCommodityIdSet = new HashSet<Integer>();
            for (Integer legSledContractId : mappingSledContract.getRelateContractIds()) {
                SledContract legSledContract = relatedLegContracts.get(legSledContractId);
                if (legSledContract == null) {
                    AppLog.e("Contract not found for legContractId=" + legSledContractId 
                            + ", using by mappingContractId=" +  getInputOrder().getContractSummary().getSledContractId());
                    return null;
                }
                relatedCommodityIdSet.add(legSledContract.getSledCommodityId());
            }
            if (relatedCommodityIdSet.isEmpty()) {
                AppLog.e("Related commodityIds is empty for mappingContractId=" + getInputOrder().getContractSummary().getSledContractId());
                return null;
            }
            List<Integer> relatedCommodityIdList = new ArrayList<Integer>(relatedCommodityIdSet);
            
            Map<Integer, SledCommodity> relatedLegCommodities = reqSledCommodities(relatedCommodityIdList);
            Map<Integer, CommodityMapping> relatedLegMappings = reqCommodityMappings(relatedCommodityIdList);
            
            int legIndex = 0;
            convertorContract.setRelateSledContractCount_(mappingSledContract.getRelateContractIdsSize());
            args.setRelateCommodityMapCount_(mappingSledContract.getRelateContractIdsSize());
            for (Integer legSledContractId : mappingSledContract.getRelateContractIds()) {
                SledContract legSledContract = relatedLegContracts.get(legSledContractId);
                SledCommodity legSledCommodity = relatedLegCommodities.get(legSledContract.getSledCommodityId());
                CommodityMapping legCommodityMapping = relatedLegMappings.get(legSledContract.getSledCommodityId());
                if (legSledCommodity == null) {
                    AppLog.e("Related SledCommodity for commodityId=" + legSledContract.getSledCommodityId()
                            + " not found using by legContractId=" + legSledContract.getSledContractId()
                            + ", mappingContractId=" + mappingSledContract.getSledContractId()
                            + "");
                    return null;
                }
                if(legCommodityMapping == null) {
                    AppLog.e("Related CommodityMapping for commodityId=" + legSledContract.getSledCommodityId()
                            + " not found using by legContractId=" + legSledContract.getSledContractId()
                            + ", mappingContractId=" + mappingSledContract.getSledContractId());
                    return null;
                }
                
                if (AppLog.debugEnabled()) {
                    StringBuilder logBuilder = new StringBuilder(256);
                    logBuilder.append("legSledContract=").append(legSledContract)
                              .append(" --> legSledCommodity=").append(legSledCommodity)
                              .append(" --> legCommodityMapping=").append(legCommodityMapping);
                    AppLog.d(logBuilder.toString());
                }
                
                com.longsheng.xueqiao.contractconvertor.swig.SledBaseContract legConvertorBaseContract 
                    = new com.longsheng.xueqiao.contractconvertor.swig.SledBaseContract();
                legConvertorBaseContract.setExchangeMic_(legSledCommodity.getExchangeMic());
                legConvertorBaseContract.setSledCommodityType_(
                        com.longsheng.xueqiao.contractconvertor.swig.SledCommodityType.swigToEnum(
                                legSledCommodity.getSledCommodityType().getValue()));
                legConvertorBaseContract.setSledCommodityCode_(legSledCommodity.getSledCommodityCode());
                legConvertorBaseContract.setSledContractCode_(legSledContract.getSledContractCode());

                convertorContract.setRelateSledContract_(legIndex, legConvertorBaseContract);
                
                com.longsheng.xueqiao.contractconvertor.swig.CommodityMap legConvertorCommodityMap
                    = new com.longsheng.xueqiao.contractconvertor.swig.CommodityMap();
                legConvertorCommodityMap.setExchangeMic_(legSledCommodity.getExchangeMic());
                legConvertorCommodityMap.setSledCommodityType_(
                        com.longsheng.xueqiao.contractconvertor.swig.SledCommodityType.swigToEnum(
                                legSledCommodity.getSledCommodityType().getValue()));
                legConvertorCommodityMap.setSledCommodityCode_(legSledCommodity.getSledCommodityCode());
                legConvertorCommodityMap.setTechPlatform_(
                        com.longsheng.xueqiao.contractconvertor.swig.TechPlatform.swigToEnum(getTechPlatform().getValue()));
                legConvertorCommodityMap.setTechPlatform_Exchange_(legCommodityMapping.getExchange());
                legConvertorCommodityMap.setTechPlatform_CommodityType_(legCommodityMapping.getCommodityType());
                legConvertorCommodityMap.setTechPlatform_CommodityCode_(legCommodityMapping.getCommodityCode());
                
                args.setRelateCommodityMap_(legIndex, legConvertorCommodityMap);
                
                // 填充合约腿信息
                if (mOutputLegContractSummary != null) {
                    HostingExecOrderLegContractSummary legContractSummary 
                        = new HostingExecOrderLegContractSummary();
                    legContractSummary.setLegSledContractId(legSledContractId);
                    legContractSummary.setLegSledContractCode(legSledContract.getSledContractCode());
                    legContractSummary.setLegSledCommodityType((short)legSledCommodity.getSledCommodityType().getValue());
                    legContractSummary.setLegSledCommodityId(legSledCommodity.getSledCommodityId());
                    legContractSummary.setLegSledCommodityCode(legSledCommodity.getSledCommodityCode());
                    legContractSummary.setLegSledExchangeMic(legSledCommodity.getExchangeMic());
                    
                    mOutputLegContractSummary.add(legContractSummary);
                }
                
                ++legIndex;
            }
        } else {
            AppLog.e("Unsupported mapping for commodity type=" 
                    + getInputOrder().getContractSummary().getSledCommodityType() 
                    + " for mappingContractId=" + getInputOrder().getContractSummary().getSledContractId());
            return null;
        }
        
        args.setTechPlatform_(
                com.longsheng.xueqiao.contractconvertor.swig.TechPlatform.swigToEnum(getTechPlatform().getValue()));
        args.setSledContract_(convertorContract);
        
        SledContractToTechPlatformResult result = ContractConvertor.SledToPlatformContract(args);
        if (AppLog.infoEnabled()) {
            StringBuilder logBuilder = new StringBuilder(256);
            logBuilder.append("SledToPlatformContract args=").append(ObjectToString.getStringFromObject(args))
                      .append(" -> result=").append(ObjectToString.getStringFromObject(result));
            if (StringUtils.isNotEmpty(result.getCommonContract_().getTechPlatform_ContractCode_())) {
                AppLog.i(logBuilder.toString());
            } else {
                AppLog.e(logBuilder.toString());
            }
        }
        
        return result;
    }
    
    public CommodityMapping getCommodityMapping() {
        return mCommodityMapping;
    }
    
    public SledContractToTechPlatformResult getMappingContractResult() {
        return mMappingContractResult;
    }
    
    public int process() throws Exception{
        CommodityMappingPage mappingPage = reqCommodityMappingPage(
                Arrays.asList((int)getInputOrder().getContractSummary().getSledCommodityId()));
        if (mappingPage.getPageSize() <= 0) {
            return TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND.getValue();
        }
        if (mappingPage.getPageSize() > 1) {
            return TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH.getValue();
        }
        mCommodityMapping = mappingPage.getPage().get(0);
        mMappingContractResult= processMappingContactCode();
        
        if (AppLog.infoEnabled()) {
            StringBuilder logBuilder = new StringBuilder(128);
            logBuilder.append("SledExchagneMic=").append(getInputOrder().getContractSummary().getSledExchangeMic())
                      .append(",SledCommodityType=").append(getInputOrder().getContractSummary().getSledCommodityType())
                      .append(",SledCommodityCode=").append(getInputOrder().getContractSummary().getSledCommodityCode())
                      .append(",SledContractId=").append(getInputOrder().getContractSummary().getSledContractId())
                      .append(",SledContractCode=").append(getInputOrder().getContractSummary().getSledContractCode())
                      .append(",TechPlatform=").append(getTechPlatform())
                      .append(",SledBrokerId=").append(getSledBrokerId())
                      .append(" -> ")
                      .append("PlatformExchange=").append(mCommodityMapping.getExchange())
                      .append(", PlatformCommodityType=String(").append(mCommodityMapping.getCommodityType()).append(")")
                      .append(", PlatformCommodityCode=").append(mCommodityMapping.getCommodityCode())
                      .append(", PlatformContractResult=").append(ObjectToString.getStringFromObject(mMappingContractResult));
            if (mMappingContractResult != null 
            		&& !StringUtils.isEmpty(mMappingContractResult.getCommonContract_().getTechPlatform_ContractCode_())) {
                AppLog.i(logBuilder.toString());
            } else {
                AppLog.e(logBuilder.toString());
            }
        }
        
        if (mMappingContractResult == null 
        		|| StringUtils.isEmpty(mMappingContractResult.getCommonContract_().getTechPlatform_ContractCode_())) {
            return TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED.getValue();
        }
        return doFill();
    }
    
    public abstract int doFill() throws Exception;

   
}
