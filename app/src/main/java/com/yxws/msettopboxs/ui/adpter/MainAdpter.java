package com.yxws.msettopboxs.ui.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.ImageBean;
import com.yxws.msettopboxs.view.CustomRecyclerView;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.List;


public class MainAdpter extends CommonAdapter<ImageBean> {


    public MainAdpter(Context context, int layoutId, List<ImageBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, ImageBean item, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(item.getResId())
                .into((ImageView) viewHolder.getView(R.id.iv_main));
        TextView tv_main = viewHolder.getView(R.id.tv_main);

        if (TextUtils.isEmpty(item.getName())) {
            tv_main.setVisibility(View.GONE);
        } else {
            tv_main.setVisibility(View.VISIBLE);
            tv_main.setText(item.getName());
        }
    }

}

