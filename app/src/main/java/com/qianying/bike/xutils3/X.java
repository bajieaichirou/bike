package com.qianying.bike.xutils3;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.SecureRandom;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 *   @describe 网络请求
 */
public class X {
    /**
     * 发送get请求
     * @param <T>
     *
     */
    public static <T> Callback.Cancelable Get(String url, Map<String,String> map, Callback.ProgressCallback<T> callback){
        RequestParams params=new RequestParams(url);
        if(null!=map){
            for(Map.Entry<String, String> entry : map.entrySet()){
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 发送post请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(String url, Map<String,Object> map, Callback.ProgressCallback<T> callback){
        RequestParams params=new RequestParams(url);
//        params.setHeader("User-Agent", String.format("%s/%s (Linux; Android %s; %s Build/%s)", "pinxiango", MyApp.getVersion(), Build.VERSION.RELEASE, Build.MANUFACTURER, Build.ID));
        params.addHeader("application/x-www-form-urlencoded","charset=UTF-8");
        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 发送post请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(String url, Map<String,Object> map, Callback.CacheCallback<T> callback){
        RequestParams params=new RequestParams(url);
        params.setCacheMaxAge(1000 * 60);//缓存60秒

        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }


    /**
     * 发送post请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(String url, JSONObject json, Callback.ProgressCallback<T> callback){
        RequestParams params=new RequestParams(url);
        params.addHeader("application/json","charset=UTF-8");
        params.addBodyParameter("",json.toString());
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 发送post请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(String url, JSONObject json,boolean ssl, Callback.ProgressCallback<T> callback){
        RequestParams params=new RequestParams(url);
        params.addHeader("application/json","charset=UTF-8");
        params.addBodyParameter("",json.toString());
        if(ssl){

        }
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    private static void setSSL(RequestParams client) throws Exception{
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }}, new SecureRandom());
        client.setSslSocketFactory(sc.getSocketFactory());
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }
    /**
     * 上传文件
     * @param <T>
     */
    public static <T> Callback.Cancelable UpLoadFile(String url, Map<String,Object> map, Callback.ProgressCallback<T> callback){
        RequestParams params=new RequestParams(url);
        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 下载文件
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.ProgressCallback<T> callback){
        RequestParams params=new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }


}
