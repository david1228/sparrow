package com.zoo.sparrow.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by David.Liu on 17/5/30.
 */
public class MyWebSocketChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast(new ChunkedWriteHandler());
        // 将多块http消息做聚合
        channelPipeline.addLast(new HttpObjectAggregator(8192));
        // 这个处理器帮助你完成一些繁重的工作，可以运行一个websocket服务器，它负责websocket的握手以及对控制帧的处理，ping pong，文本或二进制都会传给下一个处理器来处理。
        // 参数表示websocket的网址，通常形式：ws://server:port/context_path ，访问本机：ws://localhost:8899/hello
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        channelPipeline.addLast(new TextWebSocketFrameHandler()); // 添加自定义处理器
    }
}
