package com.yxws.msettopboxs.ui.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.VideoBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class TypeVideoGridViewAdpter extends CommonAdapter<VideoBean> {
    public TypeVideoGridViewAdpter(Context context, int layoutId, List<VideoBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, VideoBean item, int position) {

        viewHolder.setText(R.id.tv_video_desc, item.getTvName());
        viewHolder.setVisible(R.id.iv_video_is_purchase, item.getIsPurchase() == 0 ? true : false);

        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .override(AutoSizeUtils.pt2px(mContext, 342), AutoSizeUtils.pt2px(mContext, 215))
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default);
        Glide.with(mContext)
                .asBitmap()
                .load(TextUtils.isEmpty(item.getTvPicSmall()) ? item.getTvPicHead() : item.getTvPicSmall())
                .apply(options)
                .into((ImageView) viewHolder.getView(R.id.iv_video_cover));

    }
}
