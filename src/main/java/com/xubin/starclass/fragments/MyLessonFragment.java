package com.xubin.starclass.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.xubin.starclass.LessonInfoActivity;
import com.xubin.starclass.MyApp;
import com.xubin.starclass.R;
import com.xubin.starclass.adapters.LessonAdapter;
import com.xubin.starclass.entity.Lesson;
import com.xubin.starclass.entity.Result;
import com.xubin.starclass.https.MyCallBack;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.utils.DialogUtil;
import com.xubin.starclass.utils.JsonUtil;

import java.util.List;

/**
 * Created by Xubin on 2015/9/9.
 */
public class MyLessonFragment extends Fragment {
    private View view;
    @ViewInject(R.id.lessons_lv)
    private PullToRefreshListView lv;
    private LessonAdapter adapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_lessons, null);
            ViewUtils.inject(this, view);
            adapter = new LessonAdapter(getActivity(), null);
            lv.setAdapter(adapter);
            lv.setOnRefreshListener(listener2);
            lv.getRefreshableView().setCacheColorHint(Color.TRANSPARENT);
            lv.getRefreshableView().setVerticalFadingEdgeEnabled(false);
            loadData(false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        return view;
    }

    @OnItemClick(R.id.lessons_lv)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getLesson(id);
    }

    public void getLesson(long id){
        RequestParams params=new RequestParams();
        params.addBodyParameter("uid",String.valueOf(MyApp.user.getUid()));
        params.addBodyParameter("lid",String.valueOf(id));
        DialogUtil.showWaitting(getActivity());
        XUtils.send(XUtils.LES, params, new MyCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                DialogUtil.hiddenWaitting();
                if (null != responseInfo) {
                    JsonUtil<Result<Lesson>> jsonUtil = new JsonUtil<Result<Lesson>>(new TypeReference<Result<Lesson>>() {
                    });
                    Result<Lesson> result = jsonUtil.parse(responseInfo.result);
                    XUtils.showToast(result.desc);
                    if (result.state == Result.STATE_SUC) {
                        MyApp.lesson = result.data;
                        Intent intent = new Intent(getActivity(), LessonInfoActivity.class);
                        startActivity(intent);
                    }
                } else {
                    XUtils.showToast(R.string.error);
                }
            }
        });
    }

    private PullToRefreshBase.OnRefreshListener2 listener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            page=1;
            loadData(true);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            loadData(false);
        }
    };

    private void loadData(final boolean isFlush) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("pageIndex", String.valueOf(page));
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("uid", String.valueOf(MyApp.user.getUid()));
        DialogUtil.showWaitting(getActivity());
        XUtils.send(XUtils.MLS, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                lv.onRefreshComplete();
                DialogUtil.hiddenWaitting();
                if (null != responseInfo){
                    JsonUtil<Result<List<Lesson>>> jsonUtil=new JsonUtil<Result<List<Lesson>>>(new TypeReference<Result<List<Lesson>>>(){});
                    Result<List<Lesson>> result=jsonUtil.parse(responseInfo.result);
                    XUtils.showToast(result.desc);
                    if (result.state==Result.STATE_SUC){
                        if (isFlush){
                            adapter.clear();
                        }
                        adapter.addAll(result.data);
                        page++;
                    }
                }else{
                    XUtils.showToast(R.string.error);
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                lv.onRefreshComplete();
                DialogUtil.hiddenWaitting();
                XUtils.showToast("网络连接错误");
                Log.e("MainActivity", "====error======" + s);
                e.printStackTrace();
            }
        });
    }
}
