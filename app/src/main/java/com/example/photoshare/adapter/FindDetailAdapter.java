package com.example.photoshare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photoshare.R;
import com.example.photoshare.activity.CaoGaoDetailActivity;
import com.example.photoshare.activity.FindDetailActivity;

import java.util.ArrayList;

public class FindDetailAdapter extends RecyclerView.Adapter<LoadImageAdapter.ViewHolder> {
    ArrayList<String> fabuModelList;
    private static final String TAG = "CaoGaoDetailAdapter";

    public FindDetailAdapter(FindDetailActivity findDetailActivity, ArrayList<String> fabuModels){
        this.fabuModelList=fabuModels;
    }

    @NonNull
    @Override
    public LoadImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.adapter_image,parent,false);
        return new LoadImageAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadImageAdapter.ViewHolder holder, int position) {
        String fabuModel=fabuModelList.get(position);
        Glide.with(holder.itemView.getContext()).load(fabuModel).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        if (fabuModelList!=null){
            return fabuModelList.size();
        }else{
            return 0;
        }

    }

    static class FindDetailViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public FindDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_image);
        }
    }
}
