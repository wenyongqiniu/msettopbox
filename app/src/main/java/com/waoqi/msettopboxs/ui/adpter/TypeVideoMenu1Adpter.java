package com.waoqi.msettopboxs.ui.adpter;

import android.content.Context;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class TypeVideoMenu1Adpter extends CommonAdapter<SearchLevelBean> {
    public TypeVideoMenu1Adpter(Context context, int layoutId, List<SearchLevelBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SearchLevelBean item, int position) {
        viewHolder.setText(R.id.tv_menu_1, item.getName());
    }
}
