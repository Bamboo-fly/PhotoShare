package com.example.photoshare.service;

import com.example.photoshare.adapter.PinlunOneAdapter;
import com.example.photoshare.model.pinlun1.PinLunOneModel;
import com.example.photoshare.model.pinlunback.PinLunBackModel;
import com.example.photoshare.model.share.ShareModel;
import com.example.photoshare.postentity.PinLun;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShareService {

    @GET("member/photo/share")
    Call<ShareModel> sharedate(@Query("current") int current, @Query("size") int size, @Query("userId") String userId);

    @GET("member/photo/comment/first")
    Call<PinLunOneModel> pinlundate(@Query("shareId") String shareId);

    @POST("member/photo/comment/first")
    Call<PinLunBackModel> pinlunfabiao(@Body PinLun pinLun);

}
