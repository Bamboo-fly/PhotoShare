package com.example.photoshare.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.photoshare.R;
import com.example.photoshare.databinding.ActivityPinlunBinding;
import com.example.photoshare.model.pinlunback.PinLunBackModel;
import com.example.photoshare.postentity.PinLun;
import com.example.photoshare.service.ShareService;
import com.example.photoshare.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinlunActivity extends AppCompatActivity {

    private ActivityPinlunBinding activityPinlunBinding;
    private static final String TAG = "PinlunActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPinlunBinding=ActivityPinlunBinding.inflate(getLayoutInflater());
        View view=activityPinlunBinding.getRoot();
        setContentView(view);

        SharedPreferences sh=getSharedPreferences("user",0);
        String user_id = sh.getString("id","未找到用户ID");

        activityPinlunBinding.fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PinLun pinLun=new PinLun();
                pinLun.setContent(activityPinlunBinding.content.getText().toString());
                pinLun.setUserName(activityPinlunBinding.username.getText().toString());
                pinLun.setUserId(user_id);
                Intent intent=getIntent();
                pinLun.setShareId("12");
                Log.d(TAG, ""+user_id+" "+intent.getStringExtra("id"));

                ShareService shareService= RetrofitUtils.getInstance().getRetrofit().create(ShareService.class);
                Call<PinLunBackModel> call=shareService.pinlunfabiao(pinLun);
                call.enqueue(new Callback<PinLunBackModel>() {
                    @Override
                    public void onResponse(Call<PinLunBackModel> call, Response<PinLunBackModel> response) {
                        if (response.body().getCode()==200){
                            Toast.makeText(PinlunActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(PinlunActivity.this,FindDetailActivity.class);
                            intent1.putExtra("id",intent.getStringExtra("id"));
                            startActivity(intent1);
                        }
                    }

                    @Override
                    public void onFailure(Call<PinLunBackModel> call, Throwable t) {

                    }
                });
            }
        });


    }
}