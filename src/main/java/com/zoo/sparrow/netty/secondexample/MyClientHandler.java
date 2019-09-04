package com.zoo.sparrow.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * Created by David.Liu on 17/5/22.
 */
public class MyClientHandler  extends SimpleChannelInboundHandler<String>{

    @Override protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server remote address: " + ctx.channel().remoteAddress());
        System.out.println("server msg-->" + msg);
        ctx.writeAndFlush("from client: " + LocalDateTime.now()); // jdk8时间API
    }

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("xyz");
        ctx.channel().writeAndFlush("xyz");
        super.channelActive(ctx);
    }

    @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
