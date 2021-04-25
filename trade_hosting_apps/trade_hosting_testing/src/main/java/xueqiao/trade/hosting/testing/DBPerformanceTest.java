package xueqiao.trade.hosting.testing;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBPerformanceTest {

    public static final int TEST_TABLE_SLICE_COUNT = 4;
    public static final int TEST_THREADS_NUM = 4;

    public static class TestDB extends DBBase {
        private static TestDB sInstance;
        public static TestDB Global() {
            if (sInstance == null) {
                synchronized (TestDB.class) {
                    if (sInstance == null) {
                        sInstance = new TestDB();
                    }
                }
            }
            return sInstance;
        }

        @Override
        protected String getDBName() {
            return "dbtest_perf";
        }

        @Override
        public int getVersion() {
            return 2;
        }

        @Override
        public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
            new TestTable(null, -1).onUpgradeOneStep(operator, targetVersion);
            new TestTable(null, -1, true).onUpgradeOneStep(operator, targetVersion);
            for (int index = 0; index < TEST_TABLE_SLICE_COUNT; ++index) {
                new TestTable(null, index).onUpgradeOneStep(operator, targetVersion);
            }
        }
    }

    public static class TestRecord {
        private long key;
        private String value;

        public long getKey() {
            return key;
        }

        public void setKey(long key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class TestTable extends TableHelper<TestRecord> implements IDBTable {
        private long tableIndex;
        private boolean partitioned;

        public TestTable(Connection conn, long tableIndex) {
            this(conn, tableIndex, false);
        }

        public TestTable(Connection conn, long tableIndex, boolean partitioned) {
            super(conn);
            this.tableIndex = tableIndex;
            this.partitioned = partitioned;
        }

        public long getTableIndex() {
            return tableIndex;
        }

        public int insert(TestRecord r) throws SQLException {
            PreparedFields pf = new PreparedFields();
            pf.addLong("Fkey", r.getKey());
            pf.addString("Fvalue", r.getValue());

            return super.insert(pf);
        }

        public TestRecord get(long key) throws SQLException {
            SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
            qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, "Fkey=?", key);
            return super.getItem(qryBuilder);
        }

        @Override
        public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
            if (2 == targetVersion) {
                StringBuilder createSqlBuilder = new StringBuilder(128);
                createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                        .append("Fkey BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                        .append("Fvalue VARCHAR(64) NOT NULL DEFAULT '',")
                        .append("PRIMARY KEY(Fkey)")
                        .append(") ENGINE=InnoDB CHARACTER SET UTF8 ");
                if (partitioned) {
                    createSqlBuilder.append("PARTITION BY HASH(Fkey) PARTITIONS " + TEST_TABLE_SLICE_COUNT);
                }
                operator.execute(createSqlBuilder.toString());
            }
        }

        @Override
        protected String getTableName() throws SQLException {
            if (tableIndex < 0) {
                if (partitioned) {
                    return "t_partition";
                } else {
                    return "t_none_slice";
                }
            } else {
                return "t_slice_" + tableIndex;
            }
        }

        @Override
        public TestRecord fromResultSet(ResultSet rs) throws Exception {
            TestRecord r = new TestRecord();
            r.setKey(rs.getLong("Fkey"));
            r.setValue(rs.getString("Fvalue"));
            return r;
        }
    }

    private static interface TableTestFactory {
        TestTable getTable(Connection conn, long testKey);
    }

    public static class TableTestFactoryPartitioned implements TableTestFactory {

        @Override
        public TestTable getTable(Connection conn, long testKey) {
            return new TestTable(conn, -1, true);
        }
    }

    public static class TableTestFactoryNoneSlice implements  TableTestFactory {

        @Override
        public TestTable getTable(Connection conn, long testKey) {
            return new TestTable(conn, -1);
        }
    }

    public static class TableTestFactorySlice implements  TableTestFactory {

        @Override
        public TestTable getTable(Connection conn, long testKey) {
            return new TestTable(conn, testKey%TEST_TABLE_SLICE_COUNT);
        }
    }

    private static String[] INIT_DATABASES = new String[]{
            "dbtest_perf"
    };

    private static void initDatabases() throws Exception {
        Connection conn = null;
        try {
            conn = DBSingleConnection.getNoneDbConnection();
            for (String database : INIT_DATABASES) {
                new QueryRunner().update(conn,"drop database if exists " + database);

                StringBuilder sqlBuilder = new StringBuilder(128);
                sqlBuilder.append("CREATE DATABASE ")
                        .append(database)
                        .append(";");
                new QueryRunner().update(conn, sqlBuilder.toString());
            }
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static class RunCaseThread extends Thread {
        public long startTimestampMs;
        public long endTimestampMs;

        public long maxOpUs = 0;
        public long minOpUs = Long.MAX_VALUE/10000;

        public int index;
        public int testCount;

        public int lowerThanOneMsCount = 0;
        public int biggerThanOneMsCount = 0;
        public int biggerThanTenMsCount = 0;


        public TableTestFactory factory;

        public long getPeriodMs() {
            return endTimestampMs - startTimestampMs;
        }

        public RunCaseThread(int index, int testCount, TableTestFactory factory) {
            this.index = index;
            this.testCount = testCount;
            this.factory = factory;
        }

        public void run() {
            startTimestampMs = System.currentTimeMillis();

            for (int num = 0; num < testCount; ++num) {
                long key = index + num * TEST_THREADS_NUM;
                try {
                    long opStartNs = System.nanoTime();
                    new DBStepHelper<Void>(TestDB.Global()) {

                        @Override
                        public void onPrepareData() throws ErrorInfo, Exception {
                        }

                        @Override
                        public void onUpdate() throws ErrorInfo, Exception {
                            TestRecord r = new TestRecord();
                            r.setKey(key);
                            r.setValue(String.valueOf(key));
                            factory.getTable(getConnection(), key).insert(r);
                        }

                        @Override
                        public Void getResult() {
                            return null;
                        }
                    }.execute();

                    long peroidNs = System.nanoTime() - opStartNs;
                    if (peroidNs > maxOpUs*1000) {
                        maxOpUs = peroidNs/1000;
                    }
                    if (peroidNs < minOpUs*1000) {
                        minOpUs = peroidNs/1000;
                    }

                    if (peroidNs < 1000*1000) {
                        lowerThanOneMsCount += 1;
                    } else if (peroidNs >= 1000*1000 && peroidNs <= 10 * 1000*1000) {
                        biggerThanOneMsCount += 1;
                    } else {
                        biggerThanTenMsCount += 1;
                    }

                } catch (ErrorInfo errorInfo) {
                    AppLog.e("factory=" + factory.getClass().getSimpleName() + ", key=" + key);
                }
            }

            endTimestampMs = System.currentTimeMillis();
        }
    }

    public static void testRun(int testCount, TableTestFactory factory) throws InterruptedException {
        RunCaseThread testThreads[] = new RunCaseThread[TEST_THREADS_NUM];
        for (int index = 0; index < TEST_THREADS_NUM; ++index) {
            testThreads[index] = new RunCaseThread(index, testCount, factory);
            testThreads[index].start();
        }

        for (int index = 0; index < TEST_THREADS_NUM; ++index) {
            testThreads[index].join();

            System.out.println(factory.getClass().getSimpleName() + " index=" + index
                    + ", startTimestampMs=" + testThreads[index].startTimestampMs
                    + ", endTimstampMs=" + testThreads[index].endTimestampMs
                    + ", periodMs=" + testThreads[index].getPeriodMs()
                    + ", maxOpUs=" + testThreads[index].maxOpUs
                    + ", minOpUs=" + testThreads[index].minOpUs
                    + ", everyOPTimestampUs=" + (testThreads[index].getPeriodMs() * 1000)/testCount
                    + ", lowerThanOneMsCount=" + testThreads[index].lowerThanOneMsCount
                    + ", biggerThanOneMsCount=" + testThreads[index].biggerThanOneMsCount
                    + ", biggerThanTenMsCount=" + testThreads[index].biggerThanTenMsCount);
        }
    }

    public static void startTest(int testCount) throws Exception {
        initDatabases();
        TestDB.Global().init();

        testRun(testCount, new TableTestFactoryPartitioned());
        testRun(testCount, new TableTestFactoryNoneSlice());
        testRun(testCount, new TableTestFactorySlice());
    }
}
