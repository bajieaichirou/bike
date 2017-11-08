package com.qianying.bike;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.customer.CustomerHelper;
import com.qianying.bike.map.MapHelper;
import com.qianying.bike.model.LocationBean;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.model.AuthInfo;
import com.qianying.bike.model.MapLocation;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.UsersInfo;
import com.qianying.bike.register.EnsureTelphoneActivity;
import com.qianying.bike.search.SearchActivity;
import com.qianying.bike.slidingMenu.MenuActivity;
import com.qianying.bike.slidingMenu.VerifyActivity;
import com.qianying.bike.slidingMenu.ballet.DepositActivity;
import com.qianying.bike.slidingMenu.ballet.RechargeActivity;
import com.qianying.bike.useBk.EnterCodeActivity;
import com.qianying.bike.util.JsonUtil;
import com.qianying.bike.util.LocationHelper;
import com.qianying.bike.util.SPUtils;
import com.qianying.bike.util.SPrefUtil;
import com.qianying.bike.util.UserHelper;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity implements View.OnClickListener, AMap.OnMarkerClickListener, AMap.OnMapClickListener {

    public static final int REQUEST_SCAN = 102;
    private static final int REQUEST_CODE = 101;
    public static final int SEARCH_LOCATION = 100;
    public static final int REQUEST_SCAN_AFTER_LOGIN = 103;
    public static final int REQUEST_SCAN_AFTER_VERIFY = 105;
    public static final int REQUEST_SCAN_AFTER_RECHARGE = 106;
    private String imei; //获取机器唯一标识
    private String mdImei; //唯一标识加密
    private RegInfo regInfo;//Register对象
    private AuthInfo authInfo;//Authorize对象
    private TokenInfo tokenInfo;

    private MapView mapView;
    private MapHelper mapHelper;
    private CustomerHelper customerHelper;
    private LinearLayout closeLl;
    private TextView orderBike;
    private LinearLayout orderLl;
    private LinearLayout bikeInfo;
    private TextView orderNumber;
    private TextView orderTimer;
    private TextView orderCancel;
    private RelativeLayout adRl;
    private TextView adText;
    private CountDownTimer mTimer;
    private ImageView loading;
    private RelativeLayout loadingRl;

    private UsersInfo usersInfo;
    public static String TAG = MainActivity.class.getName();
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        phone = (String) SPUtils.get(MainActivity.this, MainActivity.TAG, "");
        setContentView(R.layout.activity_main);
        usersInfo = UsersInfo.newInstance();

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMap().setOnMarkerClickListener(this);
        mapView.getMap().setOnMapClickListener(this);
        mapHelper = new MapHelper();
        mapHelper.init(this, mapView);
        mapHelper.reLocation();
//        loadIMEI();
        doPermissionGrantedStuffs();
        initView();
        initMap();

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

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


    private void initMap() {
        MapLocation mapLocation = LocationHelper.loadMapLocation(mContext);

    }

    private void initView() {
        //个人信息
        final ImageView menuIcon = (ImageView) findViewById(R.id.memu_icon);
        menuIcon.setOnClickListener(this);
        //扫描开锁
        final TextView searchBikeIcon = (TextView) findViewById(R.id.search_bike_icon);
        searchBikeIcon.setOnClickListener(this);
        //定位
        final ImageView reloadLocation = (ImageView) findViewById(R.id.reload_location);
        reloadLocation.setOnClickListener(this);
        //搜索
        final ImageView searchIcon = (ImageView) findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(this);
        //消息
        final ImageView messageIcon = (ImageView) findViewById(R.id.msg_icon);
        messageIcon.setOnClickListener(this);
        //故障报修
        final ImageView customerIcon = (ImageView) findViewById(R.id.customer_icon);
        customerIcon.setOnClickListener(this);

        closeLl = (LinearLayout) findViewById(R.id.ll_close_bike);
        orderBike = (TextView) findViewById(R.id.txt_order);
        orderLl = (LinearLayout) findViewById(R.id.ll_order);
        orderNumber = (TextView) findViewById(R.id.txt_order_number);
        orderTimer = (TextView) findViewById(R.id.txt_order_timer);
        orderCancel = (TextView) findViewById(R.id.txt_order_cancel);
        bikeInfo = (LinearLayout) findViewById(R.id.ll_bike_info);
        adRl = (RelativeLayout) findViewById(R.id.rl_ad);
        adText = (TextView) findViewById(R.id.txt_ad);
        loading = (ImageView) findViewById(R.id.loading);//刷新
        loadingRl = (RelativeLayout) findViewById(R.id.rl_loading);


        orderBike.setOnClickListener(this);
        orderCancel.setOnClickListener(this);
        adRl.setOnClickListener(this);

        customerHelper = new CustomerHelper();
        customerHelper.init(mContext);
        Object o = SPrefUtil.getValue("flag", "homeout_phone", String.class);
        if (o == null || o.toString().equals("")) {

        } else {
            startActivity(new Intent(getApplication(), EnsureTelphoneActivity.class).putExtra("phone", o.toString()));
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.memu_icon://个人中心
                phone = (String) SPUtils.get(MainActivity.this, MainActivity.TAG, "");
                if (!phone.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    EnsureTelphoneActivity.start(mContext);
                }
//                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
//                startActivity(intent);
                break;
            case R.id.search_bike_icon://扫描开锁
                UsersInfo ui = UsersInfo.get(UsersInfo.class);
                if (ui == null) {
                    EnsureTelphoneActivity.start(mContext);
                    return;
                }
                userStatusAndOpen();


                break;
            case R.id.reload_location://定位
                mapHelper.reLocation();
                break;
            case R.id.search_icon://搜索

                SearchActivity.start(this, SEARCH_LOCATION);

                break;
            case R.id.msg_icon://消息
                phone = (String) SPUtils.get(MainActivity.this, MainActivity.TAG, "");
                if (!phone.isEmpty()) {
                    startActivity(new Intent(MainActivity.this, MsgActivity.class));
                } else {
                    EnsureTelphoneActivity.start(this, REQUEST_SCAN_AFTER_LOGIN);
                }
                break;
            case R.id.customer_icon://故障报修
                phone = (String) SPUtils.get(MainActivity.this, MainActivity.TAG, "");
                if (!phone.isEmpty()) {
                    customerHelper.show(findViewById(R.id.customer_icon));
                } else {
                    EnsureTelphoneActivity.start(mContext);
                }
//                startActivity(new Intent(MainActivity.this, WXPayEntryActivity.class));
                break;
            case R.id.txt_order:
                phone = (String) SPUtils.get(MainActivity.this, MainActivity.TAG, "");
                if (!phone.isEmpty()) {
                    orderBike();
                } else {
                    UserHelper.getInstance().checkRegisterStatus(mContext);
                }
                break;
            case R.id.txt_order_cancel:
                cancelOrderFromServer();
                break;
            case R.id.rl_ad:
                DepositActivity.start(mContext);
                break;
        }
    }

    private void userStatusAndOpen() {
        regInfo = RegInfo.getRegInfo();
        tokenInfo = TokenInfo.getTokenInfo();
        String client_id = regInfo.getApp_key();
        String state = regInfo.getSeed_secret();
        String url = regInfo.getSource_url();
        String access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id", client_id);
            json.put("state", state);
            json.put("access_token", access_token);
            json.put("action", "userStatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Toast.makeText(MainActivity.this,entity.getData().toString(),Toast.LENGTH_LONG).show();
                if (entity.getStatus().equals("true") && entity.getErrno().equals("0")) {
                    UsersInfo user = JsonUtil.jsonToEntity(entity.getData().toString(), UsersInfo.class);
                    SPUtils.put(MainActivity.this, MainActivity.TAG, user.getMobile());
                    UsersInfo.save(user, UsersInfo.class);
                    UsersInfo ui = UsersInfo.get(UsersInfo.class);
                    phone = ui.getMobile();

//                checkPromise(this);
                    if (phone != null && !phone.isEmpty()) {

                        checkPromise(MainActivity.this);
                    } else {
                        EnsureTelphoneActivity.start(mContext);
                    }
                }
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void cancelOrderFromServer() {
        bikeInfo.setVisibility(View.VISIBLE);
        orderBike.setVisibility(View.VISIBLE);
        orderLl.setVisibility(View.GONE);
        adRl.setVisibility(View.VISIBLE);
    }

    private void orderBike() {
        bikeInfo.setVisibility(View.GONE);
        orderBike.setVisibility(View.GONE);
        orderLl.setVisibility(View.VISIBLE);
        adRl.setVisibility(View.GONE);
        if (mTimer == null) {
            mTimer = new CountDownTimer(15 * 60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    orderTimer.setText(millisUntilFinished / 60000 + ":" + millisUntilFinished % 60000 / 1000);
                }

                @Override
                public void onFinish() {
                    cancelOrderFromServer();
                }
            };
        }
        mTimer.start();
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    //扫描后的解锁页面
                    Intent intent = new Intent(mContext, UnLockingActivity.class);
                    intent.putExtra("SN", msg.obj.toString());
                    startActivity(intent);
                    break;
            }
        }
    };


    long lastBackTime = 0;

    @Override
    public void onBackPressed() {
        if (closeLl.getVisibility() == View.VISIBLE) {
            closeTop();
            return;
        }
        if (System.currentTimeMillis() - lastBackTime > 1500) {
            lastBackTime = System.currentTimeMillis();
            Toast.makeText(mContext, R.string.click_once_more_to_exit, Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null&&data.getStringExtra("callback")!=null) {
            if (requestCode == REQUEST_SCAN_AFTER_RECHARGE) {
                userStatusAndOpen();
            } else if (requestCode == REQUEST_SCAN_AFTER_VERIFY) {
                checkPromise(this);
            } else if (requestCode == REQUEST_SCAN_AFTER_LOGIN) {
                userStatusAndOpen();
            } else if (requestCode == REQUEST_SCAN) {
                goToCaptureActivity();
            }
        }else
        /**
         * 处理二维码扫描结果
         */
            if (requestCode == REQUEST_CODE) {

                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Toast.makeText(this, getString(R.string.the_car_id_is) + result, Toast.LENGTH_LONG).show();
                        Message msg = new Message();
                        msg.what = 11;
                        msg.obj = result;

                        //处理扫描结果（在界面上显示）
                        mHandler.sendMessage(msg);

                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(MainActivity.this, R.string.parse_barcode_failure, Toast.LENGTH_LONG).show();
                    }
                }
            } else if (requestCode == SEARCH_LOCATION && resultCode == RESULT_OK) {
                LocationBean location = (LocationBean) data.getBundleExtra("location").getSerializable("location");
                if (location != null)
                    mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLat(), location.getLon())));
            }
    }

    private void checkPermissionStep() {
        UsersInfo info = UsersInfo.get(UsersInfo.class);
        if (info == null) {
            startActivity(new Intent(getBaseContext(), EnsureTelphoneActivity.class));
            return;
        }
        if (info.getIs_paydeposit() == null || info.getIs_paydeposit().equals("0")) {
            startActivityForResult(new Intent(getBaseContext(), RechargeActivity.class), REQUEST_SCAN_AFTER_RECHARGE);
            return;
        }
        if (info.getIs_verified() == null || info.getIs_verified().equals("0")) {
            startActivityForResult(new Intent(getBaseContext(), VerifyActivity.class), REQUEST_SCAN_AFTER_VERIFY);
            return;
        }
        goToCaptureActivity();
    }

    /**
     * 二维码扫描Activity
     */
    private void goToCaptureActivity() {
        Intent intent1 = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent1, REQUEST_CODE);
    }

    //申请相机权限后的返回码
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 2;

    public void checkPromise(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                checkPermissionStep();
            }
        } else {
            checkPermissionStep();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        checkPermissionStep();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (closeLl.getVisibility() == View.GONE) {
            openTop();
        }
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (closeLl.getVisibility() == View.VISIBLE) {
            closeTop();
        }
    }

    private void openTop() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -1, 1);
        animation.setDuration(500);
        closeLl.setVisibility(View.VISIBLE);
        closeLl.startAnimation(animation);
    }

    private void closeTop() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 1, -1);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                closeLl.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        closeLl.startAnimation(animation);

    }

//    private void loadingAnimate() {
//        loadingRl.setVisibility(View.VISIBLE);
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator rotation = ObjectAnimator.ofFloat(loading, "rotation", 0f, 1080f);
//        rotation.setDuration(1500);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(loading, "scaleX", 1f, 0.3f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(loading, "scaleY", 1f, 0.3f);
//        scaleX.setDuration(500);
//        scaleY.setDuration(500);
//        animatorSet.play(scaleX).with(scaleY).after(rotation);
//        animatorSet.play(rotation);
//        animatorSet.start();
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                loadingRl.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                loadingRl.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//    }


    private static String ACTION_NAME = "enter_pass_word";

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                //手动输入车辆的编号
                startActivity(new Intent(mContext, EnterCodeActivity.class));
            }
        }

    };


    /*
     * 获取register接口
     */
    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager

        Log.i("___________+++___", imei + "");
    }


}
