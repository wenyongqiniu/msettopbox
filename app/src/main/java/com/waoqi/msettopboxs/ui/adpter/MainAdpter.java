package com.waoqi.msettopboxs.ui.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.ImageBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MainAdpter extends CommonAdapter<ImageBean> {


    public MainAdpter(Context context, int layoutId, List<ImageBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, ImageBean item, int position) {
        Glide.with(mContext)
                .load(item.getResId())
                .into((ImageView) viewHolder.getView(R.id.iv_main));

    }

}

