package com.xubin.starclass.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xubin.starclass.MyApp;
import com.xubin.starclass.R;
import com.xubin.starclass.entity.Lesson;
import com.xubin.starclass.https.XUtils;

/**
 * Created by Xubin on 2015/9/11.
 */
public class IntrFragment extends Fragment {

    private View view;
    @ViewInject(R.id.fragment_intr_photo)
    private ImageView imgPhoto;
    @ViewInject(R.id.fragment_intr_name)
    private TextView tvName;
    @ViewInject(R.id.fragment_intr_teac_desc)
    private TextView tvTDesc;
    @ViewInject(R.id.fragment_intr_lesson_desc)
    private TextView tvLDesc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_intr, null);
            ViewUtils.inject(this, view);
            XUtils.bitmapUtils.display(imgPhoto,XUtils.BURL+ MyApp.lesson.getTeacher().getPhotoUrl());
            tvName.setText(String.format("讲师：%s",MyApp.lesson.getTeacher().getTname()));
            tvTDesc.setText(MyApp.lesson.getTeacher().getTdesc());
            tvLDesc.setText(MyApp.lesson.getLdesc());
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        return view;
    }
}
