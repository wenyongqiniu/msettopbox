package com.waoqi.msettopboxs.presenter;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.waoqi.msettopboxs.bean.AuthBean;
import com.waoqi.msettopboxs.bean.AuthParam;
import com.waoqi.msettopboxs.bean.BasePresponce;
import com.waoqi.msettopboxs.bean.DoctorInfoBean;
import com.waoqi.msettopboxs.bean.VideoAddressBean;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.bean.VideoDetailBean;
import com.waoqi.msettopboxs.net.Api;
import com.waoqi.msettopboxs.net.MyApi;
import com.waoqi.msettopboxs.ui.activity.VideoDetailActivity;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

import okhttp3.RequestBody;

public class VideoDetailPresenter extends XPresent<VideoDetailActivity> {


    public void getVideoDetail(int videoId) {
        MyApi.getMyApiService()
                .getVideoDetail(videoId)
                .compose(XApi.<VideoDetailBean>getApiTransformer())
                .compose(XApi.<VideoDetailBean>getScheduler())
                .subscribe(new ApiSubscriber<VideoDetailBean>() {
                    @Override
                    public void onNext(VideoDetailBean videoBean) {
                        if (videoBean.getData() != null)
                            getV().setVideoDetail(videoBean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void getDoctorInfo(String doctorId) {
        MyApi.getMyApiService()
                .getDoctorInfo(doctorId)
                .compose(XApi.<DoctorInfoBean>getApiTransformer())
                .compose(XApi.<DoctorInfoBean>getScheduler())
                .subscribe(new ApiSubscriber<DoctorInfoBean>() {
                    @Override
                    public void onNext(DoctorInfoBean bean) {
                        getV().setDoctorInfo(bean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void getVideo(String cpAlbumId) {
        MyApi.getMyApiService()
                .getVideo(cpAlbumId)
                .compose(XApi.<VideoBean>getApiTransformer())
                .compose(XApi.<VideoBean>getScheduler())
                .subscribe(new ApiSubscriber<VideoBean>() {
                    @Override
                    public void onNext(VideoBean videoBean) {
                        getV().setVideoGridData(videoBean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });

    }


    public void getVideoAddress(String cpAlbumId, int cpTvId) {
        MyApi.getMyApiService()
                .getVideoAddress(cpAlbumId, cpTvId)
                .compose(XApi.<VideoAddressBean>getApiTransformer())
                .compose(XApi.<VideoAddressBean>getScheduler())
                .subscribe(new ApiSubscriber<VideoAddressBean>() {
                    @Override
                    public void onNext(VideoAddressBean videoBean) {
                        nextApi(videoBean);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }


    private void nextApi(VideoAddressBean videoBean) {
        @SuppressLint("WrongConstant")
        DevInfoManager systemService = (DevInfoManager) getV().getApplicationContext().getSystemService(DevInfoManager.DATA_SERVER);

        AuthParam authParam = new AuthParam();

        authParam.setOTTUserToken(DataHelper.getStringSF(getV().getApplicationContext(), "OTTUserToken"));
        authParam.setUserID(DataHelper.getStringSF(getV().getApplicationContext(), "UserID"));
        authParam.setMAC(systemService.getValue(DevInfoManager.STB_MAC));

        String epg_addresss = systemService.getValue(DevInfoManager.EPG_ADDRESS);
        String cdn_type = systemService.getValue(DevInfoManager.CDN_TYPE);

        if (cdn_type.endsWith("HW")) {
            VideoAddressBean temp = null;
            for (VideoAddressBean videoAddressBean : videoBean.getData()) {
                if (videoAddressBean.getType() == 1) {
                    temp = videoAddressBean;
                    break;
                }
            }
            authParam.setContentID(temp.getSeriesId());

            getVideoAddress(epg_addresss, authParam, temp.getSeriesId(), temp.getMovieId());
        } else if (cdn_type.endsWith("ZTE")) {
            VideoAddressBean temp = null;
            for (VideoAddressBean videoAddressBean : videoBean.getData()) {
                if (videoAddressBean.getType() == 0) {
                    temp = videoAddressBean;
                    break;
                }
            }
            authParam.setContentID(temp.getSeriesId());
            getVideoAddress(epg_addresss, authParam, temp.getSeriesId(), temp.getMovieId());
        }
    }


    public void getVideoAddress(String epg_address, AuthParam authParam, String seriesId, String movieId) {
        Gson gson = new Gson();
        String obj = gson.toJson(authParam);
        KLog.e("wlx", "请求AuthCode参数：  " + obj);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj);
        Api.getVerService(epg_address + "/").auth(body)
                .compose(XApi.<AuthBean>getApiTransformer())
                .compose(XApi.<AuthBean>getApiTransformer())
                .compose(XApi.<AuthBean>getScheduler())
                .subscribe(new ApiSubscriber<AuthBean>() {
                    @Override
                    public void onNext(AuthBean videoBean) {
                        startActivity(videoBean, seriesId, movieId);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    private void startActivity(AuthBean videoBean, String seriesId, String movieId) {
        @SuppressLint("WrongConstant")
        DevInfoManager systemService = (DevInfoManager) getV().getApplicationContext().getSystemService(DevInfoManager.DATA_SERVER);
        String cdn_type = systemService.getValue(DevInfoManager.CDN_TYPE);
        StringBuffer stringBuffer = new StringBuffer();

        if (cdn_type.endsWith("HW")) {
            stringBuffer.append(systemService.getValue(DevInfoManager.CDN_ADDRESS_BACK))
                    .append("/tianhongyxws")
                    .append("/vod")
                    .append("/").append(seriesId)
                    .append("/").append(movieId)
                    .append("?OTTUserToken=").append(DataHelper.getStringSF(getV().getApplicationContext(), "OTTUserToken"))
                    .append("&[$").append(videoBean.getAuthCode()).append("]");

        } else if (cdn_type.contains("ZTE")) {
            stringBuffer.append(systemService.getValue(DevInfoManager.CDN_ADDRESS))
                    .append("/tianhongyxwszx")
                    .append("/vod")
                    .append("/").append(seriesId)
                    .append("/").append(movieId)
                    .append("?OTTUserToken=").append(DataHelper.getStringSF(getV().getApplicationContext(), "OTTUserToken"))
                    .append("&[$").append(videoBean.getAuthCode()).append("]");
        }
        KLog.d("wlx", "播放地址：  " + stringBuffer.toString());
        getV().startActivity(stringBuffer.toString());
    }

    public void toBuy(String userId, String userToken) {
        MyApi.getMyApiService()
                .toBuy("", userId, userToken)
                .compose(XApi.<BasePresponce>getApiTransformer())
                .compose(XApi.<BasePresponce>getScheduler())
                .subscribe(new ApiSubscriber<BasePresponce>() {
                    @Override
                    public void onNext(BasePresponce bean) {
                        getV().getH5Url(bean.getData().toString());
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }
}
