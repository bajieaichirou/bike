package com.qianying.bike.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.qianying.bike.MyApp;

/**
 * Created by Aaron on 2017/7/8.
 */

public class SPUtil {
    private static final String DEFAULT = "DEFAULT";
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
}
