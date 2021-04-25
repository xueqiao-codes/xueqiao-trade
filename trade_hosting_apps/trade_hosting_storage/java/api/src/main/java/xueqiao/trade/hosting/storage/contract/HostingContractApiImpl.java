package xueqiao.trade.hosting.storage.contract;

import com.longsheng.xueqiao.contract.online.dao.thriftapi.ContractOnlineDaoServiceVariable;
import com.longsheng.xueqiao.contract.online.dao.thriftapi.client.ContractOnlineDaoServiceStub;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqSledContractDetailsOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetailsPage;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class HostingContractApiImpl implements IHostingContractApi {
    private static Set<String> sInvolExchanges = new HashSet<>();

    static {
        sInvolExchanges.add("CCFX");
        sInvolExchanges.add("XDCE");
        sInvolExchanges.add("XSGE");
        sInvolExchanges.add("XZCE");
        sInvolExchanges.add("XINE");
    }

    @Override
    public ContractOnlineDaoServiceStub getContractOnlineStub() throws ErrorInfo {
        ContractOnlineDaoServiceStub stub = new ContractOnlineDaoServiceStub();
        stub.setSocketFile(new File("/data/run/service_" + ContractOnlineDaoServiceVariable.serviceKey + ".sock"));
        return stub;
    }

    @Override
    public SledContractDetails getContractDetailForSure(long sledContractId) throws ErrorInfo {
        ReqSledContractDetailsOption detailsOption = new ReqSledContractDetailsOption();
        Set<Integer> queryContractIds = new HashSet<Integer>();
        queryContractIds.add((int)sledContractId);
        detailsOption.setSledContractIds(queryContractIds);
        
        SledContractDetailsPage contractPage;
        try {
            contractPage = getContractOnlineStub().reqSledContractDetail(detailsOption, 0, 1);
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!");
        }
        if (contractPage.getPageSize() != 1) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONTRACT_NOT_EXISTED.getValue()
                    , "contract not existed!");
        }
        
        SledContractDetails contractDetails = contractPage.getPage().get(0);
        if (!contractDetails.isSetSledContract()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONTRACT_NOT_EXISTED.getValue()
                    , "details not set contract");
        }
        
        if (AppLog.debugEnabled()) {
            AppLog.d("sledContractId=" + sledContractId + " -> contractDetails=" + contractDetails);
        }
        
        if (contractDetails.getSledContract().getSledContractId() != sledContractId) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONTRACT_NOT_EXISTED.getValue()
                    , "details's contract id not equals query contract id");
        }
        if (!contractDetails.isSetSledCommodity()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMMODITY_NOT_EXISTED.getValue()
                    , "details not set commodity");
        }
        if (contractDetails.getSledCommodity().getSledCommodityId() != contractDetails.getSledContract().getSledCommodityId()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMMODITY_NOT_EXISTED.getValue()
                    , "details's commodity id not equals details's contract commodity id");
        }
        
        return contractDetails;
    }

    @Override
    public boolean isInvolExchange(String sledExchangeCode) {
        return sInvolExchanges.contains(sledExchangeCode);
    }

}
