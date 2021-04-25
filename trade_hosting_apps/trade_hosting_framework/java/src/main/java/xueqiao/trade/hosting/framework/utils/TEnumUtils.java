package xueqiao.trade.hosting.framework.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.thrift.TEnum;

public class TEnumUtils {
    public static <T extends TEnum> Set<Byte> toSetByte(Set<T> enumSet) {
        if (enumSet == null) {
            return null;
        }
        
        Set<Byte> resultSet = new HashSet<Byte>();
        Iterator<T> it = enumSet.iterator();
        while(it.hasNext()) {
            resultSet.add((byte)it.next().getValue());
        }
        return resultSet;
    }
    
    public static <T extends TEnum> Set<Short> toSetShort(Set<T> enumSet) {
        if (enumSet == null) {
            return null;
        }
        
        Set<Short> resultSet = new HashSet<Short>();
        Iterator<T> it = enumSet.iterator();
        while(it.hasNext()) {
            resultSet.add((short)it.next().getValue());
        }
        return resultSet;
    }
    
    public static <T extends TEnum> Set<Integer> toSetInt(Set<T> enumSet) {
        if (enumSet == null) {
            return null;
        }
        
        Set<Integer> resultSet = new HashSet<Integer>();
        Iterator<T> it = enumSet.iterator();
        while(it.hasNext()) {
            resultSet.add(it.next().getValue());
        }
        return resultSet;
    }
}
