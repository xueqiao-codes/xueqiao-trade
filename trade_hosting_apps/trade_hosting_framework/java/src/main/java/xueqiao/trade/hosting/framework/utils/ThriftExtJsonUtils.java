package xueqiao.trade.hosting.framework.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TList;
import org.apache.thrift.protocol.TMap;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.StringFactory;

public class ThriftExtJsonUtils {
	private static TJSONProtocol.Factory JSON_FACTORY = new TJSONProtocol.Factory();
	
	public static String mapToJson(Map<String, String> mapProperties) throws TException {
		if (mapProperties == null) {
			return null;
		}
		AutoExpandingBufferWriteTransport outTransport = new AutoExpandingBufferWriteTransport(256, 2.0);
		TProtocol outProtocol = JSON_FACTORY.getProtocol(outTransport);
		
		outProtocol.writeMapBegin(new TMap(TType.STRING, TType.STRING, mapProperties.size()));
		for (Map.Entry<String, String> lIt : mapProperties.entrySet()) {
			outProtocol.writeString(lIt.getKey());
			outProtocol.writeString(lIt.getValue());
		}
		outProtocol.writeMapEnd();
        
		return StringFactory.netUtf8String(outTransport.getBuf().array(), 0, outTransport.getPos());
	}
	
	public static Map<String, String> mapFromJson(String properties) throws TException {
		if (StringUtils.isEmpty(properties)) {
			return null;
		}
		
		TMemoryInputTransport inTransport = new TMemoryInputTransport(properties.getBytes(Charset.forName("UTF-8")));
		TProtocol inProtocol = JSON_FACTORY.getProtocol(inTransport);
	
		org.apache.thrift.protocol.TMap mapBegin = inProtocol.readMapBegin();
		Map<String, String> resultMap = new HashMap<String, String>(mapBegin.size + 1);
		for (int i = 0; i < mapBegin.size; ++i) {
			String key = inProtocol.readString();
			String value = inProtocol.readString();
			resultMap.put(key, value);
		}
		inProtocol.readMapEnd();
		
		return resultMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends TBase> String mapToJsonTBase(Map<String, T> mapProperties) throws TException {
		if (mapProperties == null) {
			return null;
		}
		
		AutoExpandingBufferWriteTransport outTransport = new AutoExpandingBufferWriteTransport(256, 2.0);
		TProtocol outProtocol = JSON_FACTORY.getProtocol(outTransport);
		
		outProtocol.writeMapBegin(new TMap(TType.STRING, TType.STRUCT, mapProperties.size()));
		for (Map.Entry<String, T> lIt : mapProperties.entrySet()) {
			outProtocol.writeString(lIt.getKey());
			lIt.getValue().write(outProtocol);
		}
		outProtocol.writeMapEnd();
        
		return StringFactory.netUtf8String(outTransport.getBuf().array(), 0, outTransport.getPos());
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends TBase> Map<String, T> mapFromJsonTBase(String properties, Class<T> clazz) 
			throws TException, InstantiationException, IllegalAccessException {
		if (StringUtils.isEmpty(properties)) {
			return null;
		}
		
		TMemoryInputTransport inTransport = new TMemoryInputTransport(properties.getBytes(Charset.forName("UTF-8")));
		TProtocol inProtocol = JSON_FACTORY.getProtocol(inTransport);
	
		TMap mapBegin = inProtocol.readMapBegin();
		Map<String, T> resultMap = new HashMap<String, T>(mapBegin.size + 1);
		for (int i = 0; i < mapBegin.size; ++i) {
			String key = inProtocol.readString();
			T value = clazz.newInstance();
			value.read(inProtocol);
			resultMap.put(key, value);
		}
		inProtocol.readMapEnd();
		
		return resultMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends TBase> String listToJsonTBase(List<T> entries) throws TException {
		if (entries == null) {
			return null;
		}
		
		AutoExpandingBufferWriteTransport outTransport = new AutoExpandingBufferWriteTransport(256, 2.0);
		TProtocol outProtocol = JSON_FACTORY.getProtocol(outTransport);
		
		outProtocol.writeListBegin(new TList(TType.STRUCT, entries.size()));
		for (T entry : entries){
			entry.write(outProtocol);
		}
		outProtocol.writeListEnd();
		
		return StringFactory.netUtf8String(outTransport.getBuf().array(), 0, outTransport.getPos());
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends TBase> List<T> listFromJsonTBase(String listContents, Class<T> clazz) 
			throws TException, InstantiationException, IllegalAccessException {
		if (StringUtils.isEmpty(listContents)) {
			return null;
		}
		
		TMemoryInputTransport inTransport = new TMemoryInputTransport(
				listContents.getBytes(Charset.forName("UTF-8")));
		TProtocol inProtocol = JSON_FACTORY.getProtocol(inTransport);
		
		TList listBegin = inProtocol.readListBegin();
		List<T> resultList = new ArrayList<T>(listBegin.size + 1);
		for (int index = 0; index < listBegin.size; ++index) {
			T entry = clazz.newInstance();
			entry.read(inProtocol);
			resultList.add(entry);
		}
		inProtocol.readListEnd();
		return resultList;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static <T extends TBase> String toJsonTBase(T obj) throws TException{
		if (obj == null) {
			return null;
		}
		
		AutoExpandingBufferWriteTransport outTransport = new AutoExpandingBufferWriteTransport(256, 2.0);
		TProtocol outProtocol = JSON_FACTORY.getProtocol(outTransport);
		obj.write(outProtocol);
		return StringFactory.netUtf8String(outTransport.getBuf().array(), 0, outTransport.getPos());
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends TBase> T fromJsonTBase(String content, Class<T> clazz) 
			throws TException, InstantiationException, IllegalAccessException {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		
		TMemoryInputTransport inTransport = new TMemoryInputTransport(content.getBytes(Charset.forName("UTF-8")));
		TProtocol inProtocol = JSON_FACTORY.getProtocol(inTransport);
		T resultObj = clazz.newInstance();
		resultObj.read(inProtocol);
		
		return resultObj;
	}
	
	
}
