package github.eddy.game.user;

import github.eddy.game.protocol.codec.Message2RainCodec;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * @author edliao
 * @since 2017/10/24
 * 用户
 */
@Getter
@Setter
public class BaseUser implements Message2RainCodec<BaseUser> {
    Long id;
    String name;

    @Override
    public byte[] encode() {
        byte[] nameBytes = writeString(name);
        return ByteBuffer.allocate(Long.BYTES + nameBytes.length)
                .putLong(id)
                .put(nameBytes)
                .array();
    }

    @Override
    public BaseUser decode(ByteBuf in) {
        this.id = in.readLong();
        this.name = readString(in);
        return this;
    }
}
