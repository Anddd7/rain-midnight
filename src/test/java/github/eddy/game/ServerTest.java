package github.eddy.game;

import github.eddy.game.handler.ServerMessageHandler;
import github.eddy.game.server.RainServer;
import github.eddy.game.server.handler.ServerChannelInitializer;

/**
 * @author edliao
 * @since 2017/10/20
 * 测试服务器
 */
public class ServerTest {
    public static void main(String[] args) throws Exception {
        ServerChannelInitializer serverChannelInitializer = new ServerChannelInitializer();
        serverChannelInitializer.registerMessageHandler(ServerMessageHandler.class);

        new RainServer(65535).start(serverChannelInitializer);
    }
}
