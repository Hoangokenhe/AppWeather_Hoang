package com.androidnetworking.appweather_hoang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.ref.ReferenceQueue;

import retrofit2.http.GET;

public class MainActivity2 extends AppCompatActivity {
String tenthanhpho="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent=getIntent();
        String city=intent.getStringExtra("name");
        Log.d("TAG", "onCreate: "+ city);
        if (city.equals("")){
            tenthanhpho="HaNoi";
            Get5DaysData(tenthanhpho);
        }else {
            tenthanhpho=city;
            Get5DaysData(tenthanhpho);
        }

    }

    private void Get5DaysData(String data) {
        String url ="https://api.openweathermap.org/data/2.5/forecast?q="+data+"&us&mode=xml&appid=353fbff4cccb99fdc8c341cf78a75c96";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("zzzzzzzzzz", "onResponse: "+response);
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}