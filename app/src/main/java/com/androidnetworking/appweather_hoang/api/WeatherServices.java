package com.androidnetworking.appweather_hoang.api;

import com.androidnetworking.appweather_hoang.models.CurrentWeatherResponse;
import com.androidnetworking.appweather_hoang.models.WeatherForecastResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherServices {

    @GET("weather?")
    Call<JsonObject> getWeatherByCityName(@Query("q") String cityName,
                                          @Query("appid") String api_key);

    @GET("weather?")
        Call<CurrentWeatherResponse> getWeatherByCityNameModel(@Query("q") String cityName,
                                                           @Query("appid") String api_key);

    @GET("forecast?")
    Call<WeatherForecastResponse> getWeatherForecast(@Query("q") String cityName,
                                                     @Query("appid") String api_key);
}
