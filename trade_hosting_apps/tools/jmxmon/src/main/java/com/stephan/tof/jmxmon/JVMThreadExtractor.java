package com.stephan.tof.jmxmon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.stephan.tof.jmxmon.Constants.CounterType;
import com.stephan.tof.jmxmon.JVMThreadExtractor.ThreadInfo;
import com.stephan.tof.jmxmon.bean.FalconItem;
import com.stephan.tof.jmxmon.jmxutil.ProxyClient;

public class JVMThreadExtractor extends JVMDataExtractor<ThreadInfo> {

	public JVMThreadExtractor(ProxyClient proxyClient, int jmxPort, String jmxProcessName)
			throws IOException {
		super(proxyClient, jmxPort, jmxProcessName);
	}

	@Override
	public ThreadInfo call() throws Exception {
		int threadNum = getThreadMXBean().getThreadCount();
		int peakThreadNum = getThreadMXBean().getPeakThreadCount();
		
		ThreadInfo threadInfo = new ThreadInfo(threadNum, peakThreadNum);
		return threadInfo;
	}

	@Override
	public List<FalconItem> build(ThreadInfo jmxResultData) throws Exception {
		List<FalconItem> items = new ArrayList<FalconItem>();
		
		String tags = StringUtils.lowerCase("jmxport=" + getJmxPort()
                + ",processname=" + getJmxProcessName());
		
		// 将jvm信息封装成openfalcon格式数据
		FalconItem threadNumItem = new FalconItem();
		threadNumItem.setCounterType(CounterType.GAUGE.toString());
		threadNumItem.setEndpoint(Config.I.getHostname());
		threadNumItem.setMetric(StringUtils.lowerCase(Constants.threadActiveCount));
		threadNumItem.setStep(Constants.defaultStep);
		threadNumItem.setTags(tags);	
		threadNumItem.setTimestamp(System.currentTimeMillis() / 1000);
		threadNumItem.setValue(jmxResultData.getThreadNum());
		items.add(threadNumItem);
		
		FalconItem peakThreadNumItem = new FalconItem();
		peakThreadNumItem.setCounterType(CounterType.GAUGE.toString());
		peakThreadNumItem.setEndpoint(Config.I.getHostname());
		peakThreadNumItem.setMetric(StringUtils.lowerCase(Constants.threadPeakCount));
		peakThreadNumItem.setStep(Constants.defaultStep);
		peakThreadNumItem.setTags(tags);	
		peakThreadNumItem.setTimestamp(System.currentTimeMillis() / 1000);
		peakThreadNumItem.setValue(jmxResultData.getPeakThreadNum());
		items.add(peakThreadNumItem);
		
		return items;
	}
	
	class ThreadInfo {
		private final int threadNum;
		private final int peakThreadNum;
		public ThreadInfo(int threadNum, int peakThreadNum) {
			this.threadNum = threadNum;
			this.peakThreadNum = peakThreadNum;
		}
		
		/**
		 * @return the threadNum
		 */
		public int getThreadNum() {
			return threadNum;
		}
		
		/**
		 * @return the peakThreadNum
		 */
		public int getPeakThreadNum() {
			return peakThreadNum;
		}
	}
	
}
