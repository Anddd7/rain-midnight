package com.github.anddd7.cloud.core.protocol.handler;

import com.github.anddd7.cloud.core.protocol.codec.MessagePackage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础消息接收器
 */
@Slf4j
@Sharable
public abstract class AbstractServiceHandler extends SimpleChannelInboundHandler<MessagePackage> {

  public abstract void serviceRouter(Channel incoming, MessagePackage msg);

  public abstract boolean filter(MessagePackage msg);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessagePackage msg) throws Exception {
    if (filter(msg)) {
      serviceRouter(ctx.channel(), msg);
      ctx.fireChannelRead(msg);
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();
    log.debug("心跳检测:{}, 在线", incoming.remoteAddress());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();
    log.debug("心跳检测:{}, 离线", incoming.remoteAddress());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    log.error("", cause);
    //ctx.close();
  }
}
