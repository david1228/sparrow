package com.zoo.sparrow.jdk8.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream 深入学习实践，按实际场景示例
 *
 * partitionBy，分区只是有两个区
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest4PartitionBy {

    public static void main(String[] args) {
        Video video1 = new Video("激情岁月01", 180001, 1);
        Video video2 = new Video("激情岁月02", 180001, 2);
        Video video3 = new Video("激情岁月预告片01", 180002, 3);
        Video video4 = new Video("激情岁月预告片02", 180002, 4);

        List<Video> list = Lists.newArrayList(video1, video2, video3, video4);
        list.stream().collect(Collectors.partitioningBy(v -> v.getPorder() > 2)).
                forEach((isPorder, videos) -> System.out.println(isPorder + ":" + videos));

    }
}
