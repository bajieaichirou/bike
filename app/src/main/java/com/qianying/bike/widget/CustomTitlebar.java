/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qianying.bike.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.util.LocalDisplay;


public class CustomTitlebar extends RelativeLayout {
    /**
     * The m context.
     */
    private Activity activity;
    /**
     * 全局的LayoutInflater对象，已经完成初始化.
     */
    public LayoutInflater mInflater;
    /**
     * 显示标题文字的View.
     */
    protected TextView titleTextBtn = null;
    private ImageButton mNavButtonView;
    private String mTitleName;  //标题
    private int titleRightMargin = LocalDisplay.dp2px(44);//ju




    public CustomTitlebar(Context context) {
        this(context, null);
    }

    public CustomTitlebar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitlebar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTitlebar, defStyleAttr, 0);
        mTitleName = a.getString(R.styleable.CustomTitlebar_TitleName);
        titleRightMargin = a.getDimensionPixelSize(R.styleable.CustomTitlebar_TitleRightMargin, LocalDisplay.dp2px(44));
        a.recycle();
        ininTitleBar(context);
    }

    private void ininTitleBar(Context context) {
        activity = (Activity) context;
        mInflater = LayoutInflater.from(context);
        if (mTitleName != null) {
            ensureTitleButtonView();
            titleTextBtn.setText(mTitleName);
        }
    }

    /**
     * 描述：设置标题文本.
     *
     * @param text 文本
     */
    public void setTitleText(String text) {
        ensureTitleButtonView();
        titleTextBtn.setText(text);
    }
    /**
     * 描述：设置标题文本颜色.
     *
     * @param color 文本颜色
     */
    public void setTitleColor(int color) {
        ensureTitleButtonView();
        titleTextBtn.setTextColor(color);
    }


    /**
     * 描述：设置标题文本.
     */
    public void setTitleMaxEms(int maxEms) {
        ensureTitleButtonView();
        titleTextBtn.setMaxEms(maxEms);
    }

    /**
     * 确保titlebutton初始化
     */
    private void ensureTitleButtonView() {
        if (titleTextBtn == null) {
            RelativeLayout.LayoutParams titleTextLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            titleTextLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            titleTextBtn = new TextView(activity);
            titleTextBtn.setTextColor(Color.rgb(0, 0, 0));
            titleTextBtn.setGravity(Gravity.CENTER);
            titleTextBtn.setPadding(LocalDisplay.dp2px(45), 0, LocalDisplay.dp2px(45), 0);
            titleTextBtn.setEllipsize(TextUtils.TruncateAt.END);
            titleTextBtn.setBackgroundDrawable(null);
            titleTextBtn.setSingleLine();
//            titleTextBtn.setLayoutParams(titleTextLayoutParams);
            addView(titleTextBtn, titleTextLayoutParams);
//            RelativeLayout.LayoutParams d= (LayoutParams) titleTextBtn.getLayoutParams();
//            d.setMargins(LocalDisplay.dp2px(45),0,titleRightMargin,0);
//            titleTextBtn.setLayoutParams(d);
        }
    }

    public void setTitlePaddingRight(int paddingRight) {
        ensureTitleButtonView();
        titleTextBtn.setPadding(paddingRight,0,paddingRight,0);
    }


    /**
     * 确保返回键初始化
     */
    private void ensureNavButtonView() {
        if (mNavButtonView == null) {
            mNavButtonView = new ImageButton(getContext());
//            final LayoutParams lp = generateDefaultLayoutParams();
//            lp.addRule(CENTER_VERTICAL);
//            mNavButtonView.setLayoutParams(lp);
            mNavButtonView.setImageResource(R.mipmap.back);
            mNavButtonView.setScaleType(ImageView.ScaleType.CENTER);
            RelativeLayout.LayoutParams titleTextLayoutParams1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            titleTextLayoutParams1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            titleTextLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            mNavButtonView.setPadding(LocalDisplay.dp2px(14), 0, LocalDisplay.dp2px(14), 0);
            mNavButtonView.setBackgroundColor(getResources().getColor(R.color.orange));
            addView(mNavButtonView, titleTextLayoutParams1);
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public void setNavigationOnClickListener(OnClickListener listener) {
        ensureNavButtonView();
        mNavButtonView.setOnClickListener(listener);
    }

}
