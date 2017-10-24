package github.eddy.game.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author edliao
 * 消息编解码
 */
@Slf4j
public class Byte2MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        log.debug("发送消息");
        out.writeInt(msg.getLength())
                .writeShort(msg.getVersion())
                .writeShort(msg.getType())
                .writeShort(msg.getModule())
                .writeShort(msg.getService())
                .writeBytes(msg.getContent());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.debug("收到消息");
        out.add(
                Message.read(
                        in.readInt(),
                        in.readShort(),
                        in.readShort(),
                        in.readShort(),
                        in.readShort(),
                        in.readBytes(in.readableBytes()))
        );
    }
}
