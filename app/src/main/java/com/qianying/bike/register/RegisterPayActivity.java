package com.qianying.bike.register;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.widget.StatusView;

/**
 * Created by Aaron on 2017/7/5.
 */

public class RegisterPayActivity extends BaseActivity implements View.OnClickListener {
    private CustomTitlebar mTitlebar;  //标题
    private StatusView statusTel;      //手机验证
    private StatusView statusDeposit;  //押金充值
    private StatusView statusCertification; //实名认证
    private StatusView statusComplete;      //注册完成

    private RelativeLayout rl_wx;     //微信按钮
    private RelativeLayout rl_alipay; //支付宝按钮
    private TextView textView_wx;
    private TextView textView_alipay;

    private TextView txt_charge;//充值按钮


    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pay);
        initViews();

    }

    private void initViews() {
        rl_wx = (RelativeLayout) findViewById(R.id.rl_wx);
        rl_alipay = (RelativeLayout) findViewById(R.id.rl_alipay);
        textView_wx = (TextView) findViewById(R.id.checkbox_wx);
        textView_alipay = (TextView) findViewById(R.id.checkbox_alipay);

        txt_charge = (TextView) findViewById(R.id.txt_charge);

        rl_wx.setOnClickListener(this);
        rl_alipay.setOnClickListener(this);
        textView_wx.setOnClickListener(this);
        textView_alipay.setOnClickListener(this);
        txt_charge.setOnClickListener(this);

        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        statusTel = (StatusView) findViewById(R.id.status_tel);
        statusDeposit = (StatusView) findViewById(R.id.status_deposit);
        statusCertification = (StatusView) findViewById(R.id.status_certification);
        statusComplete = (StatusView) findViewById(R.id.status_complete);
        mTitlebar.setTitleText("押金充值");
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        statusTel.setText("手机验证");
        statusTel.setStatus(StatusView.COMPLETE);
        statusTel.setDivider(false);
        statusDeposit.setText("押金充值");
        statusDeposit.setStatus(StatusView.CURRENT);
        statusCertification.setText("实名认证");
        statusCertification.setStatus(StatusView.NEXT);
        statusComplete.setText("注册完成");
        statusComplete.setStatus(StatusView.NEXT);
        statusComplete.setDivider(true);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_wx: //选择微信充值
                textView_wx.setBackgroundResource(R.mipmap.checked);
                textView_alipay.setBackgroundResource(R.mipmap.unchecked);
                flag= true;
                break;
            case R.id.rl_alipay://选择支付宝充值
                textView_alipay.setBackgroundResource(R.mipmap.checked);
                textView_wx.setBackgroundResource(R.mipmap.unchecked);
                flag = false;
                break;
            case R.id.txt_charge://充值按钮
                break;

        }
    }
}
