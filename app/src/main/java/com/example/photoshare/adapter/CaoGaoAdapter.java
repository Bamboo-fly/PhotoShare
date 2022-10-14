package com.example.photoshare.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photoshare.R;
import com.example.photoshare.activity.AddShareActivity;
import com.example.photoshare.activity.CaoGaoDetailActivity;
import com.example.photoshare.activity.ShouCangActivity;
import com.example.photoshare.model.caogao.RecordsBean;

import java.util.List;

public class CaoGaoAdapter extends RecyclerView.Adapter<CaoGaoAdapter.caogaoViewHolder> {

    List<RecordsBean> caoGaoList;

    String shareId;
    private static final String TAG = "CaoGaoAdapter";

    public CaoGaoAdapter(AddShareActivity addShareActivity, List<RecordsBean> caoGaoList) {
        this.caoGaoList=caoGaoList;
    }

    public CaoGaoAdapter(ShouCangActivity addShareActivity, List<RecordsBean> caoGaoList) {
        this.caoGaoList=caoGaoList;
    }

    @NonNull
    @Override
    public caogaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.caogaoview,parent,false);
        return new caogaoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull caogaoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RecordsBean caoGaoModel=caoGaoList.get(position);
        holder.title.setText(caoGaoModel.getTitle());
        holder.content.setText(caoGaoModel.getContent());
        if (caoGaoModel.getImageUrlList()!=null){
            if (caoGaoModel.getImageUrlList().size()==0){
                Glide.with(holder.itemView.getContext())
                        .load(R.mipmap.zan_unclick)
                        .into(holder.imageView);
            }else {
                Glide.with(holder.itemView.getContext())
                        .load(caoGaoModel.getImageUrlList().get(0))
                        .into(holder.imageView);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://blog.csdn.net/lovemushroom/article/details/24970605
                Intent intent=new Intent(holder.itemView.getContext(),CaoGaoDetailActivity.class);
                shareId=caoGaoList.get(position).getId();
                Log.d(TAG, "onClick: "+shareId);
                intent.putExtra("id",shareId);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return caoGaoList.size();
    }

    static class caogaoViewHolder extends RecyclerView.ViewHolder{
        TextView title,content;
        ImageView imageView;
        public caogaoViewHolder(@NonNull View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.caogao_title);
            content=itemView.findViewById(R.id.caogao_content);
            imageView=itemView.findViewById(R.id.caogao_image);

        }
    }

}


