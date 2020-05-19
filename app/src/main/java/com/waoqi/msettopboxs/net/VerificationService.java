package com.waoqi.msettopboxs.net;

import com.waoqi.msettopboxs.bean.VerificationBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VerificationService {

    @GET("EPG/Ott/jsp/verifyuser.jsp")
    Flowable<VerificationBean> getVerifyuser(@Query("OTTUserToken") String OTTUserToken,
                                             @Query("UserID") String userID,
                                             @Query("MAC") String mac);


    ///心跳接口
    @GET("EPG/Ott/jsp/HeartBeat.jsp")
    Flowable<VerificationBean> heartBeat(@Query("OTTUserToken") String OTTUserToken,
                                             @Query("UserID") String userID);

}
