package com.qianying.bike.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用的工具类
 * Created by Vinsen on 17/5/17.
 */

public class CommUtil {

    public static void showInputSolft(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);

    }

    public static void hideInputSolft(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(int sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    /**
     * 从json数据获得相应字段
     */
    public static String getStr(String json, String name) {
        try {
            JSONObject object = new JSONObject(json);
            return object.optString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从json数据根据key获得相应class
     */
    public static <T> T getBean(String json, String name, Class<T> cls) {
        try {
            JSONObject object = new JSONObject(json);
            String str = object.optString(name);
            return getBean(str, cls);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从json数据获得相应class
     */
    public static <T> T getBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }

    /**
     * 从json数据获得相应class集合
     */
    public static <T> List<T> getBeanList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 从json数据key获得相应class集合
     */
    public static <T> List<T> getBeanList(String json, String name, Class<T> cls) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String targetStr = jsonObject.optString(name);
            return getBeanList(targetStr, cls);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
