package xueqiao.trade.hosting.position.statis.service.report;

import com.antiy.error_code.ErrorCodeOuter;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class StatErrorCode {

	public static ErrorInfo innerError = new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "inner error");
	public static ErrorInfo errorExchangeRateNotFound = new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "exchange rate not found");
	public static ErrorInfo errorCurrencyTypeInvalid = new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "currency type invalid");
	public static ErrorInfo errorAddPositionWithBlankUnit = new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "units should not be blank");
	public static ErrorInfo errorAddPositionItemIdNotMatch = new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "itemId not match");
	public static ErrorInfo errorCheckNetPosition = new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "contract net position not match, please check");

	public static ErrorInfo errorInvalidParam = new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "invalid parameter");
	public static ErrorInfo errorInvalidTargetType = new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "invalid targetType");
	public static ErrorInfo errorInvalidStatDirection = new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "invalid statDirection");
	public static ErrorInfo errorAssert = new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "assert error");
}
