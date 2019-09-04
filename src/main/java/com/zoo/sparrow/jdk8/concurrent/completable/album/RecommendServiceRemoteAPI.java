package com.zoo.sparrow.jdk8.concurrent.completable.album;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 模拟推荐系统接口
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class RecommendServiceRemoteAPI {
    final static int NEED_ADD_INTO_RELATION_SIZE = 6;
    final static int CUT_RECOMMEND_ALBUM_MAX_SIZE = 6;
    final static int RELATION_ALBUM_BOUNDARY = 4;
    final static int CUT_RELATION_ALBUM_MAX_SIZE = 12;

    private AlbumDTO resultAlbumDTO;

    public static AlbumBlockDTO call() {
        System.out.println("RecommendServiceRemoteAPI 获取推荐专辑信息start...");
        ServerUnit.delay();
        System.out.println("RecommendServiceRemoteAPI 获取推荐专辑信息end...");
        return new AlbumBlockDTO("推荐数据", ServerUnit.genRandomAlbums());
    }

    public static AlbumBlockDTO callException() {
        if (true) {
            throw new RuntimeException("推荐服务..运行时异常，(～ o ～)~zZ");
        }
        System.out.println("RecommendServiceRemoteAPI 获取推荐专辑信息start...");
        ServerUnit.delay();
        System.out.println("RecommendServiceRemoteAPI 获取推荐专辑信息end...");
        return new AlbumBlockDTO("推荐数据", ServerUnit.genRandomAlbums());
    }

    public static void filter(AlbumBlockDTO relationBlockAlbum, AlbumBlockDTO recommendBlockAlbum, List<AlbumDTO> allBlockAlbums) {
        int relationAlbumsSize = relationBlockAlbum.getBlockAlbums().size();
        if (relationAlbumsSize >= RELATION_ALBUM_BOUNDARY) {
            relationBlockAlbum.setBlockAlbums(relationBlockAlbum.getBlockAlbums().subList(0, Math.min(relationAlbumsSize, CUT_RELATION_ALBUM_MAX_SIZE)));
        } else {
            // 小于4，从推荐数据中获取补齐到6个.
            int needAddIntoRelationAlbumSize = NEED_ADD_INTO_RELATION_SIZE - relationAlbumsSize;
            List<AlbumDTO> needAddIntoRelationAlbums = recommendBlockAlbum.getBlockAlbums().stream().limit(needAddIntoRelationAlbumSize).collect(Collectors.toList());
            relationBlockAlbum.getBlockAlbums().addAll(relationBlockAlbum.getBlockAlbums());
        }
        allBlockAlbums.addAll(relationBlockAlbum.getBlockAlbums());

        // 推荐去重逻辑，依赖了关联专辑数据
        recommendBlockAlbum.getBlockAlbums().removeAll(relationBlockAlbum.getBlockAlbums());
        recommendBlockAlbum.setBlockAlbums(recommendBlockAlbum.getBlockAlbums().subList(0, Math.min(recommendBlockAlbum.getBlockAlbums().size(), CUT_RECOMMEND_ALBUM_MAX_SIZE)));
        allBlockAlbums.addAll(recommendBlockAlbum.getBlockAlbums());

//        System.out.println("relation--------》" + relationBlockAlbum.getBlockAlbums());
//        System.out.println("recommend--------》" + recommendAlbums.getBlockAlbums());

        // 封装到AlbumDTO
//        resultAlbumDTO.setRelationAlbumBlock(relationBlockAlbum);
//        resultAlbumDTO.setRecommendBlock(recommendAlbums);
//        return allBlockAlbums;
    }


}
