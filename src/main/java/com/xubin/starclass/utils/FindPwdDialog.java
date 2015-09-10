package com.xubin.starclass.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.xubin.starclass.R;
import com.xubin.starclass.UpdatePwdActivity;
import com.xubin.starclass.entity.Result;
import com.xubin.starclass.https.MyCallBack;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.views.CustomEdit;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Xubin on 2015/9/10.
 */
public class FindPwdDialog {
    private Dialog findPwd;
    private CustomEdit ceAccount;
    private Button sendCode;
    private CustomEdit ceCode;
    private Context mContext;
    private Button btCancer;
    private Button btOk;
    private int time = 30;
    private String account;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    XUtils.showToast(msg.arg1);
                    break;
                case 2:
                    XUtils.showToast(msg.obj.toString());
                    break;
            }
            return true;
        }
    });

    public FindPwdDialog(Context context){
        this.mContext=context;
        SMSSDK.initSDK(context, "a44b741a25b0", "fce93466d880bfa936238f2851f5ad9e");
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 显示找回密码对话框
     */
    public void showFind(){
        if (findPwd == null){
            findPwd=new AlertDialog.Builder(mContext).create();
            findPwd.show();
            Window window=findPwd.getWindow();
            View view= LayoutInflater.from(mContext).inflate(R.layout.layout_find_pwd,null);
            ceAccount= (CustomEdit) view.findViewById(R.id.find_account);
            sendCode= (Button) view.findViewById(R.id.find_send_code);
            ceCode= (CustomEdit) view.findViewById(R.id.find_code);
            btCancer= (Button) view.findViewById(R.id.find_cancer);
            btOk= (Button) view.findViewById(R.id.find_ok);
            sendCode.setOnClickListener(l);
            btOk.setOnClickListener(l);
            btCancer.setOnClickListener(l);
            window.setContentView(view);
        }else{
            findPwd.show();
        }
    }

    private View.OnClickListener l =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.find_send_code:
                    account = ceAccount.getText().toString().trim();
                    if (account.matches("^1(3|4|5|7|8)\\d{9}$")) {
                        SMSSDK.getVerificationCode("86", account);
                        sendCode.setEnabled(false);
                        handler.postDelayed(r, 1000);
                    } else {
                        XUtils.showToast(R.string.input_phone);
                    }
                    break;
                case R.id.find_cancer:
                    hidden();
                    break;
                case R.id.find_ok:
                    toUpdatePwd();
                    break;
            }
        }
    };

    private void toUpdatePwd() {
        // 获取用户输入的账户密码昵称信息
        account = ceAccount.getText().toString().trim();
        String code = ceCode.getText().toString().trim();
        // 验证信息是否合法
        if (!account.matches("^1(3|4|5|7|8)\\d{9}$")) {
            XUtils.showToast(R.string.input_phone);
            return;
        }
        if (code.length() == 4) {
            DialogUtil.showWaitting(mContext);
            // 提交验证码
            SMSSDK.submitVerificationCode("86", account, code);
        }
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            sendCode.setText(time + "秒后重新发送");
            time--;
            if (time == 0) {
                time = 30;
                sendCode.setText("发送验证码");
                sendCode.setEnabled(true);
                handler.removeCallbacks(this);
            } else {
                sendCode.setEnabled(false);
                handler.postDelayed(this, 1000);
            }
        }
    };


    private EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    handler.sendMessage(handler.obtainMessage(1, R.string.send_suc, 0));
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    DialogUtil.hiddenWaitting();
                    Intent intent=new Intent(mContext,UpdatePwdActivity.class);
                    intent.putExtra("account",account);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    hidden();
                    destoryDialog();
                }
            } else {
                DialogUtil.hiddenWaitting();
                if (null != data) {
                    Log.e("MainActivity", "=====data======" + JSON.toJSONString(data));
                }
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    handler.sendMessage(handler.obtainMessage(1, R.string.send_fail, 0));
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    handler.sendMessage(handler.obtainMessage(1, R.string.error_code, 0));
                }
            }
        }
    };



    /**
     * 隐藏
     */
    public void hidden() {
        if (null != findPwd && findPwd.isShowing()) {
            findPwd.dismiss();
        }
    }

    public void destoryDialog(){
        if (null != findPwd){
            hidden();
            findPwd=null;
        }
        SMSSDK.unregisterEventHandler(eventHandler);
    }


}
