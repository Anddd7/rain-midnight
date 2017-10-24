package github.eddy.game.server.pool;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;

/**
 * @author edliao
 * Channel管理中心
 */
public class ChannelManager {

    @Getter
    private static ChannelManager channelManager = new ChannelManager();

    private ChannelManager() {
    }

    /**
     * 已连接的所有Channel
     */
    @Getter
    private ChannelGroup connected = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Getter
    private ChannelGroup disconnected = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 客户端连接服务器
     */
    public void connect(Channel incoming) {

    }

    /**
     * 登录
     */
    public void login(Long userId, Channel incoming) {

    }

    /**
     * 登出
     */
    public void logout(Long userId, Channel incoming) {

    }
}
