package github.eddy.game.server.handler;

import github.eddy.game.protocol.BaseChannelInitializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * Server处理类加载器 ,编解码和控制器
 */
@Slf4j
public class ServerChannelInitializer extends BaseChannelInitializer {
    public ServerChannelInitializer() {
        handlers.add(ServerMessageHandler.class);
    }
}
