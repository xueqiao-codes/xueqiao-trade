package xueqiao.trade.hosting.position.fee.controller.base;

import org.apache.thrift.TException;

public interface IController {
    public void checkParams() throws TException;
}
