package com.qianying.bike.http;


import com.qianying.bike.util.UserHelper;

/**
 * Created by VinsenZhang on 2017/5/23.
 */

public abstract class HttpResponse {
    public void onGetData(String data) {
    }

    public void onError(String error) {

    }

    public void onErrorCode(int errorCode) {
        if (errorCode == 2) {
            UserHelper.getInstance().setAccessToken(UserHelper.getInstance().getUser().getRefresh_token());
        } else if (errorCode == 3) {
            UserHelper.getInstance().loginOut();
        }
    }
}
