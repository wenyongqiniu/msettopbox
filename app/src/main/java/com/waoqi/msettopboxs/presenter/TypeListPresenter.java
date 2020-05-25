package com.waoqi.msettopboxs.presenter;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.waoqi.msettopboxs.bean.VerificationBean;
import com.waoqi.msettopboxs.net.Api;
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
}
