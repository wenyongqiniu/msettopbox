package com.waoqi.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;
import android.os.Bundle;
import android.widget.TextView;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.presenter.MainPresenter;
import com.waoqi.msettopboxs.util.DevInfoUtil;
import com.waoqi.msettopboxs.util.OnResultCall;
import com.waoqi.mvp.mvp.XActivity;


public class MainActivity extends XActivity<MainPresenter> {
    private TextView tv;

    @Override
    public void initView() {
        tv = (TextView) findViewById(R.id.tv);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        DevInfoUtil.getValue(this);
        @SuppressLint("WrongConstant") final DevInfoManager devInfoManager = (DevInfoManager) getSystemService(DevInfoManager.DATA_SERVER);
        DevInfoUtil.getToken(this, new OnResultCall() {
            @Override
            public void onResult(String token) {
                getP().verfyUser(devInfoManager.getValue(DevInfoManager.EPG_ADDRESS), token, devInfoManager.getValue(DevInfoManager.PHONE), devInfoManager.getValue(DevInfoManager.STB_MAC));
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
    }
}
