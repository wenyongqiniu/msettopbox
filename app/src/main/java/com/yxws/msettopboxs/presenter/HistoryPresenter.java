//package com.yxws.msettopboxs.presenter;
//
//import com.yxws.msettopboxs.bean.VideoBean;
//import com.yxws.msettopboxs.net.MyApi;
//import com.yxws.msettopboxs.ui.activity.HistoryAcitvity;
//import com.yxws.mvp.mvp.XPresent;
//import com.yxws.mvp.net.ApiSubscriber;
//import com.yxws.mvp.net.NetError;
//import com.yxws.mvp.net.XApi;
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
