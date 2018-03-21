package com.fengpy.myapp.gaodedemo;

import java.io.Serializable;

/**
 * @ description:
 * @ time: 2017/8/29.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class DataInfo implements Serializable {


    private String le;
    private String time;
    private String lat;
    private String lng;

    public DataInfo() {
    }

    public DataInfo(String le, String time, String lat, String lng) {
        this.le = le;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLe() {
        return le;
    }

    public void setLe(String le) {
        this.le = le;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
