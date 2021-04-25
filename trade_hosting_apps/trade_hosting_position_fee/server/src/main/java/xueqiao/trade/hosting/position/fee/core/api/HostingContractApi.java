package xueqiao.trade.hosting.position.fee.core.api;

import com.longsheng.xueqiao.contract.standard.thriftapi.*;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import xueqiao.trade.hosting.position.fee.app.AppData;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.util.ArrayList;
import java.util.List;

public class HostingContractApi {

    private static IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);

    /**
     * 查询合约信息
     */
    public static List<SledContract> queryOnlineContracts(int sledCommodityId) throws TException {
        ReqSledContractOption option = new ReqSledContractOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.setSledCommodityId(sledCommodityId);
        option.setContractStatus(ContractStatus.NORMAL);  // 只查询正常合约（不包括过期或禁用的合约）

        int pageIndex = 0;
        int pageSize = 50;
        List<SledContract> contractList = new ArrayList<>();
        SledContractPage page = null;
        do {
            page = contractApi.getContractOnlineStub().reqSledContract(option, pageIndex, pageSize);
            pageIndex++;
            if (page == null || page.getPageSize() < 1) {
                break;
            }
            contractList.addAll(page.getPage());
        } while (page.getPageSize() == pageSize);
        return contractList;
    }

    /**
     * 分页查询合约信息
     * 一次查完，可能会占用内存过多
     */
    public static List<SledContract> queryOnlineContracts(int sledCommodityId, int pageIndex, int pageSize) throws TException {
        ReqSledContractOption option = new ReqSledContractOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.setSledCommodityId(sledCommodityId);
//        option.setContractStatus(ContractStatus.NORMAL);  // 只查询正常合约（不包括过期或禁用的合约）

        SledContractPage page = contractApi.getContractOnlineStub().reqSledContract(option, pageIndex, pageSize);
        if (page != null && page.getPageSize() > 0) {
            return page.getPage();
        }
        return null;
    }

    public static List<SledContract> queryRelateContracts(SledContract sledContract, int pageIndex, int pageSize) throws TException {
        ReqSledContractOption option = new ReqSledContractOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.setSledContractIdList(sledContract.getRelateContractIds());
//        option.setContractStatus(ContractStatus.NORMAL);  // 只查询正常合约（不包括过期或禁用的合约）

        SledContractPage page = contractApi.getContractOnlineStub().reqSledContract(option, pageIndex, pageSize);
        if (page != null && page.getPageSize() > 0) {
            return page.getPage();
        }
        return null;
    }

    public static List<SledContract> queryExpiredContracts(int pageIndex, int pageSize) throws TException {
        ReqSledContractOption option = new ReqSledContractOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.setContractStatus(ContractStatus.EXPIRED);  // 只查询正常合约（不包括过期或禁用的合约）

        SledContractPage page = contractApi.getContractOnlineStub().reqSledContract(option, pageIndex, pageSize);
        if (page != null && page.getPageSize() > 0) {
            return page.getPage();
        }
        return null;
    }

    public static SledContract queryContract(int contractId) throws TException {
        ReqSledContractOption option = new ReqSledContractOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.addToSledContractIdList(contractId);

        SledContractPage page = contractApi.getContractOnlineStub().reqSledContract(option, 0, 1);
        if (page != null && page.getPageSize() > 0) {
            return page.getPage().get(0);
        }
        return null;
    }

    /**
     * 查询商品信息
     */
    public static List<SledCommodity> queryCommodities(String exchangeMic, SledCommodityType commodityType) throws TException {
        ReqSledCommodityOption option = new ReqSledCommodityOption();
        option.setExchangeMic(exchangeMic);
        if (commodityType != null) {
            option.setSledCommodityType(commodityType);
        }
        option.setPlatformEnv(AppData.getTechPlatformEnv());

        int pageIndex = 0;
        int pageSize = 50;
        List<SledCommodity> commodityList = new ArrayList<>();
        SledCommodityPage page;
        do {
            page = contractApi.getContractOnlineStub().reqSledCommodity(option, pageIndex, pageSize);
            pageIndex++;
            if (page == null || page.getPageSize() < 1) {
                break;
            }
            commodityList.addAll(page.getPage());
        } while (page.getPageSize() == pageSize);
        return commodityList;
    }

    /**
     * 查询商品信息
     */
    public static SledCommodity queryCommodity(String exchangeMic, SledCommodityType commodityType, String commodityCode) throws TException {
        ReqSledCommodityOption option = new ReqSledCommodityOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.setExchangeMic(exchangeMic);
        option.setSledCommodityType(commodityType);
        option.setSledCommodityCode(commodityCode);

        SledCommodityPage page = contractApi.getContractOnlineStub().reqSledCommodity(option, 0, 1);
        if (page != null && page.getPageSize() > 0) {
            return page.getPage().get(0);
        }
        return null;
    }

    /**
     * 查询商品信息
     */
    public static List<SledCommodity> queryCommodity(List<Integer> commodityIds) throws TException {
        if (commodityIds == null || commodityIds.size() < 1) {
            return null;
        }
        ReqSledCommodityOption option = new ReqSledCommodityOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.setSledCommodityIdList(commodityIds);

        SledCommodityPage page = contractApi.getContractOnlineStub().reqSledCommodity(option, 0, commodityIds.size());
        if (page != null && page.getPageSize() > 0) {
            return page.getPage();
        }
        return null;
    }

    /**
     * 查询商品信息
     */
    public static SledCommodity queryCommodity(int commodityId) throws TException {
        ReqSledCommodityOption option = new ReqSledCommodityOption();
        option.setPlatformEnv(AppData.getTechPlatformEnv());
        option.addToSledCommodityIdList(commodityId);

        SledCommodityPage page = contractApi.getContractOnlineStub().reqSledCommodity(option, 0, 1);
//        if (AppLog.infoEnabled()) {
//            AppLog.i("queryCommodity ---- commodityId : " + commodityId + ", SledCommodityPage : " + new Gson().toJson(page));
//        }
        if (page != null && page.getPageSize() > 0) {
            return page.getPage().get(0);
        }
        return null;
    }

    /**
     * 查交易所
     */
    public static SledExchange queryExchange(String exchangeMic) throws TException {
        ReqSledExchangeOption option = new ReqSledExchangeOption();
        option.setExchangeMic(exchangeMic);
        int pageIndex = 0;
        int pageSize = 1;
        SledExchangePage page = contractApi.getContractOnlineStub().reqSledExchange(option, pageIndex, pageSize);
        if (page != null && page.getPageSize() > 0) {
            return page.getPage().get(0);
        }
        return null;
    }
}
