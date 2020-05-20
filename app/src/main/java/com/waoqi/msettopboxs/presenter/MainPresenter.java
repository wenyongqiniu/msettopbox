package com.waoqi.msettopboxs.presenter;

import android.app.DevInfoManager;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.bean.Bean;
import com.waoqi.msettopboxs.bean.VerificationBean;
import com.waoqi.msettopboxs.net.Api;
import com.waoqi.msettopboxs.net.VerificationService;
import com.waoqi.msettopboxs.ui.activity.MainActivity;
import com.waoqi.msettopboxs.ui.activity.SplashActivity;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

import static android.app.DevInfoManager.EPG_ADDRESS;

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
                        KLog.a("wlx", verificationBean.getOTTUserToken());
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


    public void heartBeat() {
        Api.getVerService("http://182.92.118.35:8088/")
                .findHomeFavorite("0")
                .compose(XApi.<Bean>getApiTransformer())
                .compose(XApi.<Bean>getScheduler())
                .compose(getV().<Bean>bindToLifecycle())
                .subscribe(new ApiSubscriber<Bean>() {
                    @Override
                    public void onNext(Bean verificationBean) {

                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });

    }


}
