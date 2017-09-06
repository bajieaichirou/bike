package com.qianying.bike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Aaron on 2017/7/9.
 * 修改昵称
 */

public class XGNCActivity extends BaseActivity implements View.OnClickListener {
    private CustomTitlebar mTitlebar;  //标题
    private TextView xgnc_save;
    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;
    private EditText edit_sfz;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xgnc);
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();

        edit_sfz = (EditText) findViewById(R.id.edit_sfz);
        xgnc_save = (TextView) findViewById(R.id.xgnc_save);
        xgnc_save.setOnClickListener(this);
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mTitlebar.setTitleText("修改昵称");
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onClick(View v) {
        String nc = edit_sfz.getText().toString().trim();
        if(null==nc||"".equals(nc)){
            Toast.makeText(XGNCActivity.this,"请输入新的昵称",Toast.LENGTH_SHORT).show();
            return;
        }else {
            changeNC(nc);
        }
    }

    private void changeNC(String nc){
        url = regInfo.getSource_url();
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","modifyNickname");
            json.put("nickname",nc);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                Toast.makeText(XGNCActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
