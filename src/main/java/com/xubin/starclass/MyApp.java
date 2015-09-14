package com.xubin.starclass;

import android.app.Application;

import com.xubin.starclass.entity.Lesson;
import com.xubin.starclass.entity.Unit;
import com.xubin.starclass.entity.User;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.utils.SharedUtil;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Xubin on 2015/9/8.
 */
public class MyApp extends Application {
    public static User user;
    public static Lesson lesson;
    public static List<Unit> unitList;

    @Override
    public void onCreate() {
        super.onCreate();
        XUtils.init(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if (SharedUtil.isPush(getApplicationContext())) {
            JPushInterface.resumePush(getApplicationContext());
        } else {
            JPushInterface.stopPush(getApplicationContext());
        }
    }

    public static void release() {
        user = null;
        lesson = null;
        if (null != unitList) {
            unitList.clear();
        }
        unitList = null;
    }
}
