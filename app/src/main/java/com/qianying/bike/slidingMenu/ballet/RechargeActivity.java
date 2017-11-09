package com.qianying.bike.slidingMenu.ballet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.qianying.bike.MainActivity;
import com.qianying.bike.R;
import com.qianying.bike.alipay.PayResult;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.base.JsonUtil;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.model.UsersInfo;
import com.qianying.bike.util.SPUtils;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

//我的押金-充值
public class RechargeActivity extends BaseActivity implements View.OnClickListener {

    private CustomTitlebar mTitlebar;
    private TextView recharge;
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initViews();
        type = getIntent().getStringExtra("type");
        if (type != null) {
            if (type.equals("1")) {
                item = getString(R.string.deposit);
            } else {
                item = getString(R.string.recharge);
            }
        }
    }

    String item = "";
    String type;
    String action = "";

    private void initViews() {


        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        recharge = (TextView) findViewById(R.id.txt_charge);
        UsersInfo ui = UsersInfo.get(UsersInfo.class);
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recharge(RechargeActivity.this, action, "1", "0.01");

            }
        });
        ((TextView) findViewById(R.id.txt_deposit_count)).setText(ui.getDeposit());
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setTitleText(getString(R.string.deposit_recharge));
        ((CheckBox) findViewById(R.id.checkbox_alipay)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ((CheckBox) findViewById(R.id.checkbox_wx)).setChecked(false);
                    ((CheckBox) findViewById(R.id.checkbox_wx)).setEnabled(true);
                    ((CheckBox) findViewById(R.id.checkbox_alipay)).setEnabled(false);
                    action = "aliPay";
                }

            }
        });
        ((CheckBox) findViewById(R.id.checkbox_wx)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ((CheckBox) findViewById(R.id.checkbox_alipay)).setEnabled(true);
                    ((CheckBox) findViewById(R.id.checkbox_alipay)).setChecked(false);
                    ((CheckBox) findViewById(R.id.checkbox_wx)).setEnabled(false);
                    action = "wxPay";
                }
            }
        });
        ((CheckBox) findViewById(R.id.checkbox_alipay)).performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_recharge:

                break;
        }
    }


    public static void recharge(final Activity ay, final String action, String type, String total) {
        String client_id;
        String state;
        String url;
        String access_token;
        final IWXAPI mWxApi = WXAPIFactory.createWXAPI(ay, "wx0889a773cbaf6df0", true);
        mWxApi.registerApp("wx0889a773cbaf6df0");
        final RegInfo regInfo = RegInfo.newInstance();
        TokenInfo tokenInfo = TokenInfo.newInstance();
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id", client_id);
            json.put("state", state);
            json.put("access_token", access_token);
            json.put("action", action);
            json.put("total", total);
            json.put("type", type);//1 押金 2 充值
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(final NetEntity entity) {
//                Toast.makeText(ay, entity.getData().toString(), Toast.LENGTH_SHORT).show();
                SPUtils.put(ay, MainActivity.TAG, "");


                if (action.equals("aliPay")) {
                    final String order = JsonUtil.findJsonLink("order", ((NetEntity) entity).getData().toString()).toString();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            PayTask payTask = new PayTask(ay);
                            final String result = payTask.pay(order, true);
                            final Map<String, String> map = new TreeMap<String, String>();
                            String[] rtparams = result.split("&");
                            for (String s : rtparams) {
                                String[] p = s.split("=");
                                map.put(p[0], p[1]);
                            }
                            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                                @Override
                                public boolean handleMessage(Message message) {
                                    PayResult payResult = new PayResult(map);

                                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                                    String resultInfo = payResult.getResult();

                                    String resultStatus = payResult.getResultStatus();

                                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                                    if (resultStatus.contains("9000")) {
                                        Toast.makeText(ay, R.string.pay_success,
                                                Toast.LENGTH_SHORT).show();
                                        ay.setResult(MainActivity.REQUEST_SCAN_AFTER_RECHARGE, new Intent().putExtra("callback", "true"));
                                        ay.finish();
                                    } else {
                                        // 判断resultStatus 为非“9000”则代表可能支付失败
                                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                                        if (resultStatus.contains("9000")) {
                                            Toast.makeText(ay, R.string.pay_success,
                                                    Toast.LENGTH_SHORT).show();
                                            ay.setResult(MainActivity.REQUEST_SCAN_AFTER_RECHARGE, new Intent().putExtra("callback", "true"));
                                            ay.finish();
                                        } else if (resultStatus.contains("8000")) {
                                            Toast.makeText(ay, R.string.pay_result_confirming,
                                                    Toast.LENGTH_SHORT).show();

                                        } else {
                                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                            Toast.makeText(ay, R.string.pay_cancelled,
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    return false;
                                }
                            }).sendEmptyMessage(0);

                        }
                    }.start();
                } else if (action.equals("wxPay")) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            if (mWxApi != null) {
                                PayReq req = new PayReq();

                                try {
                                    JSONObject jsonObject = new JSONObject(((NetEntity) entity).getData().toString());
                                    req.appId = jsonObject.getString("appid");
                                    req.partnerId = jsonObject.getString("partnerid");// 微信支付分配的商户号
                                    req.prepayId = jsonObject.getString("prepayid");// 预支付订单号，app服务器调用“统一下单”接口获取
                                    req.nonceStr = jsonObject.getString("noncestr");// 随机字符串，不长于32位，服务器小哥会给咱生成
                                    req.timeStamp = jsonObject.getString("timestamp");// 时间戳，app服务器小哥给出
                                    req.packageValue = jsonObject.getString("package");// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
                                    req.sign = jsonObject.getString("sign");// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个

                                    mWxApi.sendReq(req);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.start();

                }
            }
        });
    }
}
