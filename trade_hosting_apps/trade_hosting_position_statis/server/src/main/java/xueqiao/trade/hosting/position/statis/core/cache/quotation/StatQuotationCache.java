package xueqiao.trade.hosting.position.statis.core.cache.quotation;

import xueqiao.quotation.QuotationItem;

public class StatQuotationCache {
    private String contractSymbol;
    private String sledExchangeCode;  // 雪橇交易所编码
    private int sledCommodityType;  // 雪橇商品类型
    private String sledCommodityCode;  // 雪橇商品编码
    private String sledContractCode;   // 雪橇合约月份编码
    private double lastPrice;
    private boolean isSetLastPrice;
    private double preSettlementPrice;
    private boolean isSetPreSettlementPrice;
    private long contractId;

    public StatQuotationCache() {
        isSetLastPrice = false;
        isSetPreSettlementPrice = false;
    }

    public String getContractSymbol() {
        return contractSymbol;
    }

    public void setContractSymbol(String contractSymbol) {
        this.contractSymbol = contractSymbol;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
        this.isSetLastPrice = true;
    }

    public double getPreSettlementPrice() {
        return preSettlementPrice;
    }

    public void setPreSettlementPrice(double preSettlementPrice) {
        this.preSettlementPrice = preSettlementPrice;
        this.isSetPreSettlementPrice = true;
    }

    public boolean isSetLastPrice() {
        return isSetLastPrice;
    }

    public void unsetLastPrice() {
        isSetLastPrice = false;
    }

    public boolean isSetPreSettlementPrice() {
        return isSetPreSettlementPrice;
    }

    public void unsetPreSettlementPrice() {
        isSetPreSettlementPrice = false;
    }

    public String getSledExchangeCode() {
        return sledExchangeCode;
    }

    public void setSledExchangeCode(String sledExchangeCode) {
        this.sledExchangeCode = sledExchangeCode;
    }

    public int getSledCommodityType() {
        return sledCommodityType;
    }

    public void setSledCommodityType(int sledCommodityType) {
        this.sledCommodityType = sledCommodityType;
    }

    public String getSledCommodityCode() {
        return sledCommodityCode;
    }

    public void setSledCommodityCode(String sledCommodityCode) {
        this.sledCommodityCode = sledCommodityCode;
    }

    public String getSledContractCode() {
        return sledContractCode;
    }

    public void setSledContractCode(String sledContractCode) {
        this.sledContractCode = sledContractCode;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public static StatQuotationCache getStatQuotationCache(QuotationItem latestQuotation) {
        StatQuotationCache statQuotationCache = new StatQuotationCache();
        statQuotationCache.setContractSymbol(latestQuotation.getContractSymbol());
        statQuotationCache.setSledExchangeCode(latestQuotation.getSledExchangeCode());
        statQuotationCache.setSledCommodityType(latestQuotation.getSledCommodityType());
        statQuotationCache.setSledCommodityCode(latestQuotation.getSledCommodityCode());
        statQuotationCache.setSledContractCode(latestQuotation.getSledContractCode());
        if (latestQuotation.isSetLastPrice()) {
            statQuotationCache.setLastPrice(latestQuotation.getLastPrice());
        }
        if (latestQuotation.isSetPreSettlementPrice()) {
            statQuotationCache.setPreSettlementPrice(latestQuotation.getPreSettlementPrice());
        }
        return statQuotationCache;
    }
}
