package com.geetol.videoedit.blocks.others;


import com.geetol.videoedit.base.activity.RVBaseActivity;
import com.geetol.videoedit.base.beans.ClassBean;
import com.geetol.videoedit.blocks.others.changeHue.ChangeHueActivity;

import java.util.List;

public class OthersActivity extends RVBaseActivity {

    @Override
    public List<ClassBean> initData() {
        mClassBeans.add(new ClassBean("修改hue", ChangeHueActivity.class));
        return mClassBeans;
    }

}
