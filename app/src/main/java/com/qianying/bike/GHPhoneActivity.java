package com.qianying.bike;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aaron on 2017/7/9.
 * 更换手机号
 */

public class GHPhoneActivity extends BaseActivity implements View.OnClickListener {
    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;

    private EditText edit_sfz;//身份证
    private EditText edit_new_phone;//新手机号
    private EditText edit_verification_code;//验证码

    private TextView txt_get_verification;//点击获取验证码

    private TextView text_ghphone;
    private TimeCount time;//获取短信倒计时间


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghphone);
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();

        final ImageView backImg = (ImageView) findViewById(R.id.comm_back_arrow);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextView titleTv = (TextView) findViewById(R.id.comm_title);
        titleTv.setText("更换手机号");
        edit_sfz = (EditText) findViewById(R.id.edit_sfz);
        edit_new_phone = (EditText) findViewById(R.id.edit_new_phone);
        edit_verification_code = (EditText) findViewById(R.id.edit_verification_code);
        txt_get_verification = (TextView) findViewById(R.id.txt_get_verification);
        txt_get_verification.setOnClickListener(this);
        text_ghphone = (TextView) findViewById(R.id.text_ghphone);
        text_ghphone.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void onClick(View v) {
        String textcode = edit_verification_code.getText().toString().trim();
        String phone = edit_new_phone.getText().toString().trim();
        String sfz = edit_sfz.getText().toString().trim();
        switch (v.getId()) {
            case R.id.txt_get_verification:
                if (null == phone || phone.equals("")) {
                    Toast.makeText(GHPhoneActivity.this, "请输入新的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendSmsCode(phone);

                break;
            case R.id.text_ghphone:

                if (null == phone || phone.equals("")) {
                    Toast.makeText(GHPhoneActivity.this, "请输入新的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == textcode || textcode.equals("")) {
                    Toast.makeText(GHPhoneActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == sfz || sfz.equals("")) {
                    Toast.makeText(GHPhoneActivity.this, "请输入身份证", Toast.LENGTH_SHORT).show();
                }
                changePhone(phone, sfz, textcode);
                break;

        }
    }
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 获取验证码
     * */

    private void sendSmsCode(String phoneNum) {
        if (!isMobileNum(phoneNum)) {
            Toast.makeText(mContext, "无效的手机号码", Toast.LENGTH_LONG).show();
            return;
        }else {
            time.start();
        }
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","sendSmsCode");
            json.put("mobile",phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Log.i("________()____",message);
            }

            @Override
            public void onSuccess(NetEntity entity) {

                Log.i("________",entity.getErrmsg());
            }
        });
    }


    /**
     * 修改手机号
     */
    private void changePhone(String mobile, String idno, String vericode) {
        JSONObject json = new JSONObject();
        try {
            json.put("client_id", client_id);
            json.put("state", state);
            json.put("access_token", access_token);
            json.put("action", "modifyMobile");
            json.put("mobile", mobile);
            json.put("idno", idno);
            json.put("vericode", vericode);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                finish();
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
            txt_get_verification.setText(millisUntilFinished / 1000 + "秒");
            txt_get_verification.setEnabled(false);
        }

        @Override
        public void onFinish() {
            txt_get_verification.setEnabled(true);
            txt_get_verification.setText("获取验证码");
        }
    }

}
