package com.waoqi.msettopboxs.net;

import com.waoqi.msettopboxs.bean.AuthBean;
import com.waoqi.msettopboxs.bean.Bean;
import com.waoqi.msettopboxs.bean.VerificationBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author: luxi
 * email : mwangluxi@163.com
 * create by 2020/8/28 11:55
 * desc : 牌照方接口
 */
public interface VerificationService {

    @GET("Ott/jsp/verifyuser.jsp")
    Flowable<VerificationBean> getVerifyuser(@Query("OTTUserToken") String OTTUserToken,
                                             @Query("UserID") String userID,
                                             @Query("MAC") String mac);


    ///心跳接口
    @GET("Ott/jsp/HeartBeat.jsp")
    Flowable<VerificationBean> heartBeat(@Query("OTTUserToken") String OTTUserToken,
                                         @Query("UserID") String userID);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Ott/jsp/Auth.jsp")
    Flowable<AuthBean> auth(@Body RequestBody info);


}
