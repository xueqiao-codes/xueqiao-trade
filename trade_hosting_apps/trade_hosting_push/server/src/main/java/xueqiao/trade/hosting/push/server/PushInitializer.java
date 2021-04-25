package xueqiao.trade.hosting.push.server;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class PushInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS))
					 .addLast(new HttpServerCodec())
					 .addLast(new HttpObjectAggregator(65536))
					 .addLast(new WebSocketServerCompressionHandler())
					 .addLast(new PushHandler());
	}

}
