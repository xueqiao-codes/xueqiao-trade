package xueqiao.trade.hosting.position.statis.core.calculator.backup;

import com.antiy.error_code.ErrorCodeInner;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCacheManager;
import xueqiao.trade.hosting.position.statis.core.calculator.AbstractCalculator;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QuotationBackupCalculator extends AbstractCalculator<Void, Void> {

    private static final long THREAD_KEY = 0;
    private static final String BACKUP_FILE_SUFFIX = ".backup";

    private static final String CONTRACT_QUOTATION_BACKUP = "contract_quotation";
    private static final String COMPOSE_QUOTATION_BACKUP = "compose_quotation";

    public QuotationBackupCalculator() {
        super(THREAD_KEY);
    }

    @Override
    public Void onCalculate(Void param) throws ErrorInfo {
        backup(CONTRACT_QUOTATION_BACKUP);
        backup(COMPOSE_QUOTATION_BACKUP);
        return null;
    }

    public static void recoveryContractQuotation() throws ErrorInfo {
        File dataFile = getFile(CONTRACT_QUOTATION_BACKUP);
        if (dataFile == null || !dataFile.exists()) {
            return;
        }
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get(dataFile.getAbsolutePath())));
            if (StringUtils.isNotBlank(jsonData)) {
                StatQuotationCacheManager.getInstance().recoveryCacheFromJson(jsonData);
            }
        } catch (Throwable throwable) {
            AppReport.reportErr("QuotationBackupCalculator # recoveryContractQuotation fail", throwable);
        }
    }

    public static void recoveryComposeQuotation() throws ErrorInfo {
        File dataFile = getFile(COMPOSE_QUOTATION_BACKUP);
        if (dataFile == null || !dataFile.exists()) {
            return;
        }
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get(dataFile.getAbsolutePath())));
            if (StringUtils.isNotBlank(jsonData)) {
                StatQuoCombCacheManager.getInstance().recoveryCacheFromJson(jsonData);
            }
        } catch (Throwable throwable) {
            AppReport.reportErr("QuotationBackupCalculator # recoveryComposeQuotation fail", throwable);
        }
    }

    private void backup(String backupFile) throws ErrorInfo {
        String jsonData = null;
        File dataFile = null;
        if (CONTRACT_QUOTATION_BACKUP.equals(backupFile)) {
            dataFile = getFile(backupFile);
            if (dataFile == null) {
                return;
            }
            jsonData = StatQuotationCacheManager.getInstance().getCacheJson();
        } else if (COMPOSE_QUOTATION_BACKUP.equals(backupFile)) {
            dataFile = getFile(backupFile);
            if (dataFile == null) {
                return;
            }
            jsonData = StatQuoCombCacheManager.getInstance().getCacheJson();
        } else {
            throw new ErrorInfo(ErrorCodeInner.ILLEGAL_OPERATION_ERROR.getErrorCode(), "unknown backupFile");
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(dataFile);
            fileOutputStream.write(jsonData.getBytes());
        } catch (FileNotFoundException e) {
            throw new ErrorInfo(StatErrorCode.innerError.getErrorCode(), e.getMessage());
        } catch (IOException e) {
            throw new ErrorInfo(StatErrorCode.innerError.getErrorCode(), e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static File getFile(String fileName) throws ErrorInfo {
        IHostingManageApi api = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
        File file = null;
        try {
            file = api.getHostingDataDir();
        } catch (Exception e) {
            AppReport.reportErr("QuotationBackupCalculator # getFile ---- getHostingDataDir fail", e);
        }
        File dataFile = null;
        if (file != null && file.exists()) {
            String basePath = file.getAbsolutePath();
            String folderName = AppConfig.APP_NAME;
            String path = basePath + "/" + folderName;
            File assetDirectory = new File(path);
            if (!assetDirectory.exists()) {
                assetDirectory.mkdir();
            }
            String dataPath = path + "/" + fileName + BACKUP_FILE_SUFFIX;
            dataFile = new File(dataPath);
            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    throw new ErrorInfo(StatErrorCode.innerError.getErrorCode(), e.getMessage());
                }
            }
        }
        return dataFile;
    }
}
