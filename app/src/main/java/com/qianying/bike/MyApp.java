package com.qianying.bike;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.qianying.bike.http.MyHttpUtils;
import com.qianying.bike.jpush.JpushUtils;
import com.qianying.bike.util.LocalDisplay;
import com.qianying.bike.util.PreUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.xutils.x;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * SHA1 :  48:7D:32:0A:16:E1:8B:0A:28:D8:31:C4:15:C1:4F:F4:5E:A1:5D:E4
 * Created by Vinsen on 17/5/16.
 */

public class MyApp extends Application {

    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Fresco.initialize(this);
        ZXingLibrary.initDisplayOpinion(this);

        //极光sdk初始化
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        JpushUtils.configJpushStyle(this);
        JShareInterface.init(this);
        JShareInterface.setDebugMode(true);
        LocalDisplay.init(this);

        PreUtils.init(this);
        x.Ext.init(this); //初始化xUtils
        x.Ext.setDebug(true);//设置是否输出debug


        MyHttpUtils.init(this);

    }


    public static MyApp getInstance() {
        if (app == null)
            app = new MyApp();
        return app;
    }
    public static MyApp getApplication() {
        return app;
    }

    @SuppressLint("WrongConstant")
    public void Toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public static String getVersion() {
        PackageManager packageManager = getApplication().getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(getApplication().getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }
}
