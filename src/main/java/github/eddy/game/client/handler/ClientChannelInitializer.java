package github.eddy.game.client.handler;

import github.eddy.game.protocol.BaseChannelInitializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 */
@Slf4j
public class ClientChannelInitializer extends BaseChannelInitializer {
    public ClientChannelInitializer() {
        handlers.add(ClientMessageHandler.class);
    }
}
