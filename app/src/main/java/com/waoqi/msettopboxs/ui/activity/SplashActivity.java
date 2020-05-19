package com.waoqi.msettopboxs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.presenter.SplashPresenter;
import com.waoqi.mvp.imageloader.ILFactory;
import com.waoqi.mvp.mvp.XActivity;

public class SplashActivity extends XActivity<SplashPresenter> {

    private ImageView lemonSplashId;

    @Override
    public void initView() {
        lemonSplashId = (ImageView) findViewById(R.id.lemon_splash_id);
        ILFactory.getLoader().loadAssets(lemonSplashId, "assets://lemon_splash.jpg", null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().startMainActivity();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashPresenter newP() {
        return new SplashPresenter();
    }

    public void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
