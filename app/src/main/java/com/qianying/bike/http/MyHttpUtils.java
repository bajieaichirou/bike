package com.qianying.bike.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qianying.bike.util.C;
import com.qianying.bike.util.UserHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 考虑项目小 ，只用到json数据所以只考虑返回String的情况
 * Created by VinsenZhang on 2017/5/23.
 */

public class MyHttpUtils {

    private static RequestQueue requestQueue;

    public static void init(Context ctx) {
        // 是否用https  目前没有ssl证书
        requestQueue = Volley.newRequestQueue(ctx, true);
    }

    public static void postWithOutHeader(String url, final Map<String, String> params, final HttpResponse httpResponse) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpResponse != null) {
                    httpResponse.onGetData(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponse != null) {
                    httpResponse.onError(error.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public static void post(String url, final Map<String, String> params, final Map<String, String> headers, final HttpResponse httpResponse) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpResponse != null) {
                    httpResponse.onGetData(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponse != null) {
                    httpResponse.onError(error.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }


    public static void postWithHeader(String url, final Map<String, String> params, final HttpResponse httpResponse) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpResponse != null) {
                    httpResponse.onGetData(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponse != null) {
                    httpResponse.onError(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", UserHelper.getInstance().getAccessToken());
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    public static void getWithOutHeader(String url, Map<String, String> params, final HttpResponse httpResponse) {
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                url += "?" + key + "=" + params.get(key);
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpResponse != null) {
                    httpResponse.onGetData(response);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponse != null) {
                    httpResponse.onError(error.getMessage());
                }
            }
        });

        requestQueue.add(stringRequest);
    }

    public static void getWithHeader(String url, Map<String, String> params, final HttpResponse httpResponse) {
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                url += "?" + key + "=" + params.get(key);
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpResponse != null) {
                    httpResponse.onGetData(response);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponse != null) {
                    httpResponse.onError(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", UserHelper.getInstance().getAccessToken());
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }


    public static void put(String url, Map<String, String> params, final HttpResponse httpResponse) {
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                url += "?" + key + "=" + params.get(key);
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpResponse != null) {
                    httpResponse.onGetData(response);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponse != null) {
                    httpResponse.onError(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", UserHelper.getInstance().getAccessToken());
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }


    /**
     * @param grant_type    password/refresh_token/client_credentials
     *                      //     * @param client_id android
     *                      //     * @param client_secret 789789
     *                      //     * @param scope all
     * @param username      只有grant_type为password时候才有用
     * @param password      只有grant_type为password时候才有用
     * @param refresh_token 只有grant_type为refresh_token时候才有用
     * @param httpResponse
     */
//    errorCode
//    0 成功
//    1 错误
//    2 access token不合法
//    3 refresh token 不合法
//    4 用户未找到
    public static void getAccessToken(String grant_type, String username,
                                      String password, String refresh_token, HttpResponse httpResponse) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", grant_type);
        params.put("client_id", "android");
        params.put("client_secret", "789789");
        params.put("scope", "all");
        params.put("username", username);
        params.put("password", password);
        params.put("refresh_token", refresh_token);
        postWithOutHeader(C.Urls.access_token, params, httpResponse);
    }

    public static void getUserInfo(HttpResponse httpResponse) {
        getWithHeader(C.Urls.current_user, null, httpResponse);
    }

    public static void userBike(double lat,double lng,String bikeId,HttpResponse httpResponse){
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", UserHelper.getInstance().getAccessToken());
        Map<String,String> params=new HashMap<>();
        params.put("lat",lat+"");
        params.put("lng",lng+"");
        post(C.Urls.getUseBikeUrl(bikeId),params,headers,httpResponse);
    }
}
