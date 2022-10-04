package com.example.photoshare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoshare.R;
import com.example.photoshare.model.share.RecordsBean;

import java.util.ArrayList;
import java.util.List;

public class PinlunOneAdapter extends RecyclerView.Adapter<PinlunOneAdapter.PinlunViewHolder>{

    List<com.example.photoshare.model.pinlun1.RecordsBean> list=new ArrayList<>();

    public PinlunOneAdapter(List<com.example.photoshare.model.pinlun1.RecordsBean> records) {
        this.list=records;
    }


    @NonNull
    @Override
    public PinlunOneAdapter.PinlunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //定义应该构造
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //创建卡片 参数——布局，组件组，是否是根节点
        View itemView=layoutInflater.inflate(R.layout.pinlunview,parent,false);
        //返回卡片组件
        return new PinlunOneAdapter.PinlunViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PinlunOneAdapter.PinlunViewHolder holder, int position) {
        if (list.size()==0){
            holder.content.setText("暂无评论");
        }else{
            holder.content.setText(list.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PinlunViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        public PinlunViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.pinlun_content);
        }
    }



}
