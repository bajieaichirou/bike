# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/qianyingmac/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#极光推送混淆
#-keep class cn.jpush.** { *; }
#
#-dontoptimize
#-dontpreverify
#
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#
#-dontwarn cn.jiguang.**
#-keep class cn.jiguang.** { *; }
#
##fresco混淆
## Keep our interfaces so they can be used by other ProGuard rules.
## See http://sourceforge.net/p/proguard/bugs/466/
#-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
#
## Do not strip any method/class that is annotated with @DoNotStrip
#-keep @com.facebook.common.internal.DoNotStrip class *
#-keepclassmembers class * {
#    @com.facebook.common.internal.DoNotStrip *;
#}
#
## Keep native methods
#-keepclassmembers class * {
#    native <methods>;
#}
#
#-dontwarn okio.**
#-dontwarn com.squareup.okhttp.**
#-dontwarn okhttp3.**
#-dontwarn javax.annotation.**
#-dontwarn com.android.volley.toolbox.**
#-dontwarn com.facebook.infer.**
#
#
##alipay支付
#-libraryjars libs/alipaySingle-20170510.jar
#
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#
#
##微信支付混淆
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
