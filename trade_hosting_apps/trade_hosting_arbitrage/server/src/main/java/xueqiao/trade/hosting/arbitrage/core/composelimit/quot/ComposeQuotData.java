package xueqiao.trade.hosting.arbitrage.core.composelimit.quot;

import net.objecthunter.exp4j.Expression;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

/**
 * 组合腿数据条目
 * @author wangli
 *
 */
public class ComposeQuotData {
    private HostingComposeLeg mComposeLeg;
    private QuotationItem mLastQuotationItem;
    private Expression mExpr; // 反算公式
    private String mExprFormular;
    
    public HostingComposeLeg getComposeLeg() {
        return mComposeLeg;
    }
    public void setComposeLeg(HostingComposeLeg mComposeLeg) {
        this.mComposeLeg = mComposeLeg;
    }
    
    public QuotationItem getLastQuotationItem() {
        return mLastQuotationItem;
    }
    public void setLastQuotationItem(QuotationItem lastQuotationItem) {
        this.mLastQuotationItem = lastQuotationItem;
    }
    
    public Expression getExpr() {
        return mExpr;
    }

    public void setExpr(String exprFormular, Expression expr) {
        this.mExprFormular = exprFormular;
        this.mExpr = expr;
    }

    public String getExprFormular() {
        return this.mExprFormular;
    }
    
    public Double getAskPrice1() {
        if (mLastQuotationItem == null) {
            return null;
        }
        if (mLastQuotationItem.getAskPriceSize() <= 0) {
            return null;
        }
        if (!PriceUtils.isAppropriatePrice(mLastQuotationItem.getAskPrice().get(0))) {
            return null;
        }
        return mLastQuotationItem.getAskPrice().get(0);
    }
    
    public long getAskQty1() {
        if (mLastQuotationItem == null || mLastQuotationItem.getAskQtySize() <= 0) {
            return 0;
        }
        return mLastQuotationItem.getAskQty().get(0);
    }
    
    public Double getBidPrice1() {
        if (mLastQuotationItem == null) {
            return null;
        }
        if (mLastQuotationItem.getBidPriceSize() <= 0) {
            return null;
        }
        if (!PriceUtils.isAppropriatePrice(mLastQuotationItem.getBidPrice().get(0))) {
            return null;
        }
        return mLastQuotationItem.getBidPrice().get(0);
    }
    
    public long getBidQty1() {
        if (mLastQuotationItem == null || mLastQuotationItem.getBidQtySize() <= 0) {
            return 0;
        }
        return mLastQuotationItem.getBidQty().get(0);
    }
    
}
