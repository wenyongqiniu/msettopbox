package com.waoqi.msettopboxs.bean;

public class ImageBean {
    private int resId;
    private String url;
    private int typeId;

    public ImageBean(int resId, int typeId) {
        this.resId = resId;
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
}
