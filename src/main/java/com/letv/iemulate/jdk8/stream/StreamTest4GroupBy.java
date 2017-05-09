package com.letv.iemulate.jdk8.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream 深入学习实践，按实际场景示例
 *
 * groupingBy，根据什么来分组，什么就是指分组的key, 结果就是分组后的values
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest4GroupBy {

    public static void main(String[] args) {
        Video video1 = new Video("激情岁月01", 180001);
        Video video2 = new Video("激情岁月02", 180001);
        Video video3 = new Video("激情岁月预告片01", 180002);
        Video video4 = new Video("激情岁月预告片02", 180002);

        List<Video> list = Lists.newArrayList(video1, video2, video3, video4);
        // key是视频类型, 值是分组后的视频名称集合
//        Map<Integer, Set<String>> namesByVideoType = list.stream().collect(Collectors.groupingBy(Video::getVideoType, Collectors.mapping(Video::getName, Collectors.toSet())));
//        namesByVideoType.forEach((integer, strings) -> System.out.println(integer + ":" + strings));

        // key是视频类型，value是分组后的对象集合
//        Map<Integer, List<Video>> videoByVideoType = list.stream().collect(Collectors.groupingBy(Video::getVideoType, Collectors.toList()));
//        videoByVideoType.forEach((integer, strings) -> System.out.println(integer + ":" + strings));

        // key是视频类型，value是分组后的对象集合, groupingBy默认会转换为Collectors.toList()
       list.stream().collect(Collectors.groupingBy(Video::getVideoType)).
               forEach((integer, strings) -> System.out.println(integer + ":" + strings));

        // select video_type, count(*) from video group by video_type;
        list.stream().collect(Collectors.groupingBy(Video::getVideoType, Collectors.counting())).
                forEach((vt, count) -> System.out.println(vt + ": " + count));

    }
}
