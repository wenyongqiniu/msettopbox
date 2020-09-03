package com.waoqi.msettopboxs.presenter;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.bean.BasePresponce;
import com.waoqi.msettopboxs.bean.VerificationBean;
import com.waoqi.msettopboxs.net.Api;
import com.waoqi.msettopboxs.net.MyApi;
import com.waoqi.msettopboxs.net.requestbean.WatchHistoryBean;
import com.waoqi.msettopboxs.ui.activity.TestActivity;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

public class TestPresenter extends XPresent<TestActivity> {

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


    public void saveHistoty() {
        WatchHistoryBean historyBean = new WatchHistoryBean();
        historyBean.setContentName("【月经不调】3、月经不调会影响生育吗？");
        historyBean.setContentId("159064924718699");
        historyBean.setExtraContentId(3);
        historyBean.setContentTotalTime(300);
        historyBean.setStartWatchTime(300);
        historyBean.setEndWatchTime(3000);
        historyBean.setPlayTime(300);
        historyBean.setLogType("watching");
        historyBean.setAccount("19825783259");
        historyBean.setImageUrl("http://video-head-pic.obs.cidc-rp-12.joint.cmecloud.cn/2020/06/29/566d45f7-e7ee-4027-be6a-4f3cfee0f2a3.jpg");
        MyApi.getMyApiService()
                .saveHistoty(historyBean)
                .compose(XApi.<BasePresponce>getApiTransformer())
                .compose(XApi.<BasePresponce>getScheduler())
                .subscribe(new ApiSubscriber<BasePresponce>() {
                    @Override
                    public void onNext(BasePresponce videoBean) {

                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });

    }


}
