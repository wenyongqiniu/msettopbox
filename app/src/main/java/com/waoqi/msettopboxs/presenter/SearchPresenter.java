package com.waoqi.msettopboxs.presenter;

import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.net.MyApi;
import com.waoqi.msettopboxs.ui.activity.SearchActivity;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

/**
 * author : Zzy
 * date   : 2020/5/29
 */
public class SearchPresenter extends XPresent<SearchActivity> {

    public void getVideo(String condition) {
        MyApi.getMyApiService()
                .search(condition)
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
