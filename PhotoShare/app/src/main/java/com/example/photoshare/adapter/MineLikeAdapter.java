package com.example.photoshare.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photoshare.R;
import com.example.photoshare.model.minelike.MineLikeModel;
import com.example.photoshare.model.share.RecordsBean;
import com.example.photoshare.model.shoucang.ShoucangModel;
import com.example.photoshare.service.MineService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MineLikeAdapter extends RecyclerView.Adapter<MineLikeAdapter.MineLikeViewHolder> {

    List<com.example.photoshare.model.minelike.RecordsBean> sharelist=new ArrayList<>();
    public String userid;

    public Context context;
    //传入上下文便于获得userid

    public static int a=0;
    public static int b=0;

    private static final String TAG = "MineLikeAdapter";

    MineService mineService= RetrofitUtils.getInstance().getRetrofit().create(MineService.class);

    public void setSharelist2(List<com.example.photoshare.model.minelike.RecordsBean> shares){
        this.sharelist=shares;
    }

    public MineLikeAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public MineLikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //定义应该构造
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //创建卡片 参数——布局，组件组，是否是根节点
        View itemView=layoutInflater.inflate(R.layout.newsview,parent,false);
        //返回卡片组件
        return new MineLikeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MineLikeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        com.example.photoshare.model.minelike.RecordsBean recordsBean=sharelist.get(position);
        holder.name.setText(recordsBean.getUsername());
        holder.title.setText(recordsBean.getTitle());
        holder.dianzan.setImageResource(R.mipmap.zan_click);

        SharedPreferences sh = context.getSharedPreferences("user",0);
        userid=sh.getString("id", String.valueOf(1));

        if (sharelist.get(position).getImageUrlList().size()!=0){
            Glide.with(holder.itemView.getContext())
                    .load(sharelist.get(position).getImageUrlList().get(0))
                    .into(holder.photo);
        }else{
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.ic_dashboard_black_24dp)
                    .into(holder.photo);
        }


        holder.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a++;
                if (a%2==1){
                    holder.shoucang.setImageResource(R.mipmap.col_click);
                }else{
                    holder.shoucang.setImageResource(R.mipmap.col_unclick);
                }

            }
        });

        holder.dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b++;
                if (b%2==0){
                    holder.dianzan.setImageResource(R.mipmap.zan_click);
                    dianzan(sharelist,position);
                }else{
                    holder.dianzan.setImageResource(R.mipmap.zan_unclick);
                    undianzan(sharelist,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sharelist.size();
    }

    static class MineLikeViewHolder extends RecyclerView.ViewHolder{
        TextView name,title;
        ImageView photo;
        ImageButton dianzan,shoucang;
        public MineLikeViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.name);
            title=itemView.findViewById(R.id.text_title);
            photo=itemView.findViewById(R.id.news_image);
            dianzan=itemView.findViewById(R.id.dianzan);
            shoucang=itemView.findViewById(R.id.shoucang);
        }
    }



    public void dianzan(List<com.example.photoshare.model.minelike.RecordsBean> sharelist, int position){
        Log.d(TAG, "dianzan: "+Integer.parseInt(sharelist.get(position).getId())+" "+userid);
        Call<ShoucangModel> call=mineService.dianzan(Integer.parseInt(sharelist.get(position).getId()),userid);
        call.enqueue(new Callback<ShoucangModel>() {
            @Override
            public void onResponse(Call<ShoucangModel> call, Response<ShoucangModel> response) {
                Log.d(TAG, "onResponse: "+"收藏成功"+response.body().getMsg()+" "+sharelist.get(position).getLikeId());
            }

            @Override
            public void onFailure(Call<ShoucangModel> call, Throwable t) {

            }
        });

    }

    public void undianzan(List<com.example.photoshare.model.minelike.RecordsBean> sharelist, int position){
        Log.d(TAG, "undianzan: "+ sharelist.get(position).getLikeId());

        if (sharelist.get(position).getLikeId()==null){
            Toast.makeText(context, "likeid为null", Toast.LENGTH_SHORT).show();
        }else{
            Call<ShoucangModel> call=mineService.undianzan(Integer.parseInt(sharelist.get(position).getLikeId().toString()));
            call.enqueue(new Callback<ShoucangModel>() {
                @Override
                public void onResponse(Call<ShoucangModel> call, Response<ShoucangModel> response) {
                    Log.d(TAG, "onResponse: "+response.body().getMsg());
                }

                @Override
                public void onFailure(Call<ShoucangModel> call, Throwable t) {

                }
            });
        }
    }
}
