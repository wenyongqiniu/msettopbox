package com.yxws.msettopboxs.util;

import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.ImageBean;
import com.yxws.msettopboxs.bean.SearchLevelBean;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {


    public static List<ImageBean> getImageBean(List<SearchLevelBean> levelList) {
        List<ImageBean> list = new ArrayList<>();

        for (int i = 0; i < levelList.size(); i++) {
            if (levelList.get(i).getName().contains("妈妈育儿"))
                list.add(new ImageBean(R.drawable.img1, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("儿科"))
                list.add(new ImageBean(R.drawable.img2, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("泌尿科"))
                list.add(new ImageBean(R.drawable.img3, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("皮肤科"))
                list.add(new ImageBean(R.drawable.img4, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("男科"))
                list.add(new ImageBean(R.drawable.img5, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("不孕不育"))
                list.add(new ImageBean(R.drawable.img6, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("消化科"))
                list.add(new ImageBean(R.drawable.img7, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("医美"))
                list.add(new ImageBean(R.drawable.img8, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("用药知识"))
                list.add(new ImageBean(R.drawable.img9, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("神经系统"))
                list.add(new ImageBean(R.drawable.img10, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("心理健康"))
                list.add(new ImageBean(R.drawable.img11, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("中医"))
                list.add(new ImageBean(R.drawable.img12, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("肿瘤"))
                list.add(new ImageBean(R.drawable.img13, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("性病"))
                list.add(new ImageBean(R.drawable.img14, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("风湿免疫科"))
                list.add(new ImageBean(R.drawable.img15, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("常见感染疾病"))
                list.add(new ImageBean(R.drawable.img16, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("肝炎"))
                list.add(new ImageBean(R.drawable.img17, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("血液疾病"))
                list.add(new ImageBean(R.drawable.img18, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("肛肠科"))
                list.add(new ImageBean(R.drawable.img19, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("康复科"))
                list.add(new ImageBean(R.drawable.img20, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("急救"))
                list.add(new ImageBean(R.drawable.img21, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("骨科"))
                list.add(new ImageBean(R.drawable.img22, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("呼吸科"))
                list.add(new ImageBean(R.drawable.img23, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("耳鼻喉科"))
                list.add(new ImageBean(R.drawable.img24, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("口腔科"))
                list.add(new ImageBean(R.drawable.img25, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("护理"))
                list.add(new ImageBean(R.drawable.img26, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("眼科"))
                list.add(new ImageBean(R.drawable.img27, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("辅助检查"))
                list.add(new ImageBean(R.drawable.img28, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("麻醉科"))
                list.add(new ImageBean(R.drawable.img29, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("心脑血管系统"))
                list.add(new ImageBean(R.drawable.img30, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("糖尿病"))
                list.add(new ImageBean(R.drawable.img31, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("内分泌疾病"))
                list.add(new ImageBean(R.drawable.img32, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("罕见病"))
                list.add(new ImageBean(R.drawable.img33, levelList.get(i).getId()));
            else if (levelList.get(i).getName().contains("妇科"))
                list.add(new ImageBean(R.drawable.img34, levelList.get(i).getId()));
        }

        return list;
    }


//    public static List<TypeListMenuBean> getTypeMenu1() {
//        List<TypeListMenuBean> list = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            list.add(new TypeListMenuBean(i, "标题测试" + i));
//        }
//
//        return list;
//    }

//    public static List<TypeListMenuBean> getTypeMenu2() {
//        List<TypeListMenuBean> list = new ArrayList<>();
//
//        for (int i = 11; i < 20; i++) {
//            list.add(new TypeListMenuBean(i, "标题测试" + i));
//        }
//
//        return list;
//    }

//    public static List<VideoBean> getTypeVideo() {
//        List<VideoBean> list = new ArrayList<>();
//
//        for (int i = 20; i < 40; i++) {
//            list.add(new VideoBean(i, "http://182.92.118.35:8088/loadFile/draw/2020/05/22/fa4f9afc-f5f4-4b2f-a417-e7a9aece89f6.png", "视频标题是" + i));
//        }
//
//        return list;
//    }

}
