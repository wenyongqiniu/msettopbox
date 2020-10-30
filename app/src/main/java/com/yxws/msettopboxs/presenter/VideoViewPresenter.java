package com.yxws.msettopboxs.presenter;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;
import android.os.Looper;
import android.text.TextUtils;

import com.chinamobile.SWDevInfoManager;
import com.chinamobile.authclient.AuthClient;
import com.chinamobile.authclient.Constants;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.yxws.msettopboxs.bean.AuthBean;
import com.yxws.msettopboxs.bean.AuthParam;
import com.yxws.msettopboxs.bean.BasePresponce;
import com.yxws.msettopboxs.bean.VerificationBean;
import com.yxws.msettopboxs.bean.VideoAddressBean;
import com.yxws.msettopboxs.bean.VideoDetailBean;
import com.yxws.msettopboxs.config.Constant;
import com.yxws.msettopboxs.net.Api;
import com.yxws.msettopboxs.net.MyApi;
import com.yxws.msettopboxs.net.requestbean.WatchHistoryBean;
import com.yxws.msettopboxs.ui.activity.VideoViewActivty;
import com.yxws.msettopboxs.util.DataHelper;
import com.yxws.mvp.mvp.XPresent;
import com.yxws.mvp.net.ApiSubscriber;
import com.yxws.mvp.net.NetError;
import com.yxws.mvp.net.XApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class VideoViewPresenter extends XPresent<VideoViewActivty> {


    /**
     * 保存观看记录
     *
     * @param contentName      视频名称
     * @param contentId        cp_album_id这个ID
     * @param extraContentId   cp_tv_id这个ID
     * @param contentTotalTime 节目总时长：秒（s）
     * @param startWatchTime   用户开始看的时间
     * @param endWatchTime     用户结束看时间
     * @param playTime         用户看了多少时长
     * @param logType          同步数据时，用户当前播放状态
     * @param userToken        用户登录账号,机顶盒登陆账号
     * @param imageUrl         海报URL
     */
    public void saveHistoty(String contentName, String contentId, int extraContentId, long contentTotalTime,
                            long startWatchTime, long endWatchTime, long playTime, String logType,
                            String userToken, String imageUrl) {

        if (contentName == null || contentId == null || logType == null || imageUrl == null) {
            return;
        }

        WatchHistoryBean historyBean = new WatchHistoryBean();
        historyBean.setContentName(contentName);
        historyBean.setContentId(contentId);
        historyBean.setExtraContentId(extraContentId);
        historyBean.setContentTotalTime(contentTotalTime);
        historyBean.setStartWatchTime(startWatchTime);
        historyBean.setEndWatchTime(endWatchTime);
        historyBean.setPlayTime(playTime);
        historyBean.setLogType(logType);
        historyBean.setUserToken(userToken);
        historyBean.setAccount("");
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


    /**
     * 获取视频详情
     *
     * @param videoId
     */
    public void getVideoDetail(int videoId) {
        MyApi.getMyApiService()
                .getVideoDetail(videoId)
                .compose(XApi.<VideoDetailBean>getApiTransformer())
                .compose(XApi.<VideoDetailBean>getScheduler())
                .subscribe(new ApiSubscriber<VideoDetailBean>() {
                    @Override
                    public void onNext(VideoDetailBean videoBean) {
                        getVideoAddress(videoBean.getData());

                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }


    /**
     * 视频播放地址
     */
    private void getVideoAddress(VideoDetailBean mVideoDetailBean) {
        KLog.e(mVideoDetailBean.getCpAlbumId() + "     " + mVideoDetailBean.getCpTvId());
        MyApi.getMyApiService()
                .getVideoAddress(mVideoDetailBean.getCpAlbumId(), mVideoDetailBean.getCpTvId())
                .compose(XApi.<VideoAddressBean>getApiTransformer())
                .compose(XApi.<VideoAddressBean>getScheduler())
                .subscribe(new ApiSubscriber<VideoAddressBean>() {
                    @Override
                    public void onNext(VideoAddressBean videoBean) {
//                        nextApi(videoBean, mVideoDetailBean);
                        verfyUser(videoBean, mVideoDetailBean);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }


    public void verfyUser(VideoAddressBean videoBean, VideoDetailBean mVideoDetailBean) {
        DevInfoManager systemService = SWDevInfoManager.getDevInfoManager(getV().getApplicationContext());
        String epg_address = systemService.getValue(DevInfoManager.EPG_ADDRESS);
        String mobile_phone_number = systemService.getValue(DevInfoManager.PHONE);
        if (TextUtils.isEmpty(mobile_phone_number)) {
            mobile_phone_number = systemService.getValue(DevInfoManager.ACCOUNT);
        }
        String stb_mac = systemService.getValue(DevInfoManager.STB_MAC);
        String cdn_type = systemService.getValue(DevInfoManager.CDN_TYPE);

        AuthClient mAuthClient = AuthClient.getIntance(getV().getApplicationContext());
        String finalMobile_phone_number = mobile_phone_number;
        mAuthClient.getToken(new AuthClient.CallBack() {
            @Override
            public void onResult(JSONObject jsonObject) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        try {
                            final String token = jsonObject.getString(Constants.VALUNE_KEY_TOKEN);

                            getVerifyuser(epg_address, cdn_type, token, finalMobile_phone_number, stb_mac, videoBean, mVideoDetailBean);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        } catch (NullPointerException e) {
                            e.printStackTrace();

                        }
                        Looper.loop();
                    }
                }).start();


            }
        });


    }

    private void getVerifyuser(String epg_address, String cdn_type, String userToken, final String mobile_phone_number, String stb_mac,
                               VideoAddressBean videoBean, VideoDetailBean mVideoDetailBean) {
        if (TextUtils.isEmpty(epg_address) || TextUtils.isEmpty(cdn_type) || TextUtils.isEmpty(userToken)
                || TextUtils.isEmpty(mobile_phone_number) || TextUtils.isEmpty(stb_mac)) {
            return;
        }
        Api.getVerService(epg_address + "/")
                .getVerifyuser(userToken, mobile_phone_number, stb_mac)
                .compose(XApi.<VerificationBean>getApiTransformer())
                .compose(XApi.<VerificationBean>getScheduler())
                .subscribe(new ApiSubscriber<VerificationBean>() {
                    @Override
                    public void onNext(VerificationBean verificationBean) {


                        heartBeat(epg_address, verificationBean.getOTTUserToken(), mobile_phone_number);

                        nextApi(verificationBean.getOTTUserToken(), epg_address, cdn_type
                                , mobile_phone_number, stb_mac, videoBean, mVideoDetailBean);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }


    private void nextApi(String OTTUserToken, String epg_address, String cdn_type, String mobile_phone_number, String stb_mac,
                         VideoAddressBean videoBean, VideoDetailBean mVideoDetailBean) {

        AuthParam authParam = new AuthParam();
        authParam.setOTTUserToken(OTTUserToken);
        authParam.setUserID(mobile_phone_number);
        authParam.setMAC(stb_mac);


        if (cdn_type.endsWith("HW")) {
            VideoAddressBean temp = null;
            for (VideoAddressBean videoAddressBean : videoBean.getData()) {
                if (videoAddressBean.getType() == 1) {
                    temp = videoAddressBean;
                    break;
                }
            }
            authParam.setContentID(temp.getSeriesId());

            getVideoAddress(OTTUserToken,epg_address, authParam, temp.getSeriesId(), temp.getMovieId(), temp.getTvName(), mVideoDetailBean);
        } else if (cdn_type.endsWith("ZTE")) {
            VideoAddressBean temp = null;
            for (VideoAddressBean videoAddressBean : videoBean.getData()) {
                if (videoAddressBean.getType() == 0) {
                    temp = videoAddressBean;
                    break;
                }
            }
            authParam.setContentID(temp.getSeriesId());
            getVideoAddress(OTTUserToken,epg_address, authParam, temp.getSeriesId(), temp.getMovieId(), temp.getTvName(), mVideoDetailBean);
        }
    }

    /**
     * 视频播放地址
     */
    private void getVideoAddress(String OTTUserToken,String epg_address, AuthParam authParam, String seriesId, String movieId, String title, VideoDetailBean mVideoDetailBean) {
        Gson gson = new Gson();
        String obj = gson.toJson(authParam);
        KLog.e("wlx", "请求AuthCode参数：  " + obj);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj);
        Api.getVerService(epg_address + "/")
                .auth(body)
                .compose(XApi.<AuthBean>getApiTransformer())
                .compose(XApi.<AuthBean>getApiTransformer())
                .compose(XApi.<AuthBean>getScheduler())
                .subscribe(new ApiSubscriber<AuthBean>() {
                    @Override
                    public void onNext(AuthBean videoBean) {
                        startActivity(OTTUserToken,videoBean, seriesId, movieId, title, mVideoDetailBean);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    private void startActivity(String OTTUserToken,AuthBean videoBean, String seriesId, String movieId, String title, VideoDetailBean mVideoDetailBean) {
        @SuppressLint("WrongConstant")
        DevInfoManager systemService = SWDevInfoManager.getDevInfoManager(getV().getApplicationContext());
        String cdn_type = systemService.getValue(DevInfoManager.CDN_TYPE);
        StringBuffer stringBuffer = new StringBuffer();

        if (cdn_type.endsWith("HW")) {
            stringBuffer.append(systemService.getValue(DevInfoManager.CDN_ADDRESS_BACK))
                    .append("/tianhongyxws")
                    .append("/vod")
                    .append("/").append(seriesId)
                    .append("/").append(movieId)
                    .append("?OTTUserToken=").append(OTTUserToken)
                    .append("&[$").append(videoBean.getAuthCode()).append("]");

        } else if (cdn_type.contains("ZTE")) {
            stringBuffer.append(systemService.getValue(DevInfoManager.CDN_ADDRESS))
                    .append("/tianhongyxwszx")
                    .append("/vod")
                    .append("/").append(seriesId)
                    .append("/").append(movieId)
                    .append("?OTTUserToken=").append(OTTUserToken)
                    .append("&[$").append(videoBean.getAuthCode()).append("]");
        }
        getV().setVideoDetail(stringBuffer.toString(), title, mVideoDetailBean);
    }


    /**
     * 心跳接口
     *
     * @param epg_address         请求地址
     * @param OTTUserToken
     * @param mobile_phone_number 手机号
     */
    public void heartBeat(String epg_address, String OTTUserToken, String mobile_phone_number) {
        Observable.interval(2, 60, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔60秒产生1个数字（从0开始递增1，无限个）
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
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
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }
}
