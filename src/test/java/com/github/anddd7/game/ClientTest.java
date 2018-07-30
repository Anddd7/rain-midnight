package com.github.anddd7.game;

import com.github.anddd7.cloud.CloudClient;
import com.github.anddd7.cloud.core.common.MessageTypeEnum;
import com.github.anddd7.cloud.core.common.ServiceCodes.CHAT;
import com.github.anddd7.cloud.core.protocol.codec.MessagePackage;
import com.github.anddd7.cloud.im.ChatClientHandler;
import com.github.anddd7.cloud.im.ChatMessage;
import com.github.anddd7.cloud.user.LoginUser;
import java.util.Collections;

public class ClientTest {


  public static void main(String[] args) throws Exception {
    CloudClient client = new CloudClient(
        "localhost", 65535,
        Collections.singletonList(new ChatClientHandler())
    ).start();

    final LoginUser loginUser = LoginUser.builder().id(10001L).name("管理员1号").build();
    MessagePackage.write(
        MessageTypeEnum.REQUEST,
        CHAT.MODULE_CODE, CHAT.ALL,
        ChatMessage.builder().type("所有人").from(loginUser).content("管理员上线啦").build()
    ).to(client.getLocalChannel());

    MessagePackage.write(
        MessageTypeEnum.REQUEST,
        CHAT.MODULE_CODE, CHAT.ALL,
        ChatMessage.builder().type("所有人").from(loginUser).content("大家好").build()
    ).to(client.getLocalChannel());
  }
}
