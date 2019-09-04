package com.zoo.sparrow.jdk8.concurrent.completable.album;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author liudewei
 * @date 2019/5/12
 */
public class AlbumDetailPageComplateFutureGetClient {

    public static void main(String[] args) {
        // 获取专辑基本信息
        // 1、关联专辑 2、推荐数据 3、搜索数据 4、CMS自动化榜单1 5、CMS自动化榜单2 6、CMS新增板块数据 7、CMS限免专区数据

        AlbumDTO firstAlbum = ServerUnit.genRandomAlbums().stream().findFirst().get();

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

        CompletableFuture<AlbumBlockDTO> realtionCompletableFuture =
                CompletableFuture.supplyAsync(() -> RelationAlbumServiceRemoteAPI.call(), executor);

        CompletableFuture<AlbumBlockDTO> recommendCompletableFuture =
                CompletableFuture.supplyAsync(() -> RecommendServiceRemoteAPI.call(), executor);

        // 设置下初始化大小，避免频繁扩容
        List<AlbumDTO> allBlockAlbums = Lists.newArrayList();

        try {
            final int NEED_ADD_INTO_RELATION_SIZE = 6;
            final int CUT_RECOMMEND_ALBUM_MAX_SIZE = 6;
            final int RELATION_ALBUM_BOUNDARY = 4;
            final int CUT_RELATION_ALBUM_MAX_SIZE = 12;
            // 关联专辑
            AlbumBlockDTO relationAlbums = realtionCompletableFuture.get();
            AlbumBlockDTO recommendAlbums = recommendCompletableFuture.get();
            int relationAlbumsSize = relationAlbums.getBlockAlbums().size();
            if (relationAlbumsSize >= RELATION_ALBUM_BOUNDARY) {
                relationAlbums.setBlockAlbums(relationAlbums.getBlockAlbums().subList(0, Math.min(relationAlbumsSize, CUT_RELATION_ALBUM_MAX_SIZE)));
                allBlockAlbums.addAll(relationAlbums.getBlockAlbums());
            } else {
                // 小于4，从推荐数据中获取补齐到6个.
                int needAddIntoRelationAlbumSize = NEED_ADD_INTO_RELATION_SIZE - relationAlbumsSize;
                List<AlbumDTO> needAddIntoRelationAlbums = recommendAlbums.getBlockAlbums().stream().limit(needAddIntoRelationAlbumSize).collect(Collectors.toList());
                relationAlbums.getBlockAlbums().addAll(needAddIntoRelationAlbums);
            }
            System.out.println("relation:" + relationAlbums.getBlockAlbums());

            // 推荐去重逻辑，依赖了关联专辑数据
            recommendAlbums.getBlockAlbums().removeAll(allBlockAlbums);
            recommendAlbums.setBlockAlbums(recommendAlbums.getBlockAlbums().subList(0, Math.min(recommendAlbums.getBlockAlbums().size(), CUT_RECOMMEND_ALBUM_MAX_SIZE)));
            allBlockAlbums.addAll(recommendAlbums.getBlockAlbums());

            System.out.println("推荐结果：" + recommendAlbums.getBlockAlbums());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        System.out.println(RelationAlbumServiceRemoteAPI.call());
        System.out.println((double)(System.nanoTime() - start) / 1_000_000 + " ms");
    }

}
