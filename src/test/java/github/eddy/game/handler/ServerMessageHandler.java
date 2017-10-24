package github.eddy.game.handler;

import github.eddy.game.protocol.AbstractMessageHandler;
import github.eddy.game.protocol.Message;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * Channel handler
 *
 * @author edliao
 */
@Slf4j
public final class ServerMessageHandler extends AbstractMessageHandler {
    @Override
    public void channelReadMessage(Channel incoming, Message msg) {
        log.debug("收到{}消息:{}", incoming.remoteAddress(), msg.getContentAsString());
    }
}