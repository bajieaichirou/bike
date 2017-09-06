package com.qianying.bike.slidingMenu.ballet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.widget.CustomTitlebar;

public class ReturnDepositActivity extends BaseActivity implements View.OnClickListener {

    private CustomTitlebar mTitlebar;
    private TextView recharge;
    private TextView lookDetail;
    private TextView depositCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_deposit);

        initViews();
    }

    private void initViews() {

        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        recharge = (TextView) findViewById(R.id.txt_charge);
        lookDetail = (TextView) findViewById(R.id.txt_look_detail);
        depositCount = (TextView) findViewById(R.id.txt_deposit_count);

        mTitlebar.setTitleText("退押金");
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        recharge.setOnClickListener(this);
        lookDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_charge:
                DepositActivity.start(mContext);
                break;
            case R.id.txt_look_detail:
                BalletDetailActivity.start(mContext);
                break;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ReturnDepositActivity.class);
        context.startActivity(intent);
    }
}
