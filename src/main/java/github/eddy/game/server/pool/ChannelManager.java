package github.eddy.game.server.pool;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;

import java.util.Optional;

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

    /**
     * 客户端连接服务器
     */
    public void connect(Channel incoming) {
        connected.add(incoming);
    }

    public void disconnect(Channel incoming) {
        Optional.ofNullable(connected.find(incoming.id())).ifPresent(connected::remove);
    }
}
