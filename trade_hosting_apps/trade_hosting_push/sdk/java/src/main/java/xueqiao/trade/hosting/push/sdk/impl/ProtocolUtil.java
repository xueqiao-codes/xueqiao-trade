package xueqiao.trade.hosting.push.sdk.impl;

import java.nio.ByteBuffer;

import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.logger.AppLog;

@SuppressWarnings("rawtypes")
public class ProtocolUtil {
	public static < T extends TBase> T unSerialize(
			TProtocolFactory factory
			, TTransport inputTransport
			, Class<T> clazz) {
		TProtocol protocol = factory.getProtocol(inputTransport);
		try {
			T instance = clazz.newInstance();
			instance.read(protocol);
			return instance;
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		} 
		return null;
	}
	
	public static < T extends TBase > T unSerialize(
			TProtocolFactory factory
			, byte[] packet
			, Class<T> clazz) {
		if (packet == null) {
			return null;
		}
		return unSerialize(factory, packet, 0, packet.length, clazz);
	}
	
	public static < T extends TBase > T unSerialize(
			TProtocolFactory factory
			, byte[] packet
			, int offset
			, int length
			, Class<T> clazz) {
		if (packet == null || packet.length <= 0 || clazz == null) {
			return null;
		}
		
		return unSerialize(factory, new TMemoryInputTransport(packet, offset, length), clazz);
		
	}
	
	public static < T extends TBase> void serialize(TProtocolFactory protocolFactory
			, TTransport outputTransport, T instance) {
		if (instance == null) {
			return ;
		}
		
		TProtocol protocol = protocolFactory.getProtocol(outputTransport);
		try {
			instance.write(protocol);
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
	public static < T extends TBase> ByteBuffer serialize(TProtocolFactory protocolFactory, T instance) {
		if (instance == null) {
			return null;
		}
		
		AutoExpandingBufferWriteTransport outputTransport 
			= new AutoExpandingBufferWriteTransport(256, 2.0);
		serialize(protocolFactory, outputTransport, instance);
		return ByteBuffer.wrap(outputTransport.getBuf().array(), 0, outputTransport.getPos());
	}
}
