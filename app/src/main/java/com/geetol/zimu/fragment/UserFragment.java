package com.geetol.zimu.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.geetol.zimu.utils.DisplayUtil;
import com.geetol.zimu.widget.ItemUserLayout;
import com.shichai.zimu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UserFragment extends DialogFragment {
    @BindView(R.id.kt_vip)
    LinearLayout ktVip;
    @BindView(R.id.vip_center)
    ItemUserLayout vipCenter;
    @BindView(R.id.feedback)
    ItemUserLayout feedback;
    @BindView(R.id.contact_customer)
    ItemUserLayout contactCustomer;
    @BindView(R.id.check_update)
    ItemUserLayout checkUpdate;
    Unbinder unbinder;
    private Context mContext;
    private View mRootView;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_user, null);
        unbinder = ButterKnife.bind(this, mRootView);
        Dialog dialog = new Dialog(mContext, R.style.UserDialog);
        dialog.setContentView(mRootView);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.leftToRightAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = DisplayUtil.getScreenWidth(mContext) * 3 / 4;
        params.gravity = Gravity.LEFT;
        window.setAttributes(params);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(50));
        layoutParams.topMargin = DisplayUtil.getStatusBarHeight(mContext) + DisplayUtil.dip2px(15);
        layoutParams.leftMargin = DisplayUtil.dip2px(40);
        layoutParams.rightMargin = DisplayUtil.dip2px(40);
        ktVip.setLayoutParams(layoutParams);

    }

    private void close() {
        dismiss();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
