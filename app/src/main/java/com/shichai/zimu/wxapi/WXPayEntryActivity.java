package com.shichai.zimu.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gtdev5.geetolsdk.mylibrary.util.DataSaveUtils;
import com.gtdev5.geetolsdk.mylibrary.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try {
           api = WXAPIFactory.createWXAPI(this, DataSaveUtils.getInstance().getWxId());//这里填入自己的微信APPID
       }catch (Exception e){
           ToastUtils.showLongToast("微信支付失败");
       }
        api.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("coyc", "onPayFinish, errCode = " + baseResp.errCode);

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCord = baseResp.errCode;
            if (errCord == 0) {
                ToastUtils.showShortToast("支付成功");
                //EventBus.getDefault().post(new EventMessageBean(888));
            } else if(errCord == -1){
                ToastUtils.showShortToast("微信支付失败"); //+errCord+baseResp.errStr
               // EventBus.getDefault().post(new EventMessageBean(777));
            }else if(errCord == -2){
                ToastUtils.showShortToast("微信支付取消"); //+errCord+baseResp.errStr
            }
            //这里接收到了返回的状态码可以进行相应的操作，如果不想在这个页面操作可以把状态码存在本地然后finish掉这个页面，这样就回到了你调起支付的那个页面
            //获取到你刚刚存到本地的状态码进行相应的操作就可以了
            finish();
        }else {
            ToastUtils.showShortToast(baseResp.errStr);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}