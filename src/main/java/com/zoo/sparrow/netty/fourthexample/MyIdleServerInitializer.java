package com.zoo.sparrow.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by David.Liu on 17/5/30.
 */
public class MyIdleServerInitializer extends ChannelInitializer<SocketChannel>{

    @Override protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new IdleStateHandler(6, 4, 10));
        channelPipeline.addLast(new MyIdleServerHandler());
    }
}
