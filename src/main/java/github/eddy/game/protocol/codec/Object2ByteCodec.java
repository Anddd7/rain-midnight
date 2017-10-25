package github.eddy.game.protocol.codec;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;

/**
 * @author edliao
 * @since 2017/10/25
 * 序列化工具 ,将Object类序列化成ByteBuf ,方便通过Message传输
 */
public class Object2ByteCodec {

    public <T> ByteBuf encode(T object) {

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

        }

        return null;
    }

    public <T> T decode(ByteBuf in, T object) {

        return null;
    }

}
