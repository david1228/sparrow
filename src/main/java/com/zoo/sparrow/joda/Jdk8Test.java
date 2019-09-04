package com.zoo.sparrow.joda;

import java.time.*;

/**
 * @author liudewei
 * @date 2019/5/18
 */
public class Jdk8Test {

    public static void main(String[] args) {

        final int seconds = 120;

        // 默认是美国东八区时间，跟上海时区相差8个小时
        Instant localDateTimeToInstant = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        System.out.println(localDateTimeToInstant);
        Instant instantNow = Instant.now();
        System.out.println(instantNow.getEpochSecond());
        Instant instantBefore = instantNow.minusSeconds(210);

        // 使用LocalDateTime + Duration 结合判断时间差
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime:" + nowDateTime);
        LocalDateTime beforeLocalDate = nowDateTime.minusMinutes(3);
        System.out.println("beforeLocalDate:" + beforeLocalDate);
        Duration duration = Duration.between(beforeLocalDate, nowDateTime);
        System.out.println("Duration:" + duration.getSeconds());
        // 时间之差，负数
        Duration duration1 = Duration.between(nowDateTime, beforeLocalDate);
        System.out.println("Duration:" + duration1.getSeconds());



        if ((instantNow.getEpochSecond() - instantBefore.getEpochSecond()) < seconds) {
            System.out.println("....instant dosomething....");
        }

        System.out.println(LocalDateTime.now());
        // 前10分钟
        System.out.println(LocalDateTime.now().minusMinutes(10));
        // 后10分钟
        System.out.println(LocalDateTime.now().plusMinutes(10));
        // 后10分钟与当前时间差
        System.out.println(Duration.between(nowDateTime, LocalDateTime.now().plusMinutes(10)).getSeconds());

    }
}
