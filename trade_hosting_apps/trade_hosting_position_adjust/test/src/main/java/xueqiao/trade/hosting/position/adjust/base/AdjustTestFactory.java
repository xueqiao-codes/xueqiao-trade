package xueqiao.trade.hosting.position.adjust.base;

import xueqiao.trade.hosting.test.util.PackageClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdjustTestFactory {
    private static AdjustTestFactory ourInstance = new AdjustTestFactory();

    public static AdjustTestFactory getInstance() {
        return ourInstance;
    }

    private Map<String, AdjustTest> testMap;

    private AdjustTestFactory() {

    }

    public void initTests() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        testMap = new HashMap<>();
        String packageName = "xueqiao.trade.hosting.position.adjust.test";
        Set<String> classNames = PackageClass.getClassName(packageName, false);

        for (String className : classNames) {
            Class onwClass = Class.forName(className);
            AdjustTest test = (AdjustTest) onwClass.newInstance();
            testMap.put(test.getName(), test);
        }
    }

    public Set<String> getTestNames() {
        return testMap.keySet();
    }

    public AdjustTest getTest(String testName) {
        return testMap.get(testName);
    }
}
