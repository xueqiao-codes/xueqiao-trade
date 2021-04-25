package xueqiao.trade.hosting.entry.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class XQOrderID {
    
    public static XQOrderID fromString(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return null;
        }
        
        String[] splits = StringUtils.split(orderId, '_');
        if (splits == null || splits.length < 5) {
            return null;
        }
        
        if (!NumberUtils.isDigits(splits[0])
            || !NumberUtils.isDigits(splits[1])
            || !NumberUtils.isDigits(splits[2])
            || !NumberUtils.isDigits(splits[3])
            || !NumberUtils.isDigits(splits[4])) {
            return null;
        }
        
        XQOrderID xqOrderID = new XQOrderID(orderId);
        xqOrderID.setMachineId(NumberUtils.toLong(splits[0]));
        xqOrderID.setSubAccountId(NumberUtils.toLong(splits[1]));
        xqOrderID.setSubUserId(NumberUtils.toInt(splits[2]));
        return xqOrderID;
    }
    
    private String orderId;
    private long machineId;
    private long subAccountId;
    private int subUserId;
    
    private XQOrderID(String orderId) {
        this.orderId = orderId;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }
    
    public String toString() {
        return orderId;
    }

    public int getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(int subUserId) {
        this.subUserId = subUserId;
    }
}
