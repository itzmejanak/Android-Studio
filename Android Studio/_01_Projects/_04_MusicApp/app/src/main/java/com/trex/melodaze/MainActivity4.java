package com.trex.melodaze;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    ListView listViewSongs;
    ArrayList<Songs> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        songs.add(new Songs("Song 1", 5));
        songs.add(new Songs("Song 2", 7));
        songs.add(new Songs("Song 3", 58));
        songs.add(new Songs("Song 4", 15));
        songs.add(new Songs("Song 5", 53));
        songs.add(new Songs("Song 6", 35));
        songs.add(new Songs("Song 7", 45));
        songs.add(new Songs("Song 8", 55));
        songs.add(new Songs("Song 9", 57));
        songs.add(new Songs("Song 1", 5));
        songs.add(new Songs("Song 2", 7));
        songs.add(new Songs("Song 3", 58));
        songs.add(new Songs("Song 4", 15));
        songs.add(new Songs("Song 5", 53));
        songs.add(new Songs("Song 6", 35));
        songs.add(new Songs("Song 7", 45));
        songs.add(new Songs("Song 8", 55));
        songs.add(new Songs("Song 9", 57));

        listViewSongs = findViewById(R.id.listViewSongs);
        SongsAdapter songsAdapter = new SongsAdapter(this, songs);
        listViewSongs.setAdapter(songsAdapter);
    }
}
