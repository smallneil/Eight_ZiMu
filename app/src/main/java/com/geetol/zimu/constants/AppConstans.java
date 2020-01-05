package com.geetol.zimu.constants;

import android.os.Environment;

/**
 * Created by qp
 * PackageName Caller_flashover
 * 2018/4/10 16:08
 */
public class AppConstans {
    public static final String first_entry = "first_entry";//

    public static final String Avatar = "Avatar";//

    public static final String LOCK_IS_REG = "Lock_is_reg";//是否是第一次注册

    public static final String HASSIMKPERMISSON="hassimkpermissopn"; //是否有sim卡权限

    public static String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

    public static final String business_card = path+"card/";// 名片照片存储目录

    public static final String APPID= "wxde5aa3c69e94d23e";//  微信id

    public static final String Autosave= "Autosave";//  自动保存设置

    public static final String Number= "Number";//  试用次数

    public static final String AppWebId= "-qnmpw";//  webid

    public static final int Video= 1;//  视频

    public static final int Music= 2;//  音乐

    // 图片保存位置
    public static final String SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SYB_PHOTOS";  //
    // 视频保存位置
    public static final String SAVE_PATH_VIDEO = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SYB_VIDEOS";  //
    // 临时保存位置
    public static final String TEMP_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TEMP_WATER";  //
    // 音乐位置
    public static final String MUSIC_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/music/";   //

    // 临时保存位置
    public static final String lansongBox = Environment.getExternalStorageDirectory().getAbsolutePath() + "/lansongBox";
}
