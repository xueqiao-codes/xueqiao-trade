package xueqiao.trade.hosting.position.fee.core.bean;

import xueqiao.trade.hosting.BrokerTechPlatform;

/**
 * 从上手获取的持仓费率数据
 */
public class UpsidePositionRate {
    private long tradeAccountId;
    private long sledCommodityId;
    private String sledContractCode = "";
    private int sledCommodityType;
    private String sledCommodityCode;
    private String sledExchangeMic;
    private BrokerTechPlatform techPlatform;
    private UpsidePositionRateDetail detail;
    private long lastmodifyTimestampMs;
    /*
     * 该条数据是否已处理
     * true: 新更新未处理的数据
     * false: 已处理
     * */
    private boolean isDirty;

    /*
    * 因为上手的数据里有商品层级（通用数据）和合约层级（专用数据）的，
    * 每次更新数据前，将对应资金账号的所有数据先设置为无效，
    * 然后将对应的上手数据写入（状态为有效）。这样，数据更新完成后，数据表
    * 里为无效的数据，则为原上手合约级的数据取消了。
    *
    * 雪橇用户合约层面持仓费率更新时，只用有效数据更新
    * */
    private boolean isValid;

    public long getTradeAccountId() {
        return tradeAccountId;
    }

    public void setTradeAccountId(long tradeAccountId) {
        this.tradeAccountId = tradeAccountId;
    }

    public long getSledCommodityId() {
        return sledCommodityId;
    }

    public void setSledCommodityId(long sledCommodityId) {
        this.sledCommodityId = sledCommodityId;
    }

    public String getSledContractCode() {
        return sledContractCode;
    }

    public void setSledContractCode(String sledContractCode) {
        this.sledContractCode = sledContractCode;
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

    public String getSledExchangeMic() {
        return sledExchangeMic;
    }

    public void setSledExchangeMic(String sledExchangeMic) {
        this.sledExchangeMic = sledExchangeMic;
    }

    public BrokerTechPlatform getTechPlatform() {
        return techPlatform;
    }

    public void setTechPlatform(BrokerTechPlatform techPlatform) {
        this.techPlatform = techPlatform;
    }

    public UpsidePositionRateDetail getDetail() {
        return detail;
    }

    public void setDetail(UpsidePositionRateDetail detail) {
        this.detail = detail;
    }

    public long getLastmodifyTimestampMs() {
        return lastmodifyTimestampMs;
    }

    public void setLastmodifyTimestampMs(long lastmodifyTimestampMs) {
        this.lastmodifyTimestampMs = lastmodifyTimestampMs;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
