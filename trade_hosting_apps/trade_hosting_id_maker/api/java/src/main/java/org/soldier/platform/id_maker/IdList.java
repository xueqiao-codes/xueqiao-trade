package org.soldier.platform.id_maker;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class IdList {
	// desc a  zone[begin, end]
	public static class Zone {
		private long begin;
		private long end;
		
		public Zone(long begin, long end) {
			this.begin = begin;
			this.end = end;
		}
		
		public long getBegin() {
			return begin;
		}
		
		public long getEnd() {
			return end;
		}
		
		public long size() {
			return end - begin + 1;
		}
		
		@Override
		public String toString() {
			return "[ " + begin + ":" + end + "]";
		}
	}
	
	private List<Zone> listZones = new LinkedList<Zone>();
	
	public IdList() {
	}
	
	public void addZone(long begin, long end) throws IdException {
		if (end < begin) {
			throw new IdException("Parameter error!");
		}
		
		Zone zone = new Zone(begin, end);
		long lastZoneEnd = Long.MIN_VALUE;
		int index = 0;
		for (Zone listZone : listZones) {
			if (lastZoneEnd < begin && end < listZone.getBegin()) {
				listZones.add(index, new Zone(begin, end));
				return ;
			}
			if ( (listZone.getBegin() <=  begin && begin <= listZone.getEnd())
				|| (listZone.getBegin() <= end && end <= listZone.getEnd())
				|| (begin <= listZone.getBegin() && listZone.getBegin() <= end)
				|| (begin <= listZone.getEnd() && listZone.getEnd() <= end)) {
				throw new IdException("Zone has duplicate section, listZone =" 
						+ listZone.toString() + ", addZone=" + zone.toString());
			}

			lastZoneEnd = listZone.getEnd();
			++index;
		}
		
		listZones.add(zone);
	}
	
	public long popId() throws IdException {
		if (listZones.isEmpty()) {
			throw new IdException("No Id Can be alloc");
		}
		
		Zone firstZone = listZones.get(0);
		long result = firstZone.begin++;
		if (firstZone.begin > firstZone.end) {
			listZones.remove(0);
		}
		
		return result;
	}
	
	public IdList popIds(int needSize) throws IdException {
		if (needSize > size()) {
			throw new IdException("Id is not enough");
		}
		
		IdList resultList = new IdList();
		ListIterator<Zone> it = listZones.listIterator();
		int remainNeedSize = needSize;
		while(it.hasNext()) {
			Zone zone = it.next();
			if (zone.size() <= remainNeedSize) {
				resultList.addZone(zone.getBegin(), zone.getEnd());
				remainNeedSize -= zone.size();
				it.remove();
			} else {
				resultList.addZone(zone.getBegin(), zone.begin + remainNeedSize - 1);
				zone.begin += remainNeedSize;
				remainNeedSize = 0;
			}
			
			if (remainNeedSize == 0) {
				break;
			}
		}
		
		return resultList;
	}
	
	public int size() {
		int totalSize = 0;
		for (Zone zone : listZones) {
			totalSize += zone.size();
		}
		return totalSize;
	}
	
	public List<Zone> getZones() {
		return listZones;
	}
}
