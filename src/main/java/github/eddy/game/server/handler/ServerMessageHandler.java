package github.eddy.game.server.handler;

import github.eddy.game.protocol.AbstractMessageHandler;
import github.eddy.game.protocol.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * Channel handler
 */
@Slf4j
public final class ServerMessageHandler extends AbstractMessageHandler {
    @Override
    public void channelReadMessage(Channel incoming, Message msg) {
        log.debug("收到{}消息:{}", incoming.remoteAddress(), msg.getContentAsString());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.debug("新连接:{}", incoming.remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        log.debug("离开:{}", incoming.remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        log.debug("心跳检测:{}, 在线", incoming.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        log.debug("心跳检测:{}, 离线", incoming.remoteAddress());
    }
}