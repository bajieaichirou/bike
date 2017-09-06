package com.qianying.bike.model;

/**
 * Created by Aaron on 2017/7/8.
 * 信用
 */

public class XYInfo {
    /**
    *  "current": "11",
     "changed": "2",
     "direction": "0",
     "note": "100"
    * */
    private String current;
    private String changed;
    private String direction;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
