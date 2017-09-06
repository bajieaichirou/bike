package com.qianying.bike.jpush;

import android.content.Context;

import com.qianying.bike.R;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * 极光相关的工具类
 * Created by Vinsen on 17/5/17.
 */

public class JpushUtils {

    /**
     * 极光推送的通知栏自定义样式
     *
     * @param context 上下文
     */
    public static void configJpushStyle(Context context) {
        CustomPushNotificationBuilder builder = new
                CustomPushNotificationBuilder(context,
                R.layout.customer_notitfication_layout,
                R.id.icon,
                R.id.title,
                R.id.content);
        // 指定定制的 Notification Layout
        builder.statusBarDrawable = R.drawable.map;
        // 指定最顶层状态栏小图标
        builder.layoutIconDrawable = R.drawable.map;
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(2, builder);
    }

}
