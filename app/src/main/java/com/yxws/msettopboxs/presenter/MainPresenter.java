package com.yxws.msettopboxs.presenter;

import android.widget.Toast;

import com.yxws.msettopboxs.bean.BasePresponce;
import com.yxws.msettopboxs.bean.HotVideoBean;
import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.msettopboxs.bean.UserBean;
import com.yxws.msettopboxs.net.MyApi;
import com.yxws.msettopboxs.ui.activity.MainActivity;
import com.yxws.mvp.mvp.XPresent;
import com.yxws.mvp.net.ApiSubscriber;
import com.yxws.mvp.net.NetError;
import com.yxws.mvp.net.XApi;

import java.util.List;

public class MainPresenter extends XPresent<MainActivity> {

    public void getAllCategory() {
        MyApi.getMyApiService()
                .getAllCategory()
                .compose(XApi.<SearchLevelBean>getApiTransformer())
                .compose(XApi.<SearchLevelBean>getScheduler())
                .subscribe(new ApiSubscriber<SearchLevelBean>() {
                    @Override
                    public void onNext(SearchLevelBean searchLevelBean) {
                        getV().setSearchLevel(searchLevelBean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {
                        Toast.makeText(getV().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getHotVideo() {
        MyApi.getMyApiService()
                .getHotVideo()
                .compose(XApi.<HotVideoBean>getApiTransformer())
                .compose(XApi.<HotVideoBean>getScheduler())
                .subscribe(new ApiSubscriber<HotVideoBean>() {
                    @Override
                    public void onNext(HotVideoBean hotVideoBean) {
                        List<HotVideoBean> hotVideoBeans = hotVideoBean.getData();
                        if (hotVideoBeans != null && !hotVideoBeans.isEmpty()) {
                            getV().setHotVideo(hotVideoBeans.get(0));
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void toLogin(String phone, String token) {
        MyApi.getMyApiService()
                .login(phone, token)
                .compose(XApi.<UserBean>getApiTransformer())
                .compose(XApi.<UserBean>getScheduler())
                .subscribe(new ApiSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        if (userBean.getData() != null) {
                            getV().getUserInfo(userBean.getData());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void toLogin(String token) {
        MyApi.getMyApiService()
                .login(token)
                .compose(XApi.<UserBean>getApiTransformer())
                .compose(XApi.<UserBean>getScheduler())
                .subscribe(new ApiSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        if (userBean.getData() != null) {
                            getV().getUserInfo(userBean.getData());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void toBuy(String userToken) {
        MyApi.getMyApiService()
                .toBuy("", userToken,"")
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


    public void isVip(String userToken) {
        MyApi.getMyApiService()
                .isVip("", userToken, "")
                .compose(XApi.<BasePresponce<String>>getApiTransformer())
                .compose(XApi.<BasePresponce<String>>getScheduler())
                .subscribe(new ApiSubscriber<BasePresponce<String>>() {
                    @Override
                    public void onNext(BasePresponce<String> vipStateBean) {
                        if (vipStateBean.getData().contains("true")) {
                            Toast.makeText(getV().getApplicationContext(), "你已经是Vip会员了", Toast.LENGTH_SHORT).show();
                        } else {
                            getV().toBuyVip();
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                    }
                });
    }


}
