package com.zoo.sparrow.jdk8.concurrent.completable.album;

import java.util.Collections;
import java.util.List;

/**
 * AlbumBlockDTO
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class AlbumBlockDTO {

    private String blockName;

    private List<AlbumDTO> blockAlbums = Collections.emptyList();

    public AlbumBlockDTO() {
    }

    public AlbumBlockDTO(String blockName, List<AlbumDTO> blockAlbums) {
        this.blockName = blockName;
        this.blockAlbums = blockAlbums;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public List<AlbumDTO> getBlockAlbums() {
        return blockAlbums;
    }

    public void setBlockAlbums(List<AlbumDTO> blockAlbums) {
        this.blockAlbums = blockAlbums;
    }

    @Override
    public String toString() {
        return "AlbumBlockDTO{" +
                "blockName='" + blockName + '\'' +
                '}';
    }
}
