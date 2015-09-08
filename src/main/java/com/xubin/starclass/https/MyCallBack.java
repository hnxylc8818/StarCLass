package com.xubin.starclass.https;

import android.util.Log;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * Created by Xubin on 2015/9/8.
 */
public abstract class MyCallBack<T> extends RequestCallBack<T> {

    @Override
    public abstract void onSuccess(ResponseInfo<T> responseInfo);

    @Override
    public void onFailure(HttpException e, String s) {
        XUtils.showToast("网络连接错误");
        Log.e("MainActivity","====error======"+s);
        e.printStackTrace();
    }
}
