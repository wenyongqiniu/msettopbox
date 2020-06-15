package com.waoqi.msettopboxs.ui.adpter;

import android.content.Context;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.MemberBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MemberAdpter extends CommonAdapter<MemberBean> {
    public MemberAdpter(Context context, int layoutId, List<MemberBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MemberBean item, int position) {

        viewHolder.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_vip_1, String.format("%.2f", item.getMoney()));
    }
}
