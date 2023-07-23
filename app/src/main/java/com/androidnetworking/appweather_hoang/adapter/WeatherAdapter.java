package com.androidnetworking.appweather_hoang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.appweather_hoang.Global;
import com.androidnetworking.appweather_hoang.R;
import com.androidnetworking.appweather_hoang.models.CurrentWeatherResponse;
import com.androidnetworking.appweather_hoang.models.ListData;
import com.androidnetworking.appweather_hoang.models.Weather;
import com.androidnetworking.appweather_hoang.models.WeatherForecastResponse;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private ArrayList<ListData> mListWather;
    private Context mContext;

    public WeatherAdapter(ArrayList<ListData> mListWather) {
        this.mListWather = mListWather;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lisview, parent, false);
        this.mContext = parent.getContext();
        ButterKnife.bind(this, view);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        ListData listData = mListWather.get(position);
        holder.tvDescription.setText(listData.getWeather().get(0).getDescription());
        holder.tvDay.setText(listData.getDtTxt());
        holder.tvTemp.setText(Global.convertK2C(listData.getMain().getTemp()));
        holder.tvMin.setText(Global.convertK2C(listData.getMain().getTempMax()));
        holder.tvMax.setText(Global.convertK2C(listData.getMain().getTempMax()));
        String weatherUrl = Global.getUrlIcon(listData.getWeather().get(0).getIcon());
        Glide.with(mContext).load(weatherUrl).into(holder.imgWeather);

    }

    @Override
    public int getItemCount() {
        if (mListWather != null) {
            return mListWather.size();
        }
        return 0;
    }

    public void updateData(ArrayList<ListData> mArrayList) {
        this.mListWather = mArrayList;
        notifyDataSetChanged();
    }


    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTemp)
        TextView tvTemp;
        @BindView(R.id.tvDay)
        TextView tvDay;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvMax)
        TextView tvMax;
        @BindView(R.id.tvMin)
        TextView tvMin;
        @BindView(R.id.imgWeather)
        ImageView imgWeather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
