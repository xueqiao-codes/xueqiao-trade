package xueqiao.trade.hosting.push.utils;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import io.netty.buffer.ByteBuf;

public class TByteBufOutputTransport extends TTransport {
	private ByteBuf mByteBuf;
	
	public TByteBufOutputTransport(ByteBuf outputBuf) {
		this.mByteBuf = outputBuf;
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
		throw new TTransportException("unsupported read for " + getClass().getSimpleName());
	}

	@Override
	public void write(byte[] buf, int off, int len) throws TTransportException {
		mByteBuf.writeBytes(buf, off, len);
	}
	
	public ByteBuf getByteBuf() {
		return mByteBuf;
	}
}
