package xueqiao.trade.hosting.storage.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;

public class HostingConfigFileTable extends TableHelper<ConfigFileEntry> implements IDBTable {
    private static final String TABLE_NAME = "thosting_config_file";
    
    public HostingConfigFileTable(Connection conn) {
        super(conn);
    }
    
    public ConfigFileEntry getEntry(String configArea, String configKey) throws SQLException {
        return getEntry(configArea, configKey, false);
    }
    
    public ConfigFileEntry getEntry(String configArea, String configKey, boolean forUpdate) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(configArea));
        Preconditions.checkArgument(StringUtils.isNotEmpty(configArea));
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fconfig_area=?", configArea);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fconfig_key=?", configKey);
        
        return super.getItem(queryBuilder, forUpdate);
    }
    
    public int addEntry(ConfigFileEntry newEntry) throws SQLException {
        Preconditions.checkNotNull(newEntry);
        Preconditions.checkArgument(StringUtils.isNotEmpty(newEntry.getConfigArea()));
        Preconditions.checkArgument(StringUtils.isNotEmpty(newEntry.getConfigKey()));
        
        PreparedFields fields = new PreparedFields();
        fields.addString("Fconfig_area", newEntry.getConfigArea());
        fields.addString("Fconfig_key", newEntry.getConfigKey());
        configEntry2PreparedFields(newEntry, fields);
        fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis()/1000));
        fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
        
        return super.insert(fields);
    }
    
    public int updateEntry(ConfigFileEntry updateEntry) throws SQLException {
        Preconditions.checkNotNull(updateEntry);
        Preconditions.checkArgument(StringUtils.isNotEmpty(updateEntry.getConfigArea()));
        Preconditions.checkArgument(StringUtils.isNotEmpty(updateEntry.getConfigKey()));
        
        PreparedFields fields = new PreparedFields();
        configEntry2PreparedFields(updateEntry, fields);
        fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
        
        return super.update(fields, "Fconfig_area=? AND Fconfig_key=?"
                , updateEntry.getConfigArea(), updateEntry.getConfigKey());
    }
    
    public int deleteEntry(String configArea, String configKey) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(configArea));
        Preconditions.checkArgument(StringUtils.isNotEmpty(configKey));
        
        return super.delete("Fconfig_area=? AND Fconfig_key=?"
                , configArea, configKey);
    }
    
    public int clearArea(String configArea) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(configArea));
        return super.delete("Fconfig_area=?", configArea);
    }
    
    private void configEntry2PreparedFields(ConfigFileEntry configEntry, PreparedFields fields) {
        if (configEntry.getConfigVersion() >= 0) {
            fields.addInt("Fconfig_version", configEntry.getConfigVersion());
        }
        if (configEntry.getConfigFilePath() != null) {
            fields.addString("Fconfig_file_path", configEntry.getConfigFilePath());
        }
        if (configEntry.getConfigFileMd5() != null) {
            fields.addString("Fconfig_file_md5", configEntry.getConfigFileMd5());
        }
    }

    @Override
    public ConfigFileEntry fromResultSet(ResultSet rs) throws Exception {
        ConfigFileEntry entry = new ConfigFileEntry();
        entry.setConfigArea(rs.getString("Fconfig_area"));
        entry.setConfigKey(rs.getString("Fconfig_key"));
        entry.setConfigVersion(rs.getInt("Fconfig_version"));
        entry.setConfigFilePath(rs.getString("Fconfig_file_path"));
        entry.setConfigFileMd5(rs.getString("Fconfig_file_md5"));
        entry.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
        entry.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
        return entry;
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (7 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE ").append(TABLE_NAME).append("(")
                      .append("Fconfig_area VARCHAR(8) NOT NULL DEFAULT '',")
                      .append("Fconfig_key VARCHAR(64) NOT NULL DEFAULT '',")
                      .append("Fconfig_version INT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fconfig_file_path VARCHAR(256) NOT NULL DEFAULT '',")
                      .append("Fconfig_file_md5 VARCHAR(65) NOT NULL DEFAULT '',")
                      .append("Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("PRIMARY KEY(Fconfig_area, Fconfig_key)")
                      .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(sqlBuilder.toString());
        }
    }

}
