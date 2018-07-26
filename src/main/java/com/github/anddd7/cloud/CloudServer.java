package com.github.anddd7.cloud;

import com.github.anddd7.cloud.core.protocol.handler.AbstractServiceHandler;
import com.github.anddd7.cloud.core.protocol.handler.GroupServiceHandler;
import com.github.anddd7.cloud.core.protocol.handler.ServicesInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudServer {

  private final Integer port;
  private final List<AbstractServiceHandler> serviceHandlers;

  /**
   * 由Service本身 或 channel管理器 进行channel群的管理
   * @deprecated
   */
  private final ChannelGroup connectedChannels =
      new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  private final EventLoopGroup bossGroup;
  private final EventLoopGroup workerGroup;

  public CloudServer(Integer port, List<AbstractServiceHandler> serviceHandlers) {
    this.port = port;
    this.serviceHandlers = serviceHandlers;

    //服务器通常使用2个group : boss接受新连接 , worker处理已有连接
    this.bossGroup = new NioEventLoopGroup();
    this.workerGroup = new NioEventLoopGroup();
  }

  public CloudServer start() throws Exception {

    registerGroup2Services();

    try {
      ServerBootstrap b = new ServerBootstrap()
          .group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          //使用initializer来配置新加入的channel ,包括handler等配置
          .childHandler(new ServicesInitializer().registerHandlers(serviceHandlers))
          //TCP相关的参数
          .option(ChannelOption.SO_BACKLOG, 128)
          .childOption(ChannelOption.SO_KEEPALIVE, true);

      ChannelFuture f = b.bind(port).sync();
      log.debug("{}已启动 ,监听端口:{}", CloudServer.class.getName(), f.channel().localAddress());
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
      log.debug("服务器关闭了");
    }

    return this;
  }

  /**
   * @deprecated
   */
  private void registerGroup2Services() {
    for (AbstractServiceHandler serviceHandler : serviceHandlers) {
      if (serviceHandler instanceof GroupServiceHandler) {
        ((GroupServiceHandler) serviceHandler).setChannels(connectedChannels);
      }
    }
  }
}
