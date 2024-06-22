package com.trex.allpracticesmaterials;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView description;
    ImageView imageUrl;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.nameTextView);
        description = itemView.findViewById(R.id.descTextView);
        imageUrl = itemView.findViewById(R.id.iconImageView);
    }
}
