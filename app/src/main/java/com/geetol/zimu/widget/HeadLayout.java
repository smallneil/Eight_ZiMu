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


public class HeadLayout extends LinearLayout {
    ImageView img;
    TextView title,right;
    LinearLayout ly;
    View view;

    public HeadLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.layout_head, this);
        img=view.findViewById(R.id.bt_img);
        title=view.findViewById(R.id.tx_title);
        right=view.findViewById(R.id.tx_right);
        //获取属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutHead);
        int setIcon = typedArray.getResourceId(R.styleable.LayoutHead_setLeft, R.mipmap.ic_launcher);
        boolean setRightVisible = typedArray.getBoolean(R.styleable.LayoutHead_setRightVisible, false);
        String setTitle = typedArray.getString(R.styleable.LayoutHead_setTextTitle);
        String setRight = typedArray.getString(R.styleable.LayoutHead_setTextRight);

        //通过绑定属性设置自定义的View
        setRightVisible(setRightVisible);
        setView(setIcon, setTitle,setRight);
        //回收
        typedArray.recycle();
    }

    /**
     * 可调用该方法去设置我们的自定义View
     *
     * @param setIcon 设置图片
     * @param setTitle 设置标题
     * @param setRight 设置右边按钮
     */
    public void setView(int setIcon, String setTitle, String setRight) {
        if (setIcon != 0) {
            img.setImageResource(setIcon);
        }
        if (setTitle != null) {
            title.setText(setTitle);
        }
        if (setRight != null) {
            right.setText(setRight);
        }
    }

    public void setText(String setText){
        if (setText != null) {
            title.setText(setText);
        }
    }

    public void setRightVisible(boolean visible){
        right.setVisibility(visible?VISIBLE:GONE);
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setOnItemViewClickListener(OnClickListener listener) {
        ly.setOnClickListener(listener);
    }

    public void setOnRightViewClickListener(OnClickListener listener) {
        right.setOnClickListener(listener);
    }

    public void setOnLeftViewClickListener(OnClickListener listener) {
        img.setOnClickListener(listener);
    }


}
