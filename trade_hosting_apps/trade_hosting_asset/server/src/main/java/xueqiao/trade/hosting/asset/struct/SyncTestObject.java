package xueqiao.trade.hosting.asset.struct;

public class SyncTestObject implements Runnable {

    private Object obj = new Object();

    private Object obj1 = new Object();

    private int num = 100;

    private int callNum = 200;

    public void callRun() {
        synchronized (obj) {
            while (callNum > 0) {
                System.out.println("call: " + callNum);
                callNum--;
                sleep();
            }
        }
    }

    public void syncRun() {
        synchronized (obj1) {
            while (num > 0) {
                System.out.println("sync: " + num);
                num--;
                sleep();
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        syncRun();
    }
}
