package com.qianying.bike.slidingMenu.ballet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.widget.CustomTitlebar;


//钱包充值
public class DepositActivity extends Activity implements View.OnClickListener {

    private CustomTitlebar mTitlebar;
    private TextView charge100;
    private TextView charge50;
    private TextView charge20;
    private TextView charge10;
    private TextView charge;
    private CheckBox wxPay;
    private CheckBox aliPay;

    private float changeCount = 0.01f;
    private String action;

    public static void start(Context context) {
        Intent intent = new Intent(context, DepositActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        initViews();
        findViewById(R.id.txt_charge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RechargeActivity.recharge(DepositActivity.this,action,"2",changeCount+"");
            }
        });
        findViewById(R.id.rl_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPay.performClick();
            }
        });
        findViewById(R.id.rl_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wxPay.performClick();
            }
        });
    }

    private void initViews() {

        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        charge = (TextView) findViewById(R.id.txt_recharge);
        charge100 = (TextView) findViewById(R.id.txt_charge_100);
        charge50 = (TextView) findViewById(R.id.txt_charge_50);
        charge20 = (TextView) findViewById(R.id.txt_charge_20);
        charge10 = (TextView) findViewById(R.id.txt_charge_10);
        wxPay = (CheckBox) findViewById(R.id.checkbox_wx);
        aliPay = (CheckBox) findViewById(R.id.checkbox_alipay);

        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText(getString(R.string.wallet_recharge));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        charge10.setOnClickListener(this);
        charge100.setOnClickListener(this);
        charge50.setOnClickListener(this);
        charge20.setOnClickListener(this);
        aliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wxPay.setChecked(false);
                    wxPay.setEnabled(true);
                    aliPay.setEnabled(false);
                    action = "aliPay";
                }

            }
        });
        wxPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    aliPay.setEnabled(true);
                    aliPay.setChecked(false);
                    wxPay.setEnabled(false);
                    action = "wxPay";
                }
            }
        });
        aliPay.performClick();
    }

    @Override
    public void onClick(View v) {
        changeCount = 0.01f;
        switch (v.getId()) {
            case R.id.txt_charge_100:
//                changeCount = 100;
                clearChargeColor();
                charge100.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            case R.id.txt_charge_50:
//                changeCount = 50;
                clearChargeColor();
                charge50.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            case R.id.txt_charge_20:
//                changeCount = 20;
                clearChargeColor();
                charge20.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            case R.id.txt_charge_10:
//                changeCount = 10;
                clearChargeColor();
                charge10.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
        }
    }

    private void clearChargeColor() {
        charge100.setBackgroundColor(getResources().getColor(R.color.btn_bg));
        charge50.setBackgroundColor(getResources().getColor(R.color.btn_bg));
        charge20.setBackgroundColor(getResources().getColor(R.color.btn_bg));
        charge10.setBackgroundColor(getResources().getColor(R.color.btn_bg));

    }
}
