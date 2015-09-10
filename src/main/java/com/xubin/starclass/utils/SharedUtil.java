package com.xubin.starclass.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Xubin on 2015/9/10.
 */
public class SharedUtil {

    private static final String NAME="user.sp";

    /**
     * 保存用户选择是否推送结果到手机sdcard
     * @param context
     * @param push
     */
    public static void savePush(Context context,boolean push){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("push",push);
        edit.commit();
    }

    /**
     * 从手机读取用户是否推送
     * @param context
     * @return
     */
    public static boolean isPush(Context context){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean("push",true);
    }
}
