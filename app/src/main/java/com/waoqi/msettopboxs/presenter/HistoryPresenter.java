//package com.waoqi.msettopboxs.presenter;
//
//import com.waoqi.msettopboxs.bean.VideoBean;
//import com.waoqi.msettopboxs.net.MyApi;
//import com.waoqi.msettopboxs.ui.activity.HistoryAcitvity;
//import com.waoqi.mvp.mvp.XPresent;
//import com.waoqi.mvp.net.ApiSubscriber;
//import com.waoqi.mvp.net.NetError;
//import com.waoqi.mvp.net.XApi;
//
//public class HistoryPresenter extends XPresent<HistoryAcitvity> {
//
//    public void getHistoryVideo(String userId) {
//        MyApi.getMyApiService()
//                .getHistoryVideo("4")
//                .compose(XApi.<VideoBean>getApiTransformer())
//                .compose(XApi.<VideoBean>getScheduler())
//                .subscribe(new ApiSubscriber<VideoBean>() {
//                    @Override
//                    public void onNext(VideoBean videoBean) {
//                        getV().setVideoGridData(videoBean.getData());
//                    }
//
//                    @Override
//                    protected void onFail(NetError error) {
//
//                    }
//                });
//
//    }
//}
