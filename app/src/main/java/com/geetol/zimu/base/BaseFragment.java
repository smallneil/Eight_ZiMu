package com.geetol.zimu.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.PayTask;
import com.gtdev5.geetolsdk.mylibrary.beans.ApliyBean;
import com.gtdev5.geetolsdk.mylibrary.beans.PayResult;
import com.gtdev5.geetolsdk.mylibrary.beans.UpdateBean;
import com.gtdev5.geetolsdk.mylibrary.beans.Vip;
import com.gtdev5.geetolsdk.mylibrary.callback.BaseCallback;
import com.gtdev5.geetolsdk.mylibrary.http.HttpUtils;
import com.gtdev5.geetolsdk.mylibrary.util.ToastUtils;

import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cheng
 * PackageName FakeCall_4
 * 2018/8/15 16:04
 */

public abstract class BaseFragment extends Fragment {
    public static Vip vip;

    protected abstract int getLayouId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initDatas();

    protected abstract void initAction();

    protected PaysuccessListener PayListener;
    private static final int SDK_PAY_FLAG = 1;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayouId(), null);
        ButterKnife.bind(this, view);
        initViews(savedInstanceState);
        initDatas();
        initAction();
        return view;
    }

    /**
     * 支付宝购买
     *
     * @param pid
     */
    protected void pay(int pid) {
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
                            PayTask alipay = new PayTask(getActivity());
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
                if (updateBean != null && response.isSuccessful()) {
                    vip = updateBean.getVip();
                }
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {

            }
        });
    }

    public interface PaysuccessListener {
        public void PaySuccess();

        public void PayFail(String resultstatu);
    }

    public void openActionView(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

    }
}
