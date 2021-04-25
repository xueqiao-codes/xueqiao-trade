package xueqiao.trade.hosting.updator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

public class DaemonMain {
    public static volatile boolean STOPPED = false;
    
    private static class ShutDownHook extends Thread {
        
        private Thread mainThread;
        
        public ShutDownHook(Thread mainThread) {
            this.mainThread = mainThread;
        }
        
        @Override
        public void run() {
            STOPPED = true;
            mainThread.interrupt();
        }
    }
    
    public static void main(String[] args) throws IOException {
        String apiBaseUrl = System.getenv("API_BASEURL");
        if (StringUtils.isEmpty(apiBaseUrl)) {
            AppLog.e("Env API_BASEURL is not set!");
            System.exit(-1);
        }
        String deployNamespace = System.getenv("DEPLOY_NAMESPACE");
        if (StringUtils.isEmpty(deployNamespace)) {
            deployNamespace = "xueqiao-trade-hostings";
        }
        String token = FileUtils.readFileToString(new File("/run/secrets/kubernetes.io/serviceaccount/token"));
        
        OCManager ocManager = new OCManager(apiBaseUrl, token, deployNamespace);
        IConfProvider confProvder = new ConfProviderImpl();
        
        AppUpdator updator = new AppUpdator(ocManager, confProvder);
        
        Runtime.getRuntime().addShutdownHook(new ShutDownHook(Thread.currentThread()));
        
        while(!STOPPED) {
            try {
                updator.runOnce();
            } catch (Throwable e) {
                AppLog.e(e.getMessage());
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
            }
        }
    }
}
