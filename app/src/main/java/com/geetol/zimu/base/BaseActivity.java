package com.geetol.zimu.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alipay.sdk.app.PayTask;
import com.geetol.zimu.constants.AppConstans;
import com.geetol.zimu.utils.ActivityCollector;
import com.geetol.zimu.utils.FileUtil;
import com.gtdev5.geetolsdk.mylibrary.beans.ApliyBean;
import com.gtdev5.geetolsdk.mylibrary.beans.OdResultBean;
import com.gtdev5.geetolsdk.mylibrary.beans.PayResult;
import com.gtdev5.geetolsdk.mylibrary.beans.UpdateBean;
import com.gtdev5.geetolsdk.mylibrary.callback.BaseCallback;
import com.gtdev5.geetolsdk.mylibrary.http.HttpUtils;
import com.gtdev5.geetolsdk.mylibrary.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.shichai.zimu.R;
import com.geetol.zimu.bean.ConstantsBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Map;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cheng
 * PackageName FakeCall_4
 * 2018/8/15 15:20
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected Activity mActivity;
    private static final int SDK_PAY_FLAG = 1;
    protected PaysuccessListener PayListener;
    public static boolean isActive; //全局变量
    public static boolean isCloseApp; //
    protected abstract int getLayouId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initDatas();

    protected abstract void initAction();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    switch (payResult.getResultStatus()) {
                        case "9000":
                            updataVip();
                            if (PayListener != null) {
                                PayListener.PaySuccess();
                            }
                            break;
                        case "8000":
                            ToastUtils.showShortToast("正在处理中");
                            if (PayListener != null) {
                                PayListener.PayFail(payResult.getResultStatus());
                            }
                            break;
                        case "4000":
                            ToastUtils.showShortToast("订单支付失败");
                            if (PayListener != null) {
                                PayListener.PayFail(payResult.getResultStatus());
                            }
                            break;
                        case "5000":
                            ToastUtils.showShortToast("重复请求");
                            if (PayListener != null) {
                                PayListener.PayFail(payResult.getResultStatus());
                            }
                            break;
                        case "6001":
                            ToastUtils.showShortToast("已取消支付");
                            if (PayListener != null) {
                                PayListener.PayFail(payResult.getResultStatus());
                            }
                            break;
                        case "6002":
                            ToastUtils.showShortToast("网络连接出错");
                            if (PayListener != null) {
                                PayListener.PayFail(payResult.getResultStatus());
                            }
                            break;
                        case "6004":
                            ToastUtils.showShortToast("正在处理中");
                            if (PayListener != null) {
                                PayListener.PayFail(payResult.getResultStatus());
                            }
                            break;
                        default:
                            ToastUtils.showShortToast("支付失败");
                            if (PayListener != null) {
                                PayListener.PayFail(payResult.getResultStatus());
                            }
                            break;
                    }
                    break;
            }

        }
    };

    /**
     * 获取软件需要的所有权限
     */
    public String[] getAllPermissions() {
        return new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayouId());
        ButterKnife.bind(this);
        mActivity = this;
        ImmersionBar.with(this)
                .statusBarColorTransform(R.color.main_color)  //状态栏变色后的颜色
                .statusBarColor(R.color.main_color)  //状态栏颜色，不写默认透明色
                //.barAlpha(0.7f)
                .statusBarDarkFont(true)
                .init();   //所有子类都将继承这些相同的属性
        initViews(savedInstanceState);
        ActivityCollector.addActivity(this);
        initDatas();
        initAction();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }


    @Override
    protected void onResume() {
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
            Log.i("ACTIVITY", "程序从后台唤醒");
//            Intent intent = new Intent(BaseActivity.this, CaculaterActivity.class);
//            startActivity(intent);
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
//        if (!isAppOnForeground()) {
//            //app 进入后台
//            isActive = false;//记录当前已经进入后台
//            Log.i("ACTIVITY", "程序进入后台");
//            ActivityCollector.finishAll();
//
//        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        EventBus.getDefault().unregister(this);
    }


    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    public void openActivity(Class<?> pClass, Bundle mBundle) {
        Intent intent = new Intent(this, pClass);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        startActivity(intent);
    }

    public void openActionView(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

    }

    public interface PaysuccessListener {
        public void PaySuccess();

        public void PayFail(String resultstatu);
    }

    /**
     * 支付宝购买
     *
     * @param pid
     */
    protected void Alipaypay(int pid) {
        HttpUtils.getInstance().postOrder(1, pid, 0, 2, new BaseCallback<ApliyBean>() {
            @Override
            public void onRequestBefore() {

            }

            @Override
            public void onFailure(Request request, Exception e) {
                ToastUtils.showShortToast("链接超时");
            }

            @Override
            public void onSuccess(Response response, ApliyBean apliyBean) {
                if (response.isSuccessful()) {
                    if (apliyBean != null) {
                        if (!apliyBean.isIssucc()) {
                            ToastUtils.showShortToast(apliyBean.getMsg());
                            return;
                        }
                        Runnable runnable = () -> {
                            PayTask alipay = new PayTask(mActivity);
                            Map<String, String> map = alipay.payV2(apliyBean.getPackage_str(), true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = map;
                            mHandler.sendMessage(msg);
                        };

                        Thread payThread = new Thread(runnable);
                        payThread.start();
                    }
                }
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {

            }
        });
    }


    /**
     * 微信购买
     *
     * @param pid
     */
    protected void WeChatpay(int pid) {
        HttpUtils.getInstance().PostOdOrder(1, pid, 0, 1, new BaseCallback<OdResultBean>() {

            @Override
            public void onRequestBefore() {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onSuccess(Response response, OdResultBean o) {

                if (o != null && !o.isIssucc()) {
                    ToastUtils.showShortToast(o.getMsg());
                    return;
                }
                if (o != null && o.isIssucc()) {

                    IWXAPI api = WXAPIFactory.createWXAPI(mActivity, o.getAppid(), false);
                    //填写自己的APPID
                    api.registerApp(o.getAppid());//填写自己的APPID，注册本身APP
                    PayReq req = new PayReq();//PayReq就是订单信息对象//给req对象赋值
                    req.appId = o.getAppid();//APPID
                    req.partnerId = o.getPartnerId();//商户号
                    req.prepayId = o.getPrepayid();  //预订单id
                    req.nonceStr = o.getNonce_str();//随机数
                    req.timeStamp = o.getTimestramp();//时间戳
                    req.packageValue = o.getPackage_str();//固定值Sign=WXPay
                    req.sign = o.getSign();//签名
                    //Log.e("zeoy","服务器签名字符串："+o.getSign());
                    api.sendReq(req);//将订单信息对象发送给微信服务器，即发送支付请求
                }
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
            }
        });
    }



    protected void updataVip() {
        HttpUtils.getInstance().postUpdate(new BaseCallback<UpdateBean>() {
            @Override
            public void onRequestBefore() {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, UpdateBean updateBean) {
                if (updateBean .getIssucc()) {
                    ConstantsBean.mUpdateBean = updateBean;
                    ConstantsBean.mVip = updateBean.getVip();
                    ConstantsBean.vip = updateBean.getVip().isIsout();
                }
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {

            }
        });
    }


    /**
     * //设置状态栏
     *
     * @param
     */
    public void Remove(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();// 隐藏ActionBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void Statusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 创建文件
     */
    public void createFile(boolean deleteTemp) {
        if (deleteTemp) {
            FileUtil.deleteDir(new File(AppConstans.TEMP_SAVE_PATH));
        }
        File file = new File(AppConstans.SAVE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        File file1 = new File(AppConstans.SAVE_PATH_VIDEO);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File file2 = new File(AppConstans.TEMP_SAVE_PATH);
        if (!file2.exists()) {
            file2.mkdir();
        }
        File file3 = new File(AppConstans.MUSIC_STORAGE_DIR);
        if (!file3.exists()) {
            file3.mkdir();
        }
        File file4 = new File(AppConstans.lansongBox);
        if (!file4.exists()) {
            file4.mkdir();
        }
    }
}
