package xueqiao.quotation.push.sdk;

public class UserMsg {
	private String msgType;
	private byte[] msgContent;
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public byte[] getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(byte[] msgContent) {
		this.msgContent = msgContent;
	}
}
