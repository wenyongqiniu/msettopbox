package com.yxws.msettopboxs.net.interceptor;

import android.app.DevInfoManager;

import com.chinamobile.SWDevInfoManager;
import com.yxws.msettopboxs.MyApplication;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: luxi
 * create by 2020/10/13 18:39
 * desc :
 */
public class UserAgent implements Interceptor {
    private MyApplication mMyApplication;

    public UserAgent(MyApplication myApplication) {
        this.mMyApplication = myApplication;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        Request.Builder builder = request.newBuilder();
        DevInfoManager systemService = SWDevInfoManager.getDevInfoManager(mMyApplication);
        String cdn_type = systemService.getValue(DevInfoManager.CDN_TYPE);
        if (systemService != null && cdn_type != null) {
            builder.removeHeader("User-Agent");
            if (cdn_type.equals("HW")) {
                builder.addHeader("User-Agent", "tianhongyxws");
            } else if (cdn_type.equals("ZTE")) {
                builder.addHeader("User-Agent", "tianhongyxwszx");
            }
        }
        builder.url(url);
        return chain.proceed(builder.build());
    }
}
