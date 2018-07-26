package com.github.anddd7.cloud.im;

import com.github.anddd7.cloud.core.protocol.codec.Object2ByteCodec;
import com.github.anddd7.cloud.user.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage implements Object2ByteCodec {

  private String type;
  private LoginUser from;
  private String content;
}
