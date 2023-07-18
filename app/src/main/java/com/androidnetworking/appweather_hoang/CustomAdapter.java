package com.androidnetworking.appweather_hoang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Weather> arrayList;

    public CustomAdapter(Context context, ArrayList<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.item_lisview,null);
        Weather weather=arrayList.get(i);

        TextView tvDay=view.findViewById(R.id.tvDay);
        TextView tvTemp=view.findViewById(R.id.tvTemp);
        TextView tvStatus=view.findViewById(R.id.tvStatus);
        TextView tvMax=view.findViewById(R.id.tvMax);
        TextView tvMim=view.findViewById(R.id.tvMin);
        ImageView imgStatus=view.findViewById(R.id.imgStatus);


        tvDay.setText(weather.day);
        tvStatus.setText(weather.status);
        tvTemp.setText(weather.temp+"℃");
        tvMim.setText(weather.min+"℃");
        tvMax.setText(weather.max+"℃");
        Picasso.get().load("https://openweathermap.org/img/wn/" + weather.image + ".png").into(imgStatus);

        return view;
    }
}
