package xueqiao.trade.hosting.proxy.handler;

import com.longsheng.xueqiao.goose.thriftapi.client.GooseAoStub;
import org.apache.thrift.TException;

public class VerifyCodeHandler {

    public boolean isInvalidate(String tel, String verifyCode) throws TException {
        boolean validate = new GooseAoStub().verifySmsCode(tel, verifyCode);
        return !validate;
    }

    public boolean isInvalidate(String tel, String verifyCode, boolean clear) throws TException {
        boolean validate = new GooseAoStub().verifySmsCodeForClear(tel, verifyCode, clear);
        return !validate;
    }

}
