package github.eddy.game;

import com.google.common.base.Charsets;
import github.eddy.game.client.RainClient;
import github.eddy.game.client.handler.ClientChannelInitializer;
import github.eddy.game.common.MsgModuleCode;
import github.eddy.game.common.MsgServiceCode;
import github.eddy.game.common.MsgTypeCode;
import github.eddy.game.handler.SampleClientHandler;
import github.eddy.game.im.ChatClientHandler;
import github.eddy.game.protocol.message.Message;
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
        clientChannelInitializer
                .registerMessageHandler(SampleClientHandler.class)
                .registerMessageHandler(ChatClientHandler.class);

        RainClient client = new RainClient("localhost", 65535);
        client.start(clientChannelInitializer);

        Channel channel = client.getLocalChannel();

        String hello = "Hello";
        ByteBuf helloByteBuf = channel.alloc().buffer(hello.length());
        helloByteBuf.writeCharSequence(hello, Charsets.UTF_8);
        Message.write(MsgTypeCode.REQUEST, MsgModuleCode.DEFAULT, MsgServiceCode.DEFAULT, helloByteBuf).to(channel);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String in = scanner.next();
//            System.out.println(in);
            ByteBuf byteBuf = channel.alloc().buffer(in.length());
            byteBuf.writeCharSequence(in, Charsets.UTF_8);
            Message.write(MsgTypeCode.REQUEST, MsgModuleCode.CHAT, MsgServiceCode.HALL_CHAT, byteBuf).to(channel);
        }
    }
}
