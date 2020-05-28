package com.waoqi.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.DevInfoManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.VideoAddressBean;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.bean.VideoDetailBean;
import com.waoqi.msettopboxs.presenter.VideoDetailPresenter;
import com.waoqi.msettopboxs.ui.adpter.TypeVideoGridViewAdpter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.DataHelper;
import com.waoqi.msettopboxs.util.DataUtil;
import com.waoqi.msettopboxs.util.DateUtil;
import com.waoqi.mvp.mvp.XActivity;
import com.waoqi.tvwidget.bridge.EffectNoDrawBridge;
import com.waoqi.tvwidget.bridge.OpenEffectBridge;
import com.waoqi.tvwidget.view.GridViewTV;
import com.waoqi.tvwidget.view.MainUpView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoDetailActivity extends XActivity<VideoDetailPresenter> implements View.OnClickListener {


    private static final String TAG = VideoDetailActivity.class.getName();

    private Button btnSearch;
    private TextView tvAppName;
    private TextView tvTime;
    private ImageView ivVideoCover;
    private TextView tvVideoTitle;
    private TextView tvVideoTeacher;
    private TextView tvVideoTeacherDesc;
    private Button btnFreeTrial;
    private Button btnPurchase;
    private GridViewTV gridviewtv;
    private MainUpView mainUpView2;


    private OpenEffectBridge mOpenEffectBridge;
    private View mOldGridView;
    private int point = 0; //gridview 位置

    private TypeVideoGridViewAdpter mVideoGridViewAdpter;

    private List<VideoBean> mVideoBeans = new ArrayList<>();
    private int videoId;
    private String classificationId;

    @Override
    public void initView() {

        btnSearch = (Button) findViewById(R.id.btn_search);
        tvAppName = (TextView) findViewById(R.id.tv_app_name);
        tvTime = (TextView) findViewById(R.id.tv_time);
        ivVideoCover = (ImageView) findViewById(R.id.iv_video_cover);
        tvVideoTitle = (TextView) findViewById(R.id.tv_video_title);
        tvVideoTeacher = (TextView) findViewById(R.id.tv_video_teacher);
        tvVideoTeacherDesc = (TextView) findViewById(R.id.tv_video_teacher_desc);
        btnFreeTrial = (Button) findViewById(R.id.btn_free_trial);
        btnPurchase = (Button) findViewById(R.id.btn_purchase);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);
        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);

        btnSearch.setOnClickListener(this);
        btnFreeTrial.setOnClickListener(this);
        btnPurchase.setOnClickListener(this);
        initGridView();

        videoId = getIntent().getIntExtra("videoId", 0);
        classificationId = getIntent().getStringExtra("classificationId");

        getP().getVideoDetail(videoId);
        getP().getVideo(classificationId);


        tvTime.setText(DateUtil.getTime());

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
                VideoBean videoBean = mVideoBeans.get(position);
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("videoId", videoBean.getId());
                intent.putExtra("classificationId", classificationId);
                ArtUtils.startActivity(context, intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        videoId = intent.getIntExtra("videoId", 0);
        classificationId = intent.getStringExtra("classificationId");
        KLog.e("wlx", "onNewIntent  videoId:" + videoId + "   classificationId:" + classificationId);
        getP().getVideoDetail(videoId);
        getP().getVideo(classificationId);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public VideoDetailPresenter newP() {
        return new VideoDetailPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_free_trial:
                String ottUserToken = DataHelper.getStringSF(this, "OTTUserToken");
                if (TextUtils.isEmpty(ottUserToken)) {
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    getP().getVideoAddress(mVideoDetailBean.getCpAlbumId(), mVideoDetailBean.getCpTvId());
                }
                break;
            case R.id.btn_purchase:
                Toast.makeText(context, "购买", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private VideoDetailBean mVideoDetailBean;

    public void setVideoDetail(VideoDetailBean videoBeanData) {
        this.mVideoDetailBean = videoBeanData;


        if (TextUtils.isEmpty(videoBeanData.getTvPicHead())) {
            Glide.with(this)
                    .load(R.drawable.bitmap2)
                    .into(ivVideoCover);
        } else {
            Glide.with(this)
                    .load(videoBeanData.getTvPicHead())
                    .into(ivVideoCover);
        }


        tvVideoTitle.setText(videoBeanData.getTvName());

//        tvVideoTeacher.setText("我是老师");
//        tvVideoTeacherDesc.setText(videoBeanData.getTvDesc());
        //相关课程

    }

    public void setVideoGridData(List<VideoBean> videoBeans) {
        mVideoBeans.clear();
        mVideoBeans.addAll(videoBeans);
        mVideoGridViewAdpter.notifyDataSetChanged();
    }

    public void startActivity(String videoAddress) {
        Intent intent = new Intent(this, VideoViewActivty.class);
        intent.putExtra("video", videoAddress);
        intent.putExtra("local", false);
        startActivity(intent);
    }
}
