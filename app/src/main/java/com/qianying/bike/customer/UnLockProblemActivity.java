package com.qianying.bike.customer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;

public class UnLockProblemActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_lock_problem);
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
