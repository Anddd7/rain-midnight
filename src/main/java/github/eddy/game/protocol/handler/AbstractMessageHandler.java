package github.eddy.game.protocol.handler;

import github.eddy.game.protocol.message.Message;
import github.eddy.game.server.pool.ChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 2017/10/23
 * 基础消息接收器
 */
@Slf4j
public abstract class AbstractMessageHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        channelReadMessage(ctx.channel(), msg);
    }

    /**
     * 消息接收和处理
     *
     * @param incoming 消息传输的channel
     * @param msg      消息
     */
    public abstract void channelReadMessage(Channel incoming, Message msg);


    private ChannelManager channelManager = ChannelManager.getChannelManager();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.debug("心跳检测:{}, 在线", incoming.remoteAddress());
        channelManager.connect(incoming);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.debug("心跳检测:{}, 离线", incoming.remoteAddress());
        channelManager.disconnect(incoming);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("", cause);
        ctx.close();
    }
}
