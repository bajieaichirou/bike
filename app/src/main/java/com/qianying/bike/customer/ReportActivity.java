package com.qianying.bike.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;

/**
 * 举报违停
 * Created by VinsenZhang on 2017/5/25.
 */

public class ReportActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report);
        initView();
    }

    private void initView() {
        final TextView commTitle = (TextView) findViewById(R.id.comm_title);
        commTitle.setText("客服服务");

        final ImageView commBackArrow = (ImageView) findViewById(R.id.comm_back_arrow);
        commBackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comm_back_arrow:
                finish();
                break;
        }
    }
}
