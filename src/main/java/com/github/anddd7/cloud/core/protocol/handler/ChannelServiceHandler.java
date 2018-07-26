package com.github.anddd7.cloud.core.protocol.handler;


import io.netty.channel.Channel;

public interface ChannelServiceHandler {

  Channel getLocalChannel();

  void setLocalChannel(Channel localChannel);
}
