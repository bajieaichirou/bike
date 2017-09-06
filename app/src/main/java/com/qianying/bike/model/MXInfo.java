package com.qianying.bike.model;

/**
 * Created by Aaron on 2017/7/9.
 */

public class MXInfo {
    /**
     *      "current": "500.00",
     "changed": "1.00",
     "direction": "0",
     "payment_id": "100001",
     "paidtype": "0",
     "note": "test1"
     * */
    private String current;
    private String changed;
    private String payment_id;
    private String paidtype;
    private String note;


    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPaidtype() {
        return paidtype;
    }

    public void setPaidtype(String paidtype) {
        this.paidtype = paidtype;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
