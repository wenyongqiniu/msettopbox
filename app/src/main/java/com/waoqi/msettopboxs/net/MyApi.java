package com.waoqi.msettopboxs.net;


import com.waoqi.msettopboxs.config.Constant;
import com.waoqi.mvp.net.XApi;

/**
 * Created by wanglei on 2016/12/31.
 */

public class MyApi {

    private static MyAppService sMyAppService;


    public static MyAppService getMyApiService() {
        if (sMyAppService == null) {
            synchronized (MyApi.class) {
                if (sMyAppService == null) {
                    sMyAppService = XApi.getInstance().getRetrofit(Constant.BASEURL, true).create(MyAppService.class);
                }
            }
        }
        return sMyAppService;
    }


}
