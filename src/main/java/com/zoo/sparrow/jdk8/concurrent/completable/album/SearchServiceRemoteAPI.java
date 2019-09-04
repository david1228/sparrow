package com.zoo.sparrow.jdk8.concurrent.completable.album;

import java.util.List;

/**
 * 模拟搜索系统接口
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class SearchServiceRemoteAPI {

    public static AlbumBlockDTO call() {
        System.out.println("SearchServiceRemoteAPI 获取搜索专辑信息start...");
        ServerUnit.delay();
        System.out.println("SearchServiceRemoteAPI 获取搜索专辑信息end...");
        return new AlbumBlockDTO("搜索板块数据", ServerUnit.genRandomAlbums());
    }

    public static boolean filter(List<AlbumDTO> filterBlockAlbum, AlbumBlockDTO searchBlockAlbum, List<AlbumDTO> allBlockAlbums) {
        int searchAlbumsSize = searchBlockAlbum.getBlockAlbums().size();
        if (searchAlbumsSize < 3) {
//            小于3显示
            return false;
        }

        searchBlockAlbum.getBlockAlbums().removeAll(filterBlockAlbum);
        searchBlockAlbum.setBlockAlbums(searchBlockAlbum.getBlockAlbums().subList(0, Math.min(searchAlbumsSize, 10)));
//        System.out.println("searchAlbums--------》" + searchBlockAlbum.getBlockAlbums());
        allBlockAlbums.addAll(searchBlockAlbum.getBlockAlbums());
        return true;
    }
}
