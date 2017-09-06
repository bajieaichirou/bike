package com.qianying.bike.util;

import android.app.Activity;

import com.qianying.bike.model.WXPrePayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 */

public class WXPayUtil {
    private Activity activity;

    public WXPayUtil(Activity activity) {
        this.activity = activity;
    }


    public void payV2(WXPrePayResult prePayResult) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(activity, C.WXAPPID);

        PayReq payReq = new PayReq();
        payReq.appId = prePayResult.getAppId();
        payReq.partnerId = prePayResult.getPartnerId();
        payReq.prepayId = prePayResult.getPrepayId();
        payReq.nonceStr = prePayResult.getNoticeStr();
        payReq.timeStamp = prePayResult.getTimestamp();
        payReq.packageValue = prePayResult.getPackageStr();
        payReq.sign = prePayResult.getSign();
        payReq.extData = "app data"; // optional
        iwxapi.sendReq(payReq);

    }
}
