package com.zoo.sparrow.netty.thirdexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by David.Liu on 17/5/30.
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
