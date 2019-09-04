package com.zoo.sparrow.netty.fourthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import static java.lang.Thread.sleep;

/**
 * Created by David.Liu on 17/5/30.
 */
public class MyIdleServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        int i=0;
        while (i< 2) {
            sleep(3000);
            System.out.println("服务端执行写入操作3s");
            ctx.channel().writeAndFlush("服务端写给客户端数据");
            i++;
        }
    }

    @Override public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvt = (IdleStateEvent) evt;

            String eventType = null; // 空闲事件类型

            switch (idleStateEvt.state()) {
            case READER_IDLE:
                eventType = "读空闲";
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
            case ALL_IDLE:
                eventType = "读写空闲";
                break;
            }

            System.out.println("客户端地址：" + ctx.channel().remoteAddress() + "超时事件被触发，eventType:" + eventType);
            ctx.channel().close();
        }
    }
}
