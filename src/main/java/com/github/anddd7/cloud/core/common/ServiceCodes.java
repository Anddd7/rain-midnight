package com.github.anddd7.cloud.core.common;

/**
 * 服务标识Code
 */
public interface ServiceCodes {

  short DEFAULT = -1;

  /**
   * 全局时钟
   */
  interface CLOCK {

    short MODULE_CODE = 1;
  }

  /**
   * 权限校验
   */
  interface AUTHORIZATION {

    short MODULE_CODE = 2;

    short USER_LOGIN = 1;
    short USER_LOGOUT = 2;
    short ROOM_IN = 3;
    short ROOM_OUT = 4;
  }

  /**
   * 聊天
   */
  interface CHAT {

    short MODULE_CODE = 3;

    /**
     * 聊天消息发送对象
     */
    short ALL = 0;
    short HALL_CHAT = 1;
    short ROOM_CHAT = 2;
    short PERSON_CHAT = 3;

    static String codeToDescription(short code) {
      switch (code) {
        case CHAT.ALL:
          return "系统";
        case CHAT.HALL_CHAT:
          return "大厅";
        case CHAT.ROOM_CHAT:
          return "房间";
        case CHAT.PERSON_CHAT:
          return "私聊";
        default:
          return "";
      }
    }
  }
}
