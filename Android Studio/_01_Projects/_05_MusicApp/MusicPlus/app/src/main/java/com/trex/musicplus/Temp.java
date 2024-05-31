package com.trex.musicplus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import android.os.AsyncTask;

public class Temp extends Fragment implements View.OnClickListener {
    private Button btnPlay, btnPause, btnStop, btnNext, btnPrevious;
    private ArrayList<String> musicPaths = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private int currentIndex = -1;
    private boolean isRandomTrackSelected = false;
    private static final int REQUEST_PERMISSION = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);
        initViews(view);
        checkPermissions();
        getAllMusicPaths();
        return view;
    }

    private void initViews(View view) {
        btnPlay = view.findViewById(R.id.btnPlay);
        btnPause = view.findViewById(R.id.btnPause);
        btnStop = view.findViewById(R.id.btnStop);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAllMusicPaths();
        } else {
            Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlay) {
            playMusic();
        } else if (v.getId() == R.id.btnPause) {
            pauseMusic();
        } else if (v.getId() == R.id.btnStop) {
            stopMusic();
        } else if (v.getId() == R.id.btnNext) {
            playNextTrack();
        } else if (v.getId() == R.id.btnPrevious) {
            playPreviousTrack();
        }
    }


    private void playMusic() {
        if (musicPaths.isEmpty()) {
            Toast.makeText(getActivity(), "No music files found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mediaPlayer == null) {
            initMediaPlayer();
        }
        if (!isRandomTrackSelected) {
            selectRandomTrack();
            isRandomTrackSelected = true;
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Toast.makeText(getActivity(), "Playing " + musicPaths.get(currentIndex), Toast.LENGTH_SHORT).show();
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Toast.makeText(getActivity(), "Paused " + musicPaths.get(currentIndex), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Nothing is playing", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
                Toast.makeText(getActivity(), "Stopped", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Nothing is playing", Toast.LENGTH_SHORT).show();
        }
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(mp -> playNextTrack());
    }

    private void selectRandomTrack() {
        Random random = new Random();
        currentIndex = random.nextInt(musicPaths.size());
        playCurrentTrack();
    }

    private void playNextTrack() {
        if (!musicPaths.isEmpty()) {
            currentIndex = (currentIndex + 1) % musicPaths.size();
            playCurrentTrack();
        } else {
            Toast.makeText(getActivity(), "No tracks available", Toast.LENGTH_SHORT).show();
        }
    }

    private void playPreviousTrack() {
        if (!musicPaths.isEmpty()) {
            currentIndex = (currentIndex - 1 + musicPaths.size()) % musicPaths.size();
            playCurrentTrack();
        } else {
            Toast.makeText(getActivity(), "No tracks available", Toast.LENGTH_SHORT).show();
        }
    }

    private void playCurrentTrack() {
        if (mediaPlayer != null && !musicPaths.isEmpty()) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musicPaths.get(currentIndex));
                mediaPlayer.prepare();
                mediaPlayer.start();
                Toast.makeText(getContext(), "Playing " + musicPaths.get(currentIndex), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error playing track", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Cannot play track", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllMusicPaths() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                getAllMusicPaths(Environment.getExternalStorageDirectory());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // Here, you can notify the user or update the UI as needed
            }
        }.execute();
    }

    private void getAllMusicPaths(File directory) {
        if (directory != null && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        getAllMusicPaths(file); // Recursively search in subdirectories
                    } else if (file.isFile() && file.getName().endsWith(".mp3")) {
                        musicPaths.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
