package com.waoqi.msettopboxs.ui.pop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.core.CenterPopupView;
import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.MemberBean;
import com.waoqi.msettopboxs.ui.adpter.MemberAdpter;
import com.waoqi.tvwidget.bridge.EffectNoDrawBridge;
import com.waoqi.tvwidget.bridge.OpenEffectBridge;
import com.waoqi.tvwidget.view.ListViewTV;
import com.waoqi.tvwidget.view.MainUpView;

import java.util.ArrayList;
import java.util.List;

public class CustomFullScreenPopup extends CenterPopupView {

    private ListViewTV listView;
    private ImageView ivCode;//二维码
    private TextView tvMoney;//钱数

    private MemberAdpter mMemberAdpter;
    private List<MemberBean> mMemberBeans;
    private Context mContext;


    public CustomFullScreenPopup(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.purchase_member;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        listView = (ListViewTV) findViewById(R.id.listView);

        ivCode = (ImageView) findViewById(R.id.iv_code);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        //todo 获取价钱和 二维码
        initListView();

    }

    private void initListView() {


        mMemberBeans = new ArrayList<>();
        mMemberBeans.add(new MemberBean("包月 VIP", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=810988444,1648289218&fm=11&gp=0.jpg", 15.0f));
        mMemberBeans.add(new MemberBean("季度 VIP", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592212131627&di=826cf33e36509d7aa5d74f59f7774bd1&imgtype=0&src=http%3A%2F%2Fimg.haote.com%2Fupload%2Fqrcode%2F1339%2Fhaote31ca2e8f449f4dc0a601feca11584866.png", 35.0f));
        mMemberBeans.add(new MemberBean("半年 VIP", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3164468217,2359493916&fm=11&gp=0.jpg", 75.0f));
        mMemberBeans.add(new MemberBean("年度 VIP", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1589540882,2791919505&fm=26&gp=0.jpg", 120.0f));

        listView.setIsParam(true);
        listView.setPoint(0);
        mMemberAdpter = new MemberAdpter(mContext, R.layout.item_member, mMemberBeans);
        listView.setAdapter(mMemberAdpter);
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setMember(mMemberBeans.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setMember(MemberBean memberBean) {
        Glide.with(this)
                .load(memberBean.getQrCode())
                .into(ivCode);
        tvMoney.setText(String.format("%.2f", memberBean.getMoney()));

    }

}
