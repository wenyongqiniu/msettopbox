package com.waoqi.msettopboxs.util;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.bean.ImageBean;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.waoqi.msettopboxs.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {


    public static List<ImageBean> getImageBean() {
        List<ImageBean> list = new ArrayList<>();

        list.add(new ImageBean(R.drawable.img1));
        list.add(new ImageBean(R.drawable.img2));
        list.add(new ImageBean(R.drawable.img3));
        list.add(new ImageBean(R.drawable.img4));
        list.add(new ImageBean(R.drawable.img5));
        list.add(new ImageBean(R.drawable.img6));
        list.add(new ImageBean(R.drawable.img7));
        list.add(new ImageBean(R.drawable.img8));
        list.add(new ImageBean(R.drawable.img9));
        list.add(new ImageBean(R.drawable.img10));
        list.add(new ImageBean(R.drawable.img11));
        list.add(new ImageBean(R.drawable.img12));
        list.add(new ImageBean(R.drawable.img13));
        list.add(new ImageBean(R.drawable.img14));
        list.add(new ImageBean(R.drawable.img15));
        list.add(new ImageBean(R.drawable.img16));
        list.add(new ImageBean(R.drawable.img17));
        list.add(new ImageBean(R.drawable.img18));
        list.add(new ImageBean(R.drawable.img19));
        list.add(new ImageBean(R.drawable.img20));
        list.add(new ImageBean(R.drawable.img21));
        list.add(new ImageBean(R.drawable.img22));
        list.add(new ImageBean(R.drawable.img23));
        list.add(new ImageBean(R.drawable.img24));
        list.add(new ImageBean(R.drawable.img25));
        list.add(new ImageBean(R.drawable.img26));
        list.add(new ImageBean(R.drawable.img27));
        list.add(new ImageBean(R.drawable.img28));
        list.add(new ImageBean(R.drawable.img29));
        list.add(new ImageBean(R.drawable.img30));
        list.add(new ImageBean(R.drawable.img31));
        list.add(new ImageBean(R.drawable.img32));
        list.add(new ImageBean(R.drawable.img33));
        list.add(new ImageBean(R.drawable.img34));


        return list;
    }


    public static List<TypeListMenuBean> getTypeMenu1() {
        List<TypeListMenuBean> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new TypeListMenuBean(i, "标题测试" + i));
        }

        return list;
    }

    public static List<TypeListMenuBean> getTypeMenu2() {
        List<TypeListMenuBean> list = new ArrayList<>();

        for (int i = 11; i < 20; i++) {
            list.add(new TypeListMenuBean(i, "标题测试" + i));
        }

        return list;
    }

    public static List<VideoBean> getTypeVideo() {
        List<VideoBean> list = new ArrayList<>();

        for (int i = 20; i < 40; i++) {
            list.add(new VideoBean(i, "http://182.92.118.35:8088/loadFile/draw/2020/05/22/fa4f9afc-f5f4-4b2f-a417-e7a9aece89f6.png", "视频标题是" + i));
        }

        return list;
    }

}
