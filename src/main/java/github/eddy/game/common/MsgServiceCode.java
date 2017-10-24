package github.eddy.game.common;

/**
 * 预定义的服务内容
 *
 * @author edliao
 */
public final class MsgServiceCode {
    public static final short DEFAULT = -1;
    public static final short TIMER_START = 11;
    public static final short TIMER_END = 12;
    /**
     * 用户相关
     */
    public static final short USER_LOGIN = 21;
    public static final short USER_LOGOUT = 22;
    public static final short ROOM_IN = 23;
    public static final short ROOM_OUT = 24;
    /**
     * 聊天消息发送对象
     */
    public static final short HALL_CHAT = 31;
    public static final short ROOM_CHAT = 32;
}
