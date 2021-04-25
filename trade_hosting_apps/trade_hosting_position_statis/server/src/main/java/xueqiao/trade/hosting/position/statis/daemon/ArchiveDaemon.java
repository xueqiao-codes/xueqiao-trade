package xueqiao.trade.hosting.position.statis.daemon;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.app.Constant;
import xueqiao.trade.hosting.position.statis.daemon.config.DaemonConfig;
import xueqiao.trade.hosting.position.statis.service.ArchivePositionService;
import xueqiao.trade.hosting.position.statis.service.util.TimeUtil;

/**
 * 归档
 * 每天0点做一次归档
 */
public class ArchiveDaemon extends AbstractDaemon {

	private ArchivePositionService archivePositionService = new ArchivePositionService();

	@Override
	public boolean isTaskTime() {
		long currentMillis = System.currentTimeMillis();
		long currentDateZeroTimestamps = TimeUtil.getCurrentDateZeroMillis(currentMillis);
		long timeOffsets = currentMillis - currentDateZeroTimestamps;
		if (timeOffsets >= 0 && timeOffsets < DaemonConfig.DAEMON_SLEEP_PERIOD_ARCHIVE) {
			return true;
		}
		return false;
//		return true;
	}

	@Override
	public void doTask() throws ErrorInfo {
		archivePositionService.archive();
	}

	@Override
	public long getSleepPeriod() {
		return DaemonConfig.DAEMON_SLEEP_PERIOD_ARCHIVE;
	}
}
