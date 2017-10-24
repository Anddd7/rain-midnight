package github.eddy.game;

import com.google.common.base.Charsets;
import github.eddy.game.client.RainClient;
import github.eddy.game.client.handler.ClientChannelInitializer;
import github.eddy.game.common.MsgModuleCode;
import github.eddy.game.common.MsgServiceCode;
import github.eddy.game.common.MsgTypeCode;
import github.eddy.game.handler.ClientMessageHandler;
import github.eddy.game.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author edliao
 * @since 2017/10/20
 * 测试客户端
 */
public class ClientTest {
    public static void main(String[] args) throws Exception {
        ClientChannelInitializer clientChannelInitializer = new ClientChannelInitializer();
        clientChannelInitializer.registerMessageHandler(ClientMessageHandler.class);

        RainClient client = new RainClient("localhost", 65535);
        client.start(clientChannelInitializer);

        Channel channel = client.getLocalChannel();

        ByteBuf byteBuf = channel.alloc().buffer();
        byteBuf.writeCharSequence("Eddy", Charsets.UTF_8);
        Message message = Message.write(MsgTypeCode.REQUEST, MsgModuleCode.CHAT, MsgServiceCode.HALL_CHAT, byteBuf);
        channel.writeAndFlush(message);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String in = scanner.next();
            System.out.println(in);
        }
    }
}
