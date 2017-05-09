package com.zoo.sparrow.jdk8.stream;

/**
 * Created by David.Liu on 17/3/30.
 */
public class Video {

    String name;

    int videoType;

    int porder;

    double score;

    public Video(String name, int videoType, int porder, double score) {
        this.name = name;
        this.videoType = videoType;
        this.porder = porder;
        this.score = score;
    }

    public Video(String name, int videoType) {
        this.name = name;
        this.videoType = videoType;
    }

    public Video(String name, int videoType, int porder) {
        this.name = name;
        this.videoType = videoType;
        this.porder = porder;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getPorder() {
        return porder;
    }

    public void setPorder(int porder) {
        this.porder = porder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    @Override public String toString() {
        return "Video{" + "name='" + name + '\'' + ", videoType=" + videoType + ", porder=" + porder + ", score=" + score + '}';
    }
}
