package com.xubin.starclass.https;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xubin.starclass.R;

/**
 * Created by Xubin on 2015/9/8.
 */
public class XUtils {

    public static final String BURL = "http://192.168.11.222:8080/StartClass/";
    public static final String LOGIN = "login";
    public static final String REG = "reg";

    public static HttpUtils httpUtils;
    public static BitmapUtils bitmapUtils;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(context);
        }
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.load_fail);
        bitmapUtils.configDefaultLoadingImage(R.drawable.loading);
        bitmapUtils.configDefaultReadTimeout(10000);
    }

    public static void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static void send(String url, RequestParams params, RequestCallBack callBack) {
        httpUtils.send(HttpRequest.HttpMethod.POST, BURL + url, params, callBack);
    }
}
