package xueqiao.trade.hosting.push.server.data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  获取全部数据时进行拷贝的集合
 * @author wileywang
 *
 */
public class CopySet<T> {
	private Set<T> mSet;
	private ReentrantLock mLock;
	
	public CopySet() {
		this.mSet = new HashSet<T>();
		this.mLock = new ReentrantLock();
	}
	
	public boolean add(T data) {
		if (data == null) {
			return false;
		}
		mLock.lock();
		try {
			return mSet.add(data);
		} finally {
			mLock.unlock();
		}
	}
	
	public boolean containes(T data) {
		mLock.lock();
		try {
			return mSet.contains(data);
		} finally {
			mLock.unlock();
		}
	}
	
	public int count() {
		return mSet.size();
	}
	
	public void remove(T data) {
		if (data == null) {
			return ;
		}
		mLock.lock();
		try {
			mSet.remove(data);
		} finally {
			mLock.unlock();
		}
	}
	
	public Set<T> copyAll() {
		mLock.lock();
		try {
			HashSet<T> results = new HashSet<T>();
			results.addAll(mSet);
			return results;
		} finally {
			mLock.unlock();
		}
	}
	
	public void clear() {
		mLock.lock();
		try {
			mSet.clear();
		} finally {
			mLock.unlock();
		}
	}
	
	public Set<T> unsafedAll() {
		return mSet;
	}
	
}
