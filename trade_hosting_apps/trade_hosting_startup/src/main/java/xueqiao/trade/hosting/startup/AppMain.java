package xueqiao.trade.hosting.startup;

import com.google.common.collect.ImmutableMap;
import com.satikey.tools.supervisord.Supervisord;
import com.satikey.tools.supervisord.exceptions.SupervisordException;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.DbUtil;
import org.soldier.platform.attr.AttrReporterFactory;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;

import java.sql.Connection;
import java.util.Map;

public class AppMain {
    private static final String EMPTY_SLEEP_START = "EMPTY_SLEEP|";

    private static final String[] PROCESSE_NAMES = new String[] {
            "trade_hosting_id_maker",
            "trade_hosting_contract",
            "trade_hosting_storage_daemon",
            "trade_hosting_dealing",
            "trade_hosting_quot_dispatcher",
            "trade_hosting_quot_comb",
            EMPTY_SLEEP_START + "15",
            "trade_hosting_upside_entry",
            "trade_hosting_tradeaccount_data",
            "trade_hosting_position_fee",
            EMPTY_SLEEP_START + "15",
            "trade_hosting_asset",
            "trade_hosting_risk_manager",
            EMPTY_SLEEP_START + "15",
            "trade_hosting_arbitrage",
            EMPTY_SLEEP_START + "10",
            "trade_hosting_history",
            "trade_hosting_position_adjust",
            "trade_hosting_position_statis",
            "trade_hosting_tasknote",
            "trade_hosting_push",
            "trade_hosting_entry"
    };

    private static void waitForDB() {
        while(true) {
            Connection conn = null;
            try {
                conn = DBSingleConnection.getNoneDbConnection();
                if (conn != null) {
                    break;
                }
            } catch (Throwable e) {
                AppLog.i(e.getMessage());
            } finally {
                DbUtil.closeQuietly(conn);
            }

            AppLog.i("wait for db ...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    private static void waitForMessageBus() {
        MessageBusAndRouteAgentDetector detector = new MessageBusAndRouteAgentDetector();
        while(true) {
            try {
                if (detector.detectOnce()) {
                    Thread.sleep(500); // 留给MessageBus和RouteAgent完整启动的时间预留
                    break;
                }
                AppLog.i("waiting for message_bus and route_agent ...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    }

    private static void startupApps() {
        Supervisord supervisor = Supervisord
                .connect().auth("sled", "sled123");

        for (String processName : PROCESSE_NAMES) {
            try {
                if (processName.startsWith(EMPTY_SLEEP_START)) {
                    int sleepSeconds = Integer.parseInt(
                            processName.substring(EMPTY_SLEEP_START.length()));
                    AppLog.i("sleep " + sleepSeconds + "s for process starting");
                    Thread.sleep(sleepSeconds * 1000);
                    continue;
                }

                Map<String, Object> response = (Map)supervisor.getProcessInfo(processName);

                System.out.println(response);
                String statename = (String)response.get("statename");
                Integer pid = (Integer)response.get("pid");
                AppLog.i(processName + ", pid =" + pid);
                if (pid != 0 || statename.equalsIgnoreCase("STARTING")) {
                    continue;
                }

                boolean result = supervisor.startProcess(processName, true);
                AppLog.i("starting process " + processName + ", result=" + result);
            } catch (SupervisordException e) {
                AppLog.e(e.getMessage());
            } catch (Throwable e) {
                AppLog.e(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        AppLog.init("apps/trade_hosting_startup");
        waitForDB();
        waitForMessageBus();
        startupApps();

        Monitor monitor = new Monitor();
        HostingMessageContext.Global().createConsumerAgent("hosting_startup", monitor);

        AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                "xueqiao.trade.hosting.process.keepalive"
                , ImmutableMap.of("processname", "trade_hosting_startup")), 1);

        monitor.run();
    }
}
