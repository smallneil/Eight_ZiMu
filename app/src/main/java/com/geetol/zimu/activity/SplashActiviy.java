package com.geetol.zimu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.shichai.zimu.R;


public class SplashActiviy extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }, 2000);
    }


    /*@Override
    protected int getLayoutID() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void jumpToNext() {
        UpdateBean updateBean= DataSaveUtils.getInstance().getUpdate();
        ConstantsBean.mUpdateBean = updateBean;
        ConstantsBean.mVip = updateBean.getVip();
        ConstantsBean.vip=updateBean.getVip().isIsout();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }, 2000);
    }*/
}

