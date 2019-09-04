package com.zoo.sparrow.algorithm;

/**
 * @author liudewei
 * @date 2019/3/29
 */
public class YIHuo {

    public static void main(String[] args) {
        int a = 3, b = 5;
        System.out.println("before swap：" + "a=" + a + ",b=" + b);
        // 开始交换位置
        a = a ^ b;
        System.out.println(a);
        b = a ^ b; // b=a^b^b=a
        System.out.println(b);
        a = a ^ b; // a=a^b^a^b^b=b
        System.out.println(a);
        System.out.println("after swap：" + "a=" + a + ",b=" + b);
    }
}
