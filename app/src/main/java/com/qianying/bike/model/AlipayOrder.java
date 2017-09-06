package com.qianying.bike.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.qianying.bike.MyApp;
import com.qianying.bike.util.JsonUtil;

/**
 * Created by Aaron on 2017/7/5.
 */

public class AlipayOrder {
    private String body;
    private String subject;
    private String out_trade_no;
    private String timeout_express;
    private String total_amount;
    private String product_code;
    private String createtime;

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getProduct_code() {
        return product_code;
    }

    private static AlipayOrder alipayOrder;
    public static AlipayOrder newInstance(){
        if(alipayOrder==null){
            alipayOrder = new AlipayOrder();
        }
        return alipayOrder;
    }

    private static final String DEFAULT = "DEFAULT";
    public static void setAlipayOrder(AlipayOrder alipayOrder) {
        AlipayOrder.alipayOrder = alipayOrder;
        AlipayOrder.save(alipayOrder, AlipayOrder.class);
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
