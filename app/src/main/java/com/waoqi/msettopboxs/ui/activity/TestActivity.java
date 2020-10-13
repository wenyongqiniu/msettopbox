package com.waoqi.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DevInfoManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.chinamobile.SWDevInfoManager;
import com.chinamobile.impl.DevInfoManagerImpl;
import com.iflytek.xiri.Feedback;
import com.iflytek.xiri.scene.ISceneListener;
import com.iflytek.xiri.scene.Scene;
import com.iflytek.xiri.scene.ScenePlus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.widget.LoadingView;
import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.config.Constant;
import com.waoqi.msettopboxs.presenter.TestPresenter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.msettopboxs.util.DevInfoUtil;
import com.waoqi.msettopboxs.util.OnResultCall;
import com.waoqi.msettopboxs.util.RawFileUtils;
import com.waoqi.mvp.mvp.XActivity;

import me.jessyan.autosize.internal.CustomAdapt;

public class TestActivity extends XActivity<TestPresenter> implements CustomAdapt {
    @Override
    public void initView() {

    }

    DevInfoManager devInfoManager;
    @SuppressLint("WrongConstant")
    @Override
    public void initData(Bundle savedInstanceState) {
        devInfoManager = SWDevInfoManager.getDevInfoManager(this);
//        KLog.e("object " + devInfoManager);
//        KLog.e(devInfoManager.getValue(DevInfoManager.PHONE));
    }


    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public TestPresenter newP() {
        return new TestPresenter();
    }

    public void click(View v) {
        Intent intent = new Intent(context, VideoViewActivty.class);

        switch (v.getId()) {
            case R.id.btn1:

//                KLog.e(devInfoManager.getValue(DevInfoManager.PHONE) +" 手机号");
//                DevInfoUtil.getToken(this, new OnResultCall() {
//                    @Override
//                    public void onResult(String token) {
//                        DataHelper.setStringSF(context, "token", token);
//                    }
//                });

                intent.putExtra("id", 946);
                ArtUtils.startActivity(context, intent);

//                DevInfoUtil.getValue(this);


                break;
            case R.id.btn2:
                getP().verfyUser(devInfoManager.getValue(DevInfoManager.EPG_ADDRESS), DataHelper.getStringSF(context, "token"), devInfoManager.getValue(DevInfoManager.PHONE)
                        , devInfoManager.getValue(DevInfoManager.STB_MAC));
                break;
            case R.id.btn3:

                intent.putExtra("local", true);
                ArtUtils.startActivity(context, intent);
                break;
            case R.id.btn4:
                intent.putExtra("video", getStr());
                intent.putExtra("title", "测试视频");
                ArtUtils.startActivity(context, intent);
                break;

            case R.id.btn5:
                Intent intent1 = new Intent();
                intent1.setClass(this, WebViewActivity.class);
                intent1.putExtra("PayH5Url", "http://183.207.215.112:8090/HDC/pay-merger/dist/#/?flag=&validCode=0&sessionId=774ca6706b825456879d8178b6250f7b&appId=00001&origin=null&deskCode=ystyl&userToken=JSHDC-ASPIRE-9f54a939-152e-4d7d-9c85-d77dde2cf6d2&diversionCode=&productCodes=ceshi0001&groupIds=1000002415000002&greenPopGroSwith=1&unSubReplaceSwitch=1");
                startActivity(intent1);
                break;
            case R.id.btn6:
                getP().saveHistoty();
                break;
            case R.id.btn7:
//
                final LoadingPopupView loadingPopup = (LoadingPopupView) new XPopup.Builder(this)
                        .asLoading("加载中")
                        .bindLayout(R.layout.xpopup_center_impl_loading)
                        .show();
                loadingPopup.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        loadingPopup.setTitle("正在加载长度变化了");
                    }
                }, 1000);
//                loadingPopup.smartDismiss();
//                loadingPopup.dismiss();
                loadingPopup.delayDismissWith(3000, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                break;
            case R.id.btn8:
                DevInfoUtil.getToken(this, new OnResultCall() {
                    @Override
                    public void onResult(String token) {
                        KLog.e("用户 " + token);
                    }
                });
                break;

        }
    }


    public String getStr() {
//        String cdnAddress = devInfoManager.getValue(DevInfoManager.CDN_ADDRESS);
//        String cdnAddressBack = devInfoManager.getValue(DevInfoManager.CDN_ADDRESS_BACK);

//        String s = cdnAddressBack + "/tianhongyxws/vod/sjsag003230100000000000000000061/mjsag003230100000000000000000061?OTTUserToken=19825783251-14:11:5D:F9:11:7C&[$accountinfo=U8SAskBajorj3AH2liD2c6b0scW44T003Qj%2Bs4QGONyYZlkWG8NVxqij3M%2BHZKY6Mjh9ZBCtU9LQFi6TPzPxIhzrYUy3gIB%2BkIrH49PbrQn69CisB3AWhegQfAu1WxLLZWbQLM4Pa1fvAHtvFe9eJQ%3D%3D%3A20200526113315%2C19825783251%2C223.70.244.11%2C20200526113315%2Cpjsag003230100000000000000000061%2C2DBC495BC72DE757777024C2EF5D9860%2C%2C1%2C0%2C-1%2C%2C1%2C%2C-1%2C-3%2C1%2CEND&GuardEncType=2]";
//                +"&UserName=" + devInfoManager.getValue(DevInfoManager.ACCOUNT);


        return "http://101.200.163.82:8080/uploads/836978dd-9006-46e7-83fc-4ba90ed506e6.mp4";
    }


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}
