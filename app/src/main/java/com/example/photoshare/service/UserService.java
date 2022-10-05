package com.example.photoshare.service;

import com.example.photoshare.model.user.UserModel;
import com.example.photoshare.postentity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Userservice {

    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
    })
    @POST("member/photo/user/register")
    Call<UserModel> register(@Body User user);

    @Headers({
            "appId:f62a6fdf7e924109896fc639a7cfdcc9",
            "appSecret:33287e5eb1869eb7b4498a9a84a2211a593ff"
            })
    @POST("member/photo/user/login")
    Call<UserModel> login(@Query("password") int password,@Query("username") int username);

}
