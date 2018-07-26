package com.github.anddd7.cloud.core.protocol.codec;

import com.github.anddd7.cloud.core.common.MessageTypeEnum;
import com.github.anddd7.cloud.core.common.ServiceCodes;
import com.github.anddd7.cloud.core.protocol.Version;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 传输数据包
 */
@Getter
@Setter
@AllArgsConstructor
public class MessagePackage {

  /**
   * 4 Message长度
   */
  private int length;
  /**
   * 2 版本号
   */
  private short version = Version.CURRENT_VERSION;
  /**
   * 2 消息类型
   *
   * @see MessageTypeEnum
   */
  private short type;
  /**
   * 2 业务模块
   *
   * @see ServiceCodes
   */
  private short module;
  /**
   * 2 具体服务
   */
  private short service;
  /**
   * 8 当前时间戳
   *
   * @see Package2MessageCodec
   */
  private long timestamp = System.currentTimeMillis();
  /**
   * 消息体
   */
  private byte[] content;


  public static MessagePackage write(
      MessageTypeEnum messageType,
      short module, short service,
      Object2ByteCodec object2ByteCodec) {
    return write(messageType, module, service, object2ByteCodec.encode());
  }

  public static MessagePackage write(
      MessageTypeEnum messageType,
      short module, short service,
      byte[] content) {
    return new MessagePackage(messageType.getCode(), module, service, content);
  }


  private MessagePackage(short type, short module, short service, byte[] content) {
    this.length = 20 + content.length;
    this.type = type;
    this.module = module;
    this.service = service;
    this.content = content;
  }

  public MessagePackage(int length, short version, short type, short module, short service,
      long timestamp, ByteBuf in) {
    this.length = length;
    this.version = version;
    this.type = type;
    this.module = module;
    this.service = service;
    this.timestamp = timestamp;
    this.content = new byte[in.readableBytes()];
    in.readBytes(content);
  }


  public void to(Channel channel) {
    channel.writeAndFlush(this);
  }

  @Override
  public String toString() {
    return "{" +
        "length=" + length +
        ", version=" + version +
        ", type=" + type +
        ", module=" + module +
        ", service=" + service +
        ", timestamp=" + timestamp +
        '}';
  }
}
