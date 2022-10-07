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

import com.example.photoshare.adapter.CaoGaoAdapter;
import com.example.photoshare.databinding.ActivityShouCangBinding;
import com.example.photoshare.model.caogao.CaoGaoModel;
import com.example.photoshare.model.caogao.RecordsBean;
import com.example.photoshare.service.MineService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShouCangActivity extends AppCompatActivity {

    private ActivityShouCangBinding binding;

    private CaoGaoAdapter shouCangAdapter;

    List<RecordsBean> shouCangList;

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
                    shouCangAdapter.notifyDataSetChanged();
                    break;
                case FAILURE:
                    Toast.makeText(ShouCangActivity.this, "获取收藏失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private static final String TAG = "ShouCangActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShouCangBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        SharedPreferences sp_user=getSharedPreferences("user",MODE_PRIVATE);
        String user_id = sp_user.getString("id","未找到用户ID");

        shouCangList=new ArrayList<>();
        binding.rv.setLayoutManager(new GridLayoutManager(this,2));
        shouCangAdapter=new CaoGaoAdapter(ShouCangActivity.this,shouCangList);
        binding.rv.setAdapter(shouCangAdapter);

        MineService mineService= RetrofitUtils.getInstance().getRetrofit().create(MineService.class);
        Call<CaoGaoModel> call=mineService.minecollect(user_id);
        call.enqueue(new Callback<CaoGaoModel>() {
            @Override
            public void onResponse(Call<CaoGaoModel> call, Response<CaoGaoModel> response) {
                if (response.body().getData()!=null){
                    shouCangList.addAll(response.body().getData().getRecords());
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