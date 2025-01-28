package com.weather.SkyScout;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {
    TextView dayName;
    ImageView weatherLogo;
    TextView weatherStatus;
    TextView MaxTemp;
    TextView MinTemp;
    public Holder(@NonNull View itemView) {
        super(itemView);
        dayName = itemView.findViewById(R.id.day_name);
        weatherLogo = itemView.findViewById(R.id.weather_icon);
        weatherStatus = itemView.findViewById(R.id.weather_name);
        MaxTemp = itemView.findViewById(R.id.max_temperature);
        MinTemp = itemView.findViewById(R.id.min_temperature);
    }
}
