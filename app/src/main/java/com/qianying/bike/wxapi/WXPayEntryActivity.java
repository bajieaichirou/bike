package com.qianying.bike.wxapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.qianying.bike.R;
import com.qianying.bike.alipay.AuthResult;
import com.qianying.bike.alipay.OrderInfoUtil2_0;
import com.qianying.bike.alipay.PayResult;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.AlipayOrder;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.register.CertificationActivity;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.widget.StatusView;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler, View.OnClickListener {

    private static final String TAG = WXPayEntryActivity.class.getSimpleName();

    private IWXAPI api;

    private CustomTitlebar mTitlebar;  //标题
    private StatusView statusTel;      //手机验证
    private StatusView statusDeposit;  //押金充值
    private StatusView statusCertification; //实名认证
    private StatusView statusComplete;      //注册完成

    private LinearLayout ll_wx;     //微信按钮
    private LinearLayout ll_alipay; //支付宝按钮
    private TextView textView_wx;
    private TextView textView_alipay;

    private TextView txt_charge;//充值按钮


    private boolean flag;

    private String client_id;
    private String state;
    private String url;
    private String access_token;


    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private AlipayOrder alipayOrder;

    private String resultInfo;
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2017061107466058";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088621890366270";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0J5GGpOqZuvUnD8QI6xDeyq6jaYe/VdU/t+6fmPv26+og8EgY/fJHzObJxQc+1doyRPIu59G1To6q+MC/wFZhxNDewa70i+8AjPS8QDa9pOqgvwJH5BlayclRCZXKzecERMA0DBteFB0baYif5G+8iBqQwPC5J1HdD82wHjXC8Lwtarg6Ix52/eB0XL/i6dYDbUuZekLJIZiDIkkWreB/En62tjgNKD6Bs0oi7tarPsDoibPeRxL3DnHFrbMkBrZaWXslLcj2pXeGmGb5vjmsY88cWYSgGJsBtLZ7ToU6iG54wFUPiskM3NzvBhBPKge/64alkbZTQ0q+YVScdVh7AgMBAAECggEBAI3deuOkinl0mAiiiaTcNvS6druIJrWtSbhbhzV2qzPOoxg9HwlPMLMJz9OjrAj3LlPXpz74nlNAAWjxaheVxnBHJJPFwZgheZvdY/u6NWExtPHQeGNUZALyU+3Utnh1nC3oVdKmlgaHoEQt3sDKipLUOtcymF21cOm7wCWoJH3U8MKJnoONBUnMBJcoiMZ1Vs5r+sJ0Z3fk4eP/1Z+Zg8DBSNsl9NSlbBmxVflw50aBa6I4CMnMegw/1t7L6TZtPTiP8o7hZ0G9KxJPeGnBo/um06mG3Clj3VF6DMd4eblgs8mEfWe+qTiP0IdPXUrR7eq6dszFNlC75a9+VMaRkgECgYEA7oYwyqu6RPpy00nd4gmyvyIbV2c4xygIJJ3uggXZCUSUVLX5RUapeOeC2wGjKe17Io/E6nQOWoqdkQyk3jM8kTGruXPyAd3xmzdSW3A42gjkW9QW+2Ery1JzW4GlVN0k86nBWPpqmYsp9Aqtj7sMgM5rRKOGEnPnCu4GL0pcA9sCgYEAwVqVymkW29Vtp1yC6ifMZK5uhTVlHAKAYjEl/9D8CBuN3vBaxVMVaGO3UiWqmERyiLN3tLsIAmP6ms6HgJnZpLakQbfNJgtOpKLLm2ypI88YrDoKZSHgW2J4Z8kaAQNLXbWY2BF+jH1UGYxyWm5/vaTo0SaxgMM2rTSJokzeb+ECgYAW8oAFL4pHEpUzcJrRIT+6FazttreGqXpHE46bobZkpt1iXPNzT74ELLmxGjI5WWiMRaqbJ7ktysIn70B5RBKioVW1DMuOlGynEyZwN5awm0Rk9T2Ux59v+ymv9wQR6wigDIfWaJkS1omdud1Cw6sLRVCalOTUJ6Rlr8qWiB/cGwKBgQCn/7wsvbil08DN7Py21VOrmz/eMDGk76t7JbcdmgiSRtazAWXtE66DIDkVgDLE0JwvmLgG6YchBJunTJHBtGu9yQ/ZJgly59oyBF0is3wW6AdJBbkofBHDdUCm9L3KaYFfb7zY6AJrsS2UcUqetmn5bkL4D0WlWni0b/Syd1XCIQKBgEnLVMR4mYbsnDj94owqjgC7y0A6Pq4+XGmwd+jj/uw/Tlh0z6J6dzT175faKFeuGGX0mb7cOikY/IAMUzvahYqdzAqdVDnHSJ+KbiF0bw32uN4O2iMFtHnCDzzus7NmYvjCGYxfnGalX6viBUFPUTOd+sX4zaAjkMOwVKbbmhcO";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pay);
        initViews();

        api = WXAPIFactory.createWXAPI(this, "appid");
        api.handleIntent(getIntent(), this);

        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
    }

    /**
     * 支付宝
     * */
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Log.i("________pay",resultInfo+"");
                        sendData();
                        startActivity(new Intent(WXPayEntryActivity.this, CertificationActivity.class));
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(WXPayEntryActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(WXPayEntryActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 支付成功之后把数据返回给服务器
     * */
    private void sendData(){
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","getAlipayNotifyOrder");
            json.put("data",resultInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                Log.i("__________send","______send");
            }
        });

    }
    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(WXPayEntryActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(WXPayEntryActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

        }
    }

    private void initViews() {
        ll_wx = (LinearLayout) findViewById(R.id.ll_wx);
        ll_alipay = (LinearLayout) findViewById(R.id.ll_alipay);
        textView_wx = (TextView) findViewById(R.id.checkbox_wx);
        textView_alipay = (TextView) findViewById(R.id.checkbox_alipay);

        txt_charge = (TextView) findViewById(R.id.txt_charge);

        ll_wx.setOnClickListener(this);
        ll_alipay.setOnClickListener(this);
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
            case R.id.checkbox_wx: //选择微信充值
                textView_wx.setBackgroundResource(R.mipmap.checked);
                textView_alipay.setBackgroundResource(R.mipmap.unchecked);
                flag= true;
                break;
            case R.id.checkbox_alipay://选择支付宝充值
                textView_alipay.setBackgroundResource(R.mipmap.checked);
                textView_wx.setBackgroundResource(R.mipmap.unchecked);
                flag = false;
                break;
            case R.id.txt_charge://充值按钮
                if(flag){
                    //调微信支付
                }else {
                    //调支付宝支付
                    getPay();

                }
                break;

        }
    }

    /**
     * 获取支付宝接口
     * */
    private void getPay(){

        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","getAlipayOrder");
            json.put("total","99.00");
            json.put("type","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                alipayOrder = entity.toObj(AlipayOrder.class);
                AlipayOrder.setAlipayOrder(alipayOrder);

                Log.i("_______++++____+++",alipayOrder.getTotal_amount()+"+++++++");

                Message msg = new Message();
                msg.what = 101;
                handler.sendMessage(msg);

            }
        });

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    payV2();
                    break;
            }
        }
    };

}