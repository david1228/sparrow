package com.zoo.sparrow.netty.sixthexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by David.Liu on 17/6/21.
 */
public class TestClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(bossGroup).channel(NioSocketChannel.class).handler(new TestClientInitializer());

            System.out.println("服务端已启动..");
            ChannelFuture channelFuture = clientBootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            System.out.println("优雅关闭group");
            bossGroup.shutdownGracefully();
        }
    }
}
