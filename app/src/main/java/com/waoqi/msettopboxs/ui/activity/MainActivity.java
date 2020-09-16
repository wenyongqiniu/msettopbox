package com.waoqi.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.SWDevInfoManager;
import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.ImageBean;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.UserBean;
import com.waoqi.msettopboxs.config.Constant;
import com.waoqi.msettopboxs.presenter.MainPresenter;
import com.waoqi.msettopboxs.ui.adpter.MainAdpter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.msettopboxs.util.DataUtil;
import com.waoqi.msettopboxs.util.DateUtil;
import com.waoqi.msettopboxs.util.DevInfoUtil;
import com.waoqi.msettopboxs.util.OnResultCall;
import com.waoqi.mvp.mvp.XActivity;
import com.waoqi.tvwidget.bridge.EffectNoDrawBridge;
import com.waoqi.tvwidget.bridge.OpenEffectBridge;
import com.waoqi.tvwidget.view.GridViewTV;
import com.waoqi.tvwidget.view.MainUpView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author: luxi
 * email : mwangluxi@163.com
 * create by 2020/8/28 11:59
 * desc : 主页显示界面
 */
public class MainActivity extends XActivity<MainPresenter> implements View.OnClickListener {
    private String TAG = MainActivity.class.getName();
    private Button btnSearch, btnLogin, btnOpenVip;



    private ImageView ivMain1;
    private LinearLayout lineDesc;
    private ImageView ivMain2;
    private TextView tvMainDesc;
    private MainUpView mainUpView2;
    private GridViewTV gridviewtv;


    private MainAdpter mMainAdpter;


    private OpenEffectBridge mOpenEffectBridge;
    private View mOldGridView;
    private int point = 0; //gridview 位置
    private DevInfoManager devInfoManager;

    private List<ImageBean> typeList = new ArrayList<>();

    @SuppressLint("WrongConstant")
    @Override
    public void initView() {
        devInfoManager = SWDevInfoManager.getDevInfoManager(this);

        btnSearch = (Button) findViewById(R.id.btn_search);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnOpenVip = (Button) findViewById(R.id.btn_open_vip);

        ivMain1 = (ImageView) findViewById(R.id.iv_main_1);
        lineDesc = (LinearLayout) findViewById(R.id.line_desc);
        ivMain2 = (ImageView) findViewById(R.id.iv_main_2);
        tvMainDesc = (TextView) findViewById(R.id.tv_main_desc);

        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);
//        btn_history = (Button) findViewById(R.id.btn_history);


        btnSearch.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnOpenVip.setOnClickListener(this);
//        btn_history.setOnClickListener(this);

        getP().getSearchLevel();
        initGridView();

//
        DevInfoUtil.getValue(this);
    }


    private void initGridView() {
        mOpenEffectBridge = (OpenEffectBridge) mainUpView2.getEffectBridge();
        EffectNoDrawBridge effectNoDrawBridge = new EffectNoDrawBridge();
        effectNoDrawBridge.setTranDurAnimTime(1);
        mOpenEffectBridge.setDrawUpRectEnabled(false);
        mainUpView2.setEffectBridge(effectNoDrawBridge); // 4.3以下版本边框移动.
        mainUpView2.setUpRectResource(R.drawable.health_foucus_border); // 设置移动边框的图片.

        gridviewtv.setIsSearch(true);
        mOpenEffectBridge.setVisibleWidget(true); // 隐藏
        mMainAdpter = new MainAdpter(this, R.layout.item_image, typeList);
        gridviewtv.setAdapter(mMainAdpter);
        gridviewtv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridviewtv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 这里注意要加判断是否为NULL.
                 * 因为在重新加载数据以后会出问题.
                 */
                mOpenEffectBridge.setVisibleWidget(false);
                if (view != null) {
                    if (point == 0) {
                        if (mOldGridView != view) {
                            view.bringToFront();
                            KLog.i(TAG, "放大" + position);
                            mainUpView2.setFocusView(view, mOldGridView, 1.1f);
                        }
                    } else {
                        view.bringToFront();
                        mainUpView2.setFocusView(view, mOldGridView, 1.1f);
                    }
                }
                point = position;
                mOldGridView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        gridviewtv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                KLog.i(TAG, "gridView" + hasFocus);
                if (hasFocus) {
                    mOpenEffectBridge.setVisibleWidget(false);
                    mainUpView2.setUpRectResource(R.drawable.health_foucus_border); // 设置移动边框的图片.
                    if (mOldGridView == null) {

                    } else {
                        KLog.i(TAG, "非空");
                        mainUpView2.setFocusView(mOldGridView, 1.1f);
                    }
                } else {
                    mOpenEffectBridge.setVisibleWidget(true); // 隐藏
                    mainUpView2.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
                    mainUpView2.setUnFocusView(mOldGridView);
                }
            }
        });
        gridviewtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TypeVideoActivity.class);
                intent.putExtra("user_phone", devInfoManager.getValue(DevInfoManager.PHONE));
                intent.putExtra("main_type_id", typeList.get(position).getTypeId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        String userInfo = DataHelper.getStringSF(getApplication(), Constant.USERINFO);
        if (userInfo != null) {
            getP().toLogin(devInfoManager.getValue(DevInfoManager.PHONE));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                ArtUtils.startActivity(this, SearchActivity.class);
                break;
            case R.id.btn_login:
                DevInfoUtil.getToken(this, new OnResultCall() {
                    @Override
                    public void onResult(String token) {
                        DataHelper.setStringSF(context, Constant.TOKEN, token);

                        getP().verfyUser(devInfoManager.getValue(DevInfoManager.EPG_ADDRESS)
                                , token, devInfoManager.getValue(DevInfoManager.PHONE)
                                , devInfoManager.getValue(DevInfoManager.STB_MAC));
                    }
                });
                getP().toLogin(devInfoManager.getValue(DevInfoManager.PHONE));

                break;
            case R.id.btn_open_vip:
                if (DataHelper.getStringSF(getApplication(), Constant.USERINFO) == null) {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userId = DataHelper.getStringSF(this, Constant.USERID);
                String ottUserToken = DataHelper.getStringSF(this, Constant.TOKEN);
                getP().toBuy(userId, ottUserToken);
                break;
//            case R.id.btn_history:
//                ArtUtils.startActivity(this, HistoryAcitvity.class);
//                break;
        }
    }

    public void setSearchLevel(List<SearchLevelBean> searchLevel) {
        typeList.clear();
        typeList.addAll(DataUtil.getImageBean(searchLevel));
        mMainAdpter.notifyDataSetChanged();
    }

    public void getUserInfo(UserBean userBean) {
        DataHelper.setStringSF(getApplication(), Constant.USERINFO, userBean.toString());
        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
        if (userBean.getIsVip() == 0) {
            btnOpenVip.setText("已开通");
            btnOpenVip.setEnabled(false);
        } else
            btnOpenVip.setEnabled(true);
        btnLogin.setText(TextUtils.isEmpty(userBean.getPhone()) ? "用户" : userBean.getPhone());
    }

    public void getH5Url(String data) {
        if (TextUtils.isEmpty(data) || data.equals("null"))
            return;
        KLog.d(data);
        Intent intent = new Intent();
        intent.setClass(this, WebViewActivity.class);
        intent.putExtra("PayH5Url", data);
        startActivity(intent);
    }


}
