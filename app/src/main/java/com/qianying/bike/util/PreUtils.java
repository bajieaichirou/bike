package com.qianying.bike.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.qianying.bike.model.User;

import java.util.Set;

/**
 * 考虑需要缓存的东西不多
 * 所以考虑用sharedPreferences 不用数据库
 * Created by Vinsen on 17/5/22.
 */

public class PreUtils {

    public static final class PreKeys {
        public static final String always_url_one = "always_url_one";
        public static final String always_url_two = "always_url_two";
    }

    public static final String UID = "uid";
    public static final String NICKNAME = "nickname";
    public static final String AVATAR = "avatar";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String REGISTER_STATUS = "register_status";
    public static final String PHONE ="phone";
    public static final String IS_FIRST_LOGIN = "is_first_login";


    private static final String PreKey = PreUtils.class.getSimpleName();

    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName() + PreKey, Context.MODE_PRIVATE);
    }

    public static boolean putStr(String key, String value) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.edit().putString(key, value).commit();
    }

    public static boolean putInt(String key, int value) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.edit().putInt(key, value).commit();
    }

    public static boolean putLong(String key, long value) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.edit().putLong(key, value).commit();
    }

    public static boolean putFloat(String key, float value) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.edit().putFloat(key, value).commit();
    }

    public static boolean putBool(String key, boolean value) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean putStringSet(String key, Set<String> set) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.edit().putStringSet(key, set).commit();
    }


    public static String getStr(String key, String defaultValue) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.getString(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.getInt(key, defaultValue);
    }


    public static Long getLong(String key, long defaultValue) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static boolean getBool(String key, boolean defaultValue) {
        if (sharedPreferences == null) {
            throw new NullPointerException("have not init sharedPreferences");
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

    public static User getUser() {
        User user = new User();
        user.setAvatar(getStr(AVATAR, ""));
        user.setNickname(getStr(NICKNAME, ""));
        user.setUid(getStr(UID, ""));
        user.setAccessToken(getStr(ACCESS_TOKEN, ""));
        user.setUsername(getStr(USERNAME, ""));
        user.setPassword(getStr(PASSWORD, ""));
        user.setRefresh_token(getStr(REFRESH_TOKEN, ""));
        user.setRegisterStatus(getInt(REGISTER_STATUS, -1));
        user.setPhone(getStr(PHONE,""));
        return user;
    }

    public static void setUser(User user) {
        putStr(AVATAR, user.getAvatar());
        putStr(UID, user.getUid());
        putStr(PHONE,user.getPhone());
        putStr(NICKNAME, user.getNickname());
        putStr(ACCESS_TOKEN, user.getAccessToken());
        putStr(USERNAME, user.getUsername());
        putStr(PASSWORD, user.getPassword());
        putStr(REFRESH_TOKEN, user.getRefresh_token());
        putInt(REGISTER_STATUS, user.getRegisterStatus());
    }

    public static void loginOut() {
        putStr(AVATAR, "");
        putStr(PHONE,"");
        putStr(UID, "");
        putStr(NICKNAME, "");
        putStr(ACCESS_TOKEN, "");
        putStr(USERNAME, "");
        putStr(PASSWORD, "");
        putStr(REFRESH_TOKEN, "");
        putInt(REGISTER_STATUS, -1);
    }
}
