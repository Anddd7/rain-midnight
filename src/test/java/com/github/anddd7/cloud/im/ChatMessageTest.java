package com.github.anddd7.cloud.im;

import com.github.anddd7.cloud.core.protocol.codec.Object2ByteCodec;
import com.github.anddd7.cloud.user.LoginUser;
import org.junit.Assert;
import org.junit.Test;

public class ChatMessageTest {

  private ChatMessage expected = ChatMessage.builder()
      .type("房间")
      .from(
          LoginUser.builder()
              .id(0L)
              .name("玩家1001")
              .build()
      )
      .content("Hello, 你好!")
      .build();

  @Test
  public void encodeAndDecode() {
    byte[] out = expected.encode();
    ChatMessage actual = Object2ByteCodec.decode(out, ChatMessage.class);
    Assert.assertEquals(expected, actual);
  }
}