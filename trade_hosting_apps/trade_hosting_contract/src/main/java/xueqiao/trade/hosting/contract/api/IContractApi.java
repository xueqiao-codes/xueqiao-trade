package xueqiao.trade.hosting.contract.api;

import java.io.File;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

public interface IContractApi {
	public ContractVersionEntry getLastestVersion() throws ErrorInfo;
	public void updateLastestVersion(int lastestVersion) throws ErrorInfo;
	
	public File getStubFile() throws ErrorInfo;
	
	public static enum EApiMode {
        IN_HOSTING,  // 托管机内置API模式
        OUT_HOSTING, // 非托管机模式
    }
	
	public EApiMode getApiMode();
}
