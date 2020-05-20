package com.waoqi.msettopboxs.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.divider.GridSpaceItemDecoration;
import com.waoqi.msettopboxs.presenter.MainPresenter;
import com.waoqi.msettopboxs.ui.adpter.MainAdpter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.DataUtil;
import com.waoqi.mvp.mvp.XActivity;


public class MainActivity extends XActivity<MainPresenter> implements View.OnClickListener {
    private Button btnSearch;
    private Button btnLogin;
    private Button btnOpenVip;
    private TextView tvTime;

    private ImageView ivMain1;
    private LinearLayout lineDesc;
    private ImageView ivMain2;
    private TextView tvMainDesc;

    private RecyclerView recycler;


    private MainAdpter mMainAdpter;

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

        recycler = (RecyclerView) findViewById(R.id.recycler);


        btnSearch.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnOpenVip.setOnClickListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mMainAdpter = new MainAdpter(R.layout.item_image);
        mMainAdpter.setOnItemClickListener((adapter, view, position) -> {
            Toast.makeText(context, "我是第" + position + "个", Toast.LENGTH_SHORT).show();
        });
        ArtUtils.configRecyclerView(recycler, new GridLayoutManager(this, 6));
        recycler.addItemDecoration(new GridSpaceItemDecoration(ArtUtils.dip2px(this, 15)));
        recycler.setAdapter(mMainAdpter);
        mMainAdpter.setNewData(DataUtil.getImageBean());
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
