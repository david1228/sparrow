package com.zoo.sparrow.netty.thirdexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 发起聊天的客户端
 *
 * Created by David.Liu on 17/5/24.
 */
public class MyCatClient {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyChatClientInitalizer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            // channelFuture.channel().closeFuture().sync();

            // 从控制台不断的读取输入
            boolean running = true;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
                while (running) {
                    channelFuture.channel().writeAndFlush(br.readLine() + "\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
