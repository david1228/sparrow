package com.zoo.sparrow.jdk8.concurrent.completable.album;

import java.util.Arrays;
import java.util.Objects;

/**
 * AlbumDTO
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class AlbumDTO {

    private Long albumId;
    private String name;
    private String subTitle;
    private String bigPic;
    private String JumpData;
    private String[] chargeInfo;

    // 板块数据
    private AlbumBlockDTO relationAlbumBlock;
    private AlbumBlockDTO searchBlock;
    private AlbumBlockDTO recommendBlock;
    private AlbumBlockDTO cmsAutoRankBlock1;
    private AlbumBlockDTO cmsAutoRankBlock2;
    private AlbumBlockDTO cmsFreeBlock;
    private AlbumBlockDTO cmsNewBlock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getJumpData() {
        return JumpData;
    }

    public void setJumpData(String jumpData) {
        JumpData = jumpData;
    }

    public String[] getChargeInfo() {
        return chargeInfo;
    }

    public void setChargeInfo(String[] chargeInfo) {
        this.chargeInfo = chargeInfo;
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
                "albumId=" + albumId +
                ", name='" + name + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", bigPic='" + bigPic + '\'' +
                ", JumpData='" + JumpData + '\'' +
                ", chargeInfo=" + Arrays.toString(chargeInfo) +
                ", relationAlbumBlock=" + relationAlbumBlock +
                ", searchBlock=" + searchBlock +
                ", recommendBlock=" + recommendBlock +
                ", cmsAutoRankBlock1=" + cmsAutoRankBlock1 +
                ", cmsAutoRankBlock2=" + cmsAutoRankBlock2 +
                ", cmsFreeBlock=" + cmsFreeBlock +
                ", cmsNewBlock=" + cmsNewBlock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlbumDTO albumDto = (AlbumDTO) o;
        return Objects.equals(albumId, albumDto.albumId) &&
                Objects.equals(name, albumDto.name) &&
                Objects.equals(subTitle, albumDto.subTitle) &&
                Objects.equals(bigPic, albumDto.bigPic) &&
                Objects.equals(JumpData, albumDto.JumpData) &&
                Arrays.equals(chargeInfo, albumDto.chargeInfo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(albumId, name, subTitle, bigPic, JumpData);
        result = 31 * result + Arrays.hashCode(chargeInfo);
        return result;
    }

    public AlbumBlockDTO getRelationAlbumBlock() {
        return relationAlbumBlock;
    }

    public void setRelationAlbumBlock(AlbumBlockDTO relationAlbumBlock) {
        this.relationAlbumBlock = relationAlbumBlock;
    }

    public AlbumBlockDTO getSearchBlock() {
        return searchBlock;
    }

    public void setSearchBlock(AlbumBlockDTO searchBlock) {
        this.searchBlock = searchBlock;
    }

    public AlbumBlockDTO getRecommendBlock() {
        return recommendBlock;
    }

    public void setRecommendBlock(AlbumBlockDTO recommendBlock) {
        this.recommendBlock = recommendBlock;
    }

    public AlbumBlockDTO getCmsAutoRankBlock1() {
        return cmsAutoRankBlock1;
    }

    public void setCmsAutoRankBlock1(AlbumBlockDTO cmsAutoRankBlock1) {
        this.cmsAutoRankBlock1 = cmsAutoRankBlock1;
    }

    public AlbumBlockDTO getCmsAutoRankBlock2() {
        return cmsAutoRankBlock2;
    }

    public void setCmsAutoRankBlock2(AlbumBlockDTO cmsAutoRankBlock2) {
        this.cmsAutoRankBlock2 = cmsAutoRankBlock2;
    }

    public AlbumBlockDTO getCmsFreeBlock() {
        return cmsFreeBlock;
    }

    public void setCmsFreeBlock(AlbumBlockDTO cmsFreeBlock) {
        this.cmsFreeBlock = cmsFreeBlock;
    }

    public AlbumBlockDTO getCmsNewBlock() {
        return cmsNewBlock;
    }

    public void setCmsNewBlock(AlbumBlockDTO cmsNewBlock) {
        this.cmsNewBlock = cmsNewBlock;
    }
}
