package xueqiao.trade.hosting.storage.apis;

import java.io.File;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingInfo;
import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.storage.apis.structs.HostingInitInfo;

public interface IHostingManageApi {
    /**
     *  初始化托管机
     */
	public void initHosting(HostingInitInfo info) throws ErrorInfo;
	
	/**
	 *  重置销毁托管机
	 */
	public void resetHosting(long machineId, String hostingAES16Key) throws ErrorInfo;
	
	/**
	 *  分配子用户ID
	 */
	public int allocSubUserId() throws ErrorInfo;
	
	/**
	 *  获取当前机器ID
	 */
	public long getMachineId() throws ErrorInfo;
	
	/**
	 * 获取机器运行模式
	 */
	public HostingRunningMode getRunningMode() throws ErrorInfo;
	
	/**
	 * 当前托管机信息
	 */
	public HostingInfo getHostingInfo(boolean needStaticsData) throws ErrorInfo;
	
	/**
	 *  获取当前托管机的数据文件存储路径
	 */
	public File getHostingDataDir() throws ErrorInfo;
	
	/**
	 *  托管机清理完毕
	 */
	public void clearFinished() throws ErrorInfo;
}
