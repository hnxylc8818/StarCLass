package com.xubin.starclass;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xubin.starclass.https.XUtils;

/**
 * Created by Xubin on 2015/9/14.
 */
public class PlayActivity extends BaseActivity {

    @ViewInject(R.id.play_vv)
    private VideoView vv;

    private int groupPosition;
    private int childPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ViewUtils.inject(this);
        groupPosition = getIntent().getIntExtra("group", -1);
        childPosition = getIntent().getIntExtra("child", -1);
        if (groupPosition == -1 || childPosition == -1) {
            XUtils.showToast(R.string.error);
            finish();
            return;
        }
        vv.setMediaController(new MediaController(this));
        vv.setVideoPath(getPath());
        vv.setOnCompletionListener(completionListener); // 设置播放完成监听
        vv.start();
    }

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mp.getCurrentPosition() > 100) {
                mp.reset();
                next();
            }
        }
    };

    private void next() {
        boolean bool = true;
        if (childPosition + 1 < MyApp.unitList.get(groupPosition).getParts().size()) {
            childPosition++;
        } else {
            if (groupPosition + 1 < MyApp.unitList.size()) {
                groupPosition++;
                childPosition = 0;
            } else {
                bool = false;
                XUtils.showToast(R.string.lesson_complete);
            }
        }
        if (bool) {
            vv.setVideoPath(getPath());
            vv.start();
        }
    }

    public String getPath() {
        return XUtils.BURL + MyApp.unitList.get(groupPosition).getParts().get(childPosition).getPartUrl();
    }
}
