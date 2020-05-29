package com.waoqi.msettopboxs.net;

import com.waoqi.msettopboxs.bean.Bean;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.waoqi.msettopboxs.bean.VerificationBean;
import com.waoqi.msettopboxs.bean.VideoAddressBean;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.bean.VideoDetailBean;

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


    //获取分类下的视频
    @GET("api/home/searchAllVideoUploadTvlistByCpAlbumId")
    Flowable<VideoBean> getVideo(@Query("cpAlbumId") String cpAlbumId);

    //关键字查询
    @GET("api/home/searchVideoByCondition")
    Flowable<VideoBean> search(@Query("condition") String condition);

    //视频详情
    @GET("api/home/searchVideoUploadTvlistById")
    Flowable<VideoDetailBean> getVideoDetail(@Query("id") int id);

    //视频播放地址
    @GET("api/home/searchVideoByCpAlbumIdAndCpTvId")
    Flowable<VideoAddressBean> getVideoAddress(@Query("cpAlbumId") String cpAlbumId, @Query("cpTvId") int cpTvId);


}
