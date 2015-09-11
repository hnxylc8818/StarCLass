package com.xubin.starclass;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

/**
 * Created by Xubin on 2015/9/10.
 */
public class UpdatePwdActivity extends BaseActivity {

    @ViewInject(R.id.update_title)
    private TitleView titleView;
    @ViewInject(R.id.update_new_pwd)
    private CustomEdit ceNewPwd;
    @ViewInject(R.id.update_repwd)
    private CustomEdit ceRepwd;

    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        ViewUtils.inject(this);
        account=getIntent().getStringExtra("account");
        titleView.setOnLeftClis(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ceNewPwd.setOnBtClis(btClickLis);
        ceRepwd.setOnBtClis(btClickLis);
    }

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

    @OnClick(R.id.update_ok)
    private void click(View v){
        if (TextUtils.isEmpty(account)){
            XUtils.showToast("获取账号失败");
            return;
        }
        String newPwd=ceNewPwd.getText();
        String repwd=ceRepwd.getText();
        if (TextUtils.isEmpty(newPwd)){
            XUtils.showToast("请输入新密码");
            return;
        }
        if (!newPwd.equals(repwd)){
            XUtils.showToast("两次密码不一致");
            return;
        }
        DialogUtil.showWaitting(this);
        RequestParams params=new RequestParams();
        params.addBodyParameter("u.account",account);
        params.addBodyParameter("u.pwd",newPwd);
        XUtils.send(XUtils.UUSER, params, new MyCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                DialogUtil.hiddenWaitting();
                if (null != responseInfo){
                    JsonUtil<Result<Boolean>> jsonUtil=new JsonUtil<Result<Boolean>>(new TypeReference<Result<Boolean>>(){});
                    Result<Boolean> result=jsonUtil.parse(responseInfo.result);
                    XUtils.showToast(result.desc);
                    if (result.data){
                        finish();
                    }
                }else{
                    XUtils.showToast(R.string.error);
                }
            }
        });
    }
}
