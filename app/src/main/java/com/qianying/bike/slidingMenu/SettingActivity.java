package com.qianying.bike.slidingMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.MainActivity;
import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.AuthInfo;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.register.EnsureTelphoneActivity;
import com.qianying.bike.slidingMenu.mineSecond.AlwaysUseAddressActivity;
import com.qianying.bike.util.SPUtil;
import com.qianying.bike.util.SPUtils;
import com.qianying.bike.util.UserHelper;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设置页面
 * Created by Vinsen on 17/5/18.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_setting);
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
        initView();
    }

    private void initView() {
        final ImageView backImg = (ImageView) findViewById(R.id.comm_back_arrow);
        backImg.setOnClickListener(this);

        final TextView titleTv = (TextView) findViewById(R.id.comm_title);
        titleTv.setText(mContext.getResources().getString(R.string.setting_text));

        final RelativeLayout alwaysUrlAddress = (RelativeLayout) findViewById(R.id.always_url_layout);
        alwaysUrlAddress.setOnClickListener(this);

        final RelativeLayout removeCache = (RelativeLayout) findViewById(R.id.remove_cache_layout);
        removeCache.setOnClickListener(this);

        final RelativeLayout aboutUs = (RelativeLayout) findViewById(R.id.about_us_layout);
        aboutUs.setOnClickListener(this);

        final TextView exitLogin = (TextView) findViewById(R.id.exit_login);
        exitLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comm_back_arrow:
                finish();
                break;
            case R.id.always_url_layout:
                startActivity(new Intent(mContext, AlwaysUseAddressActivity.class));
                break;
            case R.id.remove_cache_layout:
                break;
            case R.id.about_us_layout:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri CONTENT_URI_BROWSERS = Uri.parse("http://xiaoying.shnow.cn/index.html");
                intent.setData(CONTENT_URI_BROWSERS);
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                startActivity(intent);
                break;
            case R.id.exit_login:

                exit();
                break;
        }
    }

    /**
     * 退出登录接口
     * */
    private void exit(){
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","logout");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                UserHelper.getInstance().loginOut();
                RegInfo.setRegInfo(null);
                TokenInfo.setTokenInfo(null);
                AuthInfo.setAuthInfo(null);
                SPUtils.put(SettingActivity.this, MainActivity.TAG,"");
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                finish();

            }
        });
    }
}
