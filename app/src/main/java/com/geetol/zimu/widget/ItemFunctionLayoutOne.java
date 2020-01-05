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


public class ItemFunctionLayoutOne extends LinearLayout {
    ImageView img;
    TextView text;
    LinearLayout ly;
    View view;

    public ItemFunctionLayoutOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.item_function_layout_one, this);
        img=view.findViewById(R.id.img);
        text=view.findViewById(R.id.text);
        //获取属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutOne);
        int setIcon = typedArray.getResourceId(R.styleable.LayoutOne_setIcon, R.mipmap.ic_launcher);
        String setText = typedArray.getString(R.styleable.LayoutOne_setText);
        //通过绑定属性设置自定义的View
        setView(setIcon, setText);
        //回收
        typedArray.recycle();
    }

    /**
     * 可调用该方法去设置我们的自定义View
     *
     * @param setIcon 设置图片
     * @param setText 设置标题
     */
    public void setView(int setIcon, String setText) {
        if (setIcon != 0) {
            img.setImageResource(setIcon);
        }
        if (setText != null) {
            text.setText(setText);
        }
    }

    public void setText(String setText){
        if (setText != null) {
            text.setText(setText);
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
