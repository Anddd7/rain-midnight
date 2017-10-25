package github.eddy.game.im;

import github.eddy.game.common.MsgModuleCode;
import github.eddy.game.common.MsgTypeCode;
import github.eddy.game.protocol.handler.AbstractMessageHandler;
import github.eddy.game.protocol.message.Message;
import github.eddy.game.server.pool.ChannelManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 2017/10/24
 * 服务器聊天消息控制器 : 主要用于转发消息
 */
@Slf4j
public class ChatServerHandler extends AbstractMessageHandler {
    private ChannelManager channelManager = ChannelManager.getChannelManager();

    @Override
    public void channelReadMessage(Channel incoming, Message msg) {
        if (msg.getType() != MsgTypeCode.REQUEST || MsgModuleCode.CHAT != msg.getModule()) {
            return;
        }

        //回复自己发送成功
        msg.setType(MsgTypeCode.RESPONSE);
        msg.transTo(incoming);

        //转发给其他人
        msg.setType(MsgTypeCode.NOTICE);
        for (Channel channel : channelManager.getConnected()) {
            if (channel == incoming) {
                continue;
            }
            msg.transTo(channel);
        }
    }
}
