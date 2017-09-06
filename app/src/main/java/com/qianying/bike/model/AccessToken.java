package com.qianying.bike.model;

import java.io.Serializable;

/**
 * Created by fotoplace on 2017/6/6.
 */

public class AccessToken implements Serializable{


    /**
     * token_type : Bearer
     * expires_in : 3600
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQyZDQ3YThiMjljZmRjOTg3OGRjYjg2MTcyOTBmM2IwNjZhNjMxZjNmMTNlODA2NzBlYTBjOWYwZDUzOTI5MTZiZjYzOWViNDFjODBkN2M1In0.eyJhdWQiOiJhbmRyb2lkIiwianRpIjoiZDJkNDdhOGIyOWNmZGM5ODc4ZGNiODYxNzI5MGYzYjA2NmE2MzFmM2YxM2U4MDY3MGVhMGM5ZjBkNTM5MjkxNmJmNjM5ZWI0MWM4MGQ3YzUiLCJpYXQiOjE0OTY3MzQyMDksIm5iZiI6MTQ5NjczNDIwOSwiZXhwIjoxNDk2NzM3ODA5LCJzdWIiOiIiLCJzY29wZXMiOlsiYWxsIl19.XAp7jOPvTTKUD39hLiUKBj4QaJkV0oh0eFNycc842NkKXxgzI2jeL2wnHkxPxRKutI2OTh_Da6NMDrvNx2u-ftYHQaWdpIQe5yZB4eSRTwP5eBUWeyfr-v1ewrzmFw7Qm9m7tfoGb6lWpUyaCJp5gg04x4mT9f2UTP8XdI5T8VQ
     */

    private String token_type;
    private int expires_in;
    private String access_token;
    /**
     * refresh_token : UCfqoYwATUO3iQ5ktIulkwU+KqpYkScpeQVAysGYCzUDoh8W5vNJpkS/9ndltcEKjZBHDSnDiXAht7paVdt8pxGFP7Ipx+dwEiR93B/ylwqIMmuu8H+GWBd7g6vBoxwyA7rSLAlzsYPNRrMcCwW3II8huQipI9k29nTimpp56idh2G4ggLu+h+XnLvJgrGyDTHkSS6Jben0Kr7csi5dQ4Rlfg9T/5O7S8dj5XPgg5gME3jBK8KdCJ/oMsN7BRjvMs1/dHcTuXzJdphBi7plOV/YZDNKIVThTDauuCO1Ysz4auQXDhB5uCpX2cAfkgIjG9u4B418Wlyon7fIDJwYcqKmk9LfRYN6LBNogPqJOgBNZssambYnzC6f4VIS+vWA7RCWOEFndAyaMHOtPLIGc+FQ991n1Oni7e75qRuJGEUifeTcvHD0Py/1eUF1+KwzrZII5D+jxmjKfeuQi+cG6jTZrO6qMI7TC/TPMrSiM7fiOL08B/6ESKLH/EpfidJDt
     */

    private String refresh_token;


    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
