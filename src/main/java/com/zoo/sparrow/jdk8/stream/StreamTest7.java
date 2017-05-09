package com.zoo.sparrow.jdk8.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Stream 流的短路与并发流
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest7 {

    public static void main(String[] args) {
        // 生成500w个uuid添加到集合中
        List<String> list = Lists.newArrayListWithCapacity(5000000);
        for (int i = 0; i < 5000000; i++) {
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序");
        // 使用纳秒 精度最准
        long start = System.nanoTime();
        list.stream().sorted().count();
        long end = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
        System.out.println("stream排序耗时：" + millis);

        List<String> parallelist = Lists.newArrayListWithCapacity(5000000);
        for (int i = 0; i < 5000000; i++) {
            parallelist.add(UUID.randomUUID().toString());
        }

        System.out.println("开始排序");
        long parallelStart = System.nanoTime();
        parallelist.parallelStream().sorted().count();
        long parallelEnd = System.nanoTime();
        long parallelMillis = TimeUnit.NANOSECONDS.toMillis(parallelEnd - parallelStart);
        System.out.println("parallelStream排序耗时：" + parallelMillis);

        /************
            开始排序
            stream排序耗时：5542
            开始排序
            parallelStream排序耗时：1745
         看得出来使用并行流要比串行流快3倍左右。
         **************/
    }
}
