package com.waoqi.msettopboxs.ui.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.presenter.MainPresenter;
import com.waoqi.msettopboxs.ui.adpter.MainAdpter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.DataUtil;
import com.waoqi.mvp.mvp.XActivity;
import com.waoqi.tvwidget.bridge.EffectNoDrawBridge;
import com.waoqi.tvwidget.bridge.OpenEffectBridge;
import com.waoqi.tvwidget.view.GridViewTV;
import com.waoqi.tvwidget.view.MainUpView;


public class MainActivity extends XActivity<MainPresenter> implements View.OnClickListener {
    private String TAG = MainActivity.class.getName();
    private Button btnSearch;
    private Button btnLogin;
    private Button btnOpenVip;
    private TextView tvTime;

    private ImageView ivMain1;
    private LinearLayout lineDesc;
    private ImageView ivMain2;
    private TextView tvMainDesc;
    private MainUpView mainUpView2;
    private GridViewTV gridviewtv;

    private OpenEffectBridge mOpenEffectBridge;
    private MainAdpter mMainAdpter;


    private View mOldGridView;
    private int point = 0; //gridview 位置


    @Override
    public void initView() {
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnOpenVip = (Button) findViewById(R.id.btn_open_vip);
        tvTime = (TextView) findViewById(R.id.tv_time);

        ivMain1 = (ImageView) findViewById(R.id.iv_main_1);
        lineDesc = (LinearLayout) findViewById(R.id.line_desc);
        ivMain2 = (ImageView) findViewById(R.id.iv_main_2);
        tvMainDesc = (TextView) findViewById(R.id.tv_main_desc);

        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);

        mOpenEffectBridge = (OpenEffectBridge) mainUpView2.getEffectBridge();
        EffectNoDrawBridge effectNoDrawBridge = new EffectNoDrawBridge();
        effectNoDrawBridge.setTranDurAnimTime(1);
        mOpenEffectBridge.setDrawUpRectEnabled(false);
        mainUpView2.setEffectBridge(effectNoDrawBridge); // 4.3以下版本边框移动.
        mainUpView2.setUpRectResource(R.drawable.health_foucus_border); // 设置移动边框的图片.

        mainUpView2.setDrawUpRectPadding(new Rect(ArtUtils.dip2px(this, 0),
                ArtUtils.dip2px(this, -10),
                ArtUtils.dip2px(this, 0),
                ArtUtils.dip2px(this, 5))); // 边框图片设置间距.

        btnSearch.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnOpenVip.setOnClickListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        gridviewtv.setIsSearch(true);
        mOpenEffectBridge.setVisibleWidget(true); // 隐藏
        mMainAdpter = new MainAdpter(DataUtil.getImageBean(), this);
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
                        //TODO
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

            }
        });
    }


    @Override
    public void initData(Bundle savedInstanceState) {
//        DevInfoUtil.getValue(this);
//        @SuppressLint("WrongConstant") final DevInfoManager devInfoManager = (DevInfoManager) getSystemService(DevInfoManager.DATA_SERVER);
//        DevInfoUtil.getToken(this, new OnResultCall() {
//            @Override
//            public void onResult(String token) {
//                getP().verfyUser(devInfoManager.getValue(DevInfoManager.EPG_ADDRESS), token, devInfoManager.getValue(DevInfoManager.PHONE), devInfoManager.getValue(DevInfoManager.STB_MAC));
//            }
//        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
    }

    public void click(View view) {
//        X5WebViewActivity.loadUrl(this, "file:///android_asset/home.html", "");
//        X5WebViewActivity.loadUrl(this, "https://www.baidu.com", "");
//        startActivity(new Intent(this, VideoActivty.class));

//        getP().heartBeat();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (gridviewtv.isFocusable()) {
                KLog.i(TAG, "右键");
                gridviewtv.requestFocus();
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_login:
                Toast.makeText(context, "登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_open_vip:
                Toast.makeText(context, "开通", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
