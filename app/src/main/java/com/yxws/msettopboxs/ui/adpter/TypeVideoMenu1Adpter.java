package com.yxws.msettopboxs.ui.adpter;

import android.content.Context;
import android.util.Log;

import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.tvwidget.view.adapter.BaseAdapterImpl;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class TypeVideoMenu1Adpter extends CommonAdapter<SearchLevelBean> implements BaseAdapterImpl {

    private int currentPosition = -1;
    private int lastPosition = -1;

    public TypeVideoMenu1Adpter(Context context, int layoutId, List<SearchLevelBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SearchLevelBean item, int position) {
        viewHolder.setText(R.id.tv_menu, item.getName());
        if (lastPosition == position) {
            viewHolder.setTextColorRes(R.id.tv_menu, R.color.color2ABC75);
        }else if (currentPosition == position) {
            viewHolder.setTextColorRes(R.id.tv_menu, R.color.colorWhite);
        }else {
            viewHolder.setTextColorRes(R.id.tv_menu,R.color.colorWhite);
        }
    }


    @Override
    public void setSelectPosition(int position) {
        Log.d("wlx", "1 获取焦点 " + position);
        this.currentPosition = position;
        this.lastPosition=-1;
        notifyDataSetChanged();
    }

    @Override
    public void setSecondPosition(int position) {
        Log.d("wlx", "1 失去焦点 " + position);
        this.lastPosition = position;
        this.currentPosition=-1;
        notifyDataSetChanged();
    }
}
