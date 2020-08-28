package com.waoqi.msettopboxs.net;


import com.waoqi.mvp.net.XApi;

/**
 * author: luxi
 * email : mwangluxi@163.com
 * create by 2020/8/28 11:55
 * desc : 牌照方接口调用
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
