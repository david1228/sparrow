package com.letv.iemulate.jdk8.stream2;

import com.letv.iemulate.jdk8.stream.Video;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by David.Liu on 17/4/11.
 */
public class StreamPractice1 {

    public static void main(String[] args) {
        Video video1 = new Video("激情岁月01", 180001, 1, 90);
        Video video2 = new Video("激情岁月02", 180001, 2, 80);
        Video video3 = new Video("激情岁月预告片01", 180002, 4, 90);
        Video video4 = new Video("激情岁月预告片02", 180002, 4, 100);
        Video video5 = new Video("激情岁月预告片02", 180003, 6, 100);

        List<Video> list = Arrays.asList(video1, video2, video3, video4, video5);

        // 对分数求统计


        // 按名称拼接


        // 二级分组，按视频类型分组并按porder分组。返回结构Map<Integer, Map<Integer, List<Video>>>
        list.stream().collect(groupingBy(Video::getVideoType, groupingBy(Video::getPorder))).forEach((k, v) -> System.out.println("groupingBy二级分组key:" + k +", value:" + v));

        list.stream().collect(toMap(Video::getVideoType, Video::getPorder)).forEach((k, v) -> System.out.println("toMap二级分组key:" + k +", value:" + v));

        // 一级分区，true false

        // 二级分区, 比如大于180001所有的视频，并且分数大于90分的。 返回结构Map<Boolean, Map<Boolean, List<Video>>>
        list.stream().collect(Collectors.partitioningBy(v -> v.getVideoType() > 180001, partitioningBy(v -> v.getScore() >= 90))).forEach((k, v) -> System.out.println("二级分区key:" + k +", value:" + v));

        // 按名称分组，取每一组分数最小的视频
        // 1) 直接minBy返回的对象是Optional类型，可能为空
        Map<String, Optional<Video>>  map1 =
                list.stream().
                        collect(groupingBy(Video::getName, minBy(Comparator.comparingDouble(Video::getScore))));
        System.out.println(map1);
        // 2) 使用collectingAndThen适配收集最小值，如果有值
        Map<String, Video>  map2 =
        list.stream().
                collect(groupingBy(Video::getName, collectingAndThen(minBy(Comparator.comparingDouble(Video::getScore)), Optional::get)));
        System.out.println(map2);
    }
}
