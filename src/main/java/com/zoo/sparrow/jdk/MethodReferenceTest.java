package com.zoo.sparrow.jdk;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by David.Liu on 17/8/8.
 */
public class MethodReferenceTest {

    public static void main(String[] args) {

        List<Album> albums = Arrays.asList(new Album("战狼II", 5), new Album("战狼I", 6), new Album("狼牙", 4));

        System.out.println("------------------normal pattern..");
        // 普通方式  14行代码
        List<Album> favs = new ArrayList<>();
        for (Album album : albums) {
            for (Video video : album.videos) {
                if (video.score >= 4) {
                    favs.add(album);
                    break;
                }
            }
        }
        Collections.sort(favs, new Comparator<Album>() {
            public int compare(Album a1, Album a2) {
                return a1.name.compareTo(a2.name);
            }
        });

        favs.forEach(System.out::println);

        System.out.println("------------------stream pattern..");
        // 流方式 代码语义性更强
        List<Album> result = albums.stream().filter(album -> album.videos.stream().anyMatch(trk -> trk.score >= 4))
                .sorted(Comparator.comparing(Album::getName)).collect(Collectors.toList());

        // 按照名称反序排列 Comparator.comparing(Album::getName).reversed()
        result.forEach(System.out::println);

    }

    private static class Album {
        String name;
        List<Video> videos;

        public Album(String name) {
            this.name = name;
        }

        public Album(String name, int vnum) {
            this.name = name;

            List<Video> videos = Lists.newArrayList();
            for (int i= 0; i <= vnum; i++) {
                videos.add(new Video(i));
            }
            this.videos = videos;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return String.format("%s{name=%s}", this.getClass().getSimpleName(), getName());
        }
    }

    private static class Video {
        int score;

        public Video(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
