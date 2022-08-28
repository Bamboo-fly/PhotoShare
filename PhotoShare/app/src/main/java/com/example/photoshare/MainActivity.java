package com.example.photoshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.photoshare.model.user.DataBean;
import com.example.photoshare.model.user.UserModel;
import com.example.photoshare.service.UserService;
import com.example.photoshare.utils.RetrofitUtils;
import com.example.photoshare.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private String username;
    private String password;
    private static final String TAG = "MainActivity";

    static final int SUCCESS=0;
    static final int FAILURE_LOGIN=1;
    static final int FAILURE_REGISTER=2;

    Handler handler=new Handler(Looper.getMainLooper())
    {
      @Override
      public void handleMessage(@NonNull Message msg){
          switch (msg.what){
              case SUCCESS:
                  Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                  Intent intent=new Intent(MainActivity.this,ShareActivity.class);
                  startActivity(intent);
                  break;
              case FAILURE_LOGIN:
                  Toast.makeText(getApplicationContext(),"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                  break;
              case FAILURE_REGISTER:
                  Toast.makeText(getApplicationContext(),"用户名已存在！",Toast.LENGTH_SHORT).show();
                  break;
          }
      }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        activityMainBinding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getApplicationContext(),"登录",Toast.LENGTH_SHORT);
                toast.show();
                password=activityMainBinding.password.getText().toString();
                username=activityMainBinding.account.getText().toString();
                signin();
            }
        });

        activityMainBinding.registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "注册", Toast.LENGTH_SHORT).show();
                password=activityMainBinding.password.getText().toString();
                username=activityMainBinding.account.getText().toString();
                registered();
            }
        });
    }

    private void signin(){
        UserService userservice= RetrofitUtils.getInstance().getRetrofit().create(UserService.class);
        Integer a=Integer.parseInt(password);
        Integer b=Integer.parseInt(username);
        Call<UserModel> call =userservice.login(a,b);
        //同步调用
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                try {
                    DataBean a =response.body().getData();
                    int b=response.body().getCode();
                    //b==200登录成功
                    if (b==200){
                        handler.sendEmptyMessage(SUCCESS);
                    }
                    Log.e(TAG,b+"post异步请求成功");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d(TAG, "post异步请求失败"+call);
            }
        });
    }

       private void registered() {
        UserService userservice=RetrofitUtils.getInstance().getRetrofit().create(UserService.class);
        Integer a=Integer.parseInt(password);
        Integer b=Integer.parseInt(username);
        Call<UserModel> call = userservice.register(a,b);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                try {
                    int a =response.body().getCode();
                    //a==500是用户名已存在，a==200是注册成功
                    if (a==200){
                        handler.sendEmptyMessage(SUCCESS);
                    }
                    Log.e(TAG,"post异步请求成功"+a);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });

    }
}

