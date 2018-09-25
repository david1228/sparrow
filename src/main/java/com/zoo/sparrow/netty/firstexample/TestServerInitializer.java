package com.zoo.sparrow.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 服务端初始化类
 *
 * Created by David.Liu on 17/5/17.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel>{

    // 初始化器，服务端启动后会自动调用这个方法，它是一个回调方法。
    @Override protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("initChannel invoked... "); // 有客户端连接就会执行.
        ChannelPipeline channelPipeline = ch.pipeline(); // pipeline一个管道里面可以有很多的ChannelHandler，相当于包含很多个拦截器。
        // 添加处理器，可以添加多个，并且可以将处理器放到pipeline管道的不同位置上。
        channelPipeline.addLast("httpServerCodec", new HttpServerCodec()); //HttpServerCodec也是一个很重要的组件.
        channelPipeline.addLast("httpServerHandler", new TestHttpServerHandler()); // 自定义处理器
    }
}
