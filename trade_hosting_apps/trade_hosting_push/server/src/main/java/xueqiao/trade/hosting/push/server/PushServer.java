package xueqiao.trade.hosting.push.server;

import com.google.common.collect.ImmutableMap;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class PushServer {
	private int mPort;
	
	public PushServer(int port) {
		this.mPort = port;
	}
	
	public void run() {
		EventLoopGroup bossGroup = new EpollEventLoopGroup();
		EventLoopGroup workerGroup = new EpollEventLoopGroup();
		
		try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .handler(new LoggingHandler(LogLevel.INFO))
             .channel(EpollServerSocketChannel.class) 
             .childHandler(new PushInitializer())  
             .option(ChannelOption.SO_BACKLOG, 128)   
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .childOption(ChannelOption.TCP_NODELAY, true);
    		AppLog.i("server started on port " + mPort);
    		
            ChannelFuture f = b.bind(mPort).sync(); // (7)
			AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
					"xueqiao.trade.hosting.process.keepalive"
					, ImmutableMap.of("processname", "trade_hosting_push_server")), 1);
            f.channel().closeFuture().sync();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            AppLog.i("server stoped..");
        }
	}
}
