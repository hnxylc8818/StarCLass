package com.xubin.starclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xubin.starclass.views.CustomEdit;
import com.xubin.starclass.views.TitleView;

/**
 * Created by Xubin on 2015/9/8.
 */
public class LoginActivity extends Activity{

    @ViewInject(R.id.login_title)
    private TitleView titleView;
    @ViewInject(R.id.login_account)
    private CustomEdit ceAccount;
    @ViewInject(R.id.login_pwd)
    private CustomEdit cePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
    }

    @OnClick({R.id.login_sign_in,R.id.login_forget_pwd,R.id.login_quick_reg})
    public void click(View v){
        switch (v.getId()){
            case R.id.login_sign_in:
                login();
                break;
            case R.id.login_forget_pwd:

                break;
            case R.id.login_quick_reg:
                Intent intent=new Intent(LoginActivity.this,RegActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void login() {

    }
}
