package com.geetol.zimu.utils;

import com.gtdev5.geetolsdk.mylibrary.callback.BaseCallback;
import com.gtdev5.geetolsdk.mylibrary.http.HttpUtils;
import com.gtdev5.geetolsdk.mylibrary.util.MapUtils;

import java.util.Map;

/**
 * Created by Administrator on 2019/3/29.
 */

public class BusinessHttpUtils {
    //增加分组
    public static String add_group ="add_group";
    //修改分组
    public static String edit_group ="edit_group";
    //删除改分组
    public static String del_group ="del_group";
    //查询分组
    public static String get_group ="get_group";


    //添加名片
    public static String add_photo ="add_photo";
    //修改名片
    public static String edit_photo ="edit_photo";
    //删除名片
    public static String del_photo ="del_photo";
    //查询名片
    public static String get_photo ="get_photo";
    //设置自己的名片
    public static String set_my_photo ="set_my_photo";
    //设置自己的名片
    public static String get_my_photo ="get_my_photo";
    //根据id查询名片
    public static String get_photoinfo ="get_photoinfo";

    //正式
    //public static String URL_DOMAIN ="http://app.wm002.cn/app/mingpian.";
    public static String URL ="http://yuanhuize.yuanhuize.cn/app/";
    //

    //测试
    public static String URL_DOMAIN ="http://pay.wm002.cn/app/mingpian.";

    //public static String URL ="http://pay.wm002.cn/app/";




   //增加分组
    public static void add_group(String  group_names ,BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("group_names", group_names);
        HttpUtils.getInstance().post(URL_DOMAIN+add_group, map, callback);
    }

    //修改分组
    public static void edit_group(int group_id ,String  group_names ,BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("group_id", group_id+"");
        map.put("group_names", group_names);
        HttpUtils.getInstance().post(URL_DOMAIN+edit_group, map, callback);
    }

    //删除分组
    public static void del_group(int  group_id ,BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("group_id", group_id+"");
        HttpUtils.getInstance().post(URL_DOMAIN+del_group, map, callback);
    }

    //查询分组
    public static void get_group(BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        HttpUtils.getInstance().post(URL_DOMAIN+get_group, map, callback);
    }

    //添加名片
    public static void add_BusinessCard(int  group_id,
                                 String names,
                                 String mobile,
                                 String email,
                                 String addr,
                                 String company,
                                 String position,
                                 String website,
                                 String photo1,
                                 String photo2,
                                 BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("group_id", group_id+"");
        map.put("names", names);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("addr", addr);
        map.put("company", company);
        map.put("position", position);
        map.put("website", website);
        map.put("photo1", photo1);
        map.put("photo2", photo2);
        HttpUtils.getInstance().post(URL_DOMAIN+add_photo, map, callback);
    }
    //修改名片
    public static void edit_BusinessCard(int id,
             String group_id,
            String names,
            String mobile,
            String email,
            String addr,
            String company,
            String position,
            String website,
            String photo1,
            String photo2,
            BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("id", id+"");
        map.put("group_id", group_id);
        map.put("names", names);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("addr", addr);
        map.put("company", company);
        map.put("position", position);
        map.put("website", website);
        map.put("photo1", photo1);
        map.put("photo2", photo2);
        HttpUtils.getInstance().post(URL_DOMAIN+edit_photo, map, callback);
    }
    //删除名片
    public static void del_BusinessCard(int  id ,BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("id", id+"");
        HttpUtils.getInstance().post(URL_DOMAIN+del_photo, map, callback);
    }
    //查询名片
    public static void get_BusinessCard(int  group_id ,BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("group_id", group_id+"");
        HttpUtils.getInstance().post(URL_DOMAIN+get_photo, map, callback);
    }

    //设置自己的名片
    public static void set_My_BusinessCard(String  names ,
                                           String  mobile ,
                                           String  email ,
                                           String  addr ,
                                           String  company ,
                                           String  position ,
                                           String  website ,
                                           String  photo1 ,
                                           String  photo2 ,
                                           String  head_img ,
                                           BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("names", names);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("addr", addr);
        map.put("company", company);
        map.put("position", position);
        map.put("website", website);
        map.put("photo1", photo1);
        map.put("photo2", photo2);
        map.put("head_img", head_img);
        HttpUtils.getInstance().post(URL_DOMAIN+set_my_photo, map, callback);
    }

    //设置自己的名片
    public static void set_My_BusinessCard(String  names ,
                                           String  mobile ,
                                           String  email ,
                                           String  addr ,
                                           String  company ,
                                           String  position ,
                                           String  website ,
                                           String  photo1 ,
                                           String  photo2 ,
                                           BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("names", names);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("addr", addr);
        map.put("company", company);
        map.put("position", position);
        map.put("website", website);
        map.put("photo1", photo1);
        map.put("photo2", photo2);
        HttpUtils.getInstance().post(URL_DOMAIN+set_my_photo, map, callback);
    }

    //名片
    public static void get_My_BusinessCard(BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        HttpUtils.getInstance().post(URL_DOMAIN+get_my_photo, map, callback);
    }

    //查询ID名片
    public static void get_photoinfo_BusinessCard(String id,BaseCallback callback){
        Map<String, String> map = MapUtils.getCurrencyMap();
        map.put("id", id);
        HttpUtils.getInstance().post(URL_DOMAIN+get_photoinfo, map, callback);
    }

}
