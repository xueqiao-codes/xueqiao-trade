package org.soldier.platform.id_maker;

public abstract class IdMaker {
	public abstract long createId() throws IdException;
	
	public int createIdIntSafe() throws IdException {
		long id = createId();
		if (id >= Integer.MAX_VALUE || id <= Integer.MIN_VALUE) {
			throw new IdException("Value Overflow");
		}
		return (int)id;
	}
}
