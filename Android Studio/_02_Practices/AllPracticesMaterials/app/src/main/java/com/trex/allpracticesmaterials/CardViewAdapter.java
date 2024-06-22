package com.trex.allpracticesmaterials;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewHolder> {

    public CardViewAdapter(ArrayList<CardViewDataModel> data) {
        this.data = data;
    }

    ArrayList<CardViewDataModel> data;
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_row_design, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getDescription());
        holder.imageUrl.setImageResource(data.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
