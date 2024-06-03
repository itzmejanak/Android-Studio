package com.trex.musicplus;

import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class List extends Fragment {
    ListView myListView;
    ArrayList<Songs> songsArrayList = new ArrayList<>();
    ArrayList<String> cachedPaths = new ArrayList<>();
    MainActivityTwo mainActivityTwo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        myListView = view.findViewById(R.id.myListView);
        mainActivityTwo = (MainActivityTwo) getActivity();

        // Load cached paths
        cachedPaths = DataSave.loadCachedMP3Paths(getContext());

        // Start AsyncTask to retrieve metadata
        new LoadSongsTask().execute(cachedPaths.toArray(new String[0]));
        // Set item click listener for the ListView
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivityTwo.playMusicFromIndex(position);
            }
        });
        return view;
    }
    private class LoadSongsTask extends AsyncTask<String, Void, ArrayList<Songs>> {

        @Override
        protected ArrayList<Songs> doInBackground(String... paths) {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            ArrayList<Songs> songs = new ArrayList<>();

            for (String path : paths) {
                try {
                    mmr.setDataSource(path);
                    String name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                    String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    int duration = durationStr != null ? Integer.parseInt(durationStr) : 0;
                    String formattedDuration = formatDuration(duration);
                    int icon = R.drawable.list_music;  // Assuming a default icon resource

                    // If the name is null, use the filename as a fallback
                    if (name == null || name.isEmpty()) {
                        name = path.substring(path.lastIndexOf('/') + 1);
                    }

                    // Add the song to the array list
                    songs.add(new Songs(name, formattedDuration, icon));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return songs;
        }

        @Override
        protected void onPostExecute(ArrayList<Songs> result) {
            songsArrayList.addAll(result);
            MyAdapter adapter = new MyAdapter(getContext(), songsArrayList);
            myListView.setAdapter(adapter);
        }
    }

    // Utility method to format duration from milliseconds to "mm:ss"
    private String formatDuration(int durationInMillis) {
        int minutes = (durationInMillis / 1000) / 60;
        int seconds = (durationInMillis / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
