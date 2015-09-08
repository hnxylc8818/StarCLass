package com.xubin.starclass.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xubin.starclass.R;

/**
 * Created by Xubin on 2015/9/7.
 */
public class TitleView extends RelativeLayout {

    private Context mContext;
    private ImageView imgLeft;
    private TextView tvCenter;

    public TitleView(Context context) {
        super(context);
        this.mContext=context;
        init(null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init(attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init(attrs);
    }

    /**
     * 初始化
     * @param attrs
     */
    private void init(AttributeSet attrs){
        LayoutInflater.from(mContext).inflate(R.layout.layout_title, this);
        imgLeft= (ImageView) findViewById(R.id.title_left_img);
        tvCenter= (TextView) findViewById(R.id.title_center_tv);
        if (attrs == null){
            return;
        }
        TypedArray a=mContext.obtainStyledAttributes(attrs,R.styleable.TitleView);
        int count=a.getIndexCount();
        for (int i=0;i<count;i++) {
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.TitleView_left_src:
                    Drawable drawable = a.getDrawable(index);
                    imgLeft.setImageDrawable(drawable);
                    break;
                case R.styleable.TitleView_center_tv_size:
                    int size = a.getDimensionPixelSize(index, 20);
                    tvCenter.setTextSize(size);
                    break;
                case R.styleable.TitleView_center_tv_color:
                    int color = a.getColor(index, Color.WHITE);
                    tvCenter.setTextColor(color);
                    break;
                case R.styleable.TitleView_center_tv_text:
                    String text = a.getString(index);
                    tvCenter.setText(text);
                    break;
                case R.styleable.TitleView_left_visibility:
                    int v = a.getInt(index, 0);
                    if (v == 0) {
                        // 隐藏
                        imgLeft.setVisibility(View.GONE);
                    } else if (v == 1) {
                        // 显示
                        imgLeft.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    public void setOnLeftClis(OnClickListener clis){
        imgLeft.setOnClickListener(clis);
    }
}
