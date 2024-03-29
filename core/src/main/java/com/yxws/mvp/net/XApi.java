package com.yxws.mvp.net;

import android.content.Context;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.yxws.mvp.net.log.DefaultFormatPrinter;
import com.yxws.mvp.net.log.RequestInterceptor;
import com.yxws.mvp.net.progress.ProgressHelper;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wanglei on 2016/12/24.
 */

public class XApi {
    private static NetProvider sProvider = null;

    private Map<String, NetProvider> providerMap = new HashMap<>();
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String, OkHttpClient> clientMap = new HashMap<>();

    private Context mContext;
    public static final long connectTimeoutMills = 10 * 1000l;
    public static final long readTimeoutMills = 10 * 1000l;

    private static XApi instance;

    private XApi() {

    }

    public static XApi getInstance() {
        if (instance == null) {
            synchronized (XApi.class) {
                if (instance == null) {
                    instance = new XApi();
                }
            }
        }
        return instance;
    }


    public static <S> S get(String baseUrl, Class<S> service) {
        return getInstance().getRetrofit(baseUrl, true).create(service);
    }

    public static void registerProvider(NetProvider provider) {
        XApi.sProvider = provider;
    }

    public static void registerProvider(String baseUrl, NetProvider provider) {
        getInstance().providerMap.put(baseUrl, provider);
    }


    public Retrofit getRetrofit(String baseUrl, boolean useRx) {
        return getRetrofit(baseUrl, null, useRx);
    }


    public Retrofit getRetrofit(String baseUrl, NetProvider provider, boolean useRx) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (retrofitMap.get(baseUrl) != null) return retrofitMap.get(baseUrl);

        if (provider == null) {
            provider = providerMap.get(baseUrl);
            if (provider == null) {
                provider = sProvider;
            }
        }
        checkProvider(provider);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, provider))
                .addConverterFactory(GsonConverterFactory.create());
        if (useRx) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }

        Retrofit retrofit = builder.build();
        retrofitMap.put(baseUrl, retrofit);
        providerMap.put(baseUrl, provider);

        return retrofit;
    }

    private OkHttpClient getClient(String baseUrl, NetProvider provider) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (clientMap.get(baseUrl) != null) return clientMap.get(baseUrl);

        checkProvider(provider);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                ? provider.configConnectTimeoutMills()
                : connectTimeoutMills, TimeUnit.MILLISECONDS);
        builder.readTimeout(provider.configReadTimeoutMills() != 0
                ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);
//        try {
//            if (mContext != null) {
//                //https的全局自签名证书
//                InputStream certificates = mContext.getAssets().open("server.cert");
//                HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, new InputStream[]{certificates});
//                //https双向认证证书
//                //HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(bksFile, password, certificates);
//                builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        HttpsUtils.UnSafeTrustManager trustAllCert = new HttpsUtils.UnSafeTrustManager();
        SSLSocketFactoryCompat socketFactoryCompat = new SSLSocketFactoryCompat(trustAllCert);
        builder.sslSocketFactory(socketFactoryCompat, trustAllCert);

        //https的全局访问规则
//        builder.hostnameVerifier(new UnSafeHostnameVerifier(baseUrl));

        CookieJar cookieJar = provider.configCookie();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        provider.configHttps(builder);

        RequestHandler handler = provider.configHandler();
        if (handler != null) {
            builder.addInterceptor(new XInterceptor(handler));
        }

        if (provider.dispatchProgressEnable()) {
            builder.addInterceptor(
                    ProgressHelper.get().getInterceptor());
        }

        Interceptor[] interceptors = provider.configInterceptors();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (provider.configLogEnable()) {
            RequestInterceptor logInterceptor = new RequestInterceptor();
            logInterceptor.setPrintLevel(RequestInterceptor.Level.ALL);
            logInterceptor.setPrinter(new DefaultFormatPrinter());
            builder.addInterceptor(logInterceptor);
        }

        OkHttpClient client = builder.build();
        clientMap.put(baseUrl, client);
        providerMap.put(baseUrl, provider);

        return client;
    }


    public void setContext(Context context) {
        mContext = context;
    }

    private void checkProvider(NetProvider provider) {
        if (provider == null) {
            throw new IllegalStateException("must register provider first");
        }
    }

    public static NetProvider getCommonProvider() {
        return sProvider;
    }

    public Map<String, Retrofit> getRetrofitMap() {
        return retrofitMap;
    }

    public Map<String, OkHttpClient> getClientMap() {
        return clientMap;
    }

    public static void clearCache() {
        getInstance().retrofitMap.clear();
        getInstance().clientMap.clear();
    }

    /**
     * 线程切换
     *
     * @return
     */
    public static <T extends IModel> FlowableTransformer<T, T> getScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            KLog.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            KLog.i("############### verify " + hostname + " " + this.host);
            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
                return false;
            return true;
        }
    }

    /**
     * 异常处理变换
     *
     * @return
     */
    public static <T extends IModel> FlowableTransformer<T, T> getApiTransformer() {

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.flatMap(new Function<T, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(T model) throws Exception {

                        if (model == null || model.isNull()) {
                            return Flowable.error(new NetError(new Throwable("空数据"), NetError.NoDataError));
                        } else if (model.isAuthError()) {
                            return Flowable.error(new NetError(new Throwable("验证错误"), NetError.AuthError));
                        } else if (model.isBizError()) {
                            return Flowable.error(new NetError(new Throwable("业务错误"), NetError.BusinessError));
                        } else {
                            return Flowable.just(model);
                        }
                    }
                });
            }
        };
    }


}
