package github.eddy.game.im;

import github.eddy.game.common.MsgModuleCode;
import github.eddy.game.common.MsgServiceCode;
import github.eddy.game.common.MsgTypeCode;
import github.eddy.game.protocol.handler.AbstractMessageHandler;
import github.eddy.game.protocol.message.Message;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 2017/10/24
 * 客户端聊天系统控制器 : 接受自己/他人发送的消息
 */
@Slf4j
public class ChatClientHandler extends AbstractMessageHandler {
    @Override
    public void channelReadMessage(Channel incoming, Message msg) {
        if (MsgModuleCode.CHAT != msg.getModule()) {
            return;
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.decode(msg.getContent());

        switch (msg.getType()) {
            case MsgTypeCode.RESPONSE:
                log.debug("[{}][自己]{}",
                        MsgServiceCode.getChatChannel(msg.getService()),
                        chatMessage.string);
                break;
            case MsgTypeCode.NOTICE:
                log.debug("[{}][{}]{}",
                        MsgServiceCode.getChatChannel(msg.getService()),
                        chatMessage.getFrom().getName(),
                        chatMessage.getString());
            default:
        }
    }
}
