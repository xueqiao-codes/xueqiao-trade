package xueqiao.trade.hosting.asset.core;

import com.antiy.error_code.ErrorCodeOuter;
import com.longsheng.xueqiao.contract.standard.thriftapi.*;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;

import java.util.HashMap;
import java.util.Map;

public class ContractGlobal {

    private long mSubAccountId;

    public ContractGlobal(long subAccountId) {
        mSubAccountId = subAccountId;
        contractCommodityIdMap = new HashMap<>();
        commodityIdCodeContractIdMap = new HashMap<>();
        latestQuotationMap = new HashMap<>();
        sledCommodityMap = new HashMap<>();
        commoditySettlementTimestamp = new HashMap<>();
        quotationSymbolContractIdMap = new HashMap<>();
        contractExpiredMap = new HashMap<>();
    }

    private Map<Long, Long> contractCommodityIdMap;

    private Map<String, Integer> commodityIdCodeContractIdMap;

    private Map<Long, QuotationItem> latestQuotationMap;

    private Map<Long, SledCommodity> sledCommodityMap;

    // 商品id: 最新结算时间点
    private Map<Long, Long> commoditySettlementTimestamp;

    private Map<String, Long> quotationSymbolContractIdMap;

    private Map<Long, Boolean> contractExpiredMap;

    private String getQuotationSymbolKey(String symbol, String platform) {
        return "ENV_" + platform + "|SYMBOL_" + symbol;
    }

    public void putQuotationSymbol(String symbol, String platform, long sledContractId) {
        if (quotationSymbolContractIdMap == null) {
            quotationSymbolContractIdMap = new HashMap<>();
        }
        quotationSymbolContractIdMap.put(getQuotationSymbolKey(symbol, platform), sledContractId);
    }

    public boolean isContractExpired(long sledContractId) {
        if (contractExpiredMap == null) {
            contractExpiredMap = new HashMap<>();
        }

        Boolean isExpired = contractExpiredMap.get(sledContractId);
        if (isExpired == null) {
            isExpired = false;
            IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            try {
                SledContractDetails details = api.getContractDetailForSure(sledContractId);
                isExpired = ContractStatus.EXPIRED.equals(details.getSledContract().getContractStatus());
                contractExpiredMap.put(sledContractId, isExpired);
            } catch (ErrorInfo errorInfo) {
                AppLog.e(errorInfo.getMessage(), errorInfo);
            }
        }
        if (isExpired) {
            AppLog.d("ContractExpired: " + sledContractId);
        }
        return isExpired;
    }

    public void clearContractExpiredCache() {
        if (contractExpiredMap != null) {
            contractExpiredMap.clear();
        }
    }

    public long getContractIdBySymbol(String symbol, String platform) {
        if (quotationSymbolContractIdMap == null) {
            quotationSymbolContractIdMap = new HashMap<>();
        }
        Long id = quotationSymbolContractIdMap.get(getQuotationSymbolKey(symbol, platform));
        if (id == null) {
            return 0;
        }
        return id;
    }

    public void putCommodityIdCodeContractId(int sledCommodityId, String contractCode, int sledContractId) {
        if (commodityIdCodeContractIdMap == null) {
            commodityIdCodeContractIdMap = new HashMap<>();
        }
        commodityIdCodeContractIdMap.put(buidCommodityIdCodeKey(sledCommodityId, contractCode), sledContractId);
    }

    private String buidCommodityIdCodeKey(int sledCommodityId, String contractCode) {
        StringBuilder builder = new StringBuilder();
        builder.append(sledCommodityId);
        builder.append("|");
        builder.append(contractCode);
        return builder.toString();
    }

    public SledCommodity getSledCommodity(long sledCommodityId) throws TException {
        if (sledCommodityMap == null) {
            sledCommodityMap = new HashMap<>();
        }
        SledCommodity sledCommodity = sledCommodityMap.get(sledCommodityId);
        if (sledCommodity == null) {
            IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            ReqSledCommodityOption option = new ReqSledCommodityOption();
            option.addToSledCommodityIdList((int) sledCommodityId);
            SledCommodityPage page = api.getContractOnlineStub().reqSledCommodity(option, 0, 1);
            if (page.getPageSize() > 0) {
                sledCommodity = page.getPage().get(0);
                sledCommodityMap.put(sledCommodityId, sledCommodity);
            } else {
                throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), " sled commodity not found: " + sledCommodityId);
            }
        }
        return sledCommodity;
    }

    public synchronized long getCommodityIdByContractId(long sledContractId) throws TException {
//        AppLog.d("sledContractId: " + sledContractId);
//        AppLog.d("contractCommodityIdMap: " + contractCommodityIdMap);
        if (contractCommodityIdMap == null) {
            contractCommodityIdMap = new HashMap<>();
        }

        Long sledCommodityId = contractCommodityIdMap.get(sledContractId);
        if (sledCommodityId == null) {
            IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            ReqSledContractOption option = new ReqSledContractOption();
            option.addToSledContractIdList((int) sledContractId);
            SledContractPage contractPage = api.getContractOnlineStub().reqSledContract(option, 0, 1);

            if (contractPage.getPageSize() > 0) {
                sledCommodityId = (long) contractPage.getPage().get(0).getSledCommodityId();
                contractCommodityIdMap.put(sledContractId, sledCommodityId);
                putCommodityIdCodeContractId(sledCommodityId.intValue(), contractPage.getPage().get(0).getSledContractCode(), (int) sledContractId);
            } else {
                AppLog.e("contract not found: " + option);
                throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "sled contract id not found: " + sledContractId);
            }
        }

        return sledCommodityId;
    }

    public QuotationItem getLatestQuotation(long sledContractId) {
        if (latestQuotationMap == null) {
            latestQuotationMap = new HashMap<>();
        }
        return latestQuotationMap.get(sledContractId);
    }

    public void setLatestQuotationMap(long sledContractId, QuotationItem latestQuotation) {
        this.latestQuotationMap.put(sledContractId, latestQuotation);
    }

    public synchronized long getSettlementTime(long sledContractId) throws TException {

        long now = System.currentTimeMillis();
        long sledCommodityId = getCommodityIdByContractId(sledContractId);
        if (commoditySettlementTimestamp == null) {
            commoditySettlementTimestamp = new HashMap<>();
        }
        Long timestamp = commoditySettlementTimestamp.get(sledCommodityId);
        if (timestamp == null || timestamp <= now) {
            timestamp = getSettlementTimestamp(sledCommodityId, now);
            AppLog.i("settlementTimestamp: " + sledCommodityId + "-" + timestamp);
        }
        if (timestamp > 0) {
            commoditySettlementTimestamp.put(sledCommodityId, timestamp);
        }
        return timestamp;
    }

    public long getSettlementTimestamp(long sledCommodityId, long processTimestampMs) {
        IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        TradingTimeAnalysisor analysisor = new TradingTimeAnalysisor(contractApi);
        analysisor.setCommodityId(sledCommodityId);
        analysisor.setProcessTimestampMs(processTimestampMs);
        AppLog.d("begin analysisor trade time: ");
        try {
            if (analysisor.analysis()) {
                AppLog.d("analysisor trade time success ");
                SettleTimeCalculator cal = new SettleTimeCalculator(analysisor);
                if (cal.calculate()) {
                    return cal.getTTLTimestampMs();
                }
            }
            AppLog.w("Trade time not found: sledCommodityId -- " + sledCommodityId);
        } catch (Exception e) {
            AppLog.e("Trade time analysis error: sledCommodityId -- " + sledCommodityId);
            AppLog.e(e.getMessage(), e);
        }
        return 0;
    }

    public long getmSubAccountId() {
        return mSubAccountId;
    }
}
