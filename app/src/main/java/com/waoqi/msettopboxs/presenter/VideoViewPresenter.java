package com.waoqi.msettopboxs.presenter;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;
import android.content.Intent;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.waoqi.msettopboxs.bean.AuthBean;
import com.waoqi.msettopboxs.bean.AuthParam;
import com.waoqi.msettopboxs.bean.BasePresponce;
import com.waoqi.msettopboxs.bean.VerificationBean;
import com.waoqi.msettopboxs.bean.VideoAddressBean;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.bean.VideoDetailBean;
import com.waoqi.msettopboxs.net.Api;
import com.waoqi.msettopboxs.net.MyApi;
import com.waoqi.msettopboxs.net.requestbean.WatchHistoryBean;
import com.waoqi.msettopboxs.ui.activity.VideoDetailActivity;
import com.waoqi.msettopboxs.ui.activity.VideoViewActivty;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.mvp.mvp.XPresent;
import com.waoqi.mvp.net.ApiSubscriber;
import com.waoqi.mvp.net.NetError;
import com.waoqi.mvp.net.XApi;

import okhttp3.RequestBody;

public class VideoViewPresenter extends XPresent<VideoViewActivty> {


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


    /**
     * @param contentName      视频名称
     * @param contentId        cp_album_id这个ID
     * @param extraContentId   cp_tv_id这个ID
     * @param contentTotalTime 节目总时长：秒（s）
     * @param startWatchTime   用户开始看的时间
     * @param endWatchTime     用户结束看时间
     * @param playTime         用户看了多少时长
     * @param logType          同步数据时，用户当前播放状态
     * @param account          用户登录账号,机顶盒登陆账号
     * @param imageUrl         海报URL
     */
    public void saveHistoty(String contentName, String contentId, int extraContentId, int contentTotalTime,
                            int startWatchTime, int endWatchTime, int playTime, String logType,
                            String account, String imageUrl) {
        WatchHistoryBean historyBean = new WatchHistoryBean();
        historyBean.setContentName(contentName);
        historyBean.setContentId(contentId);
        historyBean.setExtraContentId(extraContentId);
        historyBean.setContentTotalTime(contentTotalTime);
        historyBean.setStartWatchTime(startWatchTime);
        historyBean.setEndWatchTime(endWatchTime);
        historyBean.setPlayTime(playTime);
        historyBean.setLogType(logType);
        historyBean.setAccount(account);
        historyBean.setImageUrl(imageUrl);
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
