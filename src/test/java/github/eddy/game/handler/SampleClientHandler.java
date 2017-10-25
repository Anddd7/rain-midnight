package github.eddy.game.handler;

import github.eddy.game.protocol.handler.AbstractMessageHandler;
import github.eddy.game.protocol.message.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * 客户端消息处理器
 */
@Slf4j
@Sharable
public final class SampleClientHandler extends AbstractMessageHandler {
    @Override
    public void channelReadMessage(Channel incoming, Message msg) {
        log.debug("收到服务器{}的消息", incoming.remoteAddress(), msg.getContentAsString());
    }
}