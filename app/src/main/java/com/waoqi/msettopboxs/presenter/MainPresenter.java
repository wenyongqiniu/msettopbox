package com.waoqi.msettopboxs.presenter;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.bean.BasePresponce;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.UserBean;
import com.waoqi.msettopboxs.bean.VerificationBean;
import com.waoqi.msettopboxs.config.Constant;
import com.waoqi.msettopboxs.net.Api;
import com.waoqi.msettopboxs.net.MyApi;
import com.waoqi.msettopboxs.ui.activity.MainActivity;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

public class MainPresenter extends XPresent<MainActivity> {

    /**
     * 认证SSO
     *
     * @param epg_address         请求地址
     * @param userToken
     * @param mobile_phone_number
     * @param stbmac
     */
    public void verfyUser(final String epg_address, String userToken, final String mobile_phone_number, String stbmac) {
        Api.getVerService(epg_address + "/")
                .getVerifyuser(userToken, mobile_phone_number, stbmac)
                .compose(XApi.<VerificationBean>getApiTransformer())
                .compose(XApi.<VerificationBean>getScheduler())
                .subscribe(new ApiSubscriber<VerificationBean>() {
                    @Override
                    public void onNext(VerificationBean verificationBean) {
                        DataHelper.setStringSF(getV().getApplication(), Constant.USERID, verificationBean.getUserID());
                        DataHelper.setStringSF(getV().getApplication(), Constant.OTTUSERTOKEN, verificationBean.getOTTUserToken());

                        heartBeat(epg_address, verificationBean.getOTTUserToken(), mobile_phone_number);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });

    }

    /**
     * 心跳接口
     *
     * @param epg_address         请求地址
     * @param OTTUserToken
     * @param mobile_phone_number 手机号
     */
    public void heartBeat(String epg_address, String OTTUserToken, String mobile_phone_number) {
        Api.getVerService(epg_address + "/")
                .heartBeat(OTTUserToken, mobile_phone_number)
                .compose(XApi.<VerificationBean>getApiTransformer())
                .compose(XApi.<VerificationBean>getScheduler())
                .compose(getV().<VerificationBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<VerificationBean>() {
                    @Override
                    public void onNext(VerificationBean verificationBean) {

                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });

    }

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

    public void toLogin(String phone) {
        MyApi.getMyApiService()
                .login(phone)
                .compose(XApi.<UserBean>getApiTransformer())
                .compose(XApi.<UserBean>getScheduler())
                .subscribe(new ApiSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        getV().getUserInfo(userBean.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    public void toBuy(String userId, String userToken) {
        MyApi.getMyApiService()
                .toBuy(userId, "JSHDC-ASPIRE-3103b351-0baf-4e33-bde4-fe71fe8c227")
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
}
