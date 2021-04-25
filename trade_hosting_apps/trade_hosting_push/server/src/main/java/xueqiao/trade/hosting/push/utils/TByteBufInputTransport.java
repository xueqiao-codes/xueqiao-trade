package xueqiao.trade.hosting.push.utils;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import io.netty.buffer.ByteBuf;

public class TByteBufInputTransport extends TTransport {
	private ByteBuf mByteBuf;
	
	public TByteBufInputTransport(ByteBuf buffer) {
		this.mByteBuf = buffer;
	}
	
	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public void open() throws TTransportException {
	}

	@Override
	public void close() {
	}

	@Override
	public int read(byte[] buf, int off, int len) throws TTransportException {
		int bytesRemaining = mByteBuf.readableBytes();
	    int amtToRead = (len > bytesRemaining ? bytesRemaining : len);
	    if (amtToRead > 0) {
	    	mByteBuf.readBytes(buf, off, len);
	    }
		return amtToRead;
	}

	@Override
	public void write(byte[] buf, int off, int len) throws TTransportException {
		throw new TTransportException("Unsupported write for " + getClass().getSimpleName());
	}

}
