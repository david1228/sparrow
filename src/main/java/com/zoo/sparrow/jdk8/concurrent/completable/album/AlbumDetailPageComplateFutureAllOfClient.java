package com.zoo.sparrow.jdk8.concurrent.completable.album;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author liudewei
 * @date 2019/5/12
 */
public class AlbumDetailPageComplateFutureAllOfClient {

    public static void main(String[] args) {
        // 获取专辑基本信息
        // 1、关联专辑 2、推荐数据 3、搜索数据 4、CMS自动化榜单1 5、CMS自动化榜单2 6、CMS新增板块数据 7、CMS限免专区数据
        AlbumDTO resultAlbumDTO = ServerUnit.genRandomAlbums().stream().findFirst().get();

        // 设置下初始化大小，避免频繁扩容
        List<AlbumDTO> allBlockAlbums = Lists.newArrayList();

        // 给 CompletableFuture 创建自定义线程池，使其有更好的使用灵活性
        Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("ALBUM_DETAILPAGE_THREADPOOL");
                return thread;
            }
        });


        long start = System.nanoTime();

        // 关联专辑、推荐、搜索结果互相有依赖，请求结束后要做去重逻辑，所以这里将三个结果使用Combine做联合处理
        CompletableFuture<List<AlbumDTO>> filtrationCompletableFuture =
                CompletableFuture.supplyAsync(() -> RelationAlbumServiceRemoteAPI.call(), executor)
                        .thenCombine(
                                CompletableFuture.supplyAsync(() -> RecommendServiceRemoteAPI.callException(), executor)
                                        // 看来必须得每一个服务都要捕获异常并处理，否则第一个线程服务异常，如果没有处理，则后续所有依赖线程都会中断
                                        .exceptionally(ex -> {
                                            System.out.println("----> message:" + ex.getMessage() + ", e:" + ex);
                                            ex.printStackTrace();
                                            return new AlbumBlockDTO();
                                        })
                                ,
                                (relationBlockAlbum, recommendBlockAlbum) -> {
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
                                });

        // cms 系统接口不会与其他接口发生依赖，所以这里独立定义completableFuture
        // 使用thenApplay，这个函数要求返回结果
        CompletableFuture cmsRankComplatableFuture = CompletableFuture.supplyAsync(() -> CmsServiceRemoteAPI.rank1call(), executor).thenApply(rank1 -> {
            resultAlbumDTO.setCmsAutoRankBlock1(rank1);
            return resultAlbumDTO;
        });

        // cms 系统使用thenAccept接受结果，这个函数不需要返回结果
        CompletableFuture cmsNewBlockComplatableFuture = CompletableFuture.supplyAsync(() -> CmsServiceRemoteAPI.newBlockCall(), executor).thenAccept(newBlock -> {
            resultAlbumDTO.setCmsNewBlock(newBlock);
        });

        // cms 板块异常了，不影响其他板块的数据获取
        CompletableFuture cmsFreeBlockComplatableFuture = CompletableFuture.supplyAsync(() -> CmsServiceRemoteAPI.freeBlockException(), executor);

        CompletableFuture<Void> resultCompletableFuture = CompletableFuture.allOf(
                filtrationCompletableFuture, cmsRankComplatableFuture, cmsFreeBlockComplatableFuture, cmsNewBlockComplatableFuture)
                .exceptionally(ex -> {
                    System.out.println(ex);
                    return null;
                });

        // join 等待所有的future完成，但是没有设置超时时间。
//        resultCompletableFuture.join();

        try {
            final int TIMEOUT = 600;
            resultCompletableFuture.get(TIMEOUT, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(BlockAttrNameEnum.RELATION_ALBUM.getAttrName());
        System.out.println(BlockAttrNameEnum.RELATION_ALBUM.name());
        System.out.println("resultAlbumDTO>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(resultAlbumDTO);

        System.out.println((double) (System.nanoTime() - start) / 1_000_000 + " ms");
    }

}
