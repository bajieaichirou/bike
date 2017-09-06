package com.qianying.bike.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qianying.bike.MainActivity;
import com.qianying.bike.O;
import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.model.UsersInfo;
import com.qianying.bike.util.SPUtil;
import com.qianying.bike.util.SPUtils;
import com.qianying.bike.util.SPrefUtil;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.wxapi.WXPayEntryActivity;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机验证
 */
public class EnsureTelphoneActivity extends BaseActivity implements View.OnClickListener {

    private CustomTitlebar mTitlebar;
    private TextView provisions;
    private EditText telphone;  //输入手机号框
    private EditText verification; //输入验证码编辑框
    private TextView getVerification;//获取验证码按钮
    private CheckBox checkbox;
    private TextView start;
    private String telNumber;

    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;

    private boolean flag;//判断点击同意按钮
    private TimeCount time;//获取短信倒计时间

    public static void start(Context context) {
        Intent intent = new Intent(context, EnsureTelphoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensure_telphone);
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();

        initViews();
    }

    private void initViews() {

        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        provisions = (TextView) findViewById(R.id.txt_provisions);
        telphone = (EditText) findViewById(R.id.edit_phone_number);
        verification = (EditText) findViewById(R.id.edit_verification_code);
        getVerification = (TextView) findViewById(R.id.txt_get_verification);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        start = (TextView) findViewById(R.id.txt_start);
        time = new TimeCount(60000, 1000);
        WatchChange watch = new WatchChange();
        telphone.addTextChangedListener(watch);
        verification.addTextChangedListener(watch);

        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setTitleText("手机验证");
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));


        getVerification.setOnClickListener(this);
        start.setOnClickListener(this);
        if (getIntent().hasExtra("phone")) {
            telphone.setText(getIntent().getStringExtra("phone"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_get_verification://点击获取验证码
                sendSmsCode(telphone.getText().toString());
                SPrefUtil.putValue(getBaseContext(), "flag", "homeout_phone", telphone.getText() + "");
//                startTimer();
                break;
            case R.id.txt_start://点击开始按钮
                if (!checkbox.isChecked()) {
                    Toast.makeText(EnsureTelphoneActivity.this, "请勾选", Toast.LENGTH_SHORT).show();
                } else {
                    login(telphone.getText().toString());
                }

                break;
        }
    }


    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    private void sendSmsCode(String phoneNum) {
        if (!isMobileNum(phoneNum)) {
            Toast.makeText(mContext, "无效的手机号码", Toast.LENGTH_LONG).show();
            return;
        } else {
            time.start();
        }

        telNumber = phoneNum;
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id", client_id);
            json.put("state", state);
            json.put("access_token", access_token);
            json.put("action", "sendSmsCode");
            json.put("mobile", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Log.i("________()____", message);
            }

            @Override
            public void onSuccess(NetEntity entity) {

                Log.i("________", entity.getErrmsg());
            }
        });
    }

    /**
     * 点击开始
     */
    private void login(final String phoneNum) {

        JSONObject json = new JSONObject();
        try {
            json.put("client_id", client_id);
            json.put("state", state);
            json.put("access_token", access_token);
            json.put("action", "login");
            json.put("mobile", phoneNum);
            json.put("vericode", verification.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                Message msg = new Message();
                msg.what = 103;
                handler.sendMessage(msg);
                UsersInfo user = UsersInfo.newInstance();
                user.setMobile(phoneNum);
                SPUtils.put(EnsureTelphoneActivity.this, MainActivity.TAG, phoneNum);
                UsersInfo.save(user, UsersInfo.class);

            }
        });
    }

    /**
     * 自定义一个倒计时内部类
     */
    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getVerification.setText(millisUntilFinished / 1000 + "秒");
            getVerification.setEnabled(false);
        }

        @Override
        public void onFinish() {
            getVerification.setEnabled(true);
            getVerification.setText("获取验证码");
        }
    }

    /**
     * 自定义监听EditText
     */
    class WatchChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (telphone.length() > 0 && verification.length() > 0) {
                Message msg = new Message();
                msg.what = 101;
                handler.sendMessage(msg);
            } else {
                Message msg = new Message();
                msg.what = 102;
                handler.sendMessage(msg);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    start.setEnabled(true);
//                    start.setBackgroundResource(R.mipmap.reg_regbtn_enable);
                    start.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    break;
                case 102:
                    start.setEnabled(false);
//                    start.setBackgroundResource(R.mipmap.reg_regbtn_disable);
                    start.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                    break;
                case 103:
                    Intent intent = new Intent(EnsureTelphoneActivity.this, WXPayEntryActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void finish() {
        SPrefUtil.putValue(getBaseContext(), "flag", "homeout_phone", "");
        super.finish();
    }
}
