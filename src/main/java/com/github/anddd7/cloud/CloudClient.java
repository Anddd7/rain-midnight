package com.github.anddd7.cloud;

import com.github.anddd7.cloud.core.protocol.handler.AbstractServiceHandler;
import com.github.anddd7.cloud.core.protocol.handler.ChannelServiceHandler;
import com.github.anddd7.cloud.core.protocol.handler.ServicesInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudClient {

  private final String host;
  private final Integer port;
  private final List<AbstractServiceHandler> serviceHandlers;

  private final EventLoopGroup group = new NioEventLoopGroup();
  private final ExecutorService clientThread = Executors.newSingleThreadExecutor();

  public CloudClient(String host, Integer port, List<AbstractServiceHandler> serviceHandlers) {
    this.host = host;
    this.port = port;
    this.serviceHandlers = serviceHandlers;
  }

  @Getter
  private Channel localChannel;

  public CloudClient start() throws Exception {
    if (this.localChannel == null || !this.localChannel.isActive()) {
      //连接server
      this.localChannel = new Bootstrap()
          .group(group)
          .channel(NioSocketChannel.class)
          .handler(new ServicesInitializer().registerHandlers(serviceHandlers))
          .connect(host, port)
          .sync()
          .channel();

      registerChannel2Services();

      //启动新线程运行监听器
      this.clientThread.execute(() -> {
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
    return this;
  }

  private void registerChannel2Services() {
    serviceHandlers.stream()
        .filter(serviceHandler -> serviceHandler instanceof ChannelServiceHandler)
        .forEach(serviceHandler ->
            ((ChannelServiceHandler) serviceHandler)
                .setLocalChannel(localChannel)
        );
  }
}
