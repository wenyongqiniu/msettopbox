package com.yxws.msettopboxs.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;

import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.tvwidget.custom.adapter.BaseAdapterImpl;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class TypeVideoMenu1Adpter extends CommonAdapter<SearchLevelBean> {



    public TypeVideoMenu1Adpter(Context context, int layoutId, List<SearchLevelBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SearchLevelBean item, int position) {
        viewHolder.setText(R.id.tv_menu, item.getName());
    }


}
