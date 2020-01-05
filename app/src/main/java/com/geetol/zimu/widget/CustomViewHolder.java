package com.geetol.zimu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geetol.zimu.bean.BannerData;
import com.geetol.zimu.utils.GlideEngine;
import com.ms.banner.holder.BannerViewHolder;
import com.shichai.zimu.R;

public class CustomViewHolder implements BannerViewHolder<BannerData> {

    @SuppressLint("InflateParams")
    @Override
    public View createView(Context context, int position, BannerData data) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        ImageView img = view.findViewById(R.id.img_banner);
        if (data.getImg()!=null&&!data.getImg().isEmpty()){
            GlideEngine.getInstance().loadPhoto(context,data.getImg(),img);
        }else{
            Glide.with(context).load(data.getrImg()).into(img);
        }
        return view;
    }

}
