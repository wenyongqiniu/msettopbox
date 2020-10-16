package com.yxws.msettopboxs.presenter;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;

import com.chinamobile.SWDevInfoManager;
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

    public void getVideoDetail(int videoId) {




        MyApi.getMyApiService()
                .getVideoDetail(videoId)
                .compose(XApi.<VideoDetailBean>getApiTransformer())
                .compose(XApi.<VideoDetailBean>getScheduler())
                .subscribe(new ApiSubscriber<VideoDetailBean>() {
                    @Override
                    public void onNext(VideoDetailBean videoBean) {
                        getVideoAddress(videoBean.getData());
                        KLog.e(" VideoDetailBean  " + videoBean.toString());
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
                        nextApi(videoBean, mVideoDetailBean);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }


    private void nextApi(VideoAddressBean videoBean, VideoDetailBean mVideoDetailBean) {
        @SuppressLint("WrongConstant")
        DevInfoManager systemService = SWDevInfoManager.getDevInfoManager(getV().getApplicationContext());

        AuthParam authParam = new AuthParam();

        authParam.setOTTUserToken(DataHelper.getStringSF(getV().getApplicationContext(), Constant.OTTUSERTOKEN));
        authParam.setUserID(DataHelper.getStringSF(getV().getApplicationContext(), Constant.USERID));
        authParam.setMAC(systemService.getValue(DevInfoManager.STB_MAC));

        String epg_addresss = systemService.getValue(DevInfoManager.EPG_ADDRESS);
        String cdn_type = systemService.getValue(DevInfoManager.CDN_TYPE);

        if (cdn_type.endsWith("HW")) {
            VideoAddressBean temp = null;
            for (VideoAddressBean videoAddressBean : videoBean.getData()) {
                if (videoAddressBean.getType() == 1) {
                    temp = videoAddressBean;
                    break;
                }
            }
            authParam.setContentID(temp.getSeriesId());

            getVideoAddress(epg_addresss, authParam, temp.getSeriesId(), temp.getMovieId(), temp.getTvName(), mVideoDetailBean);
        } else if (cdn_type.endsWith("ZTE")) {
            VideoAddressBean temp = null;
            for (VideoAddressBean videoAddressBean : videoBean.getData()) {
                if (videoAddressBean.getType() == 0) {
                    temp = videoAddressBean;
                    break;
                }
            }
            authParam.setContentID(temp.getSeriesId());
            getVideoAddress(epg_addresss, authParam, temp.getSeriesId(), temp.getMovieId(), temp.getTvName(), mVideoDetailBean);
        }
    }

    /**
     * 视频播放地址
     */
    private void getVideoAddress(String epg_address, AuthParam authParam, String seriesId, String movieId, String title, VideoDetailBean mVideoDetailBean) {
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
                        startActivity(videoBean, seriesId, movieId, title, mVideoDetailBean);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }
                });
    }

    private void startActivity(AuthBean videoBean, String seriesId, String movieId, String title, VideoDetailBean mVideoDetailBean) {
        @SuppressLint("WrongConstant")
        DevInfoManager systemService = (DevInfoManager) getV().getApplicationContext().getSystemService(DevInfoManager.DATA_SERVER);
        String cdn_type = systemService.getValue(DevInfoManager.CDN_TYPE);
        StringBuffer stringBuffer = new StringBuffer();

        if (cdn_type.endsWith("HW")) {
            stringBuffer.append(systemService.getValue(DevInfoManager.CDN_ADDRESS_BACK))
                    .append("/tianhongyxws")
                    .append("/vod")
                    .append("/").append(seriesId)
                    .append("/").append(movieId)
                    .append("?OTTUserToken=").append(DataHelper.getStringSF(getV().getApplicationContext(), Constant.OTTUSERTOKEN))
                    .append("&[$").append(videoBean.getAuthCode()).append("]");

        } else if (cdn_type.contains("ZTE")) {
            stringBuffer.append(systemService.getValue(DevInfoManager.CDN_ADDRESS))
                    .append("/tianhongyxwszx")
                    .append("/vod")
                    .append("/").append(seriesId)
                    .append("/").append(movieId)
                    .append("?OTTUserToken=").append(DataHelper.getStringSF(getV().getApplicationContext(), Constant.OTTUSERTOKEN))
                    .append("&[$").append(videoBean.getAuthCode()).append("]");
        }
        KLog.d("wlx", "播放地址：  " + stringBuffer.toString());
        getV().setVideoDetail(stringBuffer.toString(), title, mVideoDetailBean);
    }

}
