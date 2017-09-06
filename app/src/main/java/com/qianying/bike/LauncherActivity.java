package com.qianying.bike;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.comm.H;
import com.qianying.bike.model.AuthInfo;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.util.MD5Util;
import com.qianying.bike.util.PreUtils;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 启动页面 权限申请加载准备等
 * Created by Vinsen on 17/5/17.
 */

public class LauncherActivity extends BaseActivity {

    private static final int goToNextType = 111;

    private String imei; //获取机器唯一标识
    private String mdImei; //唯一标识加密
    private RegInfo regInfo;//Register对象
    private AuthInfo authInfo;//Authorize对象
    private TokenInfo tokenInfo;

    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone
        imei = tm.getDeviceId();
        Log.i("___________+++___", imei + "");
        getRegInfo(imei);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case goToNextType:

                    if (PreUtils.getBool(PreUtils.IS_FIRST_LOGIN, true)) {
                        startActivity(new Intent(LauncherActivity.this, GuardActivity.class));
                    } else {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    }

                    doPermissionGrantedStuffs();
                    finish();
                    break;
                case 1:
                    RegInfo.setRegInfo(regInfo);
                    break;
                case 2:
                    AuthInfo.setAuthInfo(authInfo);
                    break;
                case 3:
                    TokenInfo.setTokenInfo(tokenInfo);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_launcher);
        checkPromise(this);
//        doPermissionGrantedStuffs();

    }

    /**
     * 接下来权限的申请
     * 定位权限
     * 读写权限
     */
    private static final String[] permissionsArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    //还需申请的权限列表
    private List<String> permissionsList = new ArrayList<>();

    //申请权限后的返回码
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    public void checkPromise(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {

            for (String permission : permissionsArray) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsList.add(permission);
                }
            }
            if (permissionsList.size() > 0) {
                ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
            } else {

                mHandler.sendEmptyMessageDelayed(goToNextType, 3000);
            }

        } else {
            mHandler.sendEmptyMessageDelayed(goToNextType, 3000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        // 拿不到定位权限  推出应用
                        System.exit(0);
                    }
                }

                mHandler.sendEmptyMessageDelayed(goToNextType, 500);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

 /*
     * 获取register接口
     */

    private void getRegInfo(String imei) {
        mdImei = MD5Util.encrypt(imei);

        Map<String, Object> map = new HashMap<>();
        map.put("imei", imei + "");
        map.put("code", mdImei);

        X.Post(H.HOST + H.authed, map, new MyCallBack<String>() {


            @Override
            protected void onFailure(String message) {
                Log.i("_______++", message + "");
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Log.i("________", entity.getStatus() + "__________" + entity.getErrno() + "");
                regInfo = entity.toObj(RegInfo.class);
//                RegInfo.setRegInfo(regInfo);//保存对象
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);

                getAuthInfo();

            }
        });
    }


    /*获取授权接口*/
    private void getAuthInfo() {
        Map<String, Object> map = new HashMap<>();
        String client_id = regInfo.getApp_key();
        String state = regInfo.getSeed_secret();
        String url = regInfo.getAuthorize_url();
        map.put("client_id", client_id);
        map.put("state", state);
        map.put("response_type", "code");
        X.Post(url, map, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Log.i("_______++", message + "");
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Log.i("________++___",entity.getStatus());
                authInfo = entity.toObj(AuthInfo.class);
//                AuthInfo.setAuthInfo(authInfo);
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
                getTokenInfo();
            }
        });

    }



    //获取Access_token

    private void getTokenInfo(){
        Map<String, Object> map = new HashMap<>();
        String url = regInfo.getToken_url();
        String client_id = regInfo.getApp_key();
        String client_secret = regInfo.getApp_secret();
        String grant_type = "authorization_code";
        String code = authInfo.getAuthorize_code();
        String state = regInfo.getSeed_secret();
        map.put("client_id",client_id);
        map.put("grant_type",grant_type);
        map.put("client_secret",client_secret);
        map.put("code",code);
        map.put("state",state);

        X.Post(url, map, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Log.i("________**",message+"");
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Log.i("______!!",entity.getStatus());
                tokenInfo = entity.toObj(TokenInfo.class);
//                TokenInfo.setTokenInfo(tokenInfo);
                Message msg = new Message();
                msg.what = 3;
                mHandler.sendMessage(msg);
            }
        });
    }

}
