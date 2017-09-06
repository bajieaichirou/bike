package com.qianying.bike.model;

import com.google.gson.JsonElement;
import com.qianying.bike.util.JsonUtil;
import com.qianying.bike.xutils3.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by Aaron on 2017/7/2.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class NetEntity {
    private String errno;
    private String status;
    private String expire_time;
    private String errmsg;
    private JsonElement data;


    public JsonElement getData() {
        return data;
    }

    public <T> T toObj(Class<T> clazz) {
        try {
            return JsonUtil.jsonToEntity(data.toString(), clazz);
        } catch (Exception e) {
            return null;
        }
    }



    public <T> List<T> toList(Class<T> clazz) {
        try {
            return JsonUtil.jsonToEntityList(data.getAsJsonArray(), clazz);
        } catch (Exception e) {
            return null;
        }
    }


    public String getErrno() {
        return errno;
    }

    public String getStatus() {
        return status;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public String getErrmsg() {
        return errmsg;
    }


}


