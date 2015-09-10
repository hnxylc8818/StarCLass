package com.xubin.starclass.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.xubin.starclass.R;

/**
 * Created by Xubin on 2015/9/9.
 */
public class LessionsFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view=inflater.inflate(R.layout.fragment_lessons,null);
            ViewUtils.inject(this,view);
        }
        ViewGroup parent= (ViewGroup) view.getParent();
        if (null != parent){
            parent.removeView(view);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
