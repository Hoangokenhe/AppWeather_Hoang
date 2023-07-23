
package com.androidnetworking.appweather_hoang.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WeatherForecastResponse {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Integer message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private List<ListData> list;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherForecastResponse() {
    }

    /**
     * 
     * @param city
     * @param cnt
     * @param cod
     * @param message
     * @param list
     */
    public WeatherForecastResponse(String cod,List<Weather> weather, Integer message, Integer cnt, List<ListData> list, City city, Main main) {
        super();
        this.cod = cod;
        this.weather = weather;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
        this.main=main;
    }

    public String getCod() {
        return cod;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<ListData> getList() {
        return list;
    }

    public void setList(List<ListData> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "WeatherForecastResponse{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                ", city=" + city +
                ", main=" + main +
                ", weather=" + weather +
                '}';
    }
}

