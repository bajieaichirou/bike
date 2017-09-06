package com.qianying.bike.slidingMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户指南
 * Created by Vinsen on 17/5/18.
 */

public class UserBookActivity extends BaseActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_user_book);
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
        initView();
    }

    private void initView() {

        final ImageView backIcon = (ImageView) findViewById(R.id.comm_back_arrow);
        backIcon.setOnClickListener(this);

        final TextView titleTv = (TextView) findViewById(R.id.comm_title);
        titleTv.setText(mContext.getResources().getString(R.string.user_book));

        getGuider();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comm_back_arrow:
                finish();
                break;
        }
    }
    /**
     * 获取用户指南
     * */
    private void getGuider(){
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","userGuide");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {

            }
        });
    }

}
