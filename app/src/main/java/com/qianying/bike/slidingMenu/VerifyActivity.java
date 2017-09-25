package com.qianying.bike.slidingMenu;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianying.bike.MainActivity;
import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.AuthInfo;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.model.UserInfo;
import com.qianying.bike.model.UsersInfo;
import com.qianying.bike.slidingMenu.ballet.RechargeActivity;
import com.qianying.bike.slidingMenu.mineSecond.AlwaysUseAddressActivity;
import com.qianying.bike.util.SPUtil;
import com.qianying.bike.util.SPUtils;
import com.qianying.bike.util.SPrefUtil;
import com.qianying.bike.util.UserHelper;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;

/**
 * 设置页面
 * Created by Vinsen on 17/5/18.
 */


@ContentView(R.layout.layout_real_name_commit)
public class VerifyActivity extends BaseActivity  {
    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;
    private CustomTitlebar mTitlebar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setTitleText("实名认证");
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((EditText)findViewById(R.id.et_credent_id)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkNoEmpty(((EditText)findViewById(R.id.et_name)),((EditText)findViewById(R.id.et_credent_id)), (TextView) findViewById(R.id.tv_commit));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((EditText)findViewById(R.id.et_name)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkNoEmpty(((EditText)findViewById(R.id.et_name)),((EditText)findViewById(R.id.et_credent_id)), (TextView) findViewById(R.id.tv_commit));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void checkNoEmpty(EditText viewById, EditText viewById1, TextView viewById2) {
        if(!viewById.getText().toString().equals("")&&!viewById1.getText().toString().equals("")){
            viewById2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            viewById2.setEnabled(true);
            viewById2.setTextColor(getResources().getColor(R.color.white));
        }else{
            viewById2.setTextColor(getResources().getColor(R.color.gary_bg));
            viewById2.setBackgroundColor(getResources().getColor(R.color.gray_btn));
            viewById2.setEnabled(false);
        }
    }


    private void submit() {

        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","verified");
            json.put("truename",((TextView)findViewById(R.id.et_name)).getText().toString());
            json.put("idno",((TextView)findViewById(R.id.et_credent_id)).getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Toast.makeText(VerifyActivity.this,message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(NetEntity entity) {

                if(entity.getStatus().equals("true")&&entity.getErrno().equals("0")){
                    UsersInfo ui = UsersInfo.get(UsersInfo.class);
                    ui.setIs_verified(1+"");
                    UsersInfo.save(ui, UsersInfo.class);
                    if(ui.getIs_paydeposit()==null||ui.getIs_paydeposit().equals("0")){
                        startActivity(new Intent(getBaseContext(), RechargeActivity.class));
                    }else{
                        setResult(MainActivity.REQUEST_SCAN,new Intent().putExtra("callback","true"));

                        finish();
                    }

                }else{
                    Toast.makeText(VerifyActivity.this,entity.getErrmsg(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
