package com.github.anddd7.cloud.core.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Package2MessageCodec extends ByteToMessageCodec<MessagePackage> {

  @Override
  protected void encode(ChannelHandlerContext ctx, MessagePackage msg, ByteBuf out) {
    log.info("发送消息:{}", msg);

    out
        .writeInt(msg.getLength())
        .writeShort(msg.getVersion())
        .writeShort(msg.getType())
        .writeShort(msg.getModule())
        .writeShort(msg.getService())
        .writeLong(System.currentTimeMillis())
        .writeBytes(msg.getContent());
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    MessagePackage received = new MessagePackage(
        in.readInt(),
        in.readShort(),
        in.readShort(),
        in.readShort(),
        in.readShort(),
        in.readLong(),
        in.readBytes(in.readableBytes())
    );
    log.info("接受消息:{}", received);

    // TODO validate length and version
    out.add(received);
  }
}
