package com.geetol.zimu.base;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by ZL on 2019/5/30
 */

public class BaseUtils {
    private static final String ERROR_INIT = "Initialize BaseUtils with invoke init()";

    private static WeakReference<Context> mWeakReferenceContext;

    /**
     * init in Application
     */
    public static void init(Context ctx){
        mWeakReferenceContext = new WeakReference<>(ctx);
        //something to do...
    }

    public static Context getContext() {
        if (mWeakReferenceContext == null) {
            throw new IllegalArgumentException(ERROR_INIT);
        }
        return mWeakReferenceContext.get().getApplicationContext();
    }
}
