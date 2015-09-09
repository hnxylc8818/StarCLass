package com.xubin.starclass.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xubin.starclass.R;

/**
 * Created by Xubin on 2015/9/8.
 */
public class CustomEdit extends RelativeLayout {

    private Context mContext;
    private TextView label;
    private EditText et;
    private ImageView bt;
    private RelativeLayout root;
    private OnBtClickLis btClickLis;
    private boolean isPwd;

    public CustomEdit(Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public CustomEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public CustomEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.layout_custom_edit, this);
        label = (TextView) findViewById(R.id.cedit_label);
        et = (EditText) findViewById(R.id.cedit_et);
        bt = (ImageView) findViewById(R.id.cedit_bt);
        root = (RelativeLayout) findViewById(R.id.cedit_root);
        et.setOnFocusChangeListener(focusChangeListener);
        et.addTextChangedListener(textWatcher);
        bt.setOnClickListener(l);

        if (attrs == null) {
            return;
        }
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CustomEdit);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.CustomEdit_bt_src:
                    Drawable drawable = a.getDrawable(index);
                    bt.setImageDrawable(drawable);
                    break;
                case R.styleable.CustomEdit_bt_visibility:
                    int v = a.getInt(index, 0);
                    if (v == 0) {
                        bt.setVisibility(View.GONE);
                    } else if (v == 1) {
                        bt.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.styleable.CustomEdit_hint_text:
                    String hint = a.getString(index);
                    et.setHint(hint);
                    break;
                case R.styleable.CustomEdit_input_type:
                    int type = a.getInt(index, 2);
                    setInputType(type);
                    break;
                case R.styleable.CustomEdit_label_text:
                    String text = a.getString(index);
                    label.setText(text);
                    break;
                case R.styleable.CustomEdit_text_color:
                    int color = a.getColor(index, Color.BLACK);
                    et.setTextColor(color);
                    label.setTextColor(color);
                    break;
                case R.styleable.CustomEdit_text_size:
                    int size = a.getDimensionPixelSize(index, 18);
                    label.setTextSize(size);
                    et.setTextSize(size);
                    break;
                case R.styleable.CustomEdit_et_maxlength:
                    int length = a.getInt(index, 20);
                    // 设置长度过滤器
                    InputFilter filter = new InputFilter.LengthFilter(length);
                    et.setFilters(new InputFilter[]{filter});
                    break;
                case R.styleable.CustomEdit_label_width:
                    int width = a.getDimensionPixelOffset(index, 100);
                    label.getLayoutParams().width = width;
                    break;
            }
        }

    }

    public void setBtClickLis(OnBtClickLis btClickLis) {
        this.btClickLis = btClickLis;
    }

    private OnClickListener l = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (btClickLis == null) {
                et.getText().clear();
            } else {
                btClickLis.onClick(et, CustomEdit.this);
            }
        }
    };

    public void setOnBtClis(OnBtClickLis btClickLis) {
        this.btClickLis = btClickLis;
    }

    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            root.setSelected(hasFocus);
            if (hasFocus) {
                if (et.getText().length() > 0) {
                    bt.setVisibility(View.VISIBLE);
                }
            } else {
                bt.setVisibility(View.GONE);
            }
        }
    };
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                bt.setVisibility(View.VISIBLE);
            } else {
                bt.setVisibility(View.GONE);
            }
        }
    };
    public static final int PASSWORD = 1;
    public static final int NORMAL = 2;
    public static final int NUMBER = 3;

    public void setInputType(int type) {
        if (type == PASSWORD) {
            isPwd = true;
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (type == NORMAL) {
            isPwd = false;
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        } else if (type == NUMBER) {
            isPwd = false;
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
    }

    public String getText() {
        return et.getText().toString().trim();
    }

    public void setText(String text) {
        et.setText(text);
    }

    public interface OnBtClickLis {
        void onClick(EditText editText, CustomEdit customEdit);
    }

    public boolean isPwd() {
        return isPwd;
    }

    public void setEtChangeLis(TextWatcher textWatcher){
        et.addTextChangedListener(textWatcher);
    }
}
