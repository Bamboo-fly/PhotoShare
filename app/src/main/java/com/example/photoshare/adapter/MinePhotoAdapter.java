package com.example.photoshare.adapter;

import android.content.Context;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photoshare.R;
import com.example.photoshare.model.caogao.CaoGaoModel;
import com.example.photoshare.model.caogao.RecordsBean;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MinePhotoAdapter extends RecyclerView.Adapter<MinePhotoAdapter.MainPhotoViewHolder>
{
    List<RecordsBean> list=new ArrayList<>();
    private static final String TAG = "MinePhotoAdapter";

    public void setSharelist(List<RecordsBean> shares){
        this.list=shares;
    }

    @NonNull
    @Override
    public MainPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.caogaoview,parent,false);
        return new MainPhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPhotoViewHolder holder, int position) {
        RecordsBean recordsBean= list.get(position);
        holder.title.setText(recordsBean.getTitle());
        holder.content.setText(recordsBean.getContent());

        Log.d(TAG, "onBindViewHolder: "+list.get(position).getImageUrlList());
        if (list.get(position).getImageUrlList()!=null){
            if(list.get(position).getImageUrlList().size()!=0){
                Log.d(TAG, "onBindViewHolder: "+list.get(position).getImageUrlList());
                Glide.with(holder.itemView.getContext())
                        .load(list.get(position).getImageUrlList().get(0))
                        .into(holder.photo);
            }else{
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_dashboard_black_24dp)
                        .into(holder.photo);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static  class  MainPhotoViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView title,content;
        public MainPhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.caogao_image);
            title=itemView.findViewById(R.id.caogao_title);
            content=itemView.findViewById(R.id.caogao_content);
        }
    }
}
