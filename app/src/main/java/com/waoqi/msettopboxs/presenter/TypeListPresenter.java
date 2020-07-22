package com.waoqi.msettopboxs.presenter;

import com.waoqi.msettopboxs.bean.BasePresponce;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.net.MyApi;
import com.waoqi.msettopboxs.ui.activity.TypeVideoActivity;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

public class TypeListPresenter extends XPresent<TypeVideoActivity> {


    public void getSearchLevel() {
        MyApi.getMyApiService()
                .getSearchLevel()
                .compose(XApi.<SearchLevelBean>getApiTransformer())
                .compose(XApi.<SearchLevelBean>getScheduler())
                .subscribe(new ApiSubscriber<SearchLevelBean>() {
                    @Override
                    public void onNext(SearchLevelBean searchLevelBean) {
                        getV().setSearchLevel(searchLevelBean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void getSearchLevel(int parentId) {
        MyApi.getMyApiService()
                .getSearchLevel(parentId)
                .compose(XApi.<TypeListMenuBean>getApiTransformer())
                .compose(XApi.<TypeListMenuBean>getScheduler())
                .subscribe(new ApiSubscriber<TypeListMenuBean>() {
                    @Override
                    public void onNext(TypeListMenuBean searchLevelBean) {
                        getV().setSearchLevel2(searchLevelBean.getData());
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

    public void isVip(String userId) {
        MyApi.getMyApiService()
                .isVip(userId)
                .compose(XApi.<BasePresponce>getApiTransformer())
                .compose(XApi.<BasePresponce>getScheduler())
                .subscribe(new ApiSubscriber<BasePresponce>() {

                    @Override
                    public void onNext(BasePresponce vipStateBean) {
                        getV().isVip(vipStateBean.getData().toString());
                    }

                    @Override
                    protected void onFail(NetError error) {
                    }
                });
    }
}
