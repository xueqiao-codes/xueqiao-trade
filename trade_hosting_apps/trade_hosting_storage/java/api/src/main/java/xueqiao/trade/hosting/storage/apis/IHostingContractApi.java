package xueqiao.trade.hosting.storage.apis;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.longsheng.xueqiao.contract.online.dao.thriftapi.client.ContractOnlineDaoServiceStub;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;

public interface IHostingContractApi {
    public ContractOnlineDaoServiceStub getContractOnlineStub() throws ErrorInfo;
    
    public SledContractDetails getContractDetailForSure(long sledContractId) throws ErrorInfo;

    /*
     * 判断是否为内盘合约交易所
     */
    public boolean isInvolExchange(String sledExchangeCode);
}
