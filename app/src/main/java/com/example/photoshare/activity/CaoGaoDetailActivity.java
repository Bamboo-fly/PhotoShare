package com.example.photoshare.activity;

import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.photoshare.adapter.CaoGaoDetailAdapter;
import com.example.photoshare.databinding.ActivityCaoGaoDetailBinding;
import com.example.photoshare.model.fabu.FabuModel;
import com.example.photoshare.model.user.UserModel;
import com.example.photoshare.postentity.FaBu;
import com.example.photoshare.service.PhotoService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaoGaoDetailActivity extends Activity {

    private ActivityCaoGaoDetailBinding activityCaoGaoDetailBinding;

    private CaoGaoDetailAdapter mAdapter;
    //图片展示适配器

    private String caogao_title;
    private String caogao_content;

    private ArrayList<String> images;
    private static final String TAG = "CaoGaoDetailActivity";

    private FaBu faBu=new FaBu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCaoGaoDetailBinding=ActivityCaoGaoDetailBinding.inflate(getLayoutInflater());
        View view=activityCaoGaoDetailBinding.getRoot();
        setContentView(view);

        images=new ArrayList<String>();

        Intent intent = getIntent();
        String sharedate = intent.getStringExtra("id");
        SharedPreferences sh=getSharedPreferences("user",0);
        String user_id = sh.getString("id","未找到用户ID");
        Log.d(TAG, "onCreate: "+sharedate+" "+user_id);

        PhotoService photoService= RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);
        Call<FabuModel> call=photoService.get_caogaodatail(sharedate,user_id);


        call.enqueue(new retrofit2.Callback<>(){
            @Override
            public void onResponse(Call<FabuModel> call, Response<FabuModel> response) {
                caogao_content=response.body().getData().getContent();
                caogao_title=response.body().getData().getTitle();
                images=response.body().getData().getImageUrlList();
                activityCaoGaoDetailBinding.caogaoTitle.setText(caogao_title);
                activityCaoGaoDetailBinding.caogaoContent.setText(caogao_content);

                activityCaoGaoDetailBinding.caogaoRv.setLayoutManager(new GridLayoutManager(CaoGaoDetailActivity.this,3));
                mAdapter=new CaoGaoDetailAdapter(CaoGaoDetailActivity.this,images);
                activityCaoGaoDetailBinding.caogaoRv.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                faBu.setContent(caogao_content);
                faBu.setTitle(caogao_title);
                faBu.setId(Integer.parseInt(sharedate));
                faBu.setImageCode(Integer.parseInt(sharedate));
                faBu.setpUserId(Double.parseDouble(user_id));
            }

            @Override
            public void onFailure(Call<FabuModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


        activityCaoGaoDetailBinding.caogaoFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabufinished();
            }
        });

    }

    public void fabufinished(){

        PhotoService photoService=RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);
        retrofit2.Call<UserModel> retrofit=photoService.fabu(faBu);
        retrofit.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body().getCode()==200){
                    Toast.makeText(CaoGaoDetailActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CaoGaoDetailActivity.this,AddShareActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CaoGaoDetailActivity.this, "发布失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });



    }
}