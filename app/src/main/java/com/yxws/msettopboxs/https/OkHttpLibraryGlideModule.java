package com.yxws.msettopboxs.https;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

@GlideModule
public final class OkHttpLibraryGlideModule extends LibraryGlideModule {
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
    public void registerComponents(Context context, Glide glide, Registry registry) {
        SSLSocketFactoryCompat sslSocketFactoryCompat = new SSLSocketFactoryCompat(trustAllCert);
        OkHttpClient.Builder build = new OkHttpClient().newBuilder()
                .sslSocketFactory(sslSocketFactoryCompat, trustAllCert)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(build.build()));
    }
}