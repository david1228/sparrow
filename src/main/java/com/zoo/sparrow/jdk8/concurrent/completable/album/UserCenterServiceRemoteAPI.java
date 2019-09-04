package com.zoo.sparrow.jdk8.concurrent.completable.album;

/**
 * 模拟用户中心系统接口
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class UserCenterServiceRemoteAPI {

    public static String call() {
        System.out.println("用户中心收藏接口start...");
        ServerUnit.delay();
        System.out.println("用户中心收藏接口end...");
        return "已收藏";
    }

}
