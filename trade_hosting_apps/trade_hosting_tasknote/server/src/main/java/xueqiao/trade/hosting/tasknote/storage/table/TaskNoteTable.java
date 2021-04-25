package xueqiao.trade.hosting.tasknote.storage.table;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNotePage;
import xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType;
import xueqiao.trade.hosting.tasknote.thriftapi.QueryTaskNoteOption;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskNoteTable extends TableHelper<HostingTaskNote> implements IDBTable{

    public TaskNoteTable(Connection conn) {
        super(conn);
    }

    public HostingTaskNote getTaskNote(HostingTaskNoteType noteType
            , HostingTaskNoteKey noteKey
            , boolean forUpdate) throws SQLException {
        Preconditions.checkNotNull(noteType);
        Preconditions.checkNotNull(noteKey);
        Preconditions.checkArgument(noteKey.isSetKey1() && noteKey.isSetKey2() && noteKey.isSetKey3());

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fnote_type=?", noteType.getValue());
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fnote_key1=?", noteKey.getKey1());
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fnote_key2=?", noteKey.getKey2());
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                ,"Fnote_key3=?", noteKey.getKey3());

        return super.getItem(qryBuilder, forUpdate);
    }

    public HostingTaskNotePage getTaskNotePage(QueryTaskNoteOption qryOption, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);
        Preconditions.checkArgument(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0);
        Preconditions.checkArgument(pageOption.isSetPageSize() && pageOption.getPageSize() > 0);

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        if (qryOption != null) {
            if (qryOption.isSetNoteType()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                    , "Fnote_type=?", qryOption.getNoteType().getValue());
            }
            if (qryOption.isSetKey1() && !qryOption.getKey1().isEmpty()) {
                qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND
                    , "Fnote_key1", qryOption.getKey1());
            }
            if (qryOption.isSetKey2() && !qryOption.getKey2().isEmpty()) {
                qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND
                    , "Fnote_key2", qryOption.getKey2());
            }
            if (qryOption.isSetKey3() && !qryOption.getKey3().isEmpty()) {
                qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND
                    , "Fnote_key3", qryOption.getKey3());
            }
        }
        qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Fcreate_timestampms");
        qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        HostingTaskNotePage resultPage = new HostingTaskNotePage();
        if (pageOption.isSetNeedTotalCount() && pageOption.isNeedTotalCount()) {
            resultPage.setTotalNum(super.getTotalNum(qryBuilder));
        }

        resultPage.setResultList(super.getItemList(qryBuilder));

        return resultPage;
    }

    public int addTaskNote(HostingTaskNote taskNote) throws SQLException {
        Preconditions.checkNotNull(taskNote);
        Preconditions.checkNotNull(taskNote.getNoteType());
        Preconditions.checkNotNull(taskNote.getNoteKey());
        Preconditions.checkArgument(taskNote.getNoteKey().isSetKey1()
                || taskNote.getNoteKey().isSetKey2()
                || taskNote.getNoteKey().isSetKey3());

        PreparedFields pf = new PreparedFields();
        pf.addInt("Fnote_type", taskNote.getNoteType().getValue());
        if (taskNote.getNoteKey().isSetKey1()) {
            pf.addLong("Fnote_key1", taskNote.getNoteKey().getKey1());
        }
        if (taskNote.getNoteKey().isSetKey2()) {
            pf.addLong("Fnote_key2", taskNote.getNoteKey().getKey2());
        }
        if (taskNote.getNoteKey().isSetKey3()) {
            pf.addString("Fnote_key3", taskNote.getNoteKey().getKey3());
        }

        if (taskNote.isSetNoteContent()) {
            pf.addString("Fnote_content", taskNote.getNoteContent());
        }
        if (taskNote.isSetCreateTimestampMs()) {
            pf.addLong("Fcreate_timestampms", taskNote.getCreateTimestampMs());
            pf.addLong("Flastmodify_timestampms", taskNote.getCreateTimestampMs());
        } else {
            pf.addLong("Fcreate_timestampms", System.currentTimeMillis());
            pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }

        return super.insert(pf);
    }

    public int delTaskNotes(HostingTaskNoteType noteType, HostingTaskNoteKey noteKey) throws SQLException {
        Preconditions.checkNotNull(noteType);
        Preconditions.checkNotNull(noteKey);

        StringBuilder delWhereSqlBuilder = new StringBuilder(64);
        List<Object> parameters = new ArrayList<>();
        delWhereSqlBuilder.append("Fnote_type=?");
        parameters.add(noteType.getValue());
        if (noteKey.isSetKey1()) {
            delWhereSqlBuilder.append(" AND Fnote_key1=?");
            parameters.add(noteKey.getKey1());
        }
        if (noteKey.isSetKey2()) {
            delWhereSqlBuilder.append(" AND Fnote_key2=?");
            parameters.add(noteKey.getKey2());
        }
        if (noteKey.isSetKey3()) {
            delWhereSqlBuilder.append(" AND Fnote_key3=?");
            parameters.add(noteKey.getKey3());
        }
        return super.delete(delWhereSqlBuilder.toString(), parameters.toArray());
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fnote_type INT NOT NULL DEFAULT 0,")
                            .append("Fnote_key1 BIGINT NOT NULL DEFAULT 0,")
                            .append("Fnote_key2 BIGINT NOT NULL DEFAULT 0,")
                            .append("Fnote_key3 VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fnote_content TEXT,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Fnote_type, Fnote_key1, Fnote_key2, Fnote_key3));");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "tnote";
    }

    @Override
    public HostingTaskNote fromResultSet(ResultSet rs) throws Exception {
        HostingTaskNote result = new HostingTaskNote();
        result.setNoteType(HostingTaskNoteType.findByValue(rs.getInt("Fnote_type")));
        result.setNoteKey(new HostingTaskNoteKey());
        result.getNoteKey().setKey1(rs.getLong("Fnote_key1"));
        result.getNoteKey().setKey2(rs.getLong("Fnote_key2"));
        result.getNoteKey().setKey3(rs.getString("Fnote_key3"));
        result.setNoteContent(rs.getString("Fnote_content"));
        result.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        result.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));

        return result;
    }
}
