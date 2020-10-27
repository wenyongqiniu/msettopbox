package com.yxws.msettopboxs.https;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

@Deprecated
public class OkHttpGlideModule implements GlideModule {
    X509TrustManager trustAllCert =
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Do nothing.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        SSLSocketFactoryCompat sslSocketFactoryCompat = new SSLSocketFactoryCompat(trustAllCert);

        OkHttpClient build = new OkHttpClient().newBuilder()
                .sslSocketFactory(sslSocketFactoryCompat, trustAllCert)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(build));
    }

}