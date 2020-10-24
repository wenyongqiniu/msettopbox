package com.yxws.msettopboxs.net.requestbean;

public class WatchHistoryBean {
    private String contentName;//视频名称
    private String contentId;//cp_album_id这个ID
    private int extraContentId;//cp_tv_id这个ID
    private long contentTotalTime;//节目总时长：秒（s）
    private long startWatchTime;//用户开始看的时间
    private long endWatchTime;//用户结束看时间
    private long playTime;//用户看了多少时长
    private String logType;//同步数据时，用户当前播放状态
    private String account;//用户登录账号,机顶盒登陆账号
    private String imageUrl;//海报URL

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public int getExtraContentId() {
        return extraContentId;
    }

    public void setExtraContentId(int extraContentId) {
        this.extraContentId = extraContentId;
    }

    public long getContentTotalTime() {
        return contentTotalTime;
    }

    public void setContentTotalTime(long contentTotalTime) {
        this.contentTotalTime = contentTotalTime;
    }

    public long getStartWatchTime() {
        return startWatchTime;
    }

    public void setStartWatchTime(long startWatchTime) {
        this.startWatchTime = startWatchTime;
    }

    public long getEndWatchTime() {
        return endWatchTime;
    }

    public void setEndWatchTime(long endWatchTime) {
        this.endWatchTime = endWatchTime;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
