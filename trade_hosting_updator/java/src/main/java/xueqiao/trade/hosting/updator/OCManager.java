package xueqiao.trade.hosting.updator;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSession;

import org.soldier.base.logger.AppLog;

import com.openshift.internal.restclient.model.DeploymentConfig;
import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import com.openshift.restclient.ISSLCertificateCallback;

public class OCManager {
    
    private String mNamespace;
    private IClient mClient;
    
    public OCManager(String apiBaseUrl, String token, String namespace) {
        this.mNamespace = namespace;
        
        this.mClient = new ClientBuilder().sslCertificateCallback(new ISSLCertificateCallback() {
            @Override
            public boolean allowCertificate(X509Certificate[] chain) {
                return true;
            }

            @Override
            public boolean allowHostname(String hostname, SSLSession session) {
                return true;
            }
        }).usingToken(token).toCluster(apiBaseUrl).build();
        
    }
    
    public IClient getClient() {
        return mClient;
    }
    
    public Map<String, DeploymentConfig> getAllDC() {
        List<DeploymentConfig> dcList = mClient.list("DeploymentConfig", mNamespace);
        Map<String, DeploymentConfig> resultMap = new HashMap<>();
        if (dcList != null) {
            for (DeploymentConfig dc : dcList) {
                resultMap.put(dc.getName(), dc);
            }
        }
        return resultMap;
    }
    
    public DeploymentConfig createDC(DeploymentConfig dc) {
        AppLog.d("createDC " + dc.toJson());
        return mClient.create(dc, mNamespace);
    }
    
    public void deleteDC(DeploymentConfig dc) {
        dc.getNode().get("apiVersion").set("apps.openshift.io/v1");
        AppLog.d("deleteDc " + dc.getName() + ", apiVersion=" + dc.getApiVersion());
        mClient.delete(dc);
    }
    
    public DeploymentConfig updateDC(DeploymentConfig dc) {
        AppLog.d("updateDc " + dc.toJson());
        dc.setNamespace(mNamespace);
        
        return mClient.update(dc);
    }
}
