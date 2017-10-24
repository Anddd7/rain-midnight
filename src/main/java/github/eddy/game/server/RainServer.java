package github.eddy.game.server;

import github.eddy.game.server.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * 服务器
 */
@Slf4j
public class RainServer {

    private final Integer port;

    public RainServer(Integer port) {
        this.port = port;
    }

    public void start(ServerChannelInitializer serverChannelInitializer) throws Exception {
        //服务器通常使用2个group : boss接受新连接 , worker处理已有连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //使用initializer来配置新加入的channel ,包括handler等配置
                    .childHandler(serverChannelInitializer)
                    //TCP相关的参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            log.debug("{}已启动 ,监听端口:{}", RainServer.class.getName(), f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.debug("服务器关闭了");
        }
    }
}
