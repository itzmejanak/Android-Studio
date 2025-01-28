package com.weather.SkyScout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SevenDaysAdapter extends RecyclerView.Adapter<Holder> {
    private ArrayList<SevenDaysDataModel> datas;
    private Context context;

    public SevenDaysAdapter(Context context, ArrayList<SevenDaysDataModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_next, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        SevenDaysDataModel weather = datas.get(position);
        holder.dayName.setText(weather.getDayName());
        holder.weatherLogo.setImageResource(weather.getWeatherLogo());
        holder.weatherStatus.setText(weather.getWeatherStatus());
        holder.MaxTemp.setText(weather.getMaxTemp());
        holder.MinTemp.setText(weather.getMinTemp());

        // Apply fadeIn animation to itemView
        AnimationUtils.fadeIn(context, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
