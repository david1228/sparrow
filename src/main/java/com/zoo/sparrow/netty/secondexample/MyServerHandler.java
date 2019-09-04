package com.zoo.sparrow.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by David.Liu on 17/5/22.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("remote address: " + ctx.channel().remoteAddress() + ", from client msg-->" + msg);
        ctx.channel().writeAndFlush(msg); // 如果内容比较多，可以多次write，然后最后调用一次flush.
    }

    // 当出现异常时的方法重写
    @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close(); // 将Channel处理上下文关闭
    }
}
