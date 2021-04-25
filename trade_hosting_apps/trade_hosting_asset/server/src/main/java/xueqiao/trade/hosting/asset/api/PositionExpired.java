package xueqiao.trade.hosting.asset.api;

import com.antiy.error_code.ErrorCodeOuter;
import com.longsheng.xueqiao.contract.standard.thriftapi.ContractStatus;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.thriftapi.TradeHostingAssetErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

public class PositionExpired {

    public void remove(long subAccount, long sledContractId) throws ErrorInfo {
        IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        SledContractDetails details = api.getContractDetailForSure(sledContractId);
        boolean isExpired = ContractStatus.EXPIRED.equals(details.getSledContract().getContractStatus());
        if (!isExpired) {
            throw new ErrorInfo(TradeHostingAssetErrorCode.ERROR_ASSET_CONTRACT_IS_NOT_EXPIRED.getValue(), "Contract is not expired.");
        }

        AssetBaseCalculator calculator = AssetCalculatorFactory.getInstance()
                .getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_EXPIRED_POSITION_REMOVE_KEY, subAccount);
        calculator.addTask(sledContractId);
    }
}
