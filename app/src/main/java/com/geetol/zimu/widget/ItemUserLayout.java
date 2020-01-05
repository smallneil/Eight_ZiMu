package com.geetol.zimu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shichai.zimu.R;


public class ItemUserLayout extends LinearLayout {
    ImageView img;
    TextView title;
    LinearLayout ly;
    View view;

    public ItemUserLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.layout_item_user, this);
        img=view.findViewById(R.id.bt_img);
        title=view.findViewById(R.id.tx_title);
        //获取属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutHead);
        int setIcon = typedArray.getResourceId(R.styleable.LayoutHead_setLeft, R.mipmap.ic_launcher);
        String setTitle = typedArray.getString(R.styleable.LayoutHead_setTextTitle);

        //通过绑定属性设置自定义的View
        setView(setIcon, setTitle);
        //回收
        typedArray.recycle();
    }

    /**
     * 可调用该方法去设置我们的自定义View
     *
     * @param setIcon 设置图片
     * @param setTitle 设置标题
     */
    public void setView(int setIcon, String setTitle) {
        if (setIcon != 0) {
            img.setImageResource(setIcon);
        }
        if (setTitle != null) {
            title.setText(setTitle);
        }
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setOnItemViewClickListener(OnClickListener listener) {
        ly.setOnClickListener(listener);
    }

}
