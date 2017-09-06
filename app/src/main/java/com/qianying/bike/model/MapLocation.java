package com.qianying.bike.model;

import java.io.Serializable;

/**
 */
public class MapLocation implements Serializable {


    /**
     * ）（getProvince()），
     * 城市名称（getCity()），
     * 城市编码（getCityCode()），
     * 区（县）名称（getDistrict()），
     * 区域编码（getAdCode()）
     * ，街道和门牌信息（getStreet()），
     * 详细地址（getAddress()）
     * 描述信息（getExtras()）。
     */
    private double latitude;
    private double longitude;
    private String province;//获取省的名称
    private String city;//获取城市名称
    private String cityCode;//获取城市编码
    private String address;//获取地址
    private String street;//获取街道名称
    private String country;//获取国家名称
    private String district;//获取区的名称
    private String poiName; //获取兴趣点名称
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {


        this.latitude = latitude;
    }

    public double getLongitude() {


        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        if (province == null) {
            return "";
        }
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        if (city == null) {
            return "";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        if (cityCode == null) {
            return "";
        }
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddress() {
        if (address == null) {
            return "";
        }
        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getStreet() {
        if (street == null) {
            return "";
        }
        return street;
    }

    public void setStreet(String street) {

        this.street = street;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getCountry() {
        if (country == null) {
            return "";
        }

        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }
}
