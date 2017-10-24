package github.eddy.game.server.pool;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * 自定义的channel group集合
     */
    private ConcurrentHashMap<String, ChannelGroup> groups = new ConcurrentHashMap<>();

    public ChannelGroup addGroup(String name) {
        ChannelGroup newGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        groups.put(name, newGroup);
        return newGroup;
    }

    public Optional<ChannelGroup> findGroup(String name) {
        return Optional.ofNullable(groups.get(name));
    }
}
