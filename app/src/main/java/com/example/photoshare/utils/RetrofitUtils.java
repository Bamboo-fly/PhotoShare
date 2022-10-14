package com.example.photoshare.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static RetrofitUtils retrofitUtils;
    private RetrofitUtils() {
    }
    public static RetrofitUtils getInstance() {

        if (retrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofitUtils == null) {
                    retrofitUtils = new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }

    //返回Retrofit
    public Retrofit getRetrofit() {

        OkHttpClient httpClient =new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("appId", "7cf1a6087a58444ebf6c17ee75619a78")
                        .addHeader("appSecret", "523704e942d7c47f44646ab8d2de7b7e3a8a6")
                        .build();
                // 开始请求
                return chain.proceed(request);
            }
        }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://47.107.52.7:88/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


}
