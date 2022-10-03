package com.example.photoshare.service;

import com.example.photoshare.model.share.RecordsBean;
import com.example.photoshare.model.share.ShareModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ShareService {

    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @GET("member/photo/share")
    Call<ShareModel> sharedate(@Query("current") int current, @Query("size") int size, @Query("userId") String userId);


}
