package github.eddy.game.protocol.codec;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * @author edliao
 * @since 2017/10/24
 * Message和自定义Bean的相互转换
 */
public interface Message2RainCodec<T> {
    /**
     * object -> byte[]
     *
     * @return 所有属性编码后的byte[]
     */
    byte[] encode();

    /**
     * 从ByteBuf获取属性
     *
     * @param in 从Message中一步步传递的byteBuf
     * @return 一般返回Bean类本身
     */
    T decode(ByteBuf in);

    /**
     * 按 lenth,string 的规则 从byteBuf 中读取String
     *
     * @param in 从Message中一步步传递的byteBuf
     * @return 字符串
     */
    default String readString(ByteBuf in) {
        return in.readBytes(in.readInt()).toString(Charsets.UTF_8);
    }

    /**
     * 按 lenth,string 的规则把String写成byte[] ,方便下一步的encode
     *
     * @param string 字符串
     * @return 包含 字符串长度+内容 的byte[]
     */
    default byte[] writeString(String string) {
        return ByteBuffer.allocate(Integer.BYTES + string.length())
                .putInt(string.length())
                .put(string.getBytes())
                .array();
    }
}
