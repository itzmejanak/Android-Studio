package com.trex.musicplus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;
import com.trex.musicplus.Folders;
import com.trex.musicplus.List;
import com.trex.musicplus.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivityTwo extends AppCompatActivity implements ScanMusic.ScanMusicCallback{

    private TabLayout tabLayout;
    public FrameLayout frameLayout;
    private int selectedTabPosition = 0;

    // Music playback related variables
    private ArrayList<String> musicPaths = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    public int currentIndex = -1;
    TextView changeText;
    ImageView search, menu;
    SearchView search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_two);

        // Apply window insets listener for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize tab layout and frame layout
        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.frameLayout);
        changeText = findViewById(R.id.changeText);
        menu = findViewById(R.id.imageView13);
        search = findViewById(R.id.imageView14);
        search.setOnClickListener(this::closeMenu);

        // Load music paths
        musicPaths = DataSave.loadCachedMP3Paths(this);

        // Set the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();

        // Set tab selection listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTabPosition = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();
                        changeText.setText("Home");
                        break;
                    case 1:
                    case 2:
                        listSongs();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Temp()).commit();
                        changeText.setText("Playing");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        if (musicPaths.size() == 0) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                new ScanMusic(this, this).execute();
            } else {
                // Request permission if not granted (replace with your permission request logic)
                // You can use something like:
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO);
            }
        }

    }

    private void listSongs() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            loadFragmentForSelectedTab();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // Permission granted, proceed with listing songs
            loadFragmentForSelectedTab();
        } else {
            // Permission denied, show a message to the user
            Toast.makeText(this, "Permission denied to read media", Toast.LENGTH_SHORT).show();
        }
    });

    private void loadFragmentForSelectedTab() {
        if (selectedTabPosition == 1) {
            changeText.setText("List");
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new List()).commit();
        } else if (selectedTabPosition == 2) {
            changeText.setText("Folders");
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Folders()).commit();
        }
    }

    // Method to start playing music
    public void startMusic() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            } else {
                if (mediaPlayer == null) {
                    initMediaPlayer();
                    selectRandomTrack();
                }
            }
        } else {
            // Handle the case where permission is not granted
            // You might want to request permission here or show a message to the user
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO);

        }
    }


    // Method to pause music
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Initialize media player
    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(mp -> playNextTrack());
    }

    // Select random track
    private void selectRandomTrack() {
        Random random = new Random();
        currentIndex = random.nextInt(musicPaths.size());
        playCurrentTrack();
    }

    // Play next track
    // Method to play the next track
    public void playNextTrack() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        currentIndex = (currentIndex + 1) % musicPaths.size();
        playCurrentTrack();
    }

    // Method to play the previous track
    public void playPreviousTrack() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        currentIndex = (currentIndex - 1 + musicPaths.size()) % musicPaths.size();
        playCurrentTrack();
    }

    // Play current track
    private void playCurrentTrack() {
        if (mediaPlayer != null && !musicPaths.isEmpty()) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musicPaths.get(currentIndex));
                mediaPlayer.prepare();
                mediaPlayer.start();
//                showToast("Playing " + musicPaths.get(currentIndex));
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Error playing track");
            }
        } else {
            showToast("Cannot play track");
        }
    }

    // Method to check if music is currently playing
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    // Method to get the current playback position
    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    // Method to seek to a specific position in the media playback
    public void seekTo(int progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
        }
    }

    // Method to get the name of the currently playing song
    public String getCurrentSongName() {
        if (currentIndex >= 0 && currentIndex < musicPaths.size()) {
            return musicPaths.get(currentIndex);
        }
        return "No song playing";
    }

    // Method to get the duration of the currently playing song
    public int getCurrentSongDuration() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    // Inside MainActivityTwo class

    public void playMusicFromIndex(int index) {
        // Implement your logic to play music from the specified index
        // For example:
        if (index >= 0 && index < musicPaths.size()) {
            currentIndex = index;
            playCurrentTrack(); // Assuming this method plays the track at currentIndex

        } else {
            // Handle the case where the index is out of bounds
            Toast.makeText(this, "Invalid index", Toast.LENGTH_SHORT).show();
        }
    }

    void closeMenu(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScanCompleted(boolean hasMusicFiles, int totalFiles) {

    }
}
