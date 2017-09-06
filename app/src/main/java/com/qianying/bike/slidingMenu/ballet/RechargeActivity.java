package com.qianying.bike.slidingMenu.ballet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.widget.CustomTitlebar;

//我的押金-充值
public class RechargeActivity extends BaseActivity implements View.OnClickListener {

    private CustomTitlebar mTitlebar;
    private TextView recharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initViews();
    }

    private void initViews() {
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        recharge = (TextView) findViewById(R.id.txt_charge);

        recharge.setOnClickListener(this);
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setTitleText("押金充值");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_recharge:
                DepositActivity.start(mContext);
                break;
        }
    }
}
