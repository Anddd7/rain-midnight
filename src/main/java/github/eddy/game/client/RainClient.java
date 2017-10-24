package github.eddy.game.client;

import github.eddy.game.client.handler.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author edliao
 * 客户端引擎Netty版
 */
@Slf4j
public class RainClient {

    private final String host;
    private final Integer port;

    private EventLoopGroup group = new NioEventLoopGroup();
    /**
     * 维持channel连接的线程
     */
    private ExecutorService clientThread = Executors.newSingleThreadExecutor();
    /**
     * 当前连接的channel
     */
    @Getter
    private Channel localChannel;

    public RainClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void start(ClientChannelInitializer c) throws Exception {
        if (localChannel != null && localChannel.isActive()) {
            return;
        }

        //连接server
        ChannelFuture f = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(c)
                .connect(host, port)
                .sync();
        localChannel = f.channel();

        //client线程等待channel关闭
        clientThread.execute(() -> {
            try {
                localChannel.closeFuture().sync();
            } catch (Exception e) {
                log.error("", e);
            } finally {
                try {
                    group.shutdownGracefully().sync();
                } catch (InterruptedException e) {
                    log.error("", e);
                }
            }
        });
    }
}

