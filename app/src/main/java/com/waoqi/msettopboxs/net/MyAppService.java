package com.waoqi.msettopboxs.net;

import com.waoqi.msettopboxs.bean.Bean;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.waoqi.msettopboxs.bean.VerificationBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAppService {

    //获取一级分类
    @GET("api/home/searchAllTopLevel")
    Flowable<SearchLevelBean> getSearchLevel();

    //获取二级分类
    @GET("api/home/searchAllLevel2ById")
    Flowable<TypeListMenuBean> getSearchLevel(@Query("parentId") int parentId);


}
