package com.qianying.bike.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.Locale;

public class LocalDisplay {

    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;
    private static boolean sInitialed;

    private static Locale locale;

    public static int Locale_ZH=0;
    public static int Locale_EN=1;
    public static int Locale_ZH_TW=2;
    public static int Locale_ZH_HK=3;

    public static int Locale_Language=0;

    public static void init(Context context) {
        if (sInitialed || context == null) {
            return;
        }
        sInitialed = true;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);
        locale= context.getResources().getConfiguration().locale;
        Locale_Language=getLanguage();
    }


    public static int dp2px(float dp) {

        final float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5f);
    }

    public static int getLanguage() {
        int lay=0;
        if(locale!=null){
            String language = locale.getLanguage();
            if(language.endsWith("zh")){
                String country = locale.getCountry();
                if(country.endsWith("TW")){
                    lay= Locale_ZH_TW;
                }else if(country.endsWith("HK")){
                    lay= Locale_ZH_HK;
                }else {
                    lay= Locale_ZH;
                }
            }else {
                lay= Locale_EN;
            }
        }
        return lay;
    }

//    public static int dp2px(float dp) {
//        final float scale = SCREEN_DENSITY;
//        return (int) (dp * scale + 0.5f);
//    }

    public static int designedDP2px(float designedDp) {
        if (SCREEN_WIDTH_DP != 320) {
            designedDp = designedDp * SCREEN_WIDTH_DP / 320f;
        }
        return dp2px(designedDp);
    }

    public static void setPadding(final View view, float left, float top, float right, float bottom) {
        view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right), dp2px(bottom));
    }
    public static int sp2px(float spValue) {
        return (int) (spValue * SCREEN_DENSITY + 0.5f);
    }
}
