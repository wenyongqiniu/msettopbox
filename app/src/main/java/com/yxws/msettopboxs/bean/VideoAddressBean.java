package com.yxws.msettopboxs.bean;

import java.util.List;

public class VideoAddressBean extends BasePresponce<List<VideoAddressBean>> {

    /**
     * id : 427
     * cpAlbumId : 158813987059492
     * albumName : 妇科
     * cpTvId : 67
     * tvName : 1、痛经是怎么回事？原因有哪些？
     * result : 2
     * errReason : 成功
     * seriesId : sjsag003230100000000000000000077
     * programId : pjsag003230100000000000000000077
     * movieId : mjsag003230100000000000000000077
     * type : 1
     */

    private int id;
    private String cpAlbumId;
    private String albumName;
    private String cpTvId;
    private String tvName;
    private int result;
    private String errReason;
    private String seriesId;
    private String programId;
    private String movieId;
    private int type;//0 中兴 1华为

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

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getCpTvId() {
        return cpTvId;
    }

    public void setCpTvId(String cpTvId) {
        this.cpTvId = cpTvId;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrReason() {
        return errReason;
    }

    public void setErrReason(String errReason) {
        this.errReason = errReason;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
