package com.androidnetworking.appweather_hoang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.appweather_hoang.utils.DeviceUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tvDay)
    TextView tvDay;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvClound)
    TextView tvClound;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.tvWind)
    TextView tvWind;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.imgWeather)
    ImageView imgWeather;
    @BindView(R.id.btnChangeAc)
    Button btnChangeAc;

    public static final String TAG = "zzzzzzzzzzzzz";
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        GetCurrentWeatherData("HaNoi");

//        imgSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String city=edtSearch.getText().toString();
//                if (city.equals("")){
//                    City="HaNoi";
//                    GetCurrentWeatherData(City);
//                }else {
//                    City=city;
//                    GetCurrentWeatherData(city);
//                }
//
//            }
//        });
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String searchKeyWords = edtSearch.getText().toString();
                if (!TextUtils.isEmpty(searchKeyWords)) {
                    searchCityByName(searchKeyWords);
                    edtSearch.clearFocus();//Xoá focus để tự động ẩn bàn phím
                }
                return true;
            }
            return false;
        });

        // Kiểm tra xem edt search có đang được focus không nếu không thì ẩn bàn phím
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DeviceUtils.hideKeyboard(v, MainActivity.this);
                }
            }
        });

        btnChangeAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
    }

    private void searchCityByName(String searchKeyWords) {
        Log.d(TAG, "searchCityByName: " + searchKeyWords);
    }

    public void GetCurrentWeatherData(String data) {
        RequestQueue referenceQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&appid=353fbff4cccb99fdc8c341cf78a75c96";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String name = jsonObject.getString("name");
                    tvName.setText(name);

                    long l = Long.valueOf(day);
                    Date date = new Date(l * 1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                    String Day = simpleDateFormat.format(date);

                    tvDay.setText(Day);

                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                    String status = jsonObjectWeather.getString("main");
                    String icon = jsonObjectWeather.getString("icon");

                    Picasso.get().load("https://openweathermap.org/img/wn/" + icon + ".png").into(imgWeather);
                    tvStatus.setText(status);

                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String nhietdo = jsonObjectMain.getString("temp");
                    String doam = jsonObjectMain.getString("humidity");

                    Double a = Double.valueOf(nhietdo);
                    String NhietDo = String.valueOf(a.intValue());

                    tvTemp.setText(NhietDo + "℃");
                    tvHumidity.setText(doam + "%");

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    tvWind.setText(gio + "m/s");

                    JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectClouds.getString("all");
                    tvClound.setText(may + "%");

                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String country = jsonObjectSys.getString("country");
                    tvCountry.setText("Tên quốc gia: " + country);

                } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        referenceQueue.add(stringRequest);
    }

    private void initView() {
        ButterKnife.bind(this);
    }
}