package com.yxws.msettopboxs.ui.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.HotVideoBean;

public class GlideImageLoader implements ImageLoaderInterface {

    @Override
    public void displayImage(Context context, Object path, View view) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        HotVideoBean hotVideoBean = (HotVideoBean) path;

        ImageView iv_main_2 = view.findViewById(R.id.iv_main_2);
        TextView tvMainDesc = view.findViewById(R.id.tv_main_desc);

        //Glide 加载图片简单用法
        Glide.with(context).load(hotVideoBean.getPic()).into(iv_main_2);
        if (TextUtils.isEmpty(hotVideoBean.getInfo())) {
            tvMainDesc.setVisibility(View.GONE);
        } else {
            tvMainDesc.setVisibility(View.VISIBLE);
            tvMainDesc.setText(hotVideoBean.getInfo());
        }
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public View createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null, false);
        return view;
    }
}