package com.yxws.msettopboxs.ui.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.socks.library.KLog;
import com.yxws.msettopboxs.R;

import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_HOME_KEY;
import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_KEY;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String payUrl = getIntent().getStringExtra("PayH5Url");
        webView = (WebView) findViewById(R.id.webview_detail);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        //支持自动加载图片
        webView.getSettings().setLoadsImagesAutomatically(true);
        // 扩大比例的缩放
        webView.getSettings().setUseWideViewPort(false);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                KLog.e(request);
                KLog.e(error);
            }
        });
        webView.loadUrl(payUrl);
        initReceiver();
    }

    private  HomeRecaiver mHomeRecaiver;

    private void initReceiver() {
        mHomeRecaiver = new  HomeRecaiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeRecaiver, filter);
    }

    class HomeRecaiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHomeRecaiver != null) {
            unregisterReceiver(mHomeRecaiver);
        }
    }

}
