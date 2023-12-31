package com.androidnetworking.appweather_hoang;

public class Global {

    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "353fbff4cccb99fdc8c341cf78a75c96";
    private static final String URL_ICON = "https://openweathermap.org/img/wn/";
    private static final String PICTURE_FORMAT ="@2x.png";

    public static final String CELSIUS = "\u2103";
    public static final String FAHRENHEIT  = "\u2109";


    public static String convertK2C(float kelvin){
        return Math.round(kelvin- 272.15)+CELSIUS;
    }

    public static String getUrlIcon(String iconId){
        return URL_ICON+iconId+PICTURE_FORMAT;
    }

}
