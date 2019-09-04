package com.zoo.sparrow.joda;

import org.joda.time.DateTime;
import org.joda.time.Instant;

/**
 * @author liudewei
 * @date 2019/5/18
 */
public class JodaTest2 {

    public static void main(String[] args) {
        // 当前时间
        DateTime now = new DateTime();
        // 模拟缓存过期时间
        DateTime before = now.minusMinutes(1);
        // 过期阈值
        long seconds = 120 * 1000;

        if ((now.toInstant().getMillis() - before.toInstant().getMillis()) / 1000  < seconds) {
            //
            System.out.println("datetime + instant, ....dosomething, async execute refresh to cached");
        }

        Instant instantNow = new Instant();
        System.out.println(instantNow);
        Instant instantBefore = instantNow.minus(seconds - 10 * 1000);

        System.out.println((instantNow.getMillis() - instantBefore.getMillis()) / 1000);

        if ((instantNow.getMillis() - instantBefore.getMillis()) / 1000 < seconds) {
            System.out.println("....instant dosomething....");
        }


    }
}
