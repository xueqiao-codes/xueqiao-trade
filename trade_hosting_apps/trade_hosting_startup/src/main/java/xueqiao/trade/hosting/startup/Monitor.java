package xueqiao.trade.hosting.startup;

import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.DbUtil;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.attr.AttrReporterFactory;
import org.w3c.dom.Attr;
import xueqiao.trade.hosting.events.MessageBusDetectEvent;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;

import java.sql.Connection;

public class Monitor implements IMessageConsumer  {

    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.CLEAR_QUEUE_INIT;
    }

    @Override
    public void onInit() throws Exception {
    }

    @consume(MessageBusDetectEvent.class)
    ConsumeResult onHandleMessageBusDetectEvent(MessageBusDetectEvent event) {
        AttrReporterFactory.getDefault().inc(AttrReporterFactory.getDefault().requireKey(
                "xueqiao.trade.hosting.message.bus.detect.received.count", null), 1);
        return ConsumeResult.CONSUME_OK;
    }

    private void pingMysql() {
        Connection conn = null;
        try {
            conn = DBSingleConnection.getNoneDbConnection();
            if (conn != null) {
                AttrReporterFactory.getDefault().inc(AttrReporterFactory.getDefault().requireKey(
                        "xueqiao.trade.hosting.mysql.ping.success.count", null), 1);
            }
        } catch (Throwable e) {
            AppLog.i(e.getMessage());
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

            try {
                MessageBusDetectEvent detectEvent = new MessageBusDetectEvent();
                detectEvent.setDetectTimestampMs(System.currentTimeMillis());
                HostingMessageContext.Global().getSenderAgent().sendMessageDirectly(detectEvent);

                pingMysql();
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    }


}
