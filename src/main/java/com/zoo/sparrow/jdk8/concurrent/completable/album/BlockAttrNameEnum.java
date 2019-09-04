package com.zoo.sparrow.jdk8.concurrent.completable.album;

/**
 * 专辑板块显示属性名定义
 *
 * @author liudewei
 * @date 2019/5/17
 */
public enum BlockAttrNameEnum {
    ACTOR_INFO("相关明星", "actorInfo"),
    ATTATCHING_SERIES("周边看点", "attachingSeries"),
    RELATION_ALBUM("关联专辑人工推荐", "relationAlbums"),
    RELATION_RECOMMEND_ALBUM("相关推荐内容", "recommendAlbums"),
    SUBJECT_RECOMMEND_ALBUM("主题推荐内容", "subjectRecommendAlbums"),
    SEARCH_STAR_ACTED("明星演过的影片", "starActedMovie"),
    HOT_BROADCAST("热播榜", "hotBroadcast"),
    CHOICENESS_BLOCK("精选板块", "choicenessBlock"),
    FREE_LIMITED_ZONE("限免专区", "freeLimitedZone");

    String blockName;
    String attrName;

    BlockAttrNameEnum(String blockName, String attrName) {
        this.blockName = blockName;
        this.attrName = attrName;
    }

    public String getAttrName() {
        return this.attrName;
    }

    public static void main(String[] args) {
        System.out.println(ATTATCHING_SERIES.getAttrName());
    }
}
