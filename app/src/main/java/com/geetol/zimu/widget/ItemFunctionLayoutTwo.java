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


public class ItemFunctionLayoutTwo extends LinearLayout {
    ImageView img;
    TextView title;
    TextView intro;
    LinearLayout ly;
    View view;

    public ItemFunctionLayoutTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.item_function_layout_two, this);
        img=view.findViewById(R.id.img);
        title=view.findViewById(R.id.title);
        intro=view.findViewById(R.id.intro);
        ly=view.findViewById(R.id.ly);
        //获取属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutTwo);
        int setIcon = typedArray.getResourceId(R.styleable.LayoutTwo_setGo, R.mipmap.ic_launcher);
        int setBg = typedArray.getResourceId(R.styleable.LayoutTwo_setBg, R.mipmap.ic_launcher);
        String setText = typedArray.getString(R.styleable.LayoutTwo_setTitle);
        String setIntro = typedArray.getString(R.styleable.LayoutTwo_setIntro);

        //通过绑定属性设置自定义的View
        setView(setBg,setIcon, setText,setIntro);

        //回收
        typedArray.recycle();
    }

    /**
     * 可调用该方法去设置我们的自定义View
     *
     * @param setIcon 设置图片
     * @param setText 设置标题
     */
    public void setView(int setBg,int setIcon, String setText, String setIntro) {
        if (setIcon != 0) {
            ly.setBackgroundResource(setBg);
        }
        if (setIcon != 0) {
            img.setImageResource(setIcon);
        }
        if (setText != null) {
            title.setText(setText);
        }
        if (setIntro != null) {
            intro.setText(setIntro);
        }
    }

    public void setText(String setText){
        if (setText != null) {
            title.setText(setText);
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
