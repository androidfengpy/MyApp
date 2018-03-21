package com.fengpy.myapp.recyclerviewcheckbox;

/**
 * @ description:
 * @ time: 2018/1/14.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class Fruit {

    private String name;
    private double price;
    private String address;
    private String date;//生产日期
    private String endTime;//保质期

    public Fruit(String name, double price, String address, String date, String endTime) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.date = date;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
