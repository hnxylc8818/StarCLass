package com.xubin.starclass.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnChildClick;
import com.xubin.starclass.MyApp;
import com.xubin.starclass.PlayActivity;
import com.xubin.starclass.R;
import com.xubin.starclass.adapters.ListAdapter;
import com.xubin.starclass.entity.Result;
import com.xubin.starclass.entity.Unit;
import com.xubin.starclass.https.MyCallBack;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.utils.DialogUtil;
import com.xubin.starclass.utils.JsonUtil;
import java.util.List;

/**
 * Created by Xubin on 2015/9/11.
 */
public class ListFragment extends Fragment {

    private View view;
    @ViewInject(R.id.fragment_list_elv)
    private ExpandableListView elv;
    private ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, null);
            ViewUtils.inject(this, view);
            adapter=new ListAdapter(getActivity(),null);
            elv.setAdapter(adapter);
            loadData(); //加载数据
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        return view;
    }

    /**
     * 加载数据
     */
    private void loadData() {
        RequestParams params=new RequestParams();
        params.addBodyParameter("lid",String.valueOf(MyApp.lesson.getLid()));
        DialogUtil.showWaitting(getActivity());
        XUtils.send(XUtils.LES_DETAIL, params, new MyCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                DialogUtil.hiddenWaitting();
                if (null != responseInfo){
                    JsonUtil<Result<List<Unit>>> jsonUtil=new JsonUtil<Result<List<Unit>>>(new TypeReference<Result<List<Unit>>>(){});
                    Result<List<Unit>> result=jsonUtil.parse(responseInfo.result);
                    XUtils.showToast(result.desc);
                    if (result.state==Result.STATE_SUC){
                        MyApp.unitList=result.data;
                        adapter.addAll(result.data);
                        // 展开数据
                        for (int i=0;i<result.data.size();i++){
                            elv.expandGroup(i);
                        }
                    }
                }else{
                    XUtils.showToast(R.string.error);
                }
            }
        });
    }


    @OnChildClick(R.id.fragment_list_elv)
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent=new Intent(getActivity(),PlayActivity.class);
        intent.putExtra("group",groupPosition);
        intent.putExtra("child",childPosition);
        startActivity(intent);
        return true;
    }
}
