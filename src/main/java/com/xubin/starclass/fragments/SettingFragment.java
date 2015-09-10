package com.xubin.starclass.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;
import com.xubin.starclass.BuildConfig;
import com.xubin.starclass.LoginActivity;
import com.xubin.starclass.R;
import com.xubin.starclass.entity.AppVersion;
import com.xubin.starclass.entity.Result;
import com.xubin.starclass.https.MyCallBack;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.utils.DialogUtil;
import com.xubin.starclass.utils.JsonUtil;
import com.xubin.starclass.utils.SharedUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Xubin on 2015/9/9.
 */
public class SettingFragment extends Fragment {

    private View view;

    @ViewInject(R.id.setting_push)
    private CheckBox cbPush;
    private String currVersionName;
    private AppVersion appVersion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_setting, null);
            ViewUtils.inject(this, view);

        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        cbPush.setChecked(SharedUtil.isPush(getActivity()));
        return view;
    }

    @OnClick({R.id.setting_update, R.id.setting_about, R.id.setting_exit})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.setting_update:
                getUpdate();
                break;
            case R.id.setting_about:

                break;

            case R.id.setting_exit:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    @OnCompoundButtonCheckedChange(R.id.setting_push)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedUtil.savePush(getActivity(), isChecked);
        if (isChecked) {
            if (JPushInterface.isPushStopped(getActivity())) {
                JPushInterface.resumePush(getActivity());
            }
        } else {
            if (!JPushInterface.isPushStopped(getActivity())) {
                JPushInterface.stopPush(getActivity());
            }
        }
    }

    public boolean getUpdate() {
        try {
            PackageManager m = getActivity().getPackageManager();
            PackageInfo info = m.getPackageInfo(getActivity().getPackageName(), 0);
            currVersionName = info.versionName;
            RequestParams params = new RequestParams();
            params.addBodyParameter("ver", String.valueOf(info.versionCode));
            DialogUtil.showWaitting(getActivity());
            XUtils.send(XUtils.VER, params, new MyCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    DialogUtil.hiddenWaitting();
                    if (null != responseInfo) {
                        JsonUtil<Result<AppVersion>> jsonUtil = new JsonUtil<Result<AppVersion>>(new TypeReference<Result<AppVersion>>() {
                        });
                        Result<AppVersion> result = jsonUtil.parse(responseInfo.result);
                        XUtils.showToast(result.desc);
                        if (result.state == Result.STATE_SUC) {
                            appVersion=result.data;
                            showDownload();
                        }
                    } else {
                        XUtils.showToast(R.string.error);
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            XUtils.showToast(R.string.error);
        }

        return true;
    }

    public void showDownload() {
        new AlertDialog.Builder(getActivity())
                .setTitle("发现新版本")
                .setMessage(String.format("当前版本%s,最新版本%s，是否下载更新", currVersionName, appVersion.getVersionName()))
                .setNegativeButton("立即更新", dialogLis).setNeutralButton("下次再说", dialogLis).show();
    }

    private DialogInterface.OnClickListener dialogLis = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    XUtils.download(appVersion.getAppUrl());
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    dialog.dismiss();
                    break;
            }
        }
    };
}
