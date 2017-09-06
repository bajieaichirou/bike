package com.qianying.bike.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.qianying.bike.MyApp;
import com.qianying.bike.util.JsonUtil;

/**
 * Created by Aaron on 2017/7/8.
 */

public class UsersInfo {

/**
 * "mobile": "15900545092",
 "balance": "99.01",
 "integral": "1",
 "idno": "34012319990909090x",
 "truename": "leesin",
 "is_verified": "1"
 * */
    private String mobile;
    private String balance;
    private String integral;
    private String idno;
    private String truename;
    private String is_verified;
    private String is_paydeposit;

    public String getIs_paydeposit() {
        return is_paydeposit;
    }

    public void setIs_paydeposit(String is_paydeposit) {
        this.is_paydeposit = is_paydeposit;
    }

    private static UsersInfo info;
    public static UsersInfo newInstance(){
        if(null==info){
            info = new UsersInfo();
        }
        return info;
    }

    private static final String DEFAULT = "DEFAULT";
    public static void setUsersInfo(UsersInfo info) {
        UsersInfo.info = info;
        UsersInfo.save(info,UsersInfo.class);

    }
    public static <T> void save(T t, Class<T> clazz) {
        save(t, DEFAULT, clazz);
    }

    public static <T> void save(T t, String key, Class<T> clazz) {
        SharedPreferences preferences = MyApp.getApplication().getSharedPreferences(clazz.getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (t == null) {
            editor.remove(key);
        } else {
            editor.putString(key, JsonUtil.entityToJson(t));
        }
        editor.commit();
    }
    public static <T> T get( Class<T> clazz) {
        return get(DEFAULT,clazz);
    }
    public static <T> T get(String key, Class<T> clazz) {
        SharedPreferences preferences = MyApp.getApplication().getSharedPreferences(clazz.getName(), Context.MODE_PRIVATE);
        if (clazz != null) {
            String value = preferences.getString(key,"");
            return JsonUtil.jsonToEntity(value,clazz);

        }
        return null;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }
}
