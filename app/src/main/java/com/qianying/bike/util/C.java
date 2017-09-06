package com.qianying.bike.util;

/**
 * 所有常量统一这边定义
 * Created by fotoplace on 2017/5/30.
 */

public interface C {
    public static final String WXAPPID = "wxappid";


    /**
     * url
     * 所有接口
     */
    public final class Urls {

        public static final String domain = "https://ying.baibaobike.com";
        public static final String access_token = domain + "/oauth2/access_token";
        public static final String send_login_code = domain + "/v1/sms/send_login_code";
        public static final String current_user = domain + "/v1/users/current";
        public static final String bikes = domain + "/v1/bikes";

        //使用指定车辆
        public static String getCheckOutUrl(String bikeId) {
            return bikes + bikeId + "checkout";
        }

        //结账
        public static String getUseBikeUrl(String bikeId) {
            return bikes +"/"+ bikeId + "/use";
        }
    }


    public static final class BundleKeys{
        public static final String BikeId = "bike_id";
    }



    /**
     * 0 成功
     * 1 错误
     * 2 access token不合法
     * 3 refresh token 不合法
     * 4 用户未找到
     * 5 余额不足
     */
    public final class HttpError {
        public static final int success = 0;
        public static final int error = 1;
        public static final int access_token_err = 2;
        public static final int refresh_token_err = 3;
        public static final int user_not_find = 4;
        public static final int account_money_not_enough = 5;

    }

}
