package com.xubin.starclass.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.xubin.starclass.R;

/**
 * Created by Xubin on 2015/7/15.
 */
public class CircleImageView extends ImageView {

    private int mborderWidth;
    private int mborderColor;
    private Context mContext;
    private boolean checkable;
    private boolean checked;
    private boolean checkedG;
    private OnClickListener l;
    private boolean hasStroke = true;

    public CircleImageView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }


    public void init(AttributeSet attrs) {
        super.setOnClickListener(clickListener);
        if (attrs == null) {
            return;
        }
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = a.getIndex(i);
            switch (index) {
                // 设置是否可选中
                case R.styleable.CircleImageView_circle_checkable:
                    checkable = a.getBoolean(index, false);
                    break;
                // 设置是否选中
                case R.styleable.CircleImageView_circle_checked:
                    checked = a.getBoolean(index, false);
                    break;
                case R.styleable.CircleImageView_circle_checked_g:
                    checkedG = a.getBoolean(index, false);
                    break;
                // 设置边框颜色
                case R.styleable.CircleImageView_border_color:
                    mborderColor = a.getColor(index, Color.GREEN);
                    break;
                // 设置边框宽度
                case R.styleable.CircleImageView_border_width:
                    mborderWidth = a.getDimensionPixelSize(index, 3);
                    break;
                case R.styleable.CircleImageView_circle_hasstroke:
                    hasStroke=a.getBoolean(index,true);
                    break;
            }
        }
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkable && !checked) {
                checked = !checked;
            }
            // 重绘控件
            invalidate();
            if (null != l) {
                l.onClick(v);
            }
        }
    };

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    private Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取原本要绘制的图片
        Drawable drawable = getDrawable();
        // 判断图片是否是BitmapDrawable类型
        if (null != drawable && drawable instanceof BitmapDrawable) {
            // 根据Drawable获取Bitmap
            Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
            if (null != bmp) {
                int radius = getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2;
                // 获取圆形图片
                bmp = getCircleBitmap(bmp, radius);
                if (null != bmp) {

                    // 画边框
                    // 画笔重置，否则画笔的状态一直有颜色过滤器
                    paint.reset();
                    // 可选中并且是非选中状态
                    if (checkable && !checked) {
                        // 创建颜色矩阵
                        ColorMatrix colorMatrix = new ColorMatrix();
                        // 设置颜色饱和度(0,1之间的浮点数)
                        colorMatrix.setSaturation(0);
                        // 创建颜色矩阵过滤器
                        ColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
                        // 设置颜色过滤器
                        paint.setColorFilter(filter);
                    } else {
                        paint.setColorFilter(null);
                    }
                    // 绘制到画布上显示
                    canvas.drawBitmap(bmp, getWidth() / 2 - radius, getHeight() / 2 - radius, paint);
                    if (hasStroke) {
                        // 设置画笔风格，边框
                        paint.setStyle(Paint.Style.STROKE);
                        // 开启抗锯齿
                        paint.setAntiAlias(true);
                        // 设置自定义属性
                        paint.setColor(mborderColor);
                        paint.setStrokeWidth(mborderWidth);
                        // 画圆边框
                        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius - mborderWidth / 2, paint);
                    }
                }
            }
        } else {
            super.onDraw(canvas);
        }

    }

    // 根据传入的图片和半径，绘制圆形图片并返回
    public Bitmap getCircleBitmap(Bitmap bmp, int radius) {
        // 创建返回图片对象
        Bitmap outBmp = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        // 创建一个画布
        Canvas canvas = new Canvas(outBmp);
        // 创建画笔
        Paint paint = new Paint();
        // 设置画笔颜色
        paint.setARGB(255, 255, 255, 255);
        // 开启抗锯齿
        paint.setAntiAlias(true);
        // 设置画笔要画的风格，实心
        paint.setStyle(Paint.Style.FILL);
        // 设置画布背景色：透明色
        canvas.drawARGB(0, 0, 0, 0);
        // 使用secIn交叉模式，显示后绘制的，所以先画圆
        canvas.drawCircle(radius, radius, radius, paint);
        // 设置交叉模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 绘制图片
        // 由于画圆形图片，所以先创建外接矩形
        Rect rectSrc = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        Rect rectDst = new Rect(0, 0, radius * 2, radius * 2);
        // 绘制图片
        canvas.drawBitmap(bmp, rectSrc, rectDst, paint);
        // 返回
        return outBmp;
    }

    public boolean isCheckable() {
        return checkable;

    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
        invalidate();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
    }

}
