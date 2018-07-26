package com.github.anddd7.cloud.core.protocol.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import java.util.Optional;

/**
 * @deprecated 由Service本身 或 channel管理器 进行channel群的管理
 */
@Deprecated
public interface GroupServiceHandler {
  ChannelGroup getChannels();

  void setChannels(ChannelGroup channels);

  default void connect(Channel incoming) {
    getChannels().add(incoming);
  }

  default void disconnect(Channel incoming) {
    Optional.ofNullable(getChannels().find(incoming.id())).ifPresent(getChannels()::remove);
  }
}
