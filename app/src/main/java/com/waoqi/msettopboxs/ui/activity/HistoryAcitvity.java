package com.waoqi.msettopboxs.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.config.Constant;
import com.waoqi.msettopboxs.presenter.HistoryPresenter;
import com.waoqi.msettopboxs.ui.adpter.TypeVideoGridViewAdpter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.msettopboxs.util.DateUtil;
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
 * create by 2020/8/28 13:47
 * desc : 播放记录
 */
public class HistoryAcitvity extends XActivity<HistoryPresenter> {
    private static final String TAG = HistoryAcitvity.class.getName();


    private TextView tvTime;
    private GridViewTV gridviewtv;
    private MainUpView mainUpView2;


    private List<VideoBean> mVideoBeans = new ArrayList<>();


    private View mOldGridView;
    private int point = 0; //gridview 位置
    private TypeVideoGridViewAdpter mVideoGridViewAdpter;
    private OpenEffectBridge mOpenEffectBridge;
    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTime = (TextView) findViewById(R.id.tv_time);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);
        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        String userId = DataHelper.getStringSF(this, Constant.USERID);
//        if (!TextUtils.isEmpty(userId)){
//            getP().getHistoryVideo(userId);
//        }
        getP().getHistoryVideo("");
        initGridView();
        startShowViewTimer();
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
                    mOpenEffectBridge.setVisibleWidget(false);
                    mainUpView2.setUpRectResource(R.drawable.bg_video_cover); // 设置移动边框的图片.
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
                VideoBean videoBean = mVideoBeans.get(position);
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("videoId", videoBean.getId());
                intent.putExtra("classificationId", videoBean.getCpAlbumId());
                ArtUtils.startActivity(context, intent);
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_histoty;
    }

    @Override
    public HistoryPresenter newP() {
        return new HistoryPresenter();
    }



    public void setVideoGridData(List<VideoBean> videoBeanData) {
        mVideoBeans.clear();
        mVideoBeans.addAll(videoBeanData);
        mVideoGridViewAdpter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelShowViewTimer();
    }

    private Timer mTimer;
    private ShowViewTimerTask mShowViewTimerTask;

    public void startShowViewTimer() {
        cancelShowViewTimer();
        mTimer = new Timer();
        mShowViewTimerTask = new  ShowViewTimerTask();
        mTimer.schedule(mShowViewTimerTask, 0,1000);
    }

    public void cancelShowViewTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    public class ShowViewTimerTask extends TimerTask {

        @Override
        public void run() {
            tvTime.post(()->{
                tvTime.setText(DateUtil.getTime());
            });
        }
    }

}
