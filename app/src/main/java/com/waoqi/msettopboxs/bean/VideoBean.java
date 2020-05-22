package com.waoqi.msettopboxs.bean;

public class VideoBean {
    private int id;
    private String cover;
    private String name;

    public VideoBean(int id, String cover, String name) {
        this.id = id;
        this.cover = cover;
        this.name = name;
    }

    public VideoBean(String cover, String name) {
        this.cover = cover;
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
