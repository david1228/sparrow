package com.zoo.sparrow.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by David.Liu on 17/5/17.
 */
public class TestNettyServer {

    public static void main(String[] args) throws InterruptedException {
        // 服务器端可以理解为while(true){}死循环，去不断的接受请求连接。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // 启动服务端，这里的处理器都要是多实例的.
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());

            System.out.println("服务端已启动..");
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            System.out.println("优雅关闭group");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
