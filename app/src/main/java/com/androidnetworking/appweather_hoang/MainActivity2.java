package com.androidnetworking.appweather_hoang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.appweather_hoang.adapter.WeatherAdapter;
import com.androidnetworking.appweather_hoang.api.RetrofitClient;
import com.androidnetworking.appweather_hoang.api.WeatherServices;
import com.androidnetworking.appweather_hoang.models.ListData;
import com.androidnetworking.appweather_hoang.models.WeatherForecastResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    WeatherAdapter weatherAdapter;
    ArrayList<ListData> mArrayList;
    ImageView imgBack;
    TextView tvName;
    RecyclerView rcv;
    String tenthanhpho = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Init();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);

        Intent intent=getIntent();
        String city=intent.getStringExtra("name");
        Log.d("TAG", "onCreate: "+ city);
        if (city.equals("")){
            tenthanhpho="HaNoi";
            get5DaysData(tenthanhpho);
        }else {
            tenthanhpho=city;
            get5DaysData(tenthanhpho);
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//
//    }
    }

    private void Init() {
        imgBack = findViewById(R.id.imgBack);
        tvName = findViewById(R.id.tvName2);
        rcv = findViewById(R.id.lv_5day);
        mArrayList = new ArrayList<>();
         weatherAdapter = new WeatherAdapter(mArrayList);
        rcv.setAdapter(weatherAdapter);

    }

    private void get5DaysData(String cityName) {
        WeatherServices weatherServices = RetrofitClient.getService(Global.BASE_URL, WeatherServices.class);
        weatherServices.getWeatherForecast(cityName, Global.API_KEY).enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                Log.d("TAG", "onResponse: " + response.body().toString());
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        WeatherForecastResponse weatherForecastResponse = response.body();
                        mArrayList = (ArrayList<ListData>) weatherForecastResponse.getList();
                        weatherAdapter.updateData(mArrayList);

                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
//        String url ="https://api.openweathermap.org/data/2.5/forecast?q=LonDon&appid=353fbff4cccb99fdc8c341cf78a75c96";
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson=new Gson();
//                        WeatherForecastResponse weatherForecastResponse=gson.fromJson(response,WeatherForecastResponse.class);
//                        Log.d("TAG", "onResponse: "+weatherForecastResponse.getCity().getName());
//                        weatherForecastResponse.getCity().getName();
//                        tvName.setText("hanui");
//                        weatherForecastResponse.getMain().getTemp();
//                        Log.d("TAG", "onResponse: "+weatherForecastResponse.getMain().getTemp());
//                    }
//                },
//                new Response.ErrorListener(){
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("TAG", "onErrorResponse: "+error.getMessage());
//                    }
//                });
//        requestQueue.add(stringRequest);
    }
}