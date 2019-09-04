package com.zoo.sparrow.netty.sixthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by David.Liu on 17/6/21.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    // 初始化器，服务端启动后会自动调用这个方法，它是一个回调方法。
    @Override protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline(); // pipeline一个管道里面可以有很多的ChannelHandler，相当于包含很多个拦截器。

        channelPipeline.addLast(new ProtobufVarint32FrameDecoder());
        channelPipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        channelPipeline.addLast(new ProtobufEncoder());
        // 替代MyDataInfo.Person.getDefaultInstance实现，通过最外层类，其中包含枚举，传递实现类更灵活
        channelPipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
        channelPipeline.addLast(new TestServerHandler());
    }
}