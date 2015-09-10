package com.xubin.starclass;

import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.lidroid.xutils.http.HttpHandler;
import com.xubin.starclass.utils.DialogUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Xubin on 2015/9/8.
 */
public class BaseActivity extends FragmentActivity {

    protected HttpHandler httpHandler;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果点击返回键并且Waitting对话框是显示状态，取消网络连接
        if (keyCode == KeyEvent.KEYCODE_BACK && DialogUtil.isWaittingShowed()) {
            if (null != httpHandler && !httpHandler.isCancelled()) {
                httpHandler.cancel();
                httpHandler = null;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.destoryWaitting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}
