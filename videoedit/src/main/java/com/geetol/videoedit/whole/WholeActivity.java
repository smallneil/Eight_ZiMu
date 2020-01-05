package com.geetol.videoedit.whole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.geetol.videoedit.base.activity.RVBaseActivity;
import com.geetol.videoedit.base.beans.ClassBean;
import com.geetol.videoedit.whole.record.RecorderActivity;

import java.util.List;

public class WholeActivity extends RVBaseActivity {

    @Override
    public List<ClassBean> initData() {
        mClassBeans.add(new ClassBean("视频录制这边走", RecorderActivity.class));
        return mClassBeans;
    }
}
