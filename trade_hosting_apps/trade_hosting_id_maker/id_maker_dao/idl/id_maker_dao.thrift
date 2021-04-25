/**
  *  id_maker_dao
  */

namespace java org.soldier.platform.id_maker_dao

include "comm.thrift"

struct IdMakerInfo{
	1:required i32 type;
	5:optional i64 id;
	6:optional i32 allocSize;
	10:optional string desc;
	15:optional i32 createTimestamp;
	16:optional i32 lastmodifTimestamp;
}

struct IdAllocResult {
	1:required i64 beginId;
	2:required i32 allocSize;
}

struct IdMakerInfoList {
	1:optional i32 totalCount;
	5:optional list<IdMakerInfo> resultList;
}

struct IdMakerQueryOption {
	1:optional i32 type;
	2:optional string desc;
}

service(11) IdMakerDao {
		IdAllocResult 1:allocIds(1:comm.PlatformArgs platformArgs, 2:i32 type) throws(1:comm.ErrorInfo err);
		
		void 2:registerType(1:comm.PlatformArgs platformArgs, 2:IdMakerInfo info) throws(1:comm.ErrorInfo err);
		void 3:updateType(1:comm.PlatformArgs platformArgs, 2:IdMakerInfo info) throws (1:comm.ErrorInfo err);
		IdMakerInfo 4:getIdMakerInfoByType(1:comm.PlatformArgs platformArgs, 2:i32 type) throws(1:comm.ErrorInfo err);
		IdMakerInfoList 5:queryIdMakerTypeInfoList(1:comm.PlatformArgs platformArgs, 
				2:i32 pageIndex, 3:i32 pageSize, 4:IdMakerQueryOption option) throws(1:comm.ErrorInfo err);
}