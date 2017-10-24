package github.eddy.game.protocol.message;

import com.google.common.base.Charsets;
import github.eddy.game.common.MsgTypeCode;
import github.eddy.game.protocol.Version;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author edliao
 * 传输数据协议
 */
@Getter
@Setter
public class Message {
    /**
     * 4 Message长度
     */
    int length;
    /**
     * 2 版本号
     */
    short version = Version.getCurrentVersion();
    /**
     * 2 消息类型
     */
    short type;
    /**
     * 2 业务模块
     */
    short module;
    /**
     * 2 具体服务
     */
    short service;
    /**
     * 消息体
     */
    ByteBuf content;


    /**
     * 写一个消息
     *
     * @param type    消息类型 {@link MsgTypeCode}
     * @param module  业务模块
     * @param service 服务模块
     * @param content 内容体
     */
    public static Message write(short type, short module, short service, ByteBuf content) {
        return new Message(type, module, service, content);
    }

    private Message(short type, short action, short service, ByteBuf content) {
        this.length = 12 + content.readableBytes();

        this.type = type;
        this.module = action;
        this.service = service;
        this.content = content;
    }

    /**
     * 用于序列化时生成一个Message
     */
    public static Message read(int length, short version, short type, short action, short service, ByteBuf content) {
        return new Message(length, version, type, action, service, content);
    }

    private Message(int length, short version, short type, short action, short service, ByteBuf content) {
        this.length = length;

        this.version = version;
        this.type = type;
        this.module = action;
        this.service = service;
        this.content = content;
    }

    public String getContentAsString() {
        return content.toString(Charsets.UTF_8);
    }

    /**
     * 通过channel发送
     */
    public void to(Channel channel) {
        channel.writeAndFlush(this);
    }

    /**
     * 转发数据时需要修改当前Content的来源 ,其他属性不变
     */
    public void transTo(Channel channel) {
        ByteBuf byteBuf = channel.alloc().buffer(content.writableBytes());
        byteBuf.writeBytes(content);
        content = byteBuf;
        to(channel);
    }
}
