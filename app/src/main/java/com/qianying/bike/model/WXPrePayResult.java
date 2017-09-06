package com.qianying.bike.model;

import java.io.Serializable;

/**
 */

public class WXPrePayResult implements Serializable{

    /**
     * appId : wxb3ee0b5ebf2ebcdd
     * noticeStr : e74cd8368c6647aeb727af75b2978fa2
     * packageStr : Sign=WXPay
     * partnerId : 1436375602
     * prepayId : wx2017051511301894250660cb0953363901
     * timestamp : 1494819018
     * sign : 2AACEB5DE765BBA5609EE7CC703F7604
     * orderId : 691
     */

    private String appId;
    private String noticeStr;
    private String packageStr;
    private String partnerId;
    private String prepayId;
    private String timestamp;
    private String sign;
    private String orderId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNoticeStr() {
        return noticeStr;
    }

    public void setNoticeStr(String noticeStr) {
        this.noticeStr = noticeStr;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
