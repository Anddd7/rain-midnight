package com.github.anddd7.cloud.core.protocol.handler;

import com.github.anddd7.cloud.core.protocol.codec.Package2MessageCodec;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServicesInitializer extends ChannelInitializer<SocketChannel> {

  private List<ChannelHandler> channelHandlers = new ArrayList<>();


  public ServicesInitializer registerHandlers(List<AbstractServiceHandler> handlers) {
    channelHandlers.addAll(handlers);
    return this;
  }

  /**
   * 每收到一个新的channel都会对这个channel进行初始化, 添加handler
   */
  @Override
  public void initChannel(SocketChannel ch) {
    ChannelPipeline pipeline = ch.pipeline();
    // 非共享的
    pipeline.addLast(customFrameDecoder());
    pipeline.addLast(new Package2MessageCodec());
    // 自定义的需要共享的handler, 会出现交互操作
    for (ChannelHandler serviceHandler : channelHandlers) {
      pipeline.addLast(serviceHandler);
    }
  }

  private LengthFieldBasedFrameDecoder customFrameDecoder() {
    return new LengthFieldBasedFrameDecoder(
        1024 * 1024,
        0, 4, -4, 0,
        false);
  }
}
