package com.trex.musicplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Songs> songs;

    public MyAdapter(Context context, List<Songs> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Songs song = (Songs) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ImageView musicIcon = convertView.findViewById(R.id.music_icon);
        TextView titleTextView = convertView.findViewById(R.id.song_title);
        TextView durationTextView = convertView.findViewById(R.id.Duration);

        musicIcon.setImageResource(song.getIcon());
        titleTextView.setText(song.getTitle());
        durationTextView.setText(song.getDuration());

        // Apply fade-in animation
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000); // You can adjust the duration here
        convertView.startAnimation(animation);

        return convertView;
    }
}
