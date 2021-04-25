package xueqiao.trade.hosting.storage.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.watcher.file.FileWatcherModule;
import org.soldier.watcher.file.IFileWatcherListener;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;

import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;
import xueqiao.trade.hosting.storage.tradeaccount.BrokerAccessEntryProviderImpl;

public class BrokerAccessEntryWatcher implements IFileWatcherListener {
	
	public static class WatchEntry {
		private int brokerId;
		private int brokerAccessId;
		
		public int getBrokerId() {
			return brokerId;
		}
		public WatchEntry setBrokerId(int brokerId) {
			this.brokerId = brokerId;
			return this;
		}
		
		public int getBrokerAccessId() {
			return brokerAccessId;
		}
		public WatchEntry setBrokerAccessId(int brokerAccessId) {
			this.brokerAccessId = brokerAccessId;
			return this;
		}
		
		public String toFilePath() {
			StringBuilder filePathBuilder = new StringBuilder(64);
			filePathBuilder.append("/data/configs/qconf/xueqiao/broker/access/")
						   .append(brokerId)
						   .append("/")
						   .append(brokerAccessId);
			return filePathBuilder.toString();
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(128);
			builder.append("BrokerWatchEntry(").append(brokerId)
				   .append(",").append(brokerAccessId)
				   .append(")");
			return builder.toString();
		}
		
		@Override
		public boolean equals(Object t) {
			if (null == t || !(t instanceof WatchEntry)) {
				return false;
			}
			
			WatchEntry wt = (WatchEntry)t;
			return (this.brokerId) == wt.brokerId
					&& (this.brokerAccessId == wt.brokerAccessId);
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder()
					.append(this.brokerId)
					.append(brokerAccessId)
					.toHashCode();
		}
	}
	
	private ScheduledExecutorService scheduledService =  Executors.newSingleThreadScheduledExecutor();
	private Set<WatchEntry> needWatchedEntries = new HashSet<WatchEntry>();
	private Set<WatchEntry> watchingEntries = new HashSet<WatchEntry>();
	private BrokerAccessEntryProviderImpl mQconfEntryProvider = new BrokerAccessEntryProviderImpl();
	
	private boolean checkWatchEntries() {
		boolean allSuccess = true;
		
		HashSet<WatchEntry> needWatchedEntriesCp = null;
		synchronized(this) {
			needWatchedEntriesCp = new HashSet<WatchEntry>(needWatchedEntries);
		}
		
		for (WatchEntry entry : needWatchedEntriesCp) {
			if (watchingEntries.contains(entry)) {
				continue;
			}
			try {
				File wFile = new File(entry.toFilePath());
				if (checkRemoteExists(entry, wFile)) {
					AppLog.i("addWatchFile " + wFile.getAbsolutePath());
					FileWatcherModule.Instance().addWatchFile(wFile, BrokerAccessEntryWatcher.this);
					watchingEntries.add(entry);
					loadEntry(wFile);
				} else {
					// TODO alarm, found zookeeper data lose
					AppLog.w("checkWatchEntry WatchEntry=" + entry + " failed...");
					allSuccess = false;
				}
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
				allSuccess = false;
			}
		}
	
		Iterator<WatchEntry> it = watchingEntries.iterator();
		while(it.hasNext()) {
			WatchEntry entry = it.next();
			if (needWatchedEntriesCp.contains(entry)) {
				continue;
			}
		
			AppLog.i("removeWatchFile " + entry.toFilePath());
			FileWatcherModule.Instance().removeWatchFile(new File(entry.toFilePath()));
			it.remove();
		}
		
		return allSuccess;
	}
	
	private class CheckWatchEntryRunnable implements Runnable {
		@Override
		public void run() {
			try {
				AppLog.i("check broker access entry run......");
				if (!checkWatchEntries()) {
					scheduledService.schedule(new CheckWatchEntryRunnable(), 30, TimeUnit.SECONDS);
				}
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
	}
	
	private boolean checkRemoteExists(WatchEntry w, File wFile) throws ErrorInfo{
		if (new File(w.toFilePath()).exists()) {
			return true;
		}
		
		// 检查文件是否能正常获取
		BrokerAccessEntry entry = mQconfEntryProvider.getBrokerAccessEntry(w.getBrokerId(), w.getBrokerAccessId());
		if (entry != null) {
			return true;
		}
		return false;
	}
	
	public BrokerAccessEntryWatcher() {
		this.scheduledService = Executors.newSingleThreadScheduledExecutor();
	}

	public synchronized void syncWatchEntries(Set<WatchEntry> watchEntries) {
		needWatchedEntries = watchEntries;
		scheduledService.schedule(new CheckWatchEntryRunnable(), 0, TimeUnit.MILLISECONDS);
	}

	@Override
	public void onHandleFileChanged(File entryFile) {
		loadEntry(entryFile);
	}
	
	// 这个方法失败，说明出现必须手工接入的问题
	private synchronized void loadEntry(File entryFile) {
		AppLog.i("loadEntry entryFile=" + entryFile.getAbsolutePath());
		
		int retryTimes = 0;
		while((retryTimes++) < 3) {
			InputStream input = null;
			try {
				int brokerAccessId = NumberUtils.createInteger(entryFile.getName());
				int brokerId = NumberUtils.createInteger(entryFile.getParentFile().getName());
				
				input = new FileInputStream(entryFile);
				BrokerAccessEntry accessEntry 
					= ThriftExtJsonUtils.fromJsonTBase(IOUtils.toString(input, "UTF-8"), BrokerAccessEntry.class);
				if (accessEntry.getBrokerId() != brokerId
						|| accessEntry.getEntryId() != brokerAccessId) {
					throw new Exception("brokerAccessEntry content error!");
				}
				updateBrokerAccessEntryForAccounts(accessEntry);
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
				// TODO alarm, file content error!
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} finally {
				IOUtils.closeQuietly(input);
			}
		}
	}	
	
	private void updateBrokerAccessEntryForAccounts(BrokerAccessEntry entry) throws ErrorInfo {
		IHostingTradeAccountApi accountApi 
			= Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
	
		QueryTradeAccountOption queryOption = new QueryTradeAccountOption();
		queryOption.setTradeBrokerId(entry.getBrokerId());
		queryOption.setTradeBrokerAccessId(entry.getEntryId());
		
		PageResult<HostingTradeAccount> accountsResult
			= accountApi.queryTradeAccountPage(queryOption
					, new PageOption().setPageIndex(0).setPageSize(Integer.MAX_VALUE));
		for (HostingTradeAccount account : accountsResult.getPageList()) {
			accountApi.updateBrokerAccessEntry(account.getTradeAccountId(), entry);
		}
	}
}
