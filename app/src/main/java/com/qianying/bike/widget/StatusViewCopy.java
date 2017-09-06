package com.qianying.bike.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.R;

/**
 * Created by fotoplace on 17/5/23.
 */

public class StatusViewCopy extends RelativeLayout {

    public static final int COMPLETE = 0;
    public static final int CURRENT = 1;
    public static final int NEXT = 2;

    private View big;
    private View mid;
    private View small;
    private View smallBg;
    private View right;
    private View left;
    private ImageView img;

    private TextView text;

    public StatusViewCopy(Context context) {
        this(context, null);
    }

    public StatusViewCopy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusViewCopy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_status_copy, this);

        initView();
    }

    private void initView() {
        big = (View) findViewById(R.id.view_big);
        mid = (View) findViewById(R.id.view_mid);
        small = (View) findViewById(R.id.view_small);
        smallBg = (View) findViewById(R.id.view_small_bg);
        right = (View) findViewById(R.id.divider_right);
        left = (View) findViewById(R.id.divider_left);
        img = (ImageView) findViewById(R.id.img);
        text = (TextView) findViewById(R.id.txt_text);
    }

    public void setStatus(int status) {
        if (big == null || img == null || mid == null || small == null || smallBg == null)
            return;
        switch (status) {
            case COMPLETE:
                big.setVisibility(VISIBLE);
                img.setVisibility(VISIBLE);
                mid.setVisibility(INVISIBLE);
                small.setVisibility(INVISIBLE);
                smallBg.setVisibility(INVISIBLE);
                break;
            case CURRENT:
                big.setVisibility(INVISIBLE);
                img.setVisibility(INVISIBLE);
                mid.setVisibility(VISIBLE);
                small.setVisibility(VISIBLE);
                smallBg.setVisibility(INVISIBLE);
                break;
            case NEXT:
                big.setVisibility(INVISIBLE);
                img.setVisibility(INVISIBLE);
                mid.setVisibility(INVISIBLE);
                small.setVisibility(INVISIBLE);
                smallBg.setVisibility(VISIBLE);
                break;

        }
    }

    public void setDivider(boolean left) {
        if (this.left != null && this.right != null)
            if (left) {
                this.left.setVisibility(VISIBLE);
                this.right.setVisibility(GONE);
            } else {
                this.left.setVisibility(GONE);
                this.right.setVisibility(VISIBLE);
            }
    }

    public void setText(String text) {
        if (this.text != null) {
            this.text.setText(text);
        }
    }

    public void setTextColor(int color){
        if (this.text != null) {
            this.text.setTextColor(color);
        }
    }
}
