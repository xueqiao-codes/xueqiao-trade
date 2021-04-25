package xueqiao.trade.hosting.position.statis.service.bean;

public class RecoveryPositionData extends PositionData {
    /*
    * 是否与原数据合并
    * */
    private boolean needCombine;

    public boolean isNeedCombine() {
        return needCombine;
    }

    public void setNeedCombine(boolean needCombine) {
        this.needCombine = needCombine;
    }
}
