package com.zoo.sparrow.jdk8.concurrent.completable.album;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author liudewei
 * @date 2019/5/12
 */
public class AlbumDetailPageCompletableFutureCombineClient {
    public static void main(String[] args) {
        // 获取专辑基本信息
        // 1、关联专辑 2、推荐数据 3、搜索数据 4、CMS自动化榜单1 5、CMS自动化榜单2 6、CMS新增板块数据 7、CMS限免专区数据
        AlbumDTO resultAlbumDTO = ServerUnit.genRandomAlbums().stream().findFirst().get();
        // 给 CompletableFuture 创建自定义线程池，使其有更好的使用灵活性
        Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("THREADPOOL_COMPLEABLEFUTURE_DAEMON");
                return thread;
            }
        });

        long start = System.nanoTime();
        try {
            final int NEED_ADD_INTO_RELATION_SIZE = 6;
            final int CUT_RECOMMEND_ALBUM_MAX_SIZE = 6;
            final int RELATION_ALBUM_BOUNDARY = 4;
            final int CUT_RELATION_ALBUM_MAX_SIZE = 12;
            // 设置下初始化大小，避免频繁扩容
            List<AlbumDTO> allBlockAlbums = Lists.newArrayList();

            // 链式调用
            Future<List<AlbumDTO>> completableFuture =
                    CompletableFuture.supplyAsync(() -> RelationAlbumServiceRemoteAPI.call(), executor)
                            .thenCombine(
                                    CompletableFuture.supplyAsync(() -> RecommendServiceRemoteAPI.call(), executor)
                                            .exceptionally(ex -> {
                                                System.out.println("----> message:" + ex.getMessage() + ", e:" + ex);
                                        return new AlbumBlockDTO();
                                    }),
                                    (relationBlockAlbum, recommendBlockAlbum) -> {
//                                        if (null == recommendBlockAlbum) {
//                                            return relationBlockAlbum.getBlockAlbums();
//                                        }
                                        System.out.println("关键专辑&推荐去重逻辑");
                                        RecommendServiceRemoteAPI.filter(relationBlockAlbum, recommendBlockAlbum, allBlockAlbums);
                                        System.out.println("relation--------》" + relationBlockAlbum.getBlockAlbums());
                                        System.out.println("recommend--------》" + recommendBlockAlbum.getBlockAlbums());

                                        // 封装到AlbumDTO
                                        resultAlbumDTO.setRelationAlbumBlock(relationBlockAlbum);
                                        resultAlbumDTO.setRecommendBlock(recommendBlockAlbum);
                                        return allBlockAlbums;
                                    })
                            .thenCombine(
                                    CompletableFuture.supplyAsync(() -> SearchServiceRemoteAPI.call(), executor),
                                    (filterBlockAlbums, searchBlockAlbum) -> {
                                        System.out.println("搜索去重逻辑");
                                        boolean matchSucc = SearchServiceRemoteAPI.filter(filterBlockAlbums, searchBlockAlbum, allBlockAlbums);
                                        System.out.println("searchAlbums--------》" + searchBlockAlbum.getBlockAlbums());

                                        // 封装到AlbumDTO
                                        if (matchSucc) {
                                            resultAlbumDTO.setSearchBlock(searchBlockAlbum);
                                        }
                                        return allBlockAlbums;
                                    })
                            .thenCombine(
                                    CompletableFuture.supplyAsync(() -> CmsServiceRemoteAPI.rank1call(), executor),
                                    (blockAlbums, cmsRank1Albums) -> {
                                        System.out.println("CMS自动化榜单1去重");
                                        return allBlockAlbums;
                                    })
                            .thenCombine(
                                    CompletableFuture.supplyAsync(() -> CmsServiceRemoteAPI.rank2call(), executor),
                                    (blockAlbums, cmsRank1Albums) -> {
                                        System.out.println("CMS自动化榜单2去重");
                                        return allBlockAlbums;
                                    })
                            .thenCombine(
                                    CompletableFuture.supplyAsync(() -> CmsServiceRemoteAPI.newBlockCall(), executor),
                                    (blockAlbums, cmsRank1Albums) -> {
                                        System.out.println("CMS新板块去重");
                                        return allBlockAlbums;
                                    })
                            .thenCombine(
                                    CompletableFuture.supplyAsync(() -> CmsServiceRemoteAPI.newBlockCall(), executor),
                                    (blockAlbums, cmsRank1Albums) -> {
                                        System.out.println("CMS免费专区去重");
                                        return allBlockAlbums;
                                    })
                        ;
            List<AlbumDTO> result = completableFuture.get(1000, TimeUnit.MILLISECONDS);
            System.out.println("combine album result list: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("concurrey request api total elapsed time: " + (double) (System.nanoTime() - start) / 1_000_000 + " ms");
    }

}
