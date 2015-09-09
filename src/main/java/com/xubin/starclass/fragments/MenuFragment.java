package com.xubin.starclass.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.xubin.starclass.MainActivity;
import com.xubin.starclass.MyApp;
import com.xubin.starclass.R;
import com.xubin.starclass.https.XUtils;

/**
 * Created by Xubin on 2015/9/9.
 */
public class MenuFragment extends Fragment {

    private View view;

    @ViewInject(R.id.menu_photo)
    private ImageView photo;
    @ViewInject(R.id.menu_nick)
    private TextView nick;
    @ViewInject(R.id.menu_lv)
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_menu, null);
            ViewUtils.inject(this, view);
            updateMenu();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    new String[]{getActivity().getString(R.string.all_lessons),
                            getActivity().getString(R.string.my_lesson),
                            getActivity().getString(R.string.my_star),
                            getActivity().getString(R.string.settting)});
            lv.setAdapter(adapter);

        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null != parent) {
            parent.removeView(view);
        }
        return view;
    }

    @OnItemClick(R.id.menu_lv)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String tag=null;
        switch (position){
            case 0:
                tag=MainActivity.LESSONS;
                break;
            case 1:
                tag=MainActivity.MYLESSON;
                break;
            case 2:
                tag=MainActivity.MYSTARS;
                break;
            case 3:
                tag=MainActivity.SETTING;
                break;
        }
        if (null != tag) {
            ((MainActivity) getActivity()).setCurrentPage(tag);
        }
    }

    public void updateMenu() {
        XUtils.bitmapUtils.display(photo, XUtils.BURL + MyApp.user.getPhotoUrl());
        nick.setText(MyApp.user.getNick());
    }
}
