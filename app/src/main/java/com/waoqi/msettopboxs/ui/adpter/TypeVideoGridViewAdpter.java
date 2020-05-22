package com.waoqi.msettopboxs.ui.adpter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class TypeVideoGridViewAdpter extends CommonAdapter<VideoBean> {
    public TypeVideoGridViewAdpter(Context context, int layoutId, List<VideoBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, VideoBean item, int position) {

        viewHolder.setText(R.id.tv_video_desc,item.getName());


        Glide.with(mContext)
                .load(item.getCover())
                .into((ImageView) viewHolder.getView(R.id.iv_video_cover));
    }
}
