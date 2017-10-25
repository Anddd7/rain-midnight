package github.eddy.game.protocol.handler;

import github.eddy.game.protocol.codec.Message2ByteCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @author edliao
 * @since 2017/10/20
 * Channel initializer
 */
@Slf4j
public class BaseChannelInitializer extends ChannelInitializer<SocketChannel> {

    private List<Class<? extends AbstractMessageHandler>> handlers = new LinkedList<>();

    public BaseChannelInitializer registerMessageHandler(Class<? extends AbstractMessageHandler> handler) {
        handlers.add(handler);
        return this;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(
                new LengthFieldBasedFrameDecoder(1024 * 1024,
                        0,
                        4,
                        -4,
                        0,
                        false)
        );
        pipeline.addLast(new Message2ByteCodec());

        for (Class<? extends AbstractMessageHandler> handler : handlers) {
            pipeline.addLast(handler.getName(),handler.newInstance());
        }
    }
}
