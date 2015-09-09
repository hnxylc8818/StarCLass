package com.xubin.starclass.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xubin.starclass.MyApp;
import com.xubin.starclass.R;
import com.xubin.starclass.entity.Result;
import com.xubin.starclass.entity.User;
import com.xubin.starclass.https.MyCallBack;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.utils.DialogUtil;
import com.xubin.starclass.utils.JsonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xubin on 2015/9/9.
 */
public class MyStarsFragment extends Fragment {

    private View view;
    @ViewInject(R.id.star_stars)
    private TextView tvStats;
    @ViewInject(R.id.star_days)
    private TextView tvDays;
    @ViewInject(R.id.star_sign)
    private Button btSign;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mystars, null);
            ViewUtils.inject(this, view);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        updateData();
        return view;
    }

    @OnClick(R.id.star_sign)
    public void click(View v) {
        sign();
    }

    /**
     * 签到
     */
    private void sign() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("u.uid", String.valueOf(MyApp.user.getUid()));
        DialogUtil.showWaitting(getActivity());
        XUtils.send(XUtils.SIGN, params, new MyCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                DialogUtil.hiddenWaitting();
                if (null != responseInfo) {
                    JsonUtil<Result<User>> jsonUtil = new JsonUtil<Result<User>>(new TypeReference<Result<User>>() {});
                    Result<User> result = jsonUtil.parse(responseInfo.result);
                    XUtils.showToast(result.desc);
                    if (result.state == Result.STATE_SUC) {
                        MyApp.user = result.data;
                        updateData();
                    }
                } else {
                    XUtils.showToast(R.string.error);
                }
            }
        });
    }

    /**
     * 读取用户上次签到日期，判断是否已签到
     *
     * @return
     */
    private boolean hasSign() {
        String lastDateStr = MyApp.user.getLastOnlineDate();
        if (TextUtils.isEmpty(lastDateStr)) {
            return false;
        }
        try {
            Date now = new Date();
            String nowStr = sdf.format(now);
            now = sdf.parse(nowStr);
            Date lastDate = sdf.parse(lastDateStr);
            if (lastDate.getTime() < now.getTime()) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 更新数据
     */
    public void updateData() {
        tvDays.setText(String.format(getActivity().getString(R.string.days_format),
                MyApp.user.getOnlineDay() == null ? 0 : MyApp.user.getOnlineDay()));
        tvStats.setText(String.format(getActivity().getString(R.string.stars_format),
                MyApp.user.getStart() == null ? 0 : MyApp.user.getStart()));
        if (hasSign()) {
            btSign.setText("已签到");
            btSign.setEnabled(false);
        } else {
            btSign.setText("签到");
            btSign.setEnabled(true);
        }
    }
}
