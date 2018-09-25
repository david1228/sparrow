package com.zoo.sparrow.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 自定义的Http处理器
 * <p>
 * Created by David.Liu on 17/5/17.
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final String FAVICON_ICO = "/favicon.ico";

    // 读取客户端请求，向客户端响应的方法，所以这里要构造响应返回给客户端。
    // 注意：这里面跟Servlet没有任何关系，也符合Servlet规范，所以不会涉及到HttpServerltRequest和HttpServeletResponse对象。
    @Override protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("--------------httpserverHandler, remote_address " + ctx.channel().remoteAddress() + ", msg_class:" + msg.getClass());
//        Thread.sleep(3000); // 休眠5秒钟，lsof -i:8899 查看TCP连接状态

        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            System.out.println("请求方法: " + httpRequest.method() + ", 请求path: " + uri.getPath());

            if (FAVICON_ICO.equals(uri.getPath())) {
                System.out.println("请求/favicon.ico");
                return;
            }
            // BytBuf：构造给客户端的响应内容, 制定好编码
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);

            // 接下构造响应对象
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");

            // 调用flush才会将内容真正返回给客户端
            System.out.println("响应给客户端对象: " + response);
            ctx.writeAndFlush(response);
            ctx.channel().closeFuture();
        }

    }

    //------以下重写了ChannelInboundHandlerAdapter父类的方法，分析不同事件方法的调用时机------
    @Override public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel register invoked");
        super.channelRegistered(ctx);
    }

    @Override public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregister invoked");
        super.channelUnregistered(ctx);
    }

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active invoked");
        super.channelActive(ctx);
    }

    @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive invoked");
        super.channelInactive(ctx);
    }

    // TODO 这里执行了2次，具体有待分析
    @Override public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel read complete");
        super.channelReadComplete(ctx);
    }

    @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception caught invoked");
        super.exceptionCaught(ctx, cause);
    }

    @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler add invoked");
        super.handlerAdded(ctx);
    }
}
