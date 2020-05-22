package com.waoqi.msettopboxs.ui.adpter;

import android.content.Context;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class TypeVideoMenu2Adpter extends CommonAdapter<TypeListMenuBean> {
    public TypeVideoMenu2Adpter(Context context, int layoutId, List<TypeListMenuBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, TypeListMenuBean item, int position) {
        viewHolder.setText(R.id.tv_menu_2, item.getName());
    }
}
