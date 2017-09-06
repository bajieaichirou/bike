package com.qianying.bike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.widget.CustomTitlebar;

/**
 * Created by Aaron on 2017/7/6.
 * 主页面消息界面
 */

public class MsgActivity extends BaseActivity {

    private CustomTitlebar mTitlebar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        initViews();

    }

    private void initViews() {
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setTitleText("消息");
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
