package com.github.anddd7.cloud.im;

import static com.github.anddd7.cloud.core.common.ServiceCodes.CHAT.ALL;
import static com.github.anddd7.cloud.core.common.ServiceCodes.CHAT.HALL_CHAT;
import static com.github.anddd7.cloud.core.common.ServiceCodes.CHAT.PERSON_CHAT;
import static com.github.anddd7.cloud.core.common.ServiceCodes.CHAT.ROOM_CHAT;

import com.github.anddd7.cloud.core.common.MessageTypeEnum;
import com.github.anddd7.cloud.core.common.ServiceCodes.CHAT;
import com.github.anddd7.cloud.core.protocol.codec.MessagePackage;
import com.github.anddd7.cloud.core.protocol.codec.Object2ByteCodec;
import com.github.anddd7.cloud.core.protocol.handler.AbstractServiceHandler;
import com.github.anddd7.cloud.core.protocol.handler.ChannelServiceHandler;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatClientHandler extends AbstractServiceHandler implements ChannelServiceHandler {

  @Getter
  @Setter
  private Channel localChannel;

  @Override
  public boolean filter(MessagePackage msg) {
    return msg.getType() != MessageTypeEnum.REQUEST.getCode() &&
        msg.getModule() == CHAT.MODULE_CODE;
  }

  @Override
  public void serviceRouter(Channel incoming, MessagePackage msg) {
    ChatMessage chatMessage = Object2ByteCodec.decode(msg.getContent(), ChatMessage.class);
    switch (msg.getService()) {
      case ALL:
      case HALL_CHAT:
      case ROOM_CHAT:
      case PERSON_CHAT:
      default:
        receiveMessage(chatMessage);
    }
  }

  /* ----------------- */

  private void receiveMessage(ChatMessage chatMessage) {
    log.info("[{}]{}: {}",
        chatMessage.getType(),
        chatMessage.getFrom().getName(),
        chatMessage.getContent());
  }
}
