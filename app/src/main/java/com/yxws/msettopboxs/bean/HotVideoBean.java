package com.yxws.msettopboxs.bean;

import java.util.List;

public class HotVideoBean extends BasePresponce<List<HotVideoBean>> {

    /**
     * id : 1
     * videoId : 4809
     * pic : http://video-head-pic.obs.cidc-rp-12.joint.cmecloud.cn/2020/10/22/51fec8ab-b46a-4980-bb9b-53807ecddfe0.jpg
     * info : 日常生活中，应如何预防脑卒中（中风）的？
     */

    private int id;
    private int videoId;
    private String pic;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}