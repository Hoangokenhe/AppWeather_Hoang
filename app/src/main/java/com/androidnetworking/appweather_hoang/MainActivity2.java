package com.androidnetworking.appweather_hoang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    CustomAdapter customAdapter;
    ArrayList<Weather> mArrayList;
    ImageView imgBack;
    TextView tvName;
    ListView lv;
String tenthanhpho="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Init();
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
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void Init() {
        imgBack=findViewById(R.id.imgBack);
        tvName=findViewById(R.id.tvName2);
        lv=findViewById(R.id.lv_5day);
        mArrayList=new ArrayList<Weather>();
        customAdapter =new CustomAdapter(MainActivity2.this,mArrayList);
        lv.setAdapter(customAdapter);
    }

    private void Get5DaysData(String data) {
        String url ="https://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml&appid=353fbff4cccb99fdc8c341cf78a75c96";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObjectCity=jsonObject.getJSONObject("city");
                            String name=jsonObjectCity.getString("name");
                            tvName.setText(name);

                            JSONArray jsonArrayList=jsonObject.getJSONArray("list");

                            for (int i=0;i<jsonArrayList.length();i++){
                                JSONObject jsonObjectList=jsonArrayList.getJSONObject(i);
                                String day=jsonObjectList.getString("dt");
                                long l = Long.valueOf(day);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);
                                JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                                String nhietdo = jsonObjectMain.getString("temp");

                                JSONObject jsonObjectTemp=jsonObjectList.getJSONObject("temp");
                                String max=jsonObjectTemp.getString("max");
                                String min=jsonObjectTemp.getString("min");
                                Double a = Double.valueOf(nhietdo);
                                String NhietDo = String.valueOf(a.intValue());

                                Double b = Double.valueOf(max);
                                String NhietDoMax = String.valueOf(b.intValue());

                                Double c = Double.valueOf(min);
                                String NhietDoMin = String.valueOf(c.intValue());
                                JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                                String status = jsonObjectWeather.getString("main");
                                String icon = jsonObjectWeather.getString("icon");

                                mArrayList.add(new Weather(Day,NhietDo,NhietDoMax,NhietDoMin,status,icon));

                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
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