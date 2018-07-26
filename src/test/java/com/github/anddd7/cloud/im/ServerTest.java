package com.github.anddd7.cloud.im;

import com.github.anddd7.cloud.CloudServer;
import java.util.Collections;

public class ServerTest {

  public static void main(String[] args) throws Exception {
    new CloudServer(
        65535,
        Collections.singletonList(new ChatServerHandler())
    ).start();

    System.out.println(123);
  }
}
