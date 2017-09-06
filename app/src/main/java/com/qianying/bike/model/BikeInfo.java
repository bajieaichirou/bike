package com.qianying.bike.model;

/**
 * Created by zhangshengwen on 09/06/2017.
 * id *
 * lat *
 * lng *
 */

public class BikeInfo {

    private String id;
    private String lat;
    private String lng;
    private String action;
    private String distance;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
