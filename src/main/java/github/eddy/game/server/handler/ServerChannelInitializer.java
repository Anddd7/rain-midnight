package github.eddy.game.server.handler;

import github.eddy.game.protocol.BaseChannelInitializer;
import github.eddy.game.server.pool.ChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * Server处理类加载器 ,编解码和控制器
 */
@Slf4j
public class ServerChannelInitializer extends BaseChannelInitializer {
    private ChannelManager channelManager = ChannelManager.getChannelManager();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.debug("加入:来自{}的新连接", incoming.remoteAddress());
        channelManager.connect(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        log.debug("离开:{}断开连接", incoming.remoteAddress());
        channelManager.disconnect(incoming);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        log.debug("心跳检测:{}, 在线", incoming.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        log.debug("心跳检测:{}, 离线", incoming.remoteAddress());
    }
}
