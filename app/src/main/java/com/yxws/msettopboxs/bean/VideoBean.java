package com.yxws.msettopboxs.bean;

import java.util.List;

public class VideoBean  extends BasePresponce<List<VideoBean>>{

    /**
     * id : 17
     * cpAlbumId : 158813987059492
     * cpTvId : 1
     * tvName : 1、引起月经不调的原因有哪些？
     * tvIsEffective : 1
     * tvIsOnline : 1
     * isPurchase : 1
     * playOrder : 1
     * tvUrl : ftp://tianhong:6PvfNOD6DOJTKfBk@112.25.63.175:2111/home/video/1.mp4
     * tvDuration : null
     * tvPicHead :
     * bitRate : null
     * viceoFormat :
     * tvDesc :
     * tvPriority : null
     * doctorId : null
     */

    private int id;
    private String cpAlbumId;
    private int cpTvId;
    private String tvName;
    private int tvIsEffective;
    private int tvIsOnline;
    private int isPurchase;//是否收费 0免费 1收费
    private int playOrder;
    private String tvUrl;
    private Object tvDuration;
    private String tvPicHead;
    private String tvPicSmall;
    private Object bitRate;
    private String viceoFormat;
    private String tvDesc;
    private Object tvPriority;
    private Object doctorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpAlbumId() {
        return cpAlbumId;
    }

    public void setCpAlbumId(String cpAlbumId) {
        this.cpAlbumId = cpAlbumId;
    }

    public int getCpTvId() {
        return cpTvId;
    }

    public void setCpTvId(int cpTvId) {
        this.cpTvId = cpTvId;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public int getTvIsEffective() {
        return tvIsEffective;
    }

    public void setTvIsEffective(int tvIsEffective) {
        this.tvIsEffective = tvIsEffective;
    }

    public int getTvIsOnline() {
        return tvIsOnline;
    }

    public void setTvIsOnline(int tvIsOnline) {
        this.tvIsOnline = tvIsOnline;
    }

    public int getIsPurchase() {
        return isPurchase;
    }

    public void setIsPurchase(int isPurchase) {
        this.isPurchase = isPurchase;
    }

    public int getPlayOrder() {
        return playOrder;
    }

    public void setPlayOrder(int playOrder) {
        this.playOrder = playOrder;
    }

    public String getTvUrl() {
        return tvUrl;
    }

    public void setTvUrl(String tvUrl) {
        this.tvUrl = tvUrl;
    }

    public Object getTvDuration() {
        return tvDuration;
    }

    public void setTvDuration(Object tvDuration) {
        this.tvDuration = tvDuration;
    }

    public String getTvPicHead() {
        return tvPicHead;
    }

    public void setTvPicHead(String tvPicHead) {
        this.tvPicHead = tvPicHead;
    }

    public Object getBitRate() {
        return bitRate;
    }

    public void setBitRate(Object bitRate) {
        this.bitRate = bitRate;
    }

    public String getViceoFormat() {
        return viceoFormat;
    }

    public void setViceoFormat(String viceoFormat) {
        this.viceoFormat = viceoFormat;
    }

    public String getTvDesc() {
        return tvDesc;
    }

    public void setTvDesc(String tvDesc) {
        this.tvDesc = tvDesc;
    }

    public Object getTvPriority() {
        return tvPriority;
    }

    public void setTvPriority(Object tvPriority) {
        this.tvPriority = tvPriority;
    }

    public Object getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Object doctorId) {
        this.doctorId = doctorId;
    }

    public String getTvPicSmall() {
        return tvPicSmall;
    }

    public void setTvPicSmall(String tvPicSmall) {
        this.tvPicSmall = tvPicSmall;
    }
}
