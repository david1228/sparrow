package com.zoo.sparrow.netty.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by David.Liu on 17/5/22.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        // 往pipeline上添加解码器
        channelPipeline.addLast("lengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        // 添加个编码器
        channelPipeline.addLast("lengthFieldPrepender", new LengthFieldPrepender(4));
        channelPipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        // 添加自己实现的处理器
        channelPipeline.addLast(new MyServerHandler());
    }
}
