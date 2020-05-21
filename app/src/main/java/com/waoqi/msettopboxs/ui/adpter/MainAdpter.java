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

public class MainAdpter extends BaseAdapter {

    private List<ImageBean> datas;
    private Context context;

    public MainAdpter(List<ImageBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(context, R.layout.item_image, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Glide.with(context)
                .load(datas.get(position).getResId())
                .into(holder.iv_main);


        return view;
    }


    private static class ViewHolder {
        ImageView iv_main;
        public ViewHolder(View view) {

            iv_main = (ImageView) view.findViewById(R.id.iv_main);
        }
    }
}

