package com.yxws.msettopboxs.net;

import com.yxws.msettopboxs.bean.BasePresponce;
import com.yxws.msettopboxs.bean.DoctorInfoBean;
import com.yxws.msettopboxs.bean.HotVideoBean;
import com.yxws.msettopboxs.bean.SearchLevelBean;
import com.yxws.msettopboxs.bean.TypeListMenuBean;
import com.yxws.msettopboxs.bean.UserBean;
import com.yxws.msettopboxs.bean.VideoAddressBean;
import com.yxws.msettopboxs.bean.VideoBean;
import com.yxws.msettopboxs.bean.VideoDetailBean;
import com.yxws.msettopboxs.net.requestbean.WatchHistoryBean;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyAppService {

    //登录
    @FormUrlEncoded
    @POST("api/home/loginByPhone")
    Flowable<UserBean> login(@Field("phone") String phone, @Field("token") String token);

    //登录
    @FormUrlEncoded
    @POST("api/home/loginByPhone")
    Flowable<UserBean> login(@Field("token") String token);

    //购买
    @GET("api/pay/h52To1Pay")
    @Headers({"Content-Type:application/json;charset=utf-8"})
    Flowable<BasePresponce> toBuy(@Query("userId") String userId, @Query("userToken") String userToken,@Query("position") String position);

    //获取一级分类
    @GET("api/home/searchAllTopLevel")
    Flowable<SearchLevelBean> getAllCategory();

    //获取首页热门信息
    @GET("api/home/searchHotVideo")
    Flowable<HotVideoBean> getHotVideo();

    //获取二级分类
    @GET("api/home/searchAllLevel2ById")
    Flowable<TypeListMenuBean> getTwoCategory(@Query("parentId") int parentId);


    //获取该视频的相关 的视频
    @GET("api/home/searchAllVideoUploadTvlistByCpAlbumId")
    Flowable<VideoBean> getVideo(@Query("cpAlbumId") String cpAlbumId);

    @GET("api/home/getVipState")
    Flowable<BasePresponce<String>> isVip(@Query("userId") String userId,@Query("userToken") String userToken,@Query("position") String position);

    //关键字查询
    @GET("api/home/searchVideoByCondition")
    Flowable<VideoBean> search(@Query("condition") String condition);


    //视频详情
    @GET("api/home/searchVideoUploadTvlistById")
    Flowable<VideoDetailBean> getVideoDetail(@Query("id") int id);

    //视频详情
    @GET("api/home/searchVideoUploadTvlistById")
    Flowable<VideoDetailBean> getVideoDetail(@Query("id") String id);

    //医生详情
    @GET("api/home/searchVideoByDoctorId")
    Flowable<DoctorInfoBean> getDoctorInfo(@Query("doctorId") String doctorId);

    //视频播放地址
    @GET("api/home/searchVideoByCpAlbumIdAndCpTvId")
    Flowable<VideoAddressBean> getVideoAddress(@Query("cpAlbumId") String cpAlbumId, @Query("cpTvId") int cpTvId);


    @GET("api/home/searchVideoByCondition")
    Flowable<VideoBean> getHistoryVideo(@Query("condition") String condition);

    @POST("api/home/watchHistoryUpLoad")
    Flowable<BasePresponce> saveHistoty(@Body WatchHistoryBean watchHistoryBean);

}
