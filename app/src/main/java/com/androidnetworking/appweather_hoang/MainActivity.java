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

import com.androidnetworking.appweather_hoang.api.RetrofitClient;
import com.androidnetworking.appweather_hoang.api.WeatherServices;
import com.androidnetworking.appweather_hoang.models.CurrentWeatherResponse;
import com.androidnetworking.appweather_hoang.utils.DeviceUtils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tvDay)
    TextView tvDay;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.tvCurrentTemp)
    TextView tvCurrentTemp;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.tvSeaLevel)
    TextView tvSeaLevel;
    @BindView(R.id.tvWindSpeed)
    TextView tvWindSpeed;
    @BindView(R.id.tvTempMax)
    TextView tvTempMax;
    @BindView(R.id.tvPressure)
    TextView tvPressure;
    @BindView(R.id.imgWeather)
    ImageView imgWeather;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.btnChangeAc)
    Button btnChangeAc;

    WeatherServices mWeatherServices;

    public static final String TAG = "zzzzzzzzzzzzz";
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mWeatherServices = RetrofitClient.getService(Global.BASE_URL, WeatherServices.class);
        mWeatherServices
                .getWeatherByCityNameModel("saigon", Global.API_KEY)
                .enqueue(new Callback<CurrentWeatherResponse>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                CurrentWeatherResponse model = response.body();
                                bindCurrentWeather(model);


                            } else {
                                Log.d(TAG, "onResponse: " + response.code() + " | " + response.message());
                            }
                        } else {
                            Log.d(TAG, "onResponse: unsuccessful");
                        }
                    }


                    @Override
                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });

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
        String city = edtSearch.getText().toString();
        if (city.equals("")) {
            City = "HaNoi";

        } else {
            City = city;


        }
    }

    private void initView() {
        ButterKnife.bind(this);
    }


    private void bindCurrentWeather(CurrentWeatherResponse model) {
        tvName.setText(model.getName());
       // tvCountry.setText(model.getCity().getCountry());
        tvDescription.setText(model.getWeather().get(0).getDescription());
        tvTemp.setText(Global.convertK2C(model.getMain().getTemp()));
        tvCurrentTemp.setText(Global.convertK2C(model.getMain().getFeelsLike()));
        tvHumidity.setText(model.getMain().getHumidity() + " %");
        tvSeaLevel.setText(model.getMain().getSeaLevel() + "");
        tvWindSpeed.setText(model.getWind().getSpeed() + " m/s");
        tvTempMax.setText(Global.convertK2C(model.getMain().getTempMax()));
        tvPressure.setText(model.getMain().getPressure() + " hPa");


            Glide.with(getApplicationContext())
                    .load(Global.getUrlIcon(model.getWeather().get(0).getIcon()))
                    .into(imgWeather);

//        RequestQueue referenceQueue = Volley.newRequestQueue(MainActivity.this);
//        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&appid=353fbff4cccb99fdc8c341cf78a75c96&units=metric";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String day = jsonObject.getString("dt");
//                    String name = jsonObject.getString("name");
//                    tvName.setText(name);
//
//                    long l = Long.valueOf(day);
//                    Date date = new Date(l * 1000L);
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
//                    String Day = simpleDateFormat.format(date);
//
//                    tvDay.setText(Day);
//
//                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
//                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
//
//                    String status = jsonObjectWeather.getString("main");
//                    String icon = jsonObjectWeather.getString("icon");
//
//                    Picasso.get().load("https://openweathermap.org/img/wn/" + icon + ".png").into(imgWeather);
//                    tvStatus.setText(status);
//
//                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
//                    String nhietdo = jsonObjectMain.getString("temp");
//                    String doam = jsonObjectMain.getString("humidity");
//
//                    Double a = Double.valueOf(nhietdo);
//                    String NhietDo = String.valueOf(a.intValue());
//
//                    tvTemp.setText(NhietDo + "℃");
//                    tvHumidity.setText(doam + "%");
//
//                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
//                    String gio = jsonObjectWind.getString("speed");
//                    tvWind.setText(gio + "m/s");
//
//                    JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
//                    String may = jsonObjectClouds.getString("all");
//                    tvClound.setText(may + "%");
//
//                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
//                    String country = jsonObjectSys.getString("country");
//                    tvCountry.setText("Tên quốc gia: " + country);
//
//                } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        referenceQueue.add(stringRequest);
    }


}