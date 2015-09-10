package com.xubin.starclass.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.xubin.starclass.R;
import com.xubin.starclass.https.XUtils;

/**
 * Created by Xubin on 2015/9/8.
 */
public class DialogUtil {
    public static Dialog waitting;
    /**
     * 显示进度对话框
     */
    public static void showWaitting(Context context) {
        if (waitting == null) {
            waitting = new AlertDialog.Builder(context).create();
            waitting.setCanceledOnTouchOutside(false);  // 触摸边缘不消失
            waitting.show();
            Window window = waitting.getWindow();
            window.setContentView(R.layout.layout_waitting);
        } else {
            waitting.show();
        }
    }


    /**
     * 隐藏
     */
    public static void hiddenWaitting() {
        if (null != waitting && waitting.isShowing()) {
            waitting.dismiss();
        }
    }

    /**
     * 销毁
     */
    public static void destoryWaitting() {
        hiddenWaitting();
        waitting = null;
    }

    public static boolean isWaittingShowed(){
        if(null != waitting && waitting.isShowing()){
            return true;
        }
        return false;
    }
}
