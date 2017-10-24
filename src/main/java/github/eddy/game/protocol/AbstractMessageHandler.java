package github.eddy.game.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 2017/10/23
 * 基础消息接收器
 */
public abstract class AbstractMessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        channelReadMessage(ctx.channel(), msg);
    }

    public abstract void channelReadMessage(Channel incoming, Message msg);
}
