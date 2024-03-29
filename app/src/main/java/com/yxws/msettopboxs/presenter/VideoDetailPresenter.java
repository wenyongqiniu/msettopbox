package com.yxws.msettopboxs.presenter;

import android.widget.Toast;

import com.yxws.msettopboxs.bean.BasePresponce;
import com.yxws.msettopboxs.bean.DoctorInfoBean;
import com.yxws.msettopboxs.bean.VideoBean;
import com.yxws.msettopboxs.bean.VideoDetailBean;
import com.yxws.msettopboxs.net.MyApi;
import com.yxws.msettopboxs.ui.activity.VideoDetailActivity;
import com.yxws.mvp.mvp.XPresent;
import com.yxws.mvp.net.ApiSubscriber;
import com.yxws.mvp.net.NetError;
import com.yxws.mvp.net.XApi;

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


    public void toBuy(String userToken, String postion) {
        MyApi.getMyApiService()
                .toBuy("", userToken, postion)
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

    /**
     * @param userToken
     * @param position
     */
    public void isVipStatus(String userToken, String position) {
        MyApi.getMyApiService()
                .isVip("", userToken, position)
                .compose(XApi.<BasePresponce<String>>getApiTransformer())
                .compose(XApi.<BasePresponce<String>>getScheduler())
                .subscribe(new ApiSubscriber<BasePresponce<String>>() {

                    @Override
                    public void onNext(BasePresponce<String> vipStateBean) {
                        if (vipStateBean.getData().contains("true")) {
                            Toast.makeText(getV().getApplicationContext(), "您已经是会员了请勿重复购买", Toast.LENGTH_SHORT).show();
                        } else {
                            getV().toBuyPurchase();
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                    }
                });
    }

    /**
     * @param userToken
     * @param position
     */
    public void isVip(String userToken, String position) {
        MyApi.getMyApiService()
                .isVip("", userToken, position)
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
