package com.xubin.starclass;

import android.os.Bundle;

import com.xubin.starclass.https.XUtils;

/**
 * Created by Xubin on 2015/9/10.
 */
public class UpdatePwdActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XUtils.showToast(getIntent().getStringExtra("account"));
    }
}
