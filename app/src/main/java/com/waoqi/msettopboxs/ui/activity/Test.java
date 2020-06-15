package com.waoqi.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.presenter.TestPresenter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.msettopboxs.util.DevInfoUtil;
import com.waoqi.mvp.mvp.XActivity;

import me.jessyan.autosize.internal.CustomAdapt;

public class Test extends XActivity<TestPresenter> implements CustomAdapt {
    @Override
    public void initView() {

    }

    private DevInfoManager devInfoManager;

    @SuppressLint("WrongConstant")
    @Override
    public void initData(Bundle savedInstanceState) {
        devInfoManager = (DevInfoManager) getSystemService(DevInfoManager.DATA_SERVER);
    }

    @Override
    public int getLayoutId() {
        return R.layout.purchase_member;
    }

    @Override
    public TestPresenter newP() {
        return new TestPresenter();
    }

//    public void click(View v) {
//        Intent intent = new Intent(context, VideoViewActivty.class);
//
//        switch (v.getId()) {
//            case R.id.btn1:
////                DevInfoUtil.getToken(this, new OnResultCall() {
////                    @Override
////                    public void onResult(String token) {
////                        DataHelper.setStringSF(context, "token", token);
////                    }
////                });
//
//
//                DevInfoUtil.getValue(this);
//
//
//                break;
//            case R.id.btn2:
//                getP().verfyUser(devInfoManager.getValue(DevInfoManager.EPG_ADDRESS), DataHelper.getStringSF(context, "token"), devInfoManager.getValue(DevInfoManager.PHONE)
//                        , devInfoManager.getValue(DevInfoManager.STB_MAC));
//                break;
//            case R.id.btn3:
//
//                intent.putExtra("local", true);
//                ArtUtils.startActivity(context, intent);
//                break;
//            case R.id.btn4:
//                intent.putExtra("video", getStr());
//                intent.putExtra("local", false);
//                ArtUtils.startActivity(context, intent);
//                break;
//        }
//    }


//    private String getStr() {
//        String cdnAddress = devInfoManager.getValue(DevInfoManager.CDN_ADDRESS);
//        String cdnAddressBack = devInfoManager.getValue(DevInfoManager.CDN_ADDRESS_BACK);
//
//        String s = cdnAddressBack + "/tianhongyxws/vod/sjsag003230100000000000000000061/mjsag003230100000000000000000061?OTTUserToken=19825783251-14:11:5D:F9:11:7C&[$accountinfo=U8SAskBajorj3AH2liD2c6b0scW44T003Qj%2Bs4QGONyYZlkWG8NVxqij3M%2BHZKY6Mjh9ZBCtU9LQFi6TPzPxIhzrYUy3gIB%2BkIrH49PbrQn69CisB3AWhegQfAu1WxLLZWbQLM4Pa1fvAHtvFe9eJQ%3D%3D%3A20200526113315%2C19825783251%2C223.70.244.11%2C20200526113315%2Cpjsag003230100000000000000000061%2C2DBC495BC72DE757777024C2EF5D9860%2C%2C1%2C0%2C-1%2C%2C1%2C%2C-1%2C-3%2C1%2CEND&GuardEncType=2]";
////                +"&UserName=" + devInfoManager.getValue(DevInfoManager.ACCOUNT);
//
//
//        return s;
//    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}
