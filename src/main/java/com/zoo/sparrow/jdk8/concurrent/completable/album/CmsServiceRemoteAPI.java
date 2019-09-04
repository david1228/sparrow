package com.zoo.sparrow.jdk8.concurrent.completable.album;

/**
 * 模拟CMS系统接口
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class CmsServiceRemoteAPI {

    public static AlbumBlockDTO rank1call() {
        System.out.println("CmsServiceRemoteAPI 获取自动化榜单1专辑ID列表start...");
        ServerUnit.delay(); // + 读取redis缓存数据
        System.out.println("CmsServiceRemoteAPI 获取自动化榜单1专辑ID列表end...");
        return new AlbumBlockDTO("CMS自动化榜单1", ServerUnit.genRandomAlbums());
    }

    public static AlbumBlockDTO rank2call() {
        System.out.println("CmsServiceRemoteAPI2 获取自动化榜单2专辑ID列表start...");
        ServerUnit.delay();
        System.out.println("CmsServiceRemoteAPI2 获取自动化榜单2专辑ID列表start...");
        return new AlbumBlockDTO("CMS自动化榜单2", ServerUnit.genRandomAlbums());
    }

    public static AlbumBlockDTO newBlockCall() {
        System.out.println("CmsServiceRemoteAPI 获取新版块专辑信息start...");
        ServerUnit.delay(10000);
        System.out.println("CmsServiceRemoteAPI 获取新版块专辑信息end...");
        return new AlbumBlockDTO("CMS新版块", ServerUnit.genRandomAlbums());
    }

    public static AlbumBlockDTO freeBlock() {
        System.out.println("CmsServiceRemoteAPI 获取限免专区专辑信息start...");
        ServerUnit.delay(100);
        System.out.println("CmsServiceRemoteAPI 获取限免专区专辑信息end...");
        return new AlbumBlockDTO("CMS免费专区", ServerUnit.genRandomAlbums());
    }

    public static AlbumBlockDTO freeBlockException() {
        if (true) {
            throw new NullPointerException("免费专区发生了异常，😄");
        }
        System.out.println("CmsServiceRemoteAPI 获取限免专区专辑信息start...");
        ServerUnit.delay(100);
        System.out.println("CmsServiceRemoteAPI 获取限免专区专辑信息end...");
        return new AlbumBlockDTO("CMS免费专区", ServerUnit.genRandomAlbums());
    }
}
