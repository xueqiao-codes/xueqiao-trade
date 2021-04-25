package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.struct.FileOperator;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;

public class AssetDataFileCalculator extends AssetBaseCalculator<FileOperator> {

    public AssetDataFileCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.QUOTATION_DATA_FILE_KEY;
    }

    @Override
    public void onCalculate(FileOperator operator) throws Exception {
        if (FileOperator.WRITE.equals(operator)) {
            save(getFile(false));
        } else if (FileOperator.READ.equals(operator)) {
            read();
        }
//        save(getFile(true));
    }

    private void save(File dataFile) throws ErrorInfo, IOException {

        if (dataFile == null) {
            return;
        }
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }

        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(this.mAccountId);
        String jsonData = new Gson().toJson(contractGlobal);
        FileOutputStream fileOutputStream = new FileOutputStream(dataFile);
        fileOutputStream.write(jsonData.getBytes());
        fileOutputStream.close();
    }

    private File getFile(boolean isForHour) throws ErrorInfo, IOException {
        IHostingManageApi api = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
        File file = api.getHostingDataDir();
        File dataFile = null;
        if (file.exists()) {
            String basePath = file.getAbsolutePath();
            String folderName = "asset";
            String path = basePath + "/" + folderName;
            File assetDirectory = new File(path);
            if (!assetDirectory.exists()) {
                assetDirectory.mkdir();
            }
            String prefix = String.valueOf(mAccountId);
            if (isForHour) {
                Calendar calendar = Calendar.getInstance();
                prefix = prefix + "_" + calendar.get(Calendar.DAY_OF_WEEK) + "_" + calendar.get(Calendar.HOUR_OF_DAY);
            }
            String suffix = ".data";
            String dataPath = path + "/" + prefix + suffix;
            dataFile = new File(dataPath);
        }
        return dataFile;
    }

    public void read() throws ErrorInfo, IOException {

        File dataFile = getFile(false);
        if (dataFile == null || !dataFile.exists()) {
            return;
        }

        String jsonData = new String(Files.readAllBytes(Paths.get(dataFile.getAbsolutePath())));
        ContractGlobal globalData = new Gson().fromJson(jsonData, ContractGlobal.class);
        AssetGlobalDataFactory.getInstance().setContractGlobal(this.mAccountId, globalData);
    }
}
