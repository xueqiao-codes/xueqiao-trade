package xueqiao.trade.hosting.push.utils;

public class RefHolder <T> {
	public T mInstance;
	
	public RefHolder() {
	}
	
	public RefHolder(T instance) {
		this.mInstance = instance;
	}
	
	public T get() {
		return mInstance;
	}
	
	public void set(T instance) {
		this.mInstance = instance;
	}
}
