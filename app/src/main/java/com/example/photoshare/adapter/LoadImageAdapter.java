package com.example.photoshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.adapter.ImageAdapter;
import com.donkingliang.imageselector.utils.ImageUtil;
import com.donkingliang.imageselector.utils.UriUtils;
import com.donkingliang.imageselector.utils.VersionUtils;
import com.example.photoshare.R;

import java.util.ArrayList;

public class LoadImageAdapter extends RecyclerView.Adapter<LoadImageAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<String> mImages;
    private LayoutInflater mInflater;
    private boolean isAndroidQ = VersionUtils.isAndroidQ();

    public LoadImageAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_image, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String image = mImages.get(position);
        // 是否是剪切返回的图片
        boolean isCutImage = ImageUtil.isCutImage(mContext, image);
        if (isAndroidQ && !isCutImage) {
            Glide.with(mContext).load(UriUtils.getImageContentUri(mContext, image)).into(holder.ivImage);
        } else {
            Glide.with(mContext).load(image).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void refresh(ArrayList<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }

}
