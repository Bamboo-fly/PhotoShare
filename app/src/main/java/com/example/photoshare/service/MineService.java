package com.example.photoshare.service;

import com.example.photoshare.model.caogao.CaoGaoModel;
import com.example.photoshare.model.minelike.MineLikeModel;
import com.example.photoshare.model.shoucang.ShoucangModel;
import com.example.photoshare.model.user.UserModel;
import com.example.photoshare.postentity.DianZan;
import com.example.photoshare.postentity.ShareAdd;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MineService {
    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @POST("member/photo/like")
    Call<ShoucangModel> dianzan(@Query("shareId") int password, @Query("userId") String username);
    //点赞


    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @POST("member/photo/like/cancel")
    Call<ShoucangModel> undianzan(@Query("likeId") int likeId);
   //取消点赞

    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @GET("member/photo/like")
    Call<MineLikeModel> minelike(@Query("userId") String userId);

    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @POST("member/photo/collect")
    Call<ShoucangModel> collect(@Query("shareId") int password, @Query("userId") String username);

    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @POST("member/photo/collect/cancel")
    Call<ShoucangModel> uncollect(@Query("collectId") String collectId);

    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @GET("member/photo/collect")
    Call<CaoGaoModel> minecollect(@Query("userId") String userId);

}
