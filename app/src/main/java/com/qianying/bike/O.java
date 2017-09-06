package com.qianying.bike;

import android.content.Context;
import android.content.SharedPreferences;

import com.qianying.bike.util.JsonUtil;

/**
 * Created by Aaron on 2017/7/27.
 */

public class O {
    private static final String DEFAULT = "DEFAULT";
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
    public static <T> T get(Class<T> clazz) {
        return get(clazz, DEFAULT);
    }

    public static <T> T get(Class<T> clazz, String key) {
        if (clazz == null) {
            return null;
        }
        SharedPreferences preferences = MyApp.getApplication().getSharedPreferences(clazz.getName(), Context.MODE_PRIVATE);
        if (!preferences.contains(key)) {
            return null;
        }
        String json = preferences.getString(key, "");
        try {
            return JsonUtil.jsonToEntity(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
