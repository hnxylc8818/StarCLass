package com.xubin.starclass;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xubin.starclass.entity.Result;
import com.xubin.starclass.https.MyCallBack;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.utils.DialogUtil;
import com.xubin.starclass.utils.JsonUtil;
import com.xubin.starclass.views.CustomEdit;
import com.xubin.starclass.views.TitleView;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Xubin on 2015/9/8.
 */
public class RegActivity extends BaseActivity {

    @ViewInject(R.id.reg_title)
    private TitleView titleView;
    @ViewInject(R.id.reg_account)
    private CustomEdit ceAccount;
    @ViewInject(R.id.reg_pwd)
    private CustomEdit cePwd;
    @ViewInject(R.id.reg_nick)
    private CustomEdit ceNick;
    @ViewInject(R.id.reg_code)
    private CustomEdit ceCode;
    @ViewInject(R.id.reg_send_code)
    private Button sendCode;

    private int time = 30;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this, "a44b741a25b0", "fce93466d880bfa936238f2851f5ad9e");
        setContentView(R.layout.activity_reg);
        ViewUtils.inject(this);

        titleView.setOnLeftClis(clickListener);
        cePwd.setOnBtClis(btClickLis);
        // 添加回调事件
        SMSSDK.registerEventHandler(eventHandler);
        sendCode.setEnabled(false);
        // 文本框内容改变事件
        ceAccount.setEtChangeLis(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 11 && ceAccount.getText().matches("^1(3|4|5|7|8)\\d{9}$")) {
                sendCode.setEnabled(true);
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
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("u.account", account);
                    params.addBodyParameter("u.pwd", pwd);
                    params.addBodyParameter("u.nick", nick);
                    XUtils.send(XUtils.REG, params, new MyCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            DialogUtil.hiddenWaitting();
                            if (null != responseInfo) {
                                JsonUtil<Result<Boolean>> jsonUtil = new JsonUtil<Result<Boolean>>
                                        (new TypeReference<Result<Boolean>>() {
                                        });
                                Result<Boolean> res = jsonUtil.parse(responseInfo.result);
                                handler.sendMessage(handler.obtainMessage(2, res.desc));
                                if (res.data) {
                                    finish();
                                }
                            } else {
                                handler.sendMessage(handler.obtainMessage(1, R.string.error, 0));
                            }
                        }
                    });
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

    private CustomEdit.OnBtClickLis btClickLis = new CustomEdit.OnBtClickLis() {

        @Override
        public void onClick(EditText editText, CustomEdit customEdit) {
            if (customEdit.isPwd()) {
                customEdit.setInputType(CustomEdit.NORMAL);
            } else {
                customEdit.setInputType(CustomEdit.PASSWORD);
            }
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @OnClick({R.id.reg_send_code, R.id.reg_sign_up})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.reg_send_code:
                String phone = ceAccount.getText().toString().trim();
                if (phone.matches("^1(3|4|5|7|8)\\d{9}$")) {
                    SMSSDK.getVerificationCode("86", phone);
                    sendCode.setEnabled(false);
                    handler.postDelayed(r, 1000);
                } else {
                    XUtils.showToast(R.string.input_phone);
                }
                break;
            case R.id.reg_sign_up:
                reg();
                break;
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


    private String account;
    private String pwd;
    private String nick;
    private String code;

    /**
     * 注册
     */
    private void reg() {
        // 获取用户输入的账户密码昵称信息
        account = ceAccount.getText().toString().trim();
        pwd = cePwd.getText().toString().trim();
        nick = ceNick.getText().toString().trim();
        code = ceCode.getText().toString().trim();
        // 验证信息是否合法
        if (!account.matches("^1(3|4|5|7|8)\\d{9}$")) {
            XUtils.showToast(R.string.input_phone);
            return;
        }
        if (!pwd.matches("^\\w{6,20}$")) {
            XUtils.showToast(R.string.pwd_format_error);
            return;
        }
        if (TextUtils.isEmpty(nick)) {
            XUtils.showToast(R.string.input_nick);
            return;
        }
        if (code.length() == 4) {
            DialogUtil.showWaitting(this);
            // 发送验证码
            SMSSDK.submitVerificationCode("86", account, code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
