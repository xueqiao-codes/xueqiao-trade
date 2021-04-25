package xueqiao.trade.hosting.position.fee.core.calculator;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;
import xueqiao.trade.hosting.position.fee.core.util.PositionRateUtil;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractCommissionTable;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractMarginTable;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideOriginalPositionRateTable;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.util.List;

import static xueqiao.trade.hosting.position.fee.core.common.thread.TaskThreadManager.GENERAL_THREAD_KEY;

public class UpdateContractPositionRateCalculator extends AbstractContractPositionRateCalculator<UpsidePositionRate> {

    public UpdateContractPositionRateCalculator() {
        super(GENERAL_THREAD_KEY);
    }

    @Override
    public Void onCalculate(UpsidePositionRate upsidePositionRate) throws TException {
        if (StringUtils.isBlank(upsidePositionRate.getSledContractCode())) {
            /*
             * 这是商品层级的信息
             * */
            updateContractPositionRate(upsidePositionRate, UpsideDataType.UDT_COMMODITY);
        } else {
            /*
             * 这是合约层级的信息
             * */
            updateContractPositionRate(upsidePositionRate, UpsideDataType.UDT_CONTRACT);
        }

        return null;
    }

    private void updateContractPositionRate(UpsidePositionRate upsidePositionRate, UpsideDataType upsideDataType) throws ErrorInfo {
        new DBTransactionHelper<Void>(PositionFeeDB.Global()) {
            UpsideContractCommissionTable commissionTable;
            UpsideContractMarginTable marginTable;

            List<UpsideContractCommission> upsideContractCommissionList;
            List<UpsideContractMargin> upsideContractMarginList;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                commissionTable = new UpsideContractCommissionTable(getConnection());
                marginTable = new UpsideContractMarginTable(getConnection());

                upsideContractCommissionList = commissionTable.queryByTradeAccountId(upsidePositionRate.getTradeAccountId(), upsidePositionRate.getSledCommodityId(), upsidePositionRate.getSledContractCode(), upsideDataType);
                upsideContractMarginList = marginTable.queryByTradeAccountId(upsidePositionRate.getTradeAccountId(), upsidePositionRate.getSledCommodityId(), upsidePositionRate.getSledContractCode());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (upsideContractCommissionList != null && !upsideContractCommissionList.isEmpty()) {
                    for (UpsideContractCommission upsideContractCommission : upsideContractCommissionList) {
                        UpsideContractCommission updateContractCommission = getUpsideContractCommission(upsideContractCommission, upsidePositionRate, upsideDataType);
                        if (updateContractCommission.getCommission() != null) {
                            /*
                             * 数据有效时才更新，否则保持旧数据
                             * */
                            commissionTable.update(updateContractCommission);
                        }
                    }
                }
                if (upsideContractMarginList != null && !upsideContractMarginList.isEmpty()) {
                    for (UpsideContractMargin upsideContractMargin : upsideContractMarginList) {
                        UpsideContractMargin updateContractMargin = getUpsideContractMargin(upsideContractMargin, upsidePositionRate, upsideDataType);
                        if (updateContractMargin.getMargin() != null) {
                            /*
                             * 数据有效时才更新，否则保持旧数据
                             * */
                            marginTable.update(updateContractMargin);
                        }
                    }
                }

                /*
                 * 重置脏数据状态
                 * */
                new UpsideOriginalPositionRateTable(getConnection()).resetDirtyStatus(upsidePositionRate.getTradeAccountId(), upsidePositionRate.getSledCommodityId(), upsidePositionRate.getSledContractCode());
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    private UpsideContractCommission getUpsideContractCommission(UpsideContractCommission originalUpsideContractCommission, UpsidePositionRate upsidePositionRate, UpsideDataType upsideDataType) {
        UpsideContractCommission upsideContractCommission = new UpsideContractCommission();
        ContractInfo contractInfo = PositionRateUtil.getContractInfo(upsidePositionRate, originalUpsideContractCommission.getContractInfo());
        CommissionInfo commissionInfo;
        if (upsidePositionRate.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            commissionInfo = PositionRateUtil.getCTPCommissionInfo(upsidePositionRate);
        } else {
            commissionInfo = PositionRateUtil.getESCommissionInfo(upsidePositionRate);
        }
        upsideContractCommission.setSubAccountId(originalUpsideContractCommission.getSubAccountId());
        upsideContractCommission.setContractInfo(contractInfo);
        upsideContractCommission.setCommission(commissionInfo);
        upsideContractCommission.setDataType(upsideDataType);
        return upsideContractCommission;
    }

    private UpsideContractMargin getUpsideContractMargin(UpsideContractMargin originalUpsideContractMargin, UpsidePositionRate upsidePositionRate, UpsideDataType upsideDataType) {
        UpsideContractMargin upsideContractMargin = new UpsideContractMargin();
        ContractInfo contractInfo = PositionRateUtil.getContractInfo(upsidePositionRate, originalUpsideContractMargin.getContractInfo());
        MarginInfo marginInfo;
        if (upsidePositionRate.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            marginInfo = PositionRateUtil.getCTPMarginInfo(upsidePositionRate);
        } else {
            marginInfo = PositionRateUtil.getESMarginInfo(upsidePositionRate);
        }
        upsideContractMargin.setSubAccountId(originalUpsideContractMargin.getSubAccountId());
        upsideContractMargin.setContractInfo(contractInfo);
        upsideContractMargin.setMargin(marginInfo);
        upsideContractMargin.setDataType(upsideDataType);
        return upsideContractMargin;
    }
}
