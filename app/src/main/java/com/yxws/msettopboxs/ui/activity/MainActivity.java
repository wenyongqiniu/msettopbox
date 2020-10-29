package com.yxws.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.DevInfoManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chinamobile.SWDevInfoManager;
import com.socks.library.KLog;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.HotVideoBean;
import com.yxws.msettopboxs.bean.ImageBean;
import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.msettopboxs.bean.UserBean;
import com.yxws.msettopboxs.config.Constant;
import com.yxws.msettopboxs.presenter.MainPresenter;
import com.yxws.msettopboxs.ui.adpter.MainAdpter;
import com.yxws.msettopboxs.util.ArtUtils;
import com.yxws.msettopboxs.util.DataHelper;
import com.yxws.msettopboxs.util.DataUtil;
import com.yxws.msettopboxs.util.DevInfoUtil;
import com.yxws.msettopboxs.util.OnResultCall;
import com.yxws.mvp.mvp.XActivity;
import com.yxws.tvwidget.bridge.EffectNoDrawBridge;
import com.yxws.tvwidget.bridge.OpenEffectBridge;
import com.yxws.tvwidget.view.GridViewTV;
import com.yxws.tvwidget.view.MainUpView;
import com.yxws.tvwidget.view.ReflectItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: luxi
 * email : mwangluxi@163.com
 * create by 2020/8/28 11:59
 * desc : 主页显示界面
 */
public class MainActivity extends XActivity<MainPresenter> implements View.OnClickListener {
    private String TAG = MainActivity.class.getName();
    private Button btnSearch, btnOpenVip;

    private ImageView ivMain1, ivMain2;
    private TextView tvMainDesc;
    private MainUpView mainUpView2;
    private GridViewTV gridviewtv;


    private MainAdpter mMainAdpter;
    private ReflectItemView mRlHotVideo;

    private OpenEffectBridge mOpenEffectBridge;
    private View mOldGridView;
    private int point = 0; //gridview 位置

    private List<ImageBean> typeList = new ArrayList<>();

    @SuppressLint("WrongConstant")
    @Override
    public void initView() {

        KLog.e("MainActivity initView");
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnOpenVip = (Button) findViewById(R.id.btn_open_vip);
        ivMain1 = (ImageView) findViewById(R.id.iv_main_1);
        ivMain2 = (ImageView) findViewById(R.id.iv_main_2);
        tvMainDesc = (TextView) findViewById(R.id.tv_main_desc);

        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);
        mRlHotVideo = (ReflectItemView) findViewById(R.id.rl_hot_video);

        btnSearch.setOnClickListener(this);

        btnOpenVip.setOnClickListener(this);
        mRlHotVideo.setOnClickListener(this);

        getP().getSearchLevel();
        getP().getHotVideo();

        initGridView();
        mRlHotVideo.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (!(newFocus instanceof ReflectItemView)) {
                    mainUpView2.setUnFocusView(mOldGridView);
                    mOpenEffectBridge.setVisibleWidget(true);// 隐藏
                    mainUpView2.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
                    mOldGridView = null;
                } else {
                    newFocus.bringToFront();
                    mOpenEffectBridge.setVisibleWidget(false);
                    mainUpView2.setUpRectResource(R.drawable.white_light_10); // 设置移动边框的图片.
                    mainUpView2.setFocusView(newFocus, mOldGridView, 1.1f);
                    mOldGridView = newFocus;
                }
                point = 0;
            }
        });

        setDefaultImageView(R.drawable.img_home, ivMain1);
        setDefaultImageView(R.drawable.img_home, ivMain2);

    }

    //设置首页默认显示图片
    private void setDefaultImageView(int resourceId, ImageView view) {
        RequestOptions options = new RequestOptions()
                .optionalCenterInside()
                .placeholder(resourceId);
        Glide.with(this)
                .asBitmap()
                .load(resourceId)
                .apply(options)
                .into(view);
    }

    private void initGridView() {
        mOpenEffectBridge = (OpenEffectBridge) mainUpView2.getEffectBridge();
        EffectNoDrawBridge effectNoDrawBridge = new EffectNoDrawBridge();
        effectNoDrawBridge.setTranDurAnimTime(1);
        mOpenEffectBridge.setDrawUpRectEnabled(false);
        mainUpView2.setEffectBridge(effectNoDrawBridge); // 4.3以下版本边框移动.
        mainUpView2.setUpRectResource(R.drawable.white_light_10); // 设置移动边框的图片.

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
                    if (mOldGridView == null) {
                        mOldGridView = gridviewtv.getChildAt(0);
                        gridviewtv.setFocusable(true);
                        gridviewtv.setFocusableInTouchMode(true);
                        gridviewtv.setSelection(0);
                    }
                    mainUpView2.setUpRectResource(R.drawable.white_light_10); // 设置移动边框的图片.
                    mOpenEffectBridge.setVisibleWidget(false);
                    mOldGridView.bringToFront();
                    mainUpView2.setFocusView(mOldGridView, 1.1f);


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
                intent.putExtra("main_type_id", typeList.get(position).getTypeId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        DevInfoUtil.getToken(this, new OnResultCall() {
            @Override
            public void onResult(String token) {
                getP().toLogin(token);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                ArtUtils.startActivity(this, SearchActivity.class);
                break;
            case R.id.btn_open_vip:
                DevInfoUtil.getToken(this, new OnResultCall() {
                    @Override
                    public void onResult(String token) {
                        getP().isVip(token);
                    }
                });

                break;
            case R.id.rl_hot_video:
                if (mHotVideoBean != null) {
                    Intent intent = new Intent(context, VideoDetailActivity.class);
                    intent.putExtra("videoId", mHotVideoBean.getVideoId());
                    ArtUtils.startActivity(context, intent);
                }
                break;
        }

    }

    public void setSearchLevel(List<SearchLevelBean> searchLevel) {
        typeList.clear();
        typeList.addAll(DataUtil.getImageBean(searchLevel));
        mMainAdpter.notifyDataSetChanged();
    }

    public void getUserInfo(UserBean userBean) {
        if (userBean.getIsVip() == 0) {
            btnOpenVip.setText("已开通");
            btnOpenVip.setEnabled(false);
        } else {
            btnOpenVip.setEnabled(true);
        }
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

    private HotVideoBean mHotVideoBean;

    public void setHotVideo(HotVideoBean hotVideoBean) {
        this.mHotVideoBean = hotVideoBean;
        KLog.e("首页图片显示 " + hotVideoBean.getPic());
        RequestOptions options = new RequestOptions()
                .optionalCenterInside()
                .placeholder(R.drawable.img_default);
        Glide.with(this)
                .asBitmap()
                .load(hotVideoBean.getPic())
                .apply(options)
                .into(ivMain2);


        tvMainDesc.setText(hotVideoBean.getInfo());
    }

    public void toBuyVip() {
        DevInfoUtil.getToken(this, new OnResultCall() {
            @Override
            public void onResult(String token) {
                getP().toBuy(token);
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
