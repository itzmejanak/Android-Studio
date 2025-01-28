package com.trex.musicplus;

import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class List extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<Songs> songsArrayList = new ArrayList<>();
    private ArrayList<String> cachedPaths = new ArrayList<>();
    private MainActivityTwo mainActivityTwo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mainActivityTwo = (MainActivityTwo) getActivity();

        // Initialize adapter with empty list
        adapter = new MyAdapter(getContext(), songsArrayList);
        recyclerView.setAdapter(adapter);

        // Load cached paths
        cachedPaths = DataSave.loadCachedMP3Paths(getContext());

        // Start AsyncTask to retrieve metadata
        new LoadSongsTask(getContext()).execute(cachedPaths.toArray(new String[0]));

        return view;
    }

    // LoadSongsTask now accepts a context
    private class LoadSongsTask extends AsyncTask<String, Void, ArrayList<Songs>> {
        private final android.content.Context context;

        LoadSongsTask(android.content.Context context) {
            this.context = context;
        }

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

                    // Only add the song if its duration is more than 30 seconds
                    if (duration > 30000) {
                        String formattedDuration = formatDuration(duration);
                        int icon = R.drawable.list_music;  // Assuming a default icon resource

                        // If the name is null, use the filename as a fallback
                        if (name == null || name.isEmpty()) {
                            name = path.substring(path.lastIndexOf('/') + 1);
                        }

                        // Add the song to the array list
                        songs.add(new Songs(name, formattedDuration, icon));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return songs;
        }

        @Override
        protected void onPostExecute(ArrayList<Songs> result) {
            songsArrayList.clear(); // Clear the previous data
            songsArrayList.addAll(result);
            adapter.notifyDataSetChanged(); // Notify adapter of data changes
        }

        // Utility method to format duration from milliseconds to "mm:ss"
        private String formatDuration(int durationInMillis) {
            int minutes = (durationInMillis / 1000) / 60;
            int seconds = (durationInMillis / 1000) % 60;
            return String.format("%d:%02d", minutes, seconds);
        }
    }
}
