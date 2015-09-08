package com.xubin.starclass;

import android.app.Application;

import com.xubin.starclass.entity.User;
import com.xubin.starclass.https.XUtils;

/**
 * Created by Xubin on 2015/9/8.
 */
public class MyApp extends Application {
    public static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        XUtils.init(getApplicationContext());
    }
}
