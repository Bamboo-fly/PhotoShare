package com.example.photoshare.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.photoshare.R;
import com.example.photoshare.adapter.CaoGaoAdapter;
import com.example.photoshare.databinding.ActivityAddShareBinding;
import com.example.photoshare.model.caogao.CaoGaoModel;
import com.example.photoshare.model.caogao.RecordsBean;
import com.example.photoshare.model.loadphoto.UploadAll;
import com.example.photoshare.postentity.CaoGao;
import com.example.photoshare.postentity.ShareAdd;
import com.example.photoshare.service.PhotoService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddShareActivity extends AppCompatActivity {

    private ActivityAddShareBinding activityAddShareBinding;

    private static final String TAG = "AddShareActivity";

    private CaoGaoAdapter caoGaoAdapter;

    List<RecordsBean> caoGaoList;

    static final int SUCCESS = 0;
    static final int FAILURE = 1;

    Handler handler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            switch (msg.what)
            {
                case SUCCESS:
                    caoGaoAdapter.notifyDataSetChanged();
                    break;
                case FAILURE:
                    Toast.makeText(AddShareActivity.this, "获取草稿失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddShareBinding=ActivityAddShareBinding.inflate(getLayoutInflater());
        View view=activityAddShareBinding.getRoot();
        setContentView(view);

        SharedPreferences sp_user=getSharedPreferences("user",MODE_PRIVATE);
        String user_id = sp_user.getString("id","未找到用户ID");

        caoGaoList=new ArrayList<>();
        activityAddShareBinding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        caoGaoAdapter=new CaoGaoAdapter(AddShareActivity.this,caoGaoList);
        activityAddShareBinding.recyclerView.setAdapter(caoGaoAdapter);
        

        PhotoService photoService= RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);
        retrofit2.Call<CaoGaoModel> call=photoService.save_photo(0,10,user_id);
        call.enqueue(new Callback<CaoGaoModel>() {
            @Override
            public void onResponse(Call<CaoGaoModel> call, Response<CaoGaoModel> response) {
                if (response.body().getData()!=null){
                    caoGaoList.addAll(response.body().getData().getRecords());
                }
                handler.sendEmptyMessage(SUCCESS);
            }

            @Override
            public void onFailure(Call<CaoGaoModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                handler.sendEmptyMessage(FAILURE);
            }
        });

    }
}