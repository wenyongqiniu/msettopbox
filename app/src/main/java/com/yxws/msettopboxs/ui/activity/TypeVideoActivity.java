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
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.msettopboxs.bean.TypeListMenuBean;
import com.yxws.msettopboxs.bean.VideoBean;
import com.yxws.msettopboxs.config.Constant;
import com.yxws.msettopboxs.presenter.TypeListPresenter;
import com.yxws.msettopboxs.ui.adpter.TypeVideoGridViewAdpter;
import com.yxws.msettopboxs.ui.adpter.TypeVideoMenu1Adpter;
import com.yxws.msettopboxs.ui.adpter.TypeVideoMenu2Adpter;
import com.yxws.msettopboxs.util.ArtUtils;
import com.yxws.msettopboxs.util.DataHelper;
import com.yxws.mvp.mvp.XActivity;
import com.yxws.tvwidget.bridge.EffectNoDrawBridge;
import com.yxws.tvwidget.bridge.OpenEffectBridge;
import com.yxws.tvwidget.custom.MemoryListView;
import com.yxws.tvwidget.view.GridViewTV;
import com.yxws.tvwidget.view.ListViewTV;
import com.yxws.tvwidget.view.MainUpView;

import java.util.ArrayList;
import java.util.List;

import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_HOME_KEY;
import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_KEY;

/**
 * author: luxi
 * email : mwangluxi@163.com
 * create by 2020/8/28 15:27
 * desc : 视频一二级分类界面
 */
public class TypeVideoActivity extends XActivity<TypeListPresenter> implements View.OnClickListener {
    private static final String TAG = TypeVideoActivity.class.getName();
    private ListViewTV lvVideoMenuId;
    private ListViewTV lvVideoMenuId2;
    private Button btnSearch;
    private TextView tvAppName;


    private MainUpView mainUpView2;
    private GridViewTV gridviewtv;

    private TypeVideoGridViewAdpter mVideoGridViewAdpter;
    private TypeVideoMenu1Adpter mTypeListMenuAdpter;
    private TypeVideoMenu2Adpter mTypeListMenu2Adpter;

    private OpenEffectBridge mOpenEffectBridge;
    private View mOldGridView;
    private int point = 0; //gridview 位置
    private List<SearchLevelBean> mSearchLevel = new ArrayList<>();
    private List<TypeListMenuBean> mSearchLevel2 = new ArrayList<>();//二级分类

    private List<VideoBean> mVideoBeans = new ArrayList<>();

    private String classificationId;
    private int typeId;
    private VideoBean videoBean;

    @Override
    public void initView() {
        typeId = getIntent().getIntExtra("main_type_id", 0);
        lvVideoMenuId = (ListViewTV) findViewById(R.id.lv_video_menu_id);
        lvVideoMenuId2 = (ListViewTV) findViewById(R.id.lv_video_menu_id_2);
        btnSearch = (Button) findViewById(R.id.btn_search);
        tvAppName = (TextView) findViewById(R.id.tv_app_name);


        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);

        btnSearch.setOnClickListener(this);

        initListView();
        initGridView();


        getP().getAllCategory();
        getP().getTwoCategory(typeId);

        initReceiver();
    }

    private boolean islvVideoMenu1 = false;//lvVideoMenuId是否获取焦点


    private void initListView() {
        mTypeListMenuAdpter = new TypeVideoMenu1Adpter(this, R.layout.item_type, mSearchLevel);
        lvVideoMenuId.setItemsCanFocus(true);
        lvVideoMenuId.requestFocus();
        lvVideoMenuId.setAdapter(mTypeListMenuAdpter);
        lvVideoMenuId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lvVideoMenuId.setPoint(position);
                SearchLevelBean searchLevelBean = mSearchLevel.get(position);
                getP().getTwoCategory(searchLevelBean.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lvVideoMenuId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                islvVideoMenu1 = hasFocus;
                if (hasFocus) {
                    lvVideoMenuId2.setSelector(getResources().getDrawable(R.drawable.lemon_liangguang_03));
                } else {
                    lvVideoMenuId2.setSelector(new ColorDrawable(Color.TRANSPARENT));
                }
            }
        });
        mTypeListMenu2Adpter = new TypeVideoMenu2Adpter(this, R.layout.item_type, mSearchLevel2);
        lvVideoMenuId2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lvVideoMenuId2.setPoint(position);
                //应客户需求默认改为第一个
                TypeListMenuBean typeListMenuBean = mSearchLevel2.get(position);
                classificationId = typeListMenuBean.getCpAlbumId();
                getP().getVideo(typeListMenuBean.getCpAlbumId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lvVideoMenuId2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lvVideoMenuId2.setSelector(getResources().getDrawable(R.drawable.lemon_liangguang_03));
                } else {
                    if (!islvVideoMenu1) {
                        lvVideoMenuId2.setSelector(getResources().getDrawable(R.drawable.lemon_liangguang_03));
                    } else {
                        lvVideoMenuId2.setSelector(new ColorDrawable(Color.TRANSPARENT));

                    }
                }
            }
        });
        lvVideoMenuId2.setItemsCanFocus(true);
        lvVideoMenuId2.setAdapter(mTypeListMenu2Adpter);
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
        mVideoGridViewAdpter = new TypeVideoGridViewAdpter(this, R.layout.item_type_video, mVideoBeans);
        gridviewtv.setAdapter(mVideoGridViewAdpter);
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
                    mainUpView2.setUpRectResource(R.drawable.bg_video_cover); // 设置移动边框的图片.
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
                videoBean = mVideoBeans.get(position);
                toVideoDetail();
            }
        });
    }

    private DevInfoManager devInfoManager;

    @SuppressLint("WrongConstant")
    @Override
    public void initData(Bundle savedInstanceState) {
        devInfoManager = (DevInfoManager) getSystemService(DevInfoManager.DATA_SERVER);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_type_list;
    }

    @Override
    public TypeListPresenter newP() {
        return new TypeListPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                ArtUtils.startActivity(this, SearchActivity.class);
                break;
        }
    }

    public void setAllCategoty(List<SearchLevelBean> searchLevel) {
        mSearchLevel.clear();
        mSearchLevel.addAll(searchLevel);
        for (int i = 0; i < mSearchLevel.size(); i++) {
            if (mSearchLevel.get(i).getId() == typeId) {
                lvVideoMenuId.setSelection(i);
            }
        }
        mTypeListMenuAdpter.notifyDataSetChanged();
    }


    public void setSearchLevel2(List<TypeListMenuBean> searchLevelBeanData) {
        mSearchLevel2.clear();
        mSearchLevel2.addAll(searchLevelBeanData);
        mTypeListMenu2Adpter.notifyDataSetChanged();
        if (mSearchLevel2 != null && mSearchLevel2.size() > 0) {
            //应客户需求默认改为第一个
            getP().getVideo(mSearchLevel2.get(0).getCpAlbumId());
        }
    }

    public void setVideoGridData(List<VideoBean> videoBeanData) {
        mVideoBeans.clear();
        mVideoBeans.addAll(videoBeanData);
        mVideoGridViewAdpter.notifyDataSetChanged();
    }


    private void toVideoDetail() {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("id", videoBean.getId());
        intent.putExtra("classificationId", classificationId);
        ArtUtils.startActivity(context, intent);
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
