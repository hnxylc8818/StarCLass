package com.xubin.starclass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;
import com.xubin.starclass.adapters.LessonInfoAdapter;
import com.xubin.starclass.fragments.IntrFragment;
import com.xubin.starclass.fragments.ListFragment;
import com.xubin.starclass.https.XUtils;
import com.xubin.starclass.views.TitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xubin on 2015/9/11.
 */
public class LessonInfoActivity extends BaseActivity {

    @ViewInject(R.id.lesson_info_thumb)
    private ImageView imgThumb;
    @ViewInject(R.id.lesson_info_tabs)
    private RadioGroup rgTabs;
    @ViewInject(R.id.lesson_info_vp)
    private ViewPager vpContent;
    @ViewInject(R.id.lesson_info_title)
    private TitleView titleView;

    private LessonInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_info);
        ViewUtils.inject(this);
        XUtils.bitmapUtils.display(imgThumb,XUtils.BURL+MyApp.lesson.getThumbUrl());
        vpContent.addOnPageChangeListener(pageChangeListener);
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new IntrFragment());
        adapter=new LessonInfoAdapter(getSupportFragmentManager(),fragments);
        vpContent.setAdapter(adapter);
        titleView.setOnLeftClis(clickListener);
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @OnRadioGroupCheckedChange(R.id.lesson_info_tabs)
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.lesson_info_list:
                vpContent.setCurrentItem(0);
                break;
            case R.id.lesson_info_intr:
                vpContent.setCurrentItem(1);
                break;
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            ((RadioButton)rgTabs.getChildAt(position)).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
