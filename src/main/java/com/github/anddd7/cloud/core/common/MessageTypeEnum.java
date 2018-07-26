package com.github.anddd7.cloud.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

  /**
   * 客户端主动向服务器发送请求
   */
  REQUEST((short) 1),
  /**
   * 客户端主动发送请求后收到的回复
   */
  RESPONSE((short) 2),
  /**
   * 客户端被动收到服务器发送的请求
   */
  NOTICE((short) 3);

  private short code;

  public MessageTypeEnum get(short code) {
    for (MessageTypeEnum value : MessageTypeEnum.values()) {
      if (value.code == code) {
        return value;
      }
    }
    throw new EnumConstantNotPresentException(
        MessageTypeEnum.class,
        String.format("code-[%s]", code)
    );
  }
}