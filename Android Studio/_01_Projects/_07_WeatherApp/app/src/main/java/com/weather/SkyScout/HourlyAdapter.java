package com.weather.SkyScout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.holder> {

    private List<HourlyModel> weatherList;
    private Context context;

    public HourlyAdapter(Context context, List<HourlyModel> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item1, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        // Your existing binding logic
        HourlyModel weather = weatherList.get(position);
        holder.temperatureValue.setText(weather.getTemperature());
        holder.weatherIcon.setImageResource(weather.getIconResId());
        holder.weatherName.setText(weather.getName());

        // Apply animations
//        AnimationUtils.fadeOut(context, holder.itemView);
        AnimationUtils.fadeIn(context, holder.itemView);
        // Optionally, apply fadeOut animation if needed
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    class holder extends RecyclerView.ViewHolder {

        TextView temperatureValue;
        ImageView weatherIcon;
        TextView weatherName;

        public holder(@NonNull View itemView) {
            super(itemView);
            temperatureValue = itemView.findViewById(R.id.temperature_value);
            weatherIcon = itemView.findViewById(R.id.weather_icon);
            weatherName = itemView.findViewById(R.id.time);
        }
    }
}
