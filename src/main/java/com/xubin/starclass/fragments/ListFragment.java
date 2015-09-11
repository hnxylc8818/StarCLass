package com.xubin.starclass.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnChildClick;
import com.xubin.starclass.MyApp;
import com.xubin.starclass.R;
import com.xubin.starclass.https.XUtils;

/**
 * Created by Xubin on 2015/9/11.
 */
public class ListFragment extends Fragment {

    private View view;
    @ViewInject(R.id.fragment_list_elv)
    private ExpandableListView elv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, null);
            ViewUtils.inject(this, view);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        return view;
    }

    @OnChildClick(R.id.fragment_list_elv)
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }
}
