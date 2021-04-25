package xueqiao.trade.hosting.position.statis.core.cache.compose;

import com.antiy.error_code.ErrorCodeInner;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.position.statis.StatDirection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StatComposeLeg {
    public long sledContractId;
    public String contractSymbol;
    public int quantity;
    public String variableName;
    public StatDirection direction;

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }

    public String getContractSymbol() {
        return contractSymbol;
    }

    public void setContractSymbol(String contractSymbol) {
        this.contractSymbol = contractSymbol;
    }

    public void setContractSymbol(HostingComposeLeg hostingComposeLeg) throws ErrorInfo {
        try {
            this.contractSymbol = getContractSymbol(hostingComposeLeg);
        } catch (UnsupportedEncodingException e) {
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), e.toString());
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public StatDirection getDirection() {
        return direction;
    }

    public void setDirection(StatDirection direction) {
        this.direction = direction;
    }

    public static String getContractSymbol(HostingComposeLeg hostingComposeLeg) throws UnsupportedEncodingException {
        StringBuilder contractSymbolBuilder = new StringBuilder(64);
        contractSymbolBuilder.append(hostingComposeLeg.getSledExchangeMic())
                .append("|").append(hostingComposeLeg.getSledCommodityType())
                .append("|").append(hostingComposeLeg.getSledCommodityCode())
                .append("|").append(hostingComposeLeg.getSledContractCode());
        return URLEncoder.encode(contractSymbolBuilder.toString(), "UTF-8");
    }
}
