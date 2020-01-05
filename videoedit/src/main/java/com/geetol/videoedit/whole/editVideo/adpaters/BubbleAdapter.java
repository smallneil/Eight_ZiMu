package com.geetol.videoedit.whole.editVideo.adpaters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geetol.videoedit.R;
import com.geetol.videoedit.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * <pre>
 *     author : Administrator (Jacket)
 *     e-mail : 378315764@qq.com
 *     time   : 2018/01/31
 *     desc   :
 *     version: 3.2
 * </pre>
 */

public class BubbleAdapter extends RecyclerView.Adapter<BubbleAdapter.ViewHolder> {

    private String TAG = PagerAdapter.class.getSimpleName();
    private Context context;
    private int[] imgList;

    private int[] bubbleImgs = new int[]{
            R.drawable.bigicon_no_light,
            R.drawable.bubble1,
            R.drawable.bubble2,
            R.drawable.bubble3,
            R.drawable.bubble4,
            R.drawable.bubble5,
            R.drawable.bubble6,
            R.drawable.bubble7,
            R.drawable.bubble8,
    };

    public BubbleAdapter(Context context, int[] imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bubble, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bubbleview.setImageResource(imgList[position]);
    }

    @Override
    public int getItemCount() {
        return imgList == null ? 0 : imgList.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.bubbleview)
        ImageView bubbleview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("InvalidRUsage")
        @OnClick({R2.id.bubbleview})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R2.id.bubbleview:
                    if (pasterItemSelectListener != null) {
                        pasterItemSelectListener.pasterItemSelect(imgList[getLayoutPosition()], bubbleImgs[getLayoutPosition()]);
                    }
                    break;
            }
        }
    }

    public interface PasterItemSelectListener {
        void pasterItemSelect(int resourseId, int gifId);
    }

    PasterItemSelectListener pasterItemSelectListener;

    public void setPasterItemSelectListener(PasterItemSelectListener pasterItemSelectListener) {
        this.pasterItemSelectListener = pasterItemSelectListener;
    }
}
