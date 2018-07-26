package com.github.anddd7.cloud.user;

import com.github.anddd7.cloud.core.protocol.codec.Object2ByteCodec;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUser implements Object2ByteCodec {

  private long id;
  private String name;
}
