package xueqiao.trade.hosting.asset.struct;

public class Main {

    public static void main(String[] args) {

//        try {
//
//            List<Long> list = new ArrayList<>();
//            list.add(5000L);
//            list.add(5000L);
//            RefreshTool.executeDelay(list, AssetCalculatorFactory.SUB_ACCOUNT_SETTLEMENT_KEY, 100L, 5000, TimeUnit.MILLISECONDS);
//            list.add(2000L);
//            RefreshTool.executeDelay(list, AssetCalculatorFactory.SUB_ACCOUNT_SETTLEMENT_KEY, 100L, 2000, TimeUnit.MILLISECONDS);
//            list.add(8000L);
//            RefreshTool.executeDelay(list, AssetCalculatorFactory.SUB_ACCOUNT_SETTLEMENT_KEY, 100L, 8000, TimeUnit.MILLISECONDS);
//            list.add(0L);
//            RefreshTool.executeDelay(list, AssetCalculatorFactory.SUB_ACCOUNT_SETTLEMENT_KEY, 100L, 0, TimeUnit.MILLISECONDS);
//        } catch (ErrorInfo errorInfo) {
//            errorInfo.printStackTrace();
//        }

        try {
            do {
                System.out.println("sleep 1 sec");
                Thread.sleep(1000);
            } while (true);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void testTimestamp(){
//        AssetGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(1431L);
//        long sledCommidityId = 10360L;
//        long time = System.currentTimeMillis() + RandomUtils.nextInt();
//        long settleTimestamp = globalData.getSettlementTimestamp(sledCommidityId, time);
//        AppLog.d("sledCommidityId: "+ sledCommidityId);
//        AppLog.d("time: "+ time);
//        AppLog.d("settleTimestamp: " + settleTimestamp);
    }
}
