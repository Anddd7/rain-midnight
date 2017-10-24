package github.eddy.game.im;

import github.eddy.game.protocol.codec.Message2RainCodec;
import github.eddy.game.user.BaseUser;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * @author edliao
 * @since 2017/10/24
 * 聊天消息工具类
 */
@Getter
@Setter
public class ChatMessage implements Message2RainCodec<ChatMessage> {
    BaseUser from;
    String string;

    @Override
    public byte[] encode() {
        byte[] fromBytes = from.encode();
        byte[] stringBytes = writeString(string);

        return ByteBuffer.allocate(fromBytes.length + stringBytes.length)
                .put(fromBytes)
                .put(stringBytes)
                .array();
    }

    @Override
    public ChatMessage decode(ByteBuf in) {
        this.from = new BaseUser().decode(in);
        this.string = readString(in);
        return this;
    }
}
