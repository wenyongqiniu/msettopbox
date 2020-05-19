package com.waoqi.msettopboxs.net;


import com.waoqi.mvp.net.XApi;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {

    private static VerificationService verService;

    public static VerificationService getVerService(String API_BASE_URL) {
        if (verService == null) {
            synchronized (Api.class) {
                if (verService == null) {
                    verService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(VerificationService.class);
                }
            }
        }
        return verService;
    }
}
