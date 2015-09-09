package com.xubin.starclass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xubin.starclass.entity.Result;
import com.xubin.starclass.entity.User;
import com.xubin.starclass.https.MyCallBack;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.utils.DialogUtil;
import com.xubin.starclass.utils.JsonUtil;
import com.xubin.starclass.views.CustomEdit;
import com.xubin.starclass.views.TitleView;

/**
 * Created by Xubin on 2015/9/8.
 */
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.login_title)
    private TitleView titleView;
    @ViewInject(R.id.login_account)
    private CustomEdit ceAccount;
    @ViewInject(R.id.login_pwd)
    private CustomEdit cePwd;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        sp=getSharedPreferences("user_data",MODE_PRIVATE);
        String account=sp.getString("account","");
        String pwd=sp.getString("pwd","");
        // 读取本地文件并解密
        ceAccount.setText(new String(Base64.decode(account,Base64.NO_WRAP)));
        cePwd.setText(new String(Base64.decode(pwd,Base64.NO_WRAP)));
    }

    @OnClick({R.id.login_sign_in, R.id.login_forget_pwd, R.id.login_quick_reg})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.login_sign_in:
                login();
                break;
            case R.id.login_forget_pwd:
                findPwd();
                break;
            case R.id.login_quick_reg:
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void findPwd() {

    }

    private void login() {
        String account = ceAccount.getText().toString().trim();
        String pwd = cePwd.getText().toString().trim();
        if (account.length() != 11) {
            XUtils.showToast(R.string.account_error);
            return;
        }
        if (!pwd.matches("^\\w{6,20}$")) {
            XUtils.showToast(R.string.pwd_format_error);
            return;
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("u.account", account);
        params.addBodyParameter("u.pwd", pwd);
        DialogUtil.showWaitting(this);
        httpHandler = XUtils.send(XUtils.LOGIN, params, new MyCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                DialogUtil.hiddenWaitting();
                if (null != responseInfo) {
                    JsonUtil<Result<User>> jsonUtil = new JsonUtil<Result<User>>(new TypeReference<Result<User>>() {
                    });
                    Result<User> result = jsonUtil.parse(responseInfo.result);
                    XUtils.showToast(result.desc);
                    if (result.state == Result.STATE_SUC) {
                        MyApp.user = result.data;
                        // 对账号密码进行Base64加密操作 Base64.NO_WRAP：加密时不换行
                        String encodeAccount=Base64.encodeToString(MyApp.user.getAccount().getBytes(),Base64.NO_WRAP);
                        String encodePwd=Base64.encodeToString(MyApp.user.getPwd().getBytes(),Base64.NO_WRAP);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.clear();//清除之前的数据
                        editor.putString("account",encodeAccount);
                        editor.putString("pwd",encodePwd);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    XUtils.showToast(R.string.error);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
