package github.eddy.game.common;

/**
 * 预定义的数据传输类型
 *
 * @author edliao
 */
public final class MsgTypeCode {
    /**
     * 客户端主动向服务器发送请求
     */
    public static final short REQUEST = 1;
    /**
     * 客户端主动发送请求后收到的回复
     */
    public static final short RESPONSE = 2;
    /**
     * 客户端被动收到服务器发送的请求
     */
    public static final short NOTICE = 3;
}