package com.yxws.msettopboxs.presenter;

import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.msettopboxs.bean.TypeListMenuBean;
import com.yxws.msettopboxs.bean.VideoBean;
import com.yxws.msettopboxs.net.MyApi;
import com.yxws.msettopboxs.ui.activity.TypeVideoActivity;
import com.yxws.mvp.mvp.XPresent;
import com.yxws.mvp.net.ApiSubscriber;
import com.yxws.mvp.net.NetError;
import com.yxws.mvp.net.XApi;

public class TypeListPresenter extends XPresent<TypeVideoActivity> {


    public void getAllCategory() {
        MyApi.getMyApiService()
                .getAllCategory()
                .compose(XApi.<SearchLevelBean>getApiTransformer())
                .compose(XApi.<SearchLevelBean>getScheduler())
                .subscribe(new ApiSubscriber<SearchLevelBean>() {
                    @Override
                    public void onNext(SearchLevelBean searchLevelBean) {
                        getV().setAllCategoty(searchLevelBean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void getTwoCategory(int parentId) {
        MyApi.getMyApiService()
                .getTwoCategory(parentId)
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


}
