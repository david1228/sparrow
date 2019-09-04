package com.zoo.sparrow.jdk8.concurrent.completable.album;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 模拟延迟及数据工具
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class ServerUnit {

    /**
     * 模拟请求随机延迟 200 ~ (200+500) ms
     */
    public static void delay() {
        delay(500);
    }

    public static void delay(int range) {
        Random random = new Random();
        long timeout = 200 + random.nextInt(range);
        System.out.println("sleep " + timeout + " ms");
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<AlbumDTO> genRandomAlbums() {
        List<AlbumDTO> albums = Lists.newArrayList();
        IntStream.range(0, 60).forEach(albumId -> {
            Random random = new Random();
            Long randomAlbumId = Long.valueOf(random.nextInt(100));
            AlbumDTO albumDto = new AlbumDTO();
            albumDto.setAlbumId(randomAlbumId);
            albumDto.setName("我是个专辑名称_" + randomAlbumId);
            albumDto.setSubTitle("我是个专辑副标题_" + randomAlbumId);
            albumDto.setBigPic("http://xxxyyyzzz/" + randomAlbumId + ".jpg");
            albumDto.setChargeInfo(new String[]{randomAlbumId + "_charge_0", randomAlbumId + "charge_1"});
            albumDto.setJumpData("ooo");
            if (!albums.contains(albumDto)) {
                albums.add(albumDto);
            }
        });

        return albums;
    }

    public static void main(String[] args) {
//        IntStream.range(0, 10).forEach(i -> System.out.println(DelayUnit.delay()));
        System.out.println(ServerUnit.genRandomAlbums().size());
//        ServerUnit.genRandomAlbums().stream().forEach(album -> System.out.println(album));
        System.out.println(ServerUnit.genRandomAlbums().stream().findFirst().get());
    }

}
