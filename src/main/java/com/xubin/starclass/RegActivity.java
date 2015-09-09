package com.xubin.starclass;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xubin.starclass.views.CustomEdit;
import com.xubin.starclass.views.TitleView;

/**
 * Created by Xubin on 2015/9/8.
 */
public class RegActivity extends Activity {

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
    @ViewInject(R.id.reg_sign_up)
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ViewUtils.inject(this);

        titleView.setOnLeftClis(clickListener);
        cePwd.setOnBtClis(btClickLis);
    }

    private CustomEdit.OnBtClickLis btClickLis=new CustomEdit.OnBtClickLis() {

        @Override
        public void onClick(EditText editText, CustomEdit customEdit) {
            if (customEdit.isPwd()){
                customEdit.setInputType(CustomEdit.NORMAL);
            }else{
                customEdit.setInputType(CustomEdit.PASSWORD);
            }
        }
    };

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @OnClick({R.id.reg_send_code,R.id.reg_sign_up})
    private void click(View v){
        switch (v.getId()){
            case R.id.reg_send_code:

                break;
            case R.id.reg_sign_up:
                reg();
                break;
        }
    }

    private void reg() {

    }


}
