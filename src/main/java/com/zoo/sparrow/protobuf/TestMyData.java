package com.zoo.sparrow.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by David.Liu on 17/6/20.
 */
public class TestMyData {

    public static void main(String[] args) {
        MyDataInfo.Person person = MyDataInfo.Person.newBuilder().setName("zhangh").setAge(2).build();

        // 转换为字节数组
        byte[] personBytes = person.toByteArray();

        try {
            MyDataInfo.Person person1 =  MyDataInfo.Person.parseFrom(personBytes);
            System.out.println(person1);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
