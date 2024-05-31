package com.trex.musicplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        if (song.getTitle() != null) {
            // This is an individual song
            musicIcon.setImageResource(song.getIcon());
            titleTextView.setText(song.getTitle());
            durationTextView.setText(formatDuration(song.getDuration()));
        } else {
            // This is a folder
            musicIcon.setImageResource(R.drawable.folder_icon); // Replace with folder icon
            titleTextView.setText(song.getFolderName());
            durationTextView.setText(song.getFolderLength() + " songs");
        }

        return convertView;
    }

    private String formatDuration(int duration) {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
