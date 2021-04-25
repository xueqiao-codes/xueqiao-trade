package org.soldier.platform.id_maker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.soldier.base.logger.AppLog;

public class IdMakerFactory {
	private static IdMakerFactory sInstance;
	
	public static IdMakerFactory getInstance() {
		if (sInstance == null) {
			synchronized(IdMakerFactory.class) {
				if (sInstance == null) {
					sInstance = new IdMakerFactory();
				}
			}
		}
		return sInstance;
	}
	
	private Map<Integer, IdMaker> idMakersInstances
		= new ConcurrentHashMap<Integer, IdMaker>();
	
	public IdMaker getIdMaker(int type) {
		IdMaker maker = idMakersInstances.get(type);
		if (maker != null) {
			return maker;
		}
		
		try {
			synchronized(this) {
				maker = idMakersInstances.get(type);
				if (maker == null) {
					maker = new IdMakerImpl(type);
					idMakersInstances.put(type, maker);
				}
			}
			return maker;
		} catch (IdException e) {
			AppLog.e(e.getMessage(), e);
		}
		return null;
	}
}
