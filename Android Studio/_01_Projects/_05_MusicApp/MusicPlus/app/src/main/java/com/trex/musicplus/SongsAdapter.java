package com.trex.musicplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private Context context;
    private List<Songs> songs;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SongsAdapter(Context context, List<Songs> songs, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.songs = songs;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs song = songs.get(position);
        holder.musicIcon.setImageResource(song.getIcon());
        holder.titleTextView.setText(song.getTitle());
        holder.durationTextView.setText(song.getDuration());

        // Apply fade-in animation
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000); // Adjust duration as needed
        holder.itemView.startAnimation(animation);

        // Set item click listener
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void updateSongs(List<Songs> newSongs) {
        songs = newSongs;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView musicIcon;
        TextView titleTextView;
        TextView durationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            musicIcon = itemView.findViewById(R.id.music_icon);
            titleTextView = itemView.findViewById(R.id.song_title);
            durationTextView = itemView.findViewById(R.id.Duration);
        }
    }
}
