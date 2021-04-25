package xueqiao.trade.hosting.position.fee.core.bean;

import com.antiy.error_code.ErrorCodeInner;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

/**
 * 易盛接口保证金和手续费的计算方式
 *  从接口获取的CalculateMode是ASCII码，这里避免转码，直接使用ASCII值
 */
public enum ESAPICalculateMode {
    /*
     * 混合模式：比例 + 定额 （该方式公限于手续费）
     * 大于0.01的部分为定额
     * 小于0.01的部分为比例，如0.001为比例收取1%
     * */
    API_CALCULATE_MODE_COMBINE(48), // 对应 0

    /*
     * 比例模式
     * 雪橇按金额 - byMoney
     * */
    API_CALCULATE_MODE_PERCENTAGE(49),  // 对应 1

    /*
     * 定额模式
     * 雪橇按手数 - byVolume
     * */
    API_CALCULATE_MODE_QUOTA(50); // 对应 2

    private final int value;

    ESAPICalculateMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ESAPICalculateMode findByValue(int value) throws ErrorInfo {
        switch (value) {
            case 48:
                return API_CALCULATE_MODE_COMBINE;
            case 49:
                return API_CALCULATE_MODE_PERCENTAGE;
            case 50:
                return API_CALCULATE_MODE_QUOTA;
            default:
                throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "es api calculateMode error, value : " + value);
        }
    }
}
