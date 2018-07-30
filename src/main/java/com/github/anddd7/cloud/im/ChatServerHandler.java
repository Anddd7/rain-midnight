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
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Slf4j
public class ChatServerHandler extends AbstractServiceHandler {

  private static ChatServerHandler INSTANCE;

  public static ChatServerHandler getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ChatServerHandler();
    }
    return INSTANCE;
  }

  /* ----------------- */

  private final ChannelGroup channels;

  private ChatServerHandler() {
    this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  }

  @Override
  public boolean filter(MessagePackage msg) {
    return msg.getType() == MessageTypeEnum.REQUEST.getCode() &&
        msg.getModule() == CHAT.MODULE_CODE;
  }

  @Override
  public void serviceRouter(Channel incoming, MessagePackage msg) {
    ChatMessage chatMessage = Object2ByteCodec.decode(msg.getContent(), ChatMessage.class);
    switch (msg.getService()) {
      case ALL:
        sendMessageToAll(chatMessage);
        return;
      case HALL_CHAT:
      case ROOM_CHAT:
      case PERSON_CHAT:
      default:
        throw new NotImplementedException();
    }
  }

  /* ----------------- */

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();
    channels.add(ctx.channel());
    log.debug("用户[{}]已连接", incoming.remoteAddress());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();
    channels.remove(ctx.channel());
    log.info("用户[{}]已离线", incoming.remoteAddress());
  }

  /* ----------------- */

  private void sendMessageToAll(ChatMessage chatMessage) {
    channels.forEach(
        MessagePackage.write(
            MessageTypeEnum.NOTICE,
            CHAT.MODULE_CODE, CHAT.ALL,
            chatMessage
        )::to);
  }
}
