package com.qianying.bike.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义的ScrollView添加滑动监听  兼容低版本
 * Created by Vinsen on 17/5/18.
 */

public class MyScrollView extends ScrollView {

    private OnScrollChanged mOnScrollChanged;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChanged != null)
            mOnScrollChanged.onScroll(l, t, oldl, oldt);
    }

    public void setOnScrollChanged(OnScrollChanged onScrollChanged) {
        this.mOnScrollChanged = onScrollChanged;
    }

    public interface OnScrollChanged {
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
