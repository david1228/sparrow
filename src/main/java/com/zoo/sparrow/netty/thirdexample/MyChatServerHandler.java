package com.zoo.sparrow.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 聊天自定义长处理器
 *
 * Created by David.Liu on 17/5/30.
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 这里要区分下是否是自己发的消息
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch == channel) { // 两个channel对象地址相同
                System.out.println("服务器端转发聊天消息：【自己】发送的消息, 内容：" + msg + "\n");
                ch.writeAndFlush("【自己】发送的消息, 内容：" + msg + "\n");
            } else {
                System.out.println("服务器端转发聊天消息："+ ch.remoteAddress() + "发送的消息，内容：" + msg + "\n");
                ch.writeAndFlush(ch.remoteAddress() + "发送的消息，内容：" + msg + "\n");
            }
        });
    }

    // -----------以下覆写的方法是ChannelInboundHandlerAdapter中的方法---------------
    @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 加入了\n");

        // 先写入到客户端，最后再将自己添加到ChannelGroup中
        channelGroup.add(channel);
    }

    @Override public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 离开了\n");

        // 这里channelGroup会自动进行调用，所以这行代码不写也是可以的。
        channelGroup.remove(channel);
    }

    /**
     * 只要有客户端连接就会执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了\n");
    }

    @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了\n");
    }

    @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
