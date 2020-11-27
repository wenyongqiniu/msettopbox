package com.yxws.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.DevInfoManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chinamobile.SWDevInfoManager;
import com.socks.library.KLog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.HotVideoBean;
import com.yxws.msettopboxs.bean.ImageBean;
import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.msettopboxs.bean.UserBean;
import com.yxws.msettopboxs.config.Constant;
import com.yxws.msettopboxs.presenter.MainPresenter;
import com.yxws.msettopboxs.ui.adpter.GlideImageLoader;
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

import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_HOME_KEY;
import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_KEY;

/**
 * author: luxi
 * email : mwangluxi@163.com
 * create by 2020/8/28 11:59
 * desc : 主页显示界面
 */
public class MainActivity extends XActivity<MainPresenter> implements View.OnClickListener {
    private String TAG = MainActivity.class.getName();
    private Button btnSearch, btnOpenVip;

    private ImageView ivMain1;

    private MainUpView mainUpView2;
    private GridViewTV gridviewtv;
    private Banner banner;


    private MainAdpter mMainAdpter;
    private FrameLayout mRlHotVideo;

    private OpenEffectBridge mOpenEffectBridge;
    private View mOldGridView;


    private List<ImageBean> typeList = new ArrayList<>();

    @SuppressLint("WrongConstant")
    @Override
    public void initView() {
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnOpenVip = (Button) findViewById(R.id.btn_open_vip);
        ivMain1 = (ImageView) findViewById(R.id.iv_main_1);
        banner = (Banner) findViewById(R.id.banner);


        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);
        mRlHotVideo = (FrameLayout) findViewById(R.id.rl_hot_video);

        btnSearch.setOnClickListener(this);

        btnOpenVip.setOnClickListener(this);
        mRlHotVideo.setOnClickListener(this);

        getP().getAllCategory();
        getP().getHotVideo();

        initGridView();
        mRlHotVideo.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (!(newFocus instanceof FrameLayout)) {
                    //失去焦点
                    mainUpView2.setUnFocusView(mOldGridView);
                    mOpenEffectBridge.setVisibleWidget(true);// 隐藏
                    mainUpView2.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
                    mOldGridView = null;
                } else {
                    //获取焦点
                    newFocus.bringToFront();
                    mOpenEffectBridge.setVisibleWidget(false);
                    mainUpView2.setUpRectResource(R.drawable.bg_video_cover); // 设置移动边框的图片.
                    mainUpView2.setFocusView(newFocus, mOldGridView, 1.1f);
                    mOldGridView = newFocus;
                }

            }
        });

        setDefaultImageView(R.drawable.img_home_left, ivMain1);


        initReceiver();
    }

    //设置首页默认显示图片
    private void setDefaultImageView(int resourceId, ImageView view) {
        RequestOptions options = new RequestOptions()
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
        mainUpView2.setUpRectResource(R.drawable.bg_video_cover); // 设置移动边框的图片.

        gridviewtv.setIsSearch(true);
        mOpenEffectBridge.setVisibleWidget(true); // 隐藏
        mMainAdpter = new MainAdpter(this, R.layout.item_image, typeList);
        gridviewtv.setAdapter(mMainAdpter);
        gridviewtv.setSelector(new ColorDrawable(Color.TRANSPARENT));

        gridviewtv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mOpenEffectBridge.setVisibleWidget(false);
                mainUpView2.setFocusView(view,  mOldGridView,1.1f);
                view.bringToFront();
                mOldGridView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gridviewtv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mOldGridView == null) {
                        mOldGridView = gridviewtv.getChildAt(4);
                        gridviewtv.setSelection(4);
                        gridviewtv.setFocusable(true);
                        gridviewtv.setFocusableInTouchMode(true);
                    }else {
                        mainUpView2.setUpRectResource(R.drawable.bg_video_cover); // 设置移动边框的图片.
                        mOpenEffectBridge.setVisibleWidget(false);
                        mOldGridView.bringToFront();
                        mainUpView2.setFocusView(mOldGridView, 1.1f);
                    }
                } else {
                    mOldGridView=null;
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
                    intent.putExtra("id", mHotVideoBean.getVideoId());
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
            btnOpenVip.setFocusable(false);
            btnOpenVip.setFocusableInTouchMode(false);
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

    public void setHotVideo(List<HotVideoBean> hotVideoBean) {
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(hotVideoBean);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int postion, float v, int i1) {

            }

            @Override
            public void onPageSelected(int postion) {
                mHotVideoBean = hotVideoBean.get(postion);
            }

            @Override
            public void onPageScrollStateChanged(int postion) {

            }
        });
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public void toBuyVip() {
        DevInfoUtil.getToken(this, new OnResultCall() {
            @Override
            public void onResult(String token) {
                getP().toBuy(token);
            }
        });
    }


    private HomeRecaiver mHomeRecaiver;

    private void initReceiver() {
        mHomeRecaiver = new HomeRecaiver();
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
                    KLog.e("wlx", "MainActivity 关闭  ");
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHomeRecaiver != null) {
            unregisterReceiver(mHomeRecaiver);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        KLog.e("wlx", "主要 keyCode " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) {
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
