package com.trex.musicplus;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private ArrayList<Songs> songsList = new ArrayList<>();
    private SongsAdapter adapter;
    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SongsAdapter(this, songsList, position -> {
            // Handle item click
            Songs clickedSong = songsList.get(position);
            Toast.makeText(this, "Banako Xina 🤷‍♀️", Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        // Load cached paths and populate songsList
        loadSongs();

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSongs(newText);
                return true;
            }
        });
    }

    private void loadSongs() {
        // Load music paths and create Songs objects
        List<String> cachedPaths = DataSave.loadCachedMP3Paths(this);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        for (String path : cachedPaths) {
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

                    songsList.add(new Songs(name, formattedDuration, icon));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void filterSongs(String query) {
        List<Songs> filteredList = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        for (Songs song : songsList) {
            if (song.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                filteredList.add(song);
            }
        }

        adapter.updateSongs(filteredList);
    }

    private String formatDuration(int durationInMillis) {
        int minutes = (durationInMillis / 1000) / 60;
        int seconds = (durationInMillis / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

//    private void playMusicByTitle(Songs song) {
//        // Find the index of the song with the given title
//        List<String> cachedPaths = DataSave.loadCachedMP3Paths(this);
//        int index = -1;
//        for (int i = 0; i < cachedPaths.size(); i++) {
//            if (cachedPaths.get(i).contains(song.getTitle())) {
//                index = i;
//                break;
//            }
//        }
//
//        // If the song is found, play it
//        if (index != -1) {
//            // Use an Intent to start MainActivityTwo and pass the song index
//            Intent intent = new Intent(Search.this, MainActivityTwo.class);
//            intent.putExtra("song_index", index);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Song not found", Toast.LENGTH_SHORT).show();
//        }
//    }
}