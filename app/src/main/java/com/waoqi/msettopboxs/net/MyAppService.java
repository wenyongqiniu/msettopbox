package com.waoqi.msettopboxs.net;

import com.waoqi.msettopboxs.bean.BasePresponce;
import com.waoqi.msettopboxs.bean.DoctorInfoBean;
import com.waoqi.msettopboxs.bean.SearchLevelBean;
import com.waoqi.msettopboxs.bean.TypeListMenuBean;
import com.waoqi.msettopboxs.bean.UserBean;
import com.waoqi.msettopboxs.bean.VideoAddressBean;
import com.waoqi.msettopboxs.bean.VideoBean;
import com.waoqi.msettopboxs.bean.VideoDetailBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MyAppService {

    //登录
    @GET("api/home/loginByPhone")
    Flowable<UserBean> login(@Query("phone") String phone);

    //购买

    @GET("api/pay/h52To1Pay")
    Flowable<BasePresponce> toBuy(@Header("application/json") String header, @Query("userId") String userId, @Query("userToken") String userToken);

    //获取一级分类
    @GET("api/home/searchAllTopLevel")
    Flowable<SearchLevelBean> getSearchLevel();

    //获取二级分类
    @GET("api/home/searchAllLevel2ById")
    Flowable<TypeListMenuBean> getSearchLevel(@Query("parentId") int parentId);


    //获取该视频的相关 的视频
    @GET("api/home/searchAllVideoUploadTvlistByCpAlbumId")
    Flowable<VideoBean> getVideo(@Query("cpAlbumId") String cpAlbumId);

    @GET("api/home/getVipState")
    Flowable<BasePresponce<String>> isVip(@Query("userId") String userId);

    //关键字查询
    @GET("api/home/searchVideoByCondition")
    Flowable<VideoBean> search(@Query("condition") String condition);


    //视频详情
    @GET("api/home/searchVideoUploadTvlistById")
    Flowable<VideoDetailBean> getVideoDetail(@Query("id") int id);

    //医生详情
    @GET("api/home/searchVideoByDoctorId")
    Flowable<DoctorInfoBean> getDoctorInfo(@Query("doctorId") String doctorId);

    //视频播放地址
    @GET("api/home/searchVideoByCpAlbumIdAndCpTvId")
    Flowable<VideoAddressBean> getVideoAddress(@Query("cpAlbumId") String cpAlbumId, @Query("cpTvId") int cpTvId);




    // TODO 获取播放历史
    @GET("api/home/searchVideoByCondition")
    Flowable<VideoBean> getHistoryVideo(@Query("condition") String condition/*@Query("userId") String userId*/);

    //TODO 保存播放历史
    @GET("api/home/searchVideoByCondition")
    Flowable<BasePresponce> saveHistoty(@Query("condition") String condition);

}
