package com.qianying.bike.slidingMenu.mineSecond;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.util.PreUtils;

/**
 * 常用地址页面
 * Created by Vinsen on 17/5/22.
 */

public class AlwaysUseAddressActivity extends BaseActivity implements View.OnClickListener {

    private TextView alwaysUrlOne;
    private TextView alwaysUrlTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_always_address);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        alwaysUrlOne.setText(PreUtils.getStr(PreUtils.PreKeys.always_url_one, "设置常用地址1"));
        alwaysUrlTwo.setText(PreUtils.getStr(PreUtils.PreKeys.always_url_two, "设置常用地址2"));
    }

    private void initView() {
        final RelativeLayout alwaysUrlOneLayout = (RelativeLayout) findViewById(R.id.always_url_one_layout);
        alwaysUrlOneLayout.setOnClickListener(this);
        final RelativeLayout alwaysUrlTwoLayout = (RelativeLayout) findViewById(R.id.always_url_two_layout);
        alwaysUrlTwoLayout.setOnClickListener(this);

        alwaysUrlOne = (TextView) findViewById(R.id.always_url_one);
        alwaysUrlTwo = (TextView) findViewById(R.id.always_url_two);

        final ImageView backImg = (ImageView) findViewById(R.id.comm_back_arrow);
        backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.always_url_one_layout:
                break;
            case R.id.always_url_two_layout:
                break;
            case R.id.comm_back_arrow:
                finish();
                break;
        }
    }
}
