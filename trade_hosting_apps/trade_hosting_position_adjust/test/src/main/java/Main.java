import xueqiao.trade.hosting.position.adjust.base.AdjustTest;
import xueqiao.trade.hosting.position.adjust.base.AdjustTestFactory;
import xueqiao.trade.hosting.test.util.PrintUtil;

public class Main {

    public static void main(String[] args) {
        try {
            PrintUtil.printCommandLine("Init test ...");
            AdjustTestFactory.getInstance().initTests();
            PrintUtil.printCommandLine("Init test success.");
        } catch (Exception e) {
            PrintUtil.printCommandLine("Init test fail.");
            e.printStackTrace();
            return;
        }

        if (args == null || args.length == 0) {
            prompt();
            return;
        }

        String testName = args[0];
        AdjustTest test = getTest(testName);
        if (test == null) {
            prompt();
            return;
        }

        test.run();
    }

    private static void prompt() {
        PrintUtil.printCommandLine("This is main test, use arguments: ");
        for (String testName : AdjustTestFactory.getInstance().getTestNames()) {
            PrintUtil.printCommandLine(testName);
        }
    }

    private static AdjustTest getTest(String testName) {
        return AdjustTestFactory.getInstance().getTest(testName);
    }
}
