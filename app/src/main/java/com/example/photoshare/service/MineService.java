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
    @POST("member/photo/like")
    Call<ShoucangModel> dianzan(@Query("shareId") int password, @Query("userId") String username);
    //点赞

    @POST("member/photo/like/cancel")
    Call<ShoucangModel> undianzan(@Query("likeId") int likeId);
    //取消点赞

    @GET("member/photo/like")
    Call<MineLikeModel> minelike(@Query("userId") String userId);

    @POST("member/photo/collect")
    Call<ShoucangModel> collect(@Query("shareId") int password, @Query("userId") String username);

    @POST("member/photo/collect/cancel")
    Call<ShoucangModel> uncollect(@Query("collectId") String collectId);

    @GET("member/photo/collect")
    Call<CaoGaoModel> minecollect(@Query("userId") String userId);

    @POST("member/photo/focus")
    Call<ShoucangModel> follow(@Query("focusUserId") String focusUserId, @Query("userId") String username);

    @POST("member/photo/focus/cancel")
    Call<ShoucangModel> unfollow(@Query("focusUserId") String focusUserId, @Query("userId") String username);


}
