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


public class ItemUserFuncLayout extends LinearLayout {
    ImageView img;
    TextView title1;
    TextView title2;
    LinearLayout ly;
    View view;

    public ItemUserFuncLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.item_layout_user_func, this);
        img=view.findViewById(R.id.img);
        title1=view.findViewById(R.id.title1);
        title2=view.findViewById(R.id.title2);
        //获取属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemUserFuncLayout);
        int setIcon = typedArray.getResourceId(R.styleable.ItemUserFuncLayout_setImg, R.mipmap.ic_launcher);
        String setTitle1 = typedArray.getString(R.styleable.ItemUserFuncLayout_setText1);
        String setTitle2 = typedArray.getString(R.styleable.ItemUserFuncLayout_setText2);

        //通过绑定属性设置自定义的View
        setView(setIcon, setTitle1, setTitle2);
        //回收
        typedArray.recycle();
    }

    /**
     * 可调用该方法去设置我们的自定义View
     *
     * @param setIcon 设置图片
     * @param Title1 设置介绍1
     * @param Title2 设置介绍2
     */
    public void setView(int setIcon, String Title1, String Title2) {
        if (setIcon != 0) {
            img.setImageResource(setIcon);
        }
        if (Title1 != null) {
            title1.setText(Title1);
        }
        if (Title2 != null) {
            title2.setText(Title2);
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
