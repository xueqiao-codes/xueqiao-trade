package xueqiao.trade.hosting.position.statis.daemon;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.app.Constant;
import xueqiao.trade.hosting.position.statis.daemon.config.DaemonConfig;
import xueqiao.trade.hosting.position.statis.service.handler.SourceAssignPositionHandler;
import xueqiao.trade.hosting.position.statis.service.handler.SourceHostingXQTradeHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;

/**
 * 删除一个月以外的备份导入数据
 * 每天删除一次
 */
public class DeleteImportDataDaemon extends AbstractDaemon {
	@Override
	public boolean isTaskTime() {
		long currentMillis = System.currentTimeMillis();
		long timeOffsets = currentMillis % Constant.MILLIS_PER_DAY;
		if (timeOffsets >= 0 && timeOffsets < DaemonConfig.DAEMON_SLEEP_PERIOD_DELETE_IMPORT_DATA) {
			return true;
		}
		return false;
	}

	@Override
	public void doTask() throws ErrorInfo, Throwable {

		if (AppReport.TRACE) {
			AppReport.trace(this.getClass(), "DeleteImportDataDaemon -- run task : delete import data one month before");
		}

		// 删除一个月以前的成交数据
		new SourceHostingXQTradeHandler().deleteHostingXQTradeOneMonthBefore();
		// 删除一个月以前的分配持仓数据
		new SourceAssignPositionHandler().deleteAssignPositionOneMonthBefore();
	}

	@Override
	public long getSleepPeriod() {
		return DaemonConfig.DAEMON_SLEEP_PERIOD_DELETE_IMPORT_DATA;
	}
}
