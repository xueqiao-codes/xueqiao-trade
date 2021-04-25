package org.soldier.platform.id_maker_dao.storage.table;

import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.SQLException;

public class AllocIdBySectTable implements IDBTable  {

    private void registerType(IDBOperator operator
                , int type   // 注册类型
                , long initilialId // 初始水位
                , long allocSize  // 每次分配大小
                , String desc) throws SQLException {
        int currentTimestamp = (int)(System.currentTimeMillis()/ 1000);
        StringBuilder sqlBuilder = new StringBuilder(128);
        sqlBuilder.append("INSERT INTO ").append(getTableName())
                  .append(" SET Fsect=").append(type)
                  .append(", Fid=").append(initilialId)
                  .append(", Falloc_size=").append(allocSize)
                  .append(", Fdesc='").append(desc).append("'")
                  .append(", Fcreate_timestamp=").append(currentTimestamp)
                  .append(", Flastmodify_timestamp=").append(currentTimestamp);
        operator.execute(sqlBuilder.toString());
    }

    public String getTableName() {
        return "tallocid_bysect";
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fsect INT UNSIGNED NOT NULL,")
                            .append("Fid BIGINT UNSIGNED NOT NULL DEFAULT 1000,")
                            .append("Falloc_size INT UNSIGNED NOT NULL DEFAULT 500,")
                            .append("Fdesc VARCHAR(128) NOT NULL DEFAULT '',")
                            .append("Fcreate_timestamp INT NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestamp INT NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Fsect)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());

            registerType(operator, 200,1000, 1000, "组合ID生成器");
            registerType(operator, 201, 1000, 10, "交易账户ID生成器");
            registerType(operator, 202, 1000, 1000, "执行订单ID生成器");
            registerType(operator, 203, 1000, 1000, "执行成交ID生成器");
            registerType(operator, 204, 1000, 1000, "执行成交腿ID生成器");
            registerType(operator, 205, 1000, 10, "子账户ID生成器");
            registerType(operator, 206, 1000, 1000, "雪橇成交ID生成器");
            registerType(operator, 215, 1000, 1000, "持仓与资金结算ID生成器");
            registerType(operator, 218, 1000, 1000, "成交明细id生成器");

            return ;
        }
        if (2 == targetVersion) {
            registerType(operator, 230, 1000, 1000, "统计持仓之持仓数据ID");
            registerType(operator, 231,1000, 1000, "统计持仓之持仓明细数据ID");
            registerType(operator, 232, 1000, 1000, "统计持仓之持仓小单元数据ID");
            registerType(operator,233, 1000, 1000, "统计持仓之平仓数据ID");
            return ;
        }
        if (3 == targetVersion) {
            registerType(operator, 234, 1000, 1000, "统计持仓之平仓明细数据ID");

            registerType(operator, 237, 1000, 1000, "持仓调整之持仓录入明细ID");
            registerType(operator, 238, 1000, 1000, "持仓调整之持仓分配ID");
            registerType(operator, 239, 1000, 1000, "持仓调整之持仓核对ID");

            return ;
        }
        if (4 == targetVersion) {
            registerType(operator, 235, 1000, 1000, "持仓统计之平仓汇总数据ID");
            return ;
        }
        if (5 == targetVersion) {
            registerType(operator, 236, 1000, 1000, "持仓统计之平仓小单元数据ID");
            return ;
        }
        if (6 == targetVersion) {
            registerType(operator, 245, 1000, 1000, "持仓统计之成交关联数据ID");
            return ;
        }
    }
}
