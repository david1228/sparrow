package com.zoo.sparrow.netty.thirdexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by David.Liu on 17/5/30.
 */
public class MyChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        // 添加基于\r \n界定符的解码器
        channelPipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        channelPipeline.addLast(new StringDecoder(CharsetUtil.UTF_8)); // 添加字符串解码器
        channelPipeline.addLast(new StringEncoder(CharsetUtil.UTF_8)); // 添加字符串编码器
        channelPipeline.addLast(new MyChatServerHandler()); // 自定义处理器
    }
}
