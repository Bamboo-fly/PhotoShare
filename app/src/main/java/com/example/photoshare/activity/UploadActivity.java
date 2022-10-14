package com.example.photoshare.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.photoshare.adapter.LoadImageAdapter;
import com.example.photoshare.databinding.ActivityUploadBinding;
import com.example.photoshare.model.loadphoto.LoadPhotoModel;
import com.example.photoshare.model.loadphoto.UploadAll;
import com.example.photoshare.postentity.ShareAdd;
import com.example.photoshare.service.PhotoService;
import com.example.photoshare.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {

    private ActivityUploadBinding activityUploadBinding;

    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;

    private LoadImageAdapter mAdapter;

    private static final String TAG = "UploadActivity";

    private final Gson gson = new Gson();

    ArrayList<File> files=new ArrayList<File>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUploadBinding=ActivityUploadBinding.inflate(getLayoutInflater());
        View view=activityUploadBinding.getRoot();
        setContentView(view);

        activityUploadBinding.rvImage.setLayoutManager(new GridLayoutManager(UploadActivity.this,3));
        mAdapter=new LoadImageAdapter(UploadActivity.this);
        activityUploadBinding.rvImage.setAdapter(mAdapter);

        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //预加载手机图片。加载图片前，请确保app有读取储存卡权限
            ImageSelector.preload(this);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }

        getclock();
        //处理点击事件的函数
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==REQUEST_CODE && data != null){

            ArrayList<String> images= data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);

            for (String image:images){
                files.add(new File(image));
            }
            Log.d(TAG, "onActivityResult: "+files);
            post();

            mAdapter.refresh(images);
        }
    }

    private void post(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/photo/image/upload";

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", "f62a6fdf7e924109896fc639a7cfdcc9")
                    .add("appSecret", "33287e5eb1869eb7b4498a9a84a2211a593ff")
                    .add("Accept", "application/json, text/plain, */*")
                    .build();


            MediaType MEDIA_TYPE_PNG = MediaType.parse("application/image/png; charset=utf-8");
            MultipartBody.Builder mbody=new MultipartBody.Builder().setType(MultipartBody.FORM);

            for(File file:files){
                if(file.exists()){
                    Log.i(TAG,file.getName());//经过测试，此处的名称不能相同，如果相同，只能保存最后一个图片，不知道那些同名的大神是怎么成功保存图片的。
                    mbody.addFormDataPart("fileList",file.getName(), RequestBody.create(MEDIA_TYPE_PNG,file));
                }
            }


            RequestBody requestBody =mbody.build();
            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(requestBody)
                    .build();

            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        LoadPhotoModel loadPhotoModel=gson.fromJson(response.body().string(),LoadPhotoModel.class);
                        Log.d(TAG, loadPhotoModel.getData().getImageUrlList().toString());
                        for (int i=0;i<loadPhotoModel.getData().getImageUrlList().size();i++){
                            Log.d(TAG, loadPhotoModel.getData().getImageUrlList().get(i).toString());
                        }

                        SharedPreferences sp=getSharedPreferences("photo",MODE_PRIVATE);
                        sp.edit().putString("photo_id",loadPhotoModel.getData().getImageCode()).apply();
                        //在这里获取唯一的图片组编号，直接将两个接口合并

                    }
                });

                files.clear();
                //请求完成一次之后就给list置空，避免图片的叠加提交

            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    private void getclock(){
//        activityUploadBinding.btnSingle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //单选
//                ImageSelector.builder()
//                        .useCamera(true) // 设置是否使用拍照
//                        .setSingle(true)  //设置是否单选
//                        .canPreview(true) //是否点击放大图片查看,，默认为true
//                        .start(UploadActivity.this, REQUEST_CODE); // 打开相册
//            }
//        });

//        activityUploadBinding.btnLimit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImageSelector.builder()
//                        .useCamera(true) // 设置是否使用拍照
//                        .setSingle(false)  //设置是否单选
//                        .canPreview(true) //是否点击放大图片查看,，默认为true
//                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .start(UploadActivity.this, REQUEST_CODE); // 打开相册
//            }
//        });

        activityUploadBinding.btnUnlimited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(UploadActivity.this, REQUEST_CODE); // 打开相册
            }
        });

        activityUploadBinding.btnClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(UploadActivity.this, REQUEST_CODE); // 打开相册
            }
        });

//        activityUploadBinding.btnOnlyTake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImageSelector.builder()
//                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
//                        .start(UploadActivity.this, REQUEST_CODE);
//            }
//        });

        activityUploadBinding.btnTakeAndClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelector.builder()
                        .setCrop(true) // 设置是否使用图片剪切功能。
                        .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
                        .start(UploadActivity.this, REQUEST_CODE);
            }
        });

        activityUploadBinding.uploadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhotoService photoService= RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);

                String title=activityUploadBinding.uploadTitle.getText().toString();
                String content=activityUploadBinding.uploadText.getText().toString();
                SharedPreferences sp_user=getSharedPreferences("user",MODE_PRIVATE);
                String user_id = sp_user.getString("id","未找到用户ID");
                SharedPreferences sp_photo=getSharedPreferences("photo",MODE_PRIVATE);
                String photo_id = sp_photo.getString("photo_id","未找到图片ID");
                //请求需要 内容、标题、图片ID、用户ID

                Log.d(TAG, "上传图片分享的请求");

                ShareAdd shareAdd=new ShareAdd();
                shareAdd.setContent(content);
                shareAdd.setpUserId(Double.parseDouble(user_id));
                shareAdd.setimageCode(Double.parseDouble(photo_id));
                shareAdd.setTitle(title);

                retrofit2.Call<UploadAll> call=photoService.uploadall(shareAdd);
                call.enqueue(new retrofit2.Callback<UploadAll>() {
                    @Override
                    public void onResponse(retrofit2.Call<UploadAll> call, retrofit2.Response<UploadAll> response) {
                        if (response.body().getCode()==200){
                            Log.d(TAG, "上传成功"+user_id);
                            Toast toast=Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<UploadAll> call, Throwable t) {
                        Log.d(TAG, "onFailure: 上传失败"+t);
                        t.printStackTrace();
                    }
                });
            }
        });

        activityUploadBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoService photoService= RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);

                String title=activityUploadBinding.uploadTitle.getText().toString();
                String content=activityUploadBinding.uploadText.getText().toString();
                SharedPreferences sp_user=getSharedPreferences("user",MODE_PRIVATE);
                String user_id = sp_user.getString("id","未找到用户ID");
                SharedPreferences sp_photo=getSharedPreferences("photo",MODE_PRIVATE);
                String photo_id = sp_photo.getString("photo_id","未找到图片ID");
                //请求需要 内容、标题、图片ID、用户ID

                Log.d(TAG, "保存草稿");

                ShareAdd shareAdd=new ShareAdd();
                shareAdd.setContent(content);
                shareAdd.setpUserId(Double.parseDouble(user_id));
                shareAdd.setimageCode(Double.parseDouble(photo_id));
                shareAdd.setTitle(title);

                retrofit2.Call<UploadAll> call=photoService.save_photo(shareAdd);
                call.enqueue(new retrofit2.Callback<UploadAll>() {
                    @Override
                    public void onResponse(retrofit2.Call<UploadAll> call, retrofit2.Response<UploadAll> response) {
                        if (response.body().getCode()==200){
                            Log.d(TAG, "保存草稿成功"+user_id+" "+photo_id);
                            Intent intent=new Intent(UploadActivity.this, ShareActivity.class);
                            startActivity(intent);
                            Toast toast=Toast.makeText(getApplicationContext(),"保存草稿成功",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<UploadAll> call, Throwable t) {
                        Log.d(TAG, "onFailure: 保存草稿失败"+t);
                        t.printStackTrace();
                    }
                });
            }
        });

    }

}
