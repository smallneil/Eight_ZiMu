package com.geetol.zimu.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.geetol.videoedit.base.MyApplication;
import com.geetol.zimu.base.BaseUtils;
import com.geetol.zimu.bean.WxMessageBean;
import com.geetol.zimu.constants.AppConstans;
import com.geetol.zimu.utils.BusinessHttpUtils;
import com.gtdev5.geetolsdk.mylibrary.initialization.GeetolSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class BaseApplication extends MyApplication {
    //微信api
    public static IWXAPI mWxApi;
    //微信登录后的个人信息
    public WxMessageBean wxMessageBean;
    private static BaseApplication _Instance = null;

    public static BaseApplication getInstance() {
        return _Instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _Instance=this;
        GeetolSDK.init(this, BusinessHttpUtils.URL);
        BaseUtils.init(this);
        //初始化微信
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, AppConstans.APPID, false);
        // 将该app注册到微信
        mWxApi.registerApp(AppConstans.APPID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext( base );
        MultiDex.install(this);
    }

}
