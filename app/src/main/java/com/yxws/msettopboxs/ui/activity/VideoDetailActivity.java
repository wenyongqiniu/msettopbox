package com.yxws.msettopboxs.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.socks.library.KLog;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.DoctorInfoBean;
import com.yxws.msettopboxs.bean.VideoBean;
import com.yxws.msettopboxs.bean.VideoDetailBean;
import com.yxws.msettopboxs.presenter.VideoDetailPresenter;
import com.yxws.msettopboxs.ui.adpter.TypeVideoGridViewAdpter;
import com.yxws.msettopboxs.util.ArtUtils;
import com.yxws.msettopboxs.util.DevInfoUtil;
import com.yxws.msettopboxs.util.OnResultCall;
import com.yxws.mvp.mvp.XActivity;
import com.yxws.tvwidget.bridge.EffectNoDrawBridge;
import com.yxws.tvwidget.bridge.OpenEffectBridge;
import com.yxws.tvwidget.view.GridViewTV;
import com.yxws.tvwidget.view.MainUpView;

import java.util.ArrayList;
import java.util.List;

import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_HOME_KEY;
import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_KEY;

/**
 * author: luxi
 * email : mwangluxi@163.com
 * create by 2020/8/28 11:50
 * desc : 视频详情页
 */
public class VideoDetailActivity extends XActivity<VideoDetailPresenter> implements View.OnClickListener {
    private static final String TAG = VideoDetailActivity.class.getName();
    private Button btnSearch;
    private ImageView ivVideoCover;
    private TextView ivVideoIsPurchase;
    private TextView tvVideoTitle, tvVideoTeacher, tvVideoTeacherDesc;

    private Button btnFreeTrial, btnPurchase;
    private GridViewTV gridviewtv;
    private MainUpView mainUpView2;


    private OpenEffectBridge mOpenEffectBridge;
    private View mOldGridView;
    private int point = 0; //gridview 位置

    private TypeVideoGridViewAdpter mVideoGridViewAdpter;

    private List<VideoBean> mVideoBeans = new ArrayList<>();
    private int videoId;
    private String classificationId;
    private String mPosition = "";//跳转进来的位置

    @Override
    public void initView() {


        btnSearch = (Button) findViewById(R.id.btn_search);
        ivVideoCover = (ImageView) findViewById(R.id.iv_video_cover);
        ivVideoIsPurchase = (TextView) findViewById(R.id.iv_video_is_purchase);

        tvVideoTitle = (TextView) findViewById(R.id.tv_video_title);
        tvVideoTeacher = (TextView) findViewById(R.id.tv_video_teacher);
        tvVideoTeacherDesc = (TextView) findViewById(R.id.tv_video_teacher_desc);
        //TODO 显示的文字"免费试看" 有问题 逻辑 视频不是免费的，判断是会员可观看，不是会员需要购买才能观看。 免费试看的意思是看几分钟后不能再看了，需要购买才能看
        // TODO 建议修改 ： 立即观看
        btnFreeTrial = (Button) findViewById(R.id.btn_free_trial);
        btnPurchase = (Button) findViewById(R.id.btn_purchase);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);
        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);

        btnSearch.setOnClickListener(this);
        btnFreeTrial.setOnClickListener(this);
        btnPurchase.setOnClickListener(this);
        initGridView();

        videoId = getIntent().getIntExtra("id", 0);
        mPosition = getIntent().getStringExtra("position");
        classificationId = getIntent().getStringExtra("classificationId");


        getP().getVideoDetail(videoId);
        if (classificationId != null) {
            getP().getVideo(classificationId);
        }

        initReceiver();
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
                VideoBean videoBean = mVideoBeans.get(position);
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("id", videoBean.getId());
                intent.putExtra("classificationId", classificationId);
                ArtUtils.startActivity(context, intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        videoId = intent.getIntExtra("id", 0);
        classificationId = intent.getStringExtra("classificationId");
        KLog.e("wlx", "onNewIntent  videoId:" + videoId + "   classificationId:" + classificationId);
        getP().getVideoDetail(videoId);
        if (classificationId != null) {
            getP().getVideo(classificationId);
        }
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
                ArtUtils.startActivity(this, SearchActivity.class);
                break;
            case R.id.btn_free_trial:
                // 0免费 1收费
                if (mVideoDetailBean.getIsPurchase() == 1) {
                    DevInfoUtil.getToken(this, new OnResultCall() {
                        @Override
                        public void onResult(String token) {
                            getP().isVip(token, mPosition);
                        }
                    });
                } else {
                    if (mVideoDetailBean != null) {
                        startActivity(mVideoDetailBean);
                    }
                }
                break;
            case R.id.btn_purchase:
                DevInfoUtil.getToken(this, new OnResultCall() {
                    @Override
                    public void onResult(String token) {
                        getP().isVipStatus(token, mPosition);
                    }
                });
                break;
        }
    }

    public void toBuyPurchase() {
        DevInfoUtil.getToken(this, new OnResultCall() {
            @Override
            public void onResult(String token) {
                getP().toBuy(token, mPosition);
            }
        });
    }


    private VideoDetailBean mVideoDetailBean;

    public void setVideoDetail(VideoDetailBean videoBeanData) {
        this.mVideoDetailBean = videoBeanData;
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_pic_default);
        Glide.with(this)
                .asBitmap()
                .apply(options)
                .load(videoBeanData.getTvPicHead())
                .into(ivVideoCover);
        tvVideoTitle.setText(videoBeanData.getTvName());
        ivVideoIsPurchase.setVisibility(videoBeanData.getIsPurchase() == 0 ? View.VISIBLE : View.GONE);
        getP().getDoctorInfo(videoBeanData.getDoctorId());
        if (classificationId == null) {
            getP().getVideo(videoBeanData.getCpAlbumId());
        }
    }

    public void setDoctorInfo(DoctorInfoBean bean) {
        tvVideoTeacher.setText(bean.name);
        tvVideoTeacherDesc.setText(bean.introduction);
    }

    public void setVideoGridData(List<VideoBean> videoBeans) {
        mVideoBeans.clear();
        mVideoBeans.addAll(videoBeans);
        mVideoGridViewAdpter.notifyDataSetChanged();
    }

    public void startActivity(VideoDetailBean mVideoDetailBean) {
        Intent intent = new Intent(this, VideoViewActivty.class);
        intent.putExtra("id", mVideoDetailBean.getId());
        intent.putExtra("cpAlbumId", mVideoDetailBean.getCpAlbumId());
        intent.putExtra("cpTvId", mVideoDetailBean.getCpTvId());
        startActivity(intent);
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

    public void isVip(String isVip) {
        if (isVip.contains("true") ) {
            startActivity(mVideoDetailBean);
        }   else {
            toBuyPurchase();
        }
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
