package com.example.photoshare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoshare.R;
import com.example.photoshare.model.share.RecordsBean;
import com.example.photoshare.model.share.ShareModel;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.newsViewHolder> {

    List<RecordsBean> sharelist=new ArrayList<>();
    public void setSharelist(List<RecordsBean> shares){
        this.sharelist=shares;
    }

    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //定义应该构造
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //创建卡片 参数——布局，组件组，是否是根节点
        View itemView=layoutInflater.inflate(R.layout.newsview,parent,false);
        //返回卡片组件
        return new newsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull newsViewHolder holder, int position) {
        RecordsBean share=sharelist.get(position);
        holder.name.setText(share.getUsername());
        holder.title.setText(share.getTitle());
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "点击item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sharelist.size();
    }

    static class newsViewHolder extends RecyclerView.ViewHolder{
        TextView name,title;
        public newsViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.name);
            title=itemView.findViewById(R.id.text_title);
        }
    }
}
