package com.example.photoshare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.photoshare.R;
import com.example.photoshare.adapter.FindDetailAdapter;
import com.example.photoshare.adapter.PinlunOneAdapter;
import com.example.photoshare.databinding.ActivityFindDetailBinding;
import com.example.photoshare.model.fabu.FabuModel;
import com.example.photoshare.model.pinlun1.PinLunOneModel;
import com.example.photoshare.model.pinlunback.PinLunBackModel;
import com.example.photoshare.model.minelike.RecordsBean;
import com.example.photoshare.model.shoucang.ShoucangModel;
import com.example.photoshare.postentity.PinLun;
import com.example.photoshare.service.MineService;
import com.example.photoshare.service.PhotoService;
import com.example.photoshare.service.ShareService;
import com.example.photoshare.utils.RetrofitUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindMineDetailActivity extends AppCompatActivity {

    private ActivityFindDetailBinding activityFindDetailBinding;

    private FindDetailAdapter mAdapter;
    //图片展示适配器

    private String caogao_title;
    private String caogao_content;

    private String id;


    private ArrayList<String> images = new ArrayList<String>();
    private static final String TAG = "CaoGaoDetailActivity";
    PinlunOneAdapter pinlunOneAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFindDetailBinding = ActivityFindDetailBinding.inflate(getLayoutInflater());
        View view = activityFindDetailBinding.getRoot();
        setContentView(view);
        com.example.photoshare.model.minelike.RecordsBean recordsBean = new com.example.photoshare.model.minelike.RecordsBean();

        Intent intent = getIntent();
        String sharedate = intent.getStringExtra("id");
        String share_puserid = intent.getStringExtra("follow");
        String share_if_follow = intent.getStringExtra("if_follow");
        recordsBean=(RecordsBean)intent.getSerializableExtra("photolist");

        SharedPreferences sh = getSharedPreferences("user", 0);
        String user_id = sh.getString("id", "未找到用户ID");
        images= (ArrayList<String>) recordsBean.getImageUrlList();
        Log.d(TAG, "onCreate: "+images);

        if (share_if_follow.equals("false")){
            activityFindDetailBinding.detailFollow.setText("关注");
            Log.d(TAG, "shang"+share_if_follow);
        }else {
            activityFindDetailBinding.detailFollow.setText("已关注");
            Log.d(TAG, "xia"+share_if_follow);
        }

        find_content(sharedate, user_id);

        Glide.with(FindMineDetailActivity.this)
                .load(R.drawable.icon)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(activityFindDetailBinding.detailIcon);

        activityFindDetailBinding.detailFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //在需要的事件中执行如下代码，发送刷新数据的广播
                Intent intent2 = new Intent("zachary");
                intent2.putExtra("refreshInfo", "yes");
                LocalBroadcastManager.getInstance(FindMineDetailActivity.this).sendBroadcast(intent2);

                if (share_if_follow.equals("false")) {
                    MineService mineService = RetrofitUtils.getInstance().getRetrofit().create(MineService.class);
                    Call<ShoucangModel> call = mineService.follow(share_puserid, user_id);
                    call.enqueue(new Callback<ShoucangModel>() {
                        @Override
                        public void onResponse(Call<ShoucangModel> call, Response<ShoucangModel> response) {
                            Toast.makeText(FindMineDetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response.body().getMsg() + response.body().getCode());
                            activityFindDetailBinding.detailFollow.setText("已关注");
                        }

                        @Override
                        public void onFailure(Call<ShoucangModel> call, Throwable t) {
                            Log.d(TAG, "关注请求失败");
                        }
                    });
                }
                else {
                    MineService mineService = RetrofitUtils.getInstance().getRetrofit().create(MineService.class);
                    Call<ShoucangModel> call = mineService.unfollow(share_puserid, user_id);
                    call.enqueue(new Callback<ShoucangModel>() {
                        @Override
                        public void onResponse(Call<ShoucangModel> call, Response<ShoucangModel> response) {
                            Toast.makeText(FindMineDetailActivity.this, "已取消关注", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response.body().getMsg());
                            activityFindDetailBinding.detailFollow.setText("关注");
                        }

                        @Override
                        public void onFailure(Call<ShoucangModel> call, Throwable t) {

                        }
                    });

                }

            }
        });

        activityFindDetailBinding.addPinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PinLun pinLun = new PinLun();
                pinLun.setContent(activityFindDetailBinding.pinlunContent.getText().toString());
                pinLun.setUserName("Umbrella".toString());
                pinLun.setUserId(user_id);
                Intent intent = getIntent();
                pinLun.setShareId(id);
                Log.d(TAG, "" + user_id + " " + intent.getStringExtra("id"));

                ShareService shareService = RetrofitUtils.getInstance().getRetrofit().create(ShareService.class);
                Call<PinLunBackModel> call = shareService.pinlunfabiao(pinLun);
                call.enqueue(new Callback<PinLunBackModel>() {
                    @Override
                    public void onResponse(Call<PinLunBackModel> call, Response<PinLunBackModel> response) {
                        if (response.body().getCode() == 200) {
                            Toast.makeText(FindMineDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        }
                        pinlunOneAdapter.notifyItemRangeChanged(0, 10);
                    }

                    @Override
                    public void onFailure(Call<PinLunBackModel> call, Throwable t) {

                    }
                });
            }
        });

    }

    public void find_content(String sharedate, String user_id) {
        PhotoService photoService = RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);
        Call<FabuModel> call = photoService.get_caogaodatail(sharedate, user_id);
        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(Call<FabuModel> call, Response<FabuModel> response) {
                caogao_content = response.body().getData().getContent();
                caogao_title = response.body().getData().getTitle();
                activityFindDetailBinding.detailUsername.setText(response.body().getData().getTitle());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long times = Long.parseLong(response.body().getData().getCreateTime());
                String str = format.format(times);

                activityFindDetailBinding.detailTime.setText(str);

                if (images != null) {
                    if (images.size() == 0) {
                        images.add("https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2022/10/04/6df54a80-7801-4b7e-8d9f-05c9df929288.png");
                    }
                }

                activityFindDetailBinding.caogaoTitle.setText(caogao_title);
                activityFindDetailBinding.caogaoContent.setText(caogao_content);
                Log.d(TAG, "onResponse: " + images);

                activityFindDetailBinding.caogaoRv.setLayoutManager(new GridLayoutManager(FindMineDetailActivity.this, 3));
                mAdapter = new FindDetailAdapter(FindMineDetailActivity.this, images);
                activityFindDetailBinding.caogaoRv.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                id=response.body().getData().getId();

                find_pinlun(response.body().getData().getId());
            }

            @Override
            public void onFailure(Call<FabuModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void find_pinlun(String shareId) {
        ShareService shareService = RetrofitUtils.getInstance().getRetrofit().create(ShareService.class);
        Call<PinLunOneModel> call = shareService.pinlundate(shareId);
        call.enqueue(new Callback<PinLunOneModel>() {
            @Override
            public void onResponse(Call<PinLunOneModel> call, Response<PinLunOneModel> response) {
                if (response.body().getData().getRecords().size() == 0) {
                    Toast.makeText(FindMineDetailActivity.this, "暂无评论", Toast.LENGTH_SHORT).show();
                }

                activityFindDetailBinding.pinlunRv.setLayoutManager(new LinearLayoutManager(FindMineDetailActivity.this));
                pinlunOneAdapter = new PinlunOneAdapter(response.body().getData().getRecords());
                activityFindDetailBinding.pinlunRv.setAdapter(pinlunOneAdapter);
                pinlunOneAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PinLunOneModel> call, Throwable t) {
            }
        });
    }
}