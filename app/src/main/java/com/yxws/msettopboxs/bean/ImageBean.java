package com.yxws.msettopboxs.bean;

public class ImageBean {
    private int resId;//本地资源
    private String url;
    private String name;//分类名字
    private int typeId;

    public ImageBean(int resId, int typeId) {
        this.resId = resId;
        this.typeId = typeId;
    }

    public ImageBean(int resId, String name, int typeId) {
        this.resId = resId;
        this.name = name;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
