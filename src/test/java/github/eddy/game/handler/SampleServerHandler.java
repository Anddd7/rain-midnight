package github.eddy.game.handler;

import github.eddy.game.protocol.handler.AbstractMessageHandler;
import github.eddy.game.protocol.message.Message;
import github.eddy.game.server.pool.ChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Channel handler
 *
 * @author edliao
 */
@Slf4j
public final class SampleServerHandler extends AbstractMessageHandler {
    @Override
    public void channelReadMessage(Channel incoming, Message msg) {
        log.debug("收到{}消息:{}", incoming.remoteAddress(), msg.getContentAsString());
    }

}