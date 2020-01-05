package com.geetol.zimu.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.geetol.zimu.base.BaseAdapter;
import com.geetol.zimu.base.BaseViewHolder;
import com.geetol.zimu.bean.VideoBean;
import com.geetol.zimu.utils.DisplayUtil;
import com.geetol.zimu.utils.GlideEngine;
import com.shichai.zimu.R;

public class VideoAllAdapter extends BaseAdapter<VideoBean> {
    private Context context;

    public VideoAllAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getViewType(int position) {
        return 0;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(parent);
    }

    public class VideoHolder extends BaseViewHolder<VideoBean> {
        ImageView ivImg;
        CheckBox checkbox;
        RelativeLayout ry;

        public VideoHolder(ViewGroup parent) {
            super(parent, R.layout.item_video_all);
            ivImg = (ImageView) $(R.id.iv_img);
            checkbox = (CheckBox) $(R.id.checkbox);
        }

        @Override
        public void setData(VideoBean data) {
            super.setData(data);
            GlideEngine.getInstance().loadPhoto(context, data.getPath(), ivImg);
            checkbox.setChecked(data.isSel());
        }
    }
}
