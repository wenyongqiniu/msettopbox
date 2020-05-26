//package com.waoqi.msettopboxs.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.waoqi.msettopboxs.R;
//import com.waoqi.msettopboxs.presenter.SplashPresenter;
//import com.waoqi.mvp.imageloader.ILFactory;
//import com.waoqi.mvp.mvp.XActivity;
//
//public class SplashActivity extends XActivity<SplashPresenter> {
//
//
//    @Override
//    public void initView() {
//    }
//
//    @Override
//    public void initData(Bundle savedInstanceState) {
//        getP().startMainActivity();
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.item_image;
//    }
//
//    @Override
//    public SplashPresenter newP() {
//        return new SplashPresenter();
//    }
//
//
//    public void click(View v) {
//        Toast.makeText(context, v.getId()+"点击的", Toast.LENGTH_SHORT).show();
//    }
////    public void startMainActivity() {
////        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
////        startActivity(intent);
////        finish();
////    }
//}
