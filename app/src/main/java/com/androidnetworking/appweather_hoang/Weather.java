package com.androidnetworking.appweather_hoang;

public class Weather {
    String day;
    String status;
    String min;
    String max;
    String temp;
    String image;

    public Weather(String day, String status, String min, String max, String temp, String image) {
        this.day = day;
        this.status = status;
        this.min = min;
        this.max = max;
        this.temp = temp;
        this.image = image;
    }
}
