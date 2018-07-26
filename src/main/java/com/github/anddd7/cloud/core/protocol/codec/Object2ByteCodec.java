package com.github.anddd7.cloud.core.protocol.codec;

import com.google.common.base.Charsets;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;

public interface Object2ByteCodec {

  static <T> T decode(byte[] in, Class<T> clazz) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(in);
    try {
      return (T) decode(byteBuffer, clazz, clazz.newInstance());
    } catch (IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }
    return null;
  }

  default byte[] encode() {
    try {
      return encode(this.getClass(), this);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return new byte[0];
  }

  default byte[] encode(Class clazz, Object object) throws IllegalAccessException {
    int totalLength = 0;
    byte[] totalBytes = new byte[1024];

    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      byte[] bytes = null;

      // TODO support more type
      if (field.getType().equals(String.class)) {
        bytes = writeString((String) field.get(object));
      } else if (field.getType().equals(int.class)) {
        bytes = ByteBuffer.allocate(Integer.BYTES)
            .putInt(field.getInt(object))
            .array();
      } else if (field.getType().equals(long.class)) {
        bytes = ByteBuffer.allocate(Long.BYTES)
            .putLong(field.getLong(object))
            .array();
      } else if (field.getType().equals(short.class)) {
        bytes = ByteBuffer.allocate(Short.BYTES)
            .putLong(field.getShort(object))
            .array();
      } else if (Object2ByteCodec.class.isAssignableFrom(field.getType())) {
        bytes = ((Object2ByteCodec) field.get(object)).encode();
      }

      if (bytes != null && bytes.length > 0) {
        int remaining = totalBytes.length - totalLength;
        if (remaining < bytes.length) {
          totalBytes = Arrays.copyOf(totalBytes, bytes.length + 1024);
        }

        System.arraycopy(bytes, 0, totalBytes, totalLength, bytes.length);
        totalLength += bytes.length;
      }
      field.setAccessible(false);
    }

    if (totalBytes.length > totalLength) {
      return Arrays.copyOf(totalBytes, totalLength);
    }

    return totalBytes;
  }


  static Object decode(ByteBuffer in, Class clazz, Object object)
      throws IllegalAccessException, InstantiationException {
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);

      // TODO support more type
      if (field.getType().equals(String.class)) {
        field.set(object, readString(in));
      } else if (field.getType().equals(int.class)) {
        field.set(object, in.getInt());
      } else if (field.getType().equals(long.class)) {
        field.set(object, in.getLong());
      } else if (field.getType().equals(short.class)) {
        field.set(object, in.getShort());
      } else if (Object2ByteCodec.class.isAssignableFrom(field.getType())) {
        Object subObject = decode(in, field.getType(), field.getType().newInstance());
        field.set(object, subObject);
      }
      field.setAccessible(false);
    }
    return object;
  }

  static String readString(ByteBuffer in) {
    int length = in.getInt();
    byte[] bytes = new byte[length];
    in.get(bytes);
    return new String(bytes, Charsets.UTF_8);
  }

  static byte[] writeString(String string) {
    byte[] stringBytes = string.getBytes(Charsets.UTF_8);
    return ByteBuffer.allocate(Integer.BYTES + stringBytes.length)
        .putInt(stringBytes.length)
        .put(stringBytes)
        .array();
  }
}
