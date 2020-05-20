package com.waoqi.msettopboxs.ui.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.ImageBean;

public class MainAdpter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {

    public MainAdpter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean item) {
        ImageView iv_main = (ImageView) helper.getView(R.id.iv_main);

        iv_main.setImageResource(item.getResId());

//        Glide.with(mContext)
//                .load(item.getResId())
//                .into(iv_main);
    }
}
