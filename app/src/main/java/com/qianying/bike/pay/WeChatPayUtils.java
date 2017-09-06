package com.qianying.bike.pay;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONObject;

/**
 * 微信支付
 * Created by Vinsen on 17/5/18.
 */

public enum WeChatPayUtils {
    INTANCE;

    private IWXAPI api;
    private Activity activity;

    public void init(Activity act) {
        this.activity = act;

    }

    public void pay(String content) {
        try {
            if (!TextUtils.isEmpty(content)) {
                JSONObject json = new JSONObject(content);
                if (null != json && !json.has("retcode")) {
                    PayReq req = new PayReq();
                    req.appId = json.getString("appid");
                    req.partnerId = json.getString("partnerid");
                    req.prepayId = json.getString("prepayid");
                    req.nonceStr = json.getString("noncestr");
                    req.timeStamp = json.getString("timestamp");
                    req.packageValue = json.getString("package");
                    req.sign = json.getString("sign");
                    Toast.makeText(activity, "Õý³£µ÷ÆðÖ§¸¶", Toast.LENGTH_SHORT).show();
                    // ÔÚÖ§¸¶Ö®Ç°£¬Èç¹ûÓ¦ÓÃÃ»ÓÐ×¢²áµ½Î¢ÐÅ£¬Ó¦¸ÃÏÈµ÷ÓÃIWXMsg.registerApp½«Ó¦ÓÃ×¢²áµ½Î¢ÐÅ
                    api.sendReq(req);
                } else {
                    Toast.makeText(activity, "·µ»Ø´íÎó" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, "·þÎñÆ÷ÇëÇó´íÎó", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(activity, "Òì³££º" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
