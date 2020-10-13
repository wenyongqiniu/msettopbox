package com.waoqi.msettopboxs.presenter;

import com.waoqi.msettopboxs.bean.BasePresponce;
import com.waoqi.msettopboxs.bean.DoctorInfoBean;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.bean.VideoDetailBean;
import com.waoqi.msettopboxs.net.MyApi;
import com.waoqi.msettopboxs.ui.activity.VideoDetailActivity;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

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



    public void toBuy(String userId, String userToken) {
        MyApi.getMyApiService()
                .toBuy(userId, userToken)
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


    public void isVip(String userId) {
        MyApi.getMyApiService()
                .isVip(userId)
                .compose(XApi.<BasePresponce<String>>getApiTransformer())
                .compose(XApi.<BasePresponce<String>>getScheduler())
                .subscribe(new ApiSubscriber<BasePresponce<String>>() {

                    @Override
                    public void onNext(BasePresponce<String> vipStateBean) {
                        getV().isVip(vipStateBean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {
                    }
                });
    }
}
