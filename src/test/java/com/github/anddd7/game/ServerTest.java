package com.github.anddd7.game;

import com.github.anddd7.cloud.CloudServer;
import com.github.anddd7.cloud.im.ChatServerHandler;
import java.util.Collections;

public class ServerTest {

  public static void main(String[] args) throws Exception {
    new CloudServer(
        65535,
        Collections.singletonList(ChatServerHandler.getInstance())
    ).start();

    System.out.println(123);
  }
}
