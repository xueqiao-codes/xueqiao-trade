package xueqiao.trade.hosting.updator.test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.internal.restclient.model.DeploymentConfig;
import com.openshift.internal.restclient.model.ImageStream;
import com.openshift.internal.util.JBossDmrExtentions;
import com.openshift.restclient.images.DockerImageURI;
import com.openshift.restclient.model.IContainer;

import xueqiao.trade.hosting.updator.OCManager;

public class TestMain {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        cal.setTimeInMillis(System.currentTimeMillis());
        
        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss");
        sdf.setCalendar(cal);
        System.out.println(sdf.format(cal.getTime()));
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        
        System.out.println(TimeZone.getDefault().inDaylightTime(new Date()));
        System.out.println( new DockerImageURI("docker-registry.default.svc:5000/xueqiao-trade"
                , null
                , "trade-hosting-apps"
                , "v52"));
        cal.setTime(new Date());
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        
        OCManager ocManager = new OCManager("https://devk8s.xueqiao.cn:8443"
                , "FmmbjNaMlXZqsJWz_VM_as1g2O86qDl0EMgSkHXEse4"
                , "xueqiao-trade");
//        Map<String, DeploymentConfig> allDcs = ocManager.getAllDC();
//        for (DeploymentConfig dc : allDcs.values()) {
//            IContainer c = dc.getContainer("trade-hosting-proxy");
//            System.out.println(c.getImage().getTag());
//        }
//        
//        ImageStream is = ocManager.getClient().get("ImageStream", "trade-hosting-apps", "xueqiao-trade");
//        System.out.println(is.getImageId("v52"));
//        
//        ModelNode status = JBossDmrExtentions.get(is.getNode(), is.getPropertyKeys(), "status.tags");
//        System.out.println(status.toJSONString(false));
//        System.out.println(getCreateDateTime("v56", is).toEpochSecond());
//        System.out.println(is.toPrettyString());
    }
    
    private static String getCreateTimestamp(String tagName, ImageStream is) {
        ModelNode tags = JBossDmrExtentions.get(is.getNode(), is.getPropertyKeys(), "status.tags");
        if (tags.getType() != ModelType.LIST || tagName == null) {
            return null;
        }

        List<ModelNode> tagWrappers = tags.asList();
        for (ModelNode tagWrapper : tagWrappers) {
            ModelNode tag = tagWrapper.get("tag");
            ModelNode items = tagWrapper.get("items");
            if (tag.asString().equals(tagName) && items.getType() == ModelType.LIST) {
                for (ModelNode itemWrapper : items.asList()) {
                    ModelNode created = itemWrapper.get("created");
                    if (created != null) {
                        return created.asString();
                    }
                }
                break;
            }
        }
        return null;
    }
    
    private static ZonedDateTime getCreateDateTime(String tagName, ImageStream is) {
        String createTimeStr = getCreateTimestamp(tagName, is);
        if (createTimeStr == null) {
            return null;
        }
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'HH:mm:ss'");
        return ZonedDateTime.parse(createTimeStr);
    }
    

}
