package org.soldier.platform.id_maker;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.client.IdMakerDaoStub;
import org.soldier.platform.id_maker_dao.helper.IdMakerDaoStubFactory;

class IdMakerImpl extends IdMaker {
	private int type;
	private IdList currentIdList;
	private ReentrantLock lock;
	private int lastLevelSize;
	
	public IdMakerImpl(int type) throws IdException {
		this.type = type;
		currentIdList = new IdList();
		lock = new ReentrantLock();
	}
	
	public int getType() {
		return type;
	}
	
	public void setLastLevelSize(int size) {
		lastLevelSize = size;
	}

	private void allocIdFromDao() throws IdException {
		IdMakerDaoStub stub = IdMakerDaoStubFactory.getStub();
		try {
			IdAllocResult result = stub.allocIds(RandomUtils.nextInt(), 500, type);
			addIds(result.getBeginId(), result.getAllocSize());
			setLastLevelSize(result.getAllocSize());
		} catch (Exception e) {
			throw new IdException("Alloc id failed, " + e.getMessage());
		}
	}
	
	@Override
	public long createId() throws IdException{
		lock.lock();
		try {
			if (currentIdList.size() <= 0) {
				allocIdFromDao();
			}
			return currentIdList.popId();
		} finally {
			lock.unlock();
		}
	}

	private void addIds(long id, int size) throws IdException {
		try {
			currentIdList.addZone(id, id + size - 1);
			lastLevelSize = size;
		} catch (IdException e) {
			AppLog.e(e.getMessage(), e);
			throw e;
		}
	}
}
