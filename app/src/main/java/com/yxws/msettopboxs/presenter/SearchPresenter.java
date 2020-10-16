package com.yxws.msettopboxs.presenter;

import com.yxws.msettopboxs.bean.VideoBean;
import com.yxws.msettopboxs.net.MyApi;
import com.yxws.msettopboxs.ui.activity.SearchActivity;
import com.yxws.mvp.mvp.XPresent;
import com.yxws.mvp.net.ApiSubscriber;
import com.yxws.mvp.net.NetError;
import com.yxws.mvp.net.XApi;

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
