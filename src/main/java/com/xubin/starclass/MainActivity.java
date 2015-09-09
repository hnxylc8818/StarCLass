package com.xubin.starclass;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xubin.starclass.fragments.LessionsFragment;
import com.xubin.starclass.fragments.MyLessonFragment;
import com.xubin.starclass.fragments.MyStarsFragment;
import com.xubin.starclass.fragments.SettingFragment;
import com.xubin.starclass.views.TitleView;

public class MainActivity extends BaseActivity {

    @ViewInject(R.id.main_sliding)
    private SlidingMenu sliding;
    private TitleView titleView;

    private FragmentTabHost tabHost;

    public static final String LESSONS = "tab_lessons";
    public static final String MYLESSON = "tab_mylesson";
    public static final String MYSTARS = "tab_mystars";
    public static final String SETTING = "tab_setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        sliding.setMenu(R.layout.layout_menu);
        titleView= (TitleView) findViewById(R.id.main_title);
        titleView.setOnLeftClis(clickListener);
        tabHost = (FragmentTabHost) findViewById(R.id.main_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.main_content);
        tabHost.addTab(tabHost.newTabSpec(LESSONS).setIndicator(LESSONS), LessionsFragment.class,null);
        tabHost.addTab(tabHost.newTabSpec(MYLESSON).setIndicator(MYLESSON), MyLessonFragment.class,null);
        tabHost.addTab(tabHost.newTabSpec(MYSTARS).setIndicator(MYSTARS), MyStarsFragment.class,null);
        tabHost.addTab(tabHost.newTabSpec(SETTING).setIndicator(SETTING), SettingFragment.class,null);
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sliding.showMenu();
        }
    };

    /**
     * 设置当前显示的界面
     * @param tag
     */
    public void setCurrentPage(String tag){
        // 关闭菜单
        sliding.showContent();
        tabHost.setCurrentTabByTag(tag);
        switch (tag){
            case LESSONS:
                titleView.setTvCenterText(R.string.all_lessons);
                break;
            case MYLESSON:
                titleView.setTvCenterText(R.string.my_lesson);
                break;
            case MYSTARS:
                titleView.setTvCenterText(R.string.my_star);
                break;
            case SETTING:
                titleView.setTvCenterText(R.string.settting);
                break;
        }

    }
}
