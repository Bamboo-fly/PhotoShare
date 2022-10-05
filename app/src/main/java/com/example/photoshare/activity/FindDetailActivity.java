package com.example.photoshare.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.photoshare.adapter.FindDetailAdapter;
import com.example.photoshare.adapter.PinlunOneAdapter;
import com.example.photoshare.databinding.ActivityFindDetailBinding;
import com.example.photoshare.model.fabu.FabuModel;
import com.example.photoshare.model.pinlun1.PinLunOneModel;
import com.example.photoshare.service.PhotoService;
import com.example.photoshare.service.ShareService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindDetailActivity extends AppCompatActivity {

    private ActivityFindDetailBinding activityFindDetailBinding;

    private FindDetailAdapter mAdapter;
    //图片展示适配器

    private String caogao_title;
    private String caogao_content;


    private ArrayList<String> images=new ArrayList<String>();
    private static final String TAG = "CaoGaoDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFindDetailBinding=ActivityFindDetailBinding.inflate(getLayoutInflater());
        View view=activityFindDetailBinding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String sharedate = intent.getStringExtra("id");
        SharedPreferences sh=getSharedPreferences("user",0);
        String user_id = sh.getString("id","未找到用户ID");

        find_content(sharedate,user_id);

        activityFindDetailBinding.addPinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(FindDetailActivity.this,PinlunActivity.class);
                intent1.putExtra("id",sharedate);
                startActivity(intent1);
            }
        });
    }

    public void find_content(String sharedate,String user_id){
        PhotoService photoService= RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);
        Call<FabuModel> call=photoService.get_caogaodatail(sharedate,user_id);
        call.enqueue(new retrofit2.Callback<>(){
            @Override
            public void onResponse(Call<FabuModel> call, Response<FabuModel> response) {
                caogao_content=response.body().getData().getContent();
                caogao_title=response.body().getData().getTitle();
                images=response.body().getData().getImageUrlList();
                if (images.size()==0){
                    images.add("https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2022/10/04/6df54a80-7801-4b7e-8d9f-05c9df929288.png");
                }
                activityFindDetailBinding.caogaoTitle.setText(caogao_title);
                activityFindDetailBinding.caogaoContent.setText(caogao_content);
                Log.d(TAG, "onResponse: "+images);

                activityFindDetailBinding.caogaoRv.setLayoutManager(new GridLayoutManager(FindDetailActivity.this,3));
                mAdapter=new FindDetailAdapter(FindDetailActivity.this,images);
                activityFindDetailBinding.caogaoRv.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                find_pinlun(response.body().getData().getId());
            }

            @Override
            public void onFailure(Call<FabuModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void find_pinlun(String shareId){
        ShareService shareService=RetrofitUtils.getInstance().getRetrofit().create(ShareService.class);
        Call<PinLunOneModel> call=shareService.pinlundate("12");
        call.enqueue(new Callback<PinLunOneModel>() {
            @Override
            public void onResponse(Call<PinLunOneModel> call, Response<PinLunOneModel> response) {
                if (response.body().getData().getRecords().size()==0){
                    Toast.makeText(FindDetailActivity.this, "暂无评论", Toast.LENGTH_SHORT).show();
                }

                activityFindDetailBinding.pinlunRv.setLayoutManager(new LinearLayoutManager(FindDetailActivity.this));
                PinlunOneAdapter pinlunOneAdapter=new PinlunOneAdapter(response.body().getData().getRecords());
                activityFindDetailBinding.pinlunRv.setAdapter(pinlunOneAdapter);
                pinlunOneAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PinLunOneModel> call, Throwable t) {
            }
        });
    }
}
