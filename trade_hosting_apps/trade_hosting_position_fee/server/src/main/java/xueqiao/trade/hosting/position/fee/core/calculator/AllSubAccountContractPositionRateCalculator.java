package xueqiao.trade.hosting.position.fee.core.calculator;

import org.apache.thrift.TException;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.position.fee.core.api.HostingSubAccountApi;

import java.util.List;

import static xueqiao.trade.hosting.position.fee.core.common.thread.TaskThreadManager.GENERAL_THREAD_KEY;

/**
 * 更新所有操作账号的上手合约数据
 * 服务启动时在上手源数据获取完成后调用一次
 * 其他时间不需要调用，其他时间的更新，由路由变动事件来驱动
 * */
public class AllSubAccountContractPositionRateCalculator extends AbstractContractPositionRateCalculator<Void> {

    protected AllSubAccountContractPositionRateCalculator() {
        super(GENERAL_THREAD_KEY);
    }

    @Override
    public Void onCalculate(Void param) throws TException {
        List<HostingSubAccount> hostingSubAccountList = HostingSubAccountApi.querySubAccounts();
        if (hostingSubAccountList == null || hostingSubAccountList.isEmpty()) {
            return null;
        }
        for (HostingSubAccount hostingSubAccount : hostingSubAccountList) {
            updateOneSubAccountContractPositionRate(hostingSubAccount.getSubAccountId());
        }
        return null;
    }
}
