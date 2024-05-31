package com.trex.melodaze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Songs> songs;

    public SongsAdapter(Context context, ArrayList<Songs> songs) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        }

        Songs song = (Songs) getItem(position);

        TextView songTitle = convertView.findViewById(R.id.songTitle);
        TextView songDuration = convertView.findViewById(R.id.songDuration);

        songTitle.setText(song.getTitle());
        songDuration.setText(String.valueOf(song.getDuration()));

        return convertView;
    }
}
