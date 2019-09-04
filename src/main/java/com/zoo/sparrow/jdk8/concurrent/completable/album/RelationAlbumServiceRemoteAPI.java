package com.zoo.sparrow.jdk8.concurrent.completable.album;

/**
 * 模拟关联专辑数据接口
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class RelationAlbumServiceRemoteAPI {

    public static AlbumBlockDTO call() {
        System.out.println("RelationAlbumServiceRemoteAPI 从redis批量获取关联专辑start...");
        ServerUnit.delay(100);
        System.out.println("RelationAlbumServiceRemoteAPI 从redis批量获取关联专辑end...");
        return new AlbumBlockDTO("关联专辑数据", ServerUnit.genRandomAlbums());
    }
}
