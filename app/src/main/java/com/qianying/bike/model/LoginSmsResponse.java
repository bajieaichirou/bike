package com.qianying.bike.model;

import java.io.Serializable;

/**
 * Created by fotoplace on 2017/6/6.
 */

public class LoginSmsResponse implements Serializable {


    /**
     * errno : 0
     * errmsg :
     * data :"873321"}
     */

    private int errno;
    private String errmsg;
    private String data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
