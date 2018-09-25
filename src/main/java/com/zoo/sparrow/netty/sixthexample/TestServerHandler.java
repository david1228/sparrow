package com.zoo.sparrow.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by David.Liu on 17/6/22.
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        // 判断不同的实现，输出数据

        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();

        switch (dataType) {

        case PersonType:
            MyDataInfo.Person person =  msg.getPerson();
            System.out.println(person);
            break;
        case CatType:
            MyDataInfo.Cat cat =  msg.getCat();
            System.out.println(cat);
            break;
        case DogType:
            MyDataInfo.Dog dog =  msg.getDog();
            System.out.println(dog);
            break;
        default:

            break;
        }

    }
}
