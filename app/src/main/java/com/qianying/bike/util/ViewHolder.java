package com.qianying.bike.util;

import android.view.View;

import org.xutils.x;

/**
 * Created by Aaron on 2017/7/8.
 */

public abstract class ViewHolder {
    protected View root;

    public ViewHolder(View root) {
        this.root = root;
        x.view().inject(this, root);
    }
}