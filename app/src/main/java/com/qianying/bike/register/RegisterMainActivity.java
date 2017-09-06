package com.qianying.bike.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.util.UserHelper;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.widget.StatusView;
/**
 * 注册---押金界面
 */
public class RegisterMainActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private CustomTitlebar mTitlebar;
    private StatusView statusTel;
    private StatusView statusDeposit;
    private StatusView statusCertification;
    private StatusView statusComplete;
    private LinearLayout certification;
    private RelativeLayout deposit;
    private LinearLayout success;
    private TextView counter;

    private EditText name;
    private EditText personCard;
    private TextView certificate;
    private TextView certificateOther;

    private CheckBox wxCheck;
    private CheckBox apilyCheck;
    private TextView charge;

    private CountDownTimer timer;
    private int status = 0;
    public static final int CERTIFICATION = 100;

    public static void start(Context context, int status) {
        Intent intent = new Intent(context, RegisterMainActivity.class);
        intent.putExtra("status", status);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        status = getIntent().getIntExtra("status", 0);
        initViews();
    }

    private void initViews() {

        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        statusTel = (StatusView) findViewById(R.id.status_tel);
        statusDeposit = (StatusView) findViewById(R.id.status_deposit);
        statusCertification = (StatusView) findViewById(R.id.status_certification);
        statusComplete = (StatusView) findViewById(R.id.status_complete);
        certification = (LinearLayout) findViewById(R.id.ll_certification);
        deposit = (RelativeLayout) findViewById(R.id.ll_deposit);
        success = (LinearLayout) findViewById(R.id.ll_success);

        counter = (TextView) findViewById(R.id.txt_counter);
        name = (EditText) findViewById(R.id.edit_name);
        personCard = (EditText) findViewById(R.id.edit_card);

        certificate = (TextView) findViewById(R.id.txt_certificate);
        certificateOther = (TextView) findViewById(R.id.txt_certificate_other);
        wxCheck = (CheckBox) findViewById(R.id.checkbox_wx);
        apilyCheck = (CheckBox) findViewById(R.id.checkbox_alipay);
        charge = (TextView) findViewById(R.id.txt_charge);

        certificate.setOnClickListener(this);
        certificateOther.setOnClickListener(this);
        charge.setOnClickListener(this);
        name.addTextChangedListener(this);
        personCard.addTextChangedListener(this);
        certificate.setEnabled(false);

        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText("实名认证");
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
        statusCertification.setStatus(StatusView.CURRENT);
        statusComplete.setText("注册完成");
        statusComplete.setStatus(StatusView.NEXT);
        statusComplete.setDivider(true);

        certification.setVisibility(View.VISIBLE);
        deposit.setVisibility(View.GONE);
        success.setVisibility(View.GONE);

        if (status == UserHelper.ENSURED_TO_CERTIFICATE) {

        } else if (status == UserHelper.CERTIFICATED_TO_CHARGE) {
            certification();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_charge:
                charge();
                UserHelper.getInstance().getUser().setRegisterStatus(UserHelper.CHARGED_COMPLETE);
                break;
            case R.id.txt_certificate:
                certification();
                UserHelper.getInstance().getUser().setRegisterStatus(UserHelper.CERTIFICATED_TO_CHARGE);
                break;
            case R.id.txt_certificate_other:
//                CertificationActivity.startForResult(RegisterMainActivity.this, CERTIFICATION);
                break;
        }
    }

    private void charge() {
        certification.setVisibility(View.GONE);
        deposit.setVisibility(View.GONE);
        success.setVisibility(View.VISIBLE);
        statusCertification.setStatus(StatusView.COMPLETE);
        statusDeposit.setStatus(StatusView.COMPLETE);
        statusComplete.setStatus(StatusView.CURRENT);
        startTimer();
    }

    private void certification() {
        certification.setVisibility(View.GONE);
        deposit.setVisibility(View.VISIBLE);
        success.setVisibility(View.GONE);
        statusCertification.setStatus(StatusView.COMPLETE);
        statusDeposit.setStatus(StatusView.CURRENT);
        statusComplete.setStatus(StatusView.NEXT);

    }

    private void startTimer() {
        timer = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                counter.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                finish();
            }
        };
        timer.start();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!name.getText().toString().equals("") && !personCard.getText().toString().equals("")) {
            certificate.setEnabled(true);
            certificate.setBackgroundColor(getResources().getColor(R.color.orange));
        } else {
            certificate.setEnabled(false);
            certificate.setBackgroundColor(getResources().getColor(R.color.gary_bg));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CERTIFICATION && resultCode == RESULT_OK) {
            certification();
        }
    }
}
