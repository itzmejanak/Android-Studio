package com.trex.musicplus;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Temp extends Fragment implements View.OnClickListener {
    private MainActivityTwo mainActivity;
    private Button btnPlay, btnNext, btnPrevious;
    private SeekBar seekBar;
    private TextView nameOfSong, songDuration, currentTime;
    private Handler handler = new Handler();
    private Runnable updateSeekBar;
    TextView emoji;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivityTwo) {
            mainActivity = (MainActivityTwo) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement MainActivityTwo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);

        btnPlay = view.findViewById(R.id.btnPlay);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        seekBar = view.findViewById(R.id.seekBar);
        nameOfSong = view.findViewById(R.id.title);
        emoji = view.findViewById(R.id.emoji);
        currentTime = view.findViewById(R.id.currentTime);
        emoji.setOnClickListener(this::changeLove);
        songDuration = view.findViewById(R.id.duration);


        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        if (mainActivity != null) {
            updateSongInfo();
        }


        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mainActivity.isPlaying()) {
                    int currentPosition = mainActivity.getCurrentPosition();
                    String time = formatDuration(currentPosition);
                    currentTime.setText(time);
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000); // Update every second
            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mainActivity.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(updateSeekBar);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    public void onClick(View v) {
        if (mainActivity == null) {
            return;
        }

        int id = v.getId();

        if (id == R.id.btnPlay) {
            if (mainActivity.isPlaying()) {
                mainActivity.pauseMusic();
                btnPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);
            } else {
                mainActivity.startMusic();
                btnPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0);
            }
        } else if (id == R.id.btnNext) {
            mainActivity.playNextTrack();
            updateSongInfo();
        } else if (id == R.id.btnPrevious) {
            mainActivity.playPreviousTrack();
            updateSongInfo();
        }
    }


    private void updateSongInfo() {
        if (mainActivity != null) {
            String songPath = mainActivity.getCurrentSongName();
            String songName = getSongNameFromPath(songPath);
            int duration = mainActivity.getCurrentSongDuration();
            nameOfSong.setText(songName);
            songDuration.setText(formatDuration(duration));

            // Set maximum value of SeekBar to the duration of the song
            // Make sure the duration is set in milliseconds
            seekBar.setMax(duration);

            // Update SeekBar progress according to current position
            int currentPosition = mainActivity.getCurrentPosition();
            seekBar.setProgress(currentPosition);
        }
    }

    // Method to extract song name from the file path
    private String getSongNameFromPath(String songPath) {
        // Split the path using the file separator ("/" for Unix-like systems)
        String[] parts = songPath.split("/");
        // Get the last part which represents the file name
        String fileName = parts[parts.length - 1];
        // Remove the file extension
        String[] nameParts = fileName.split("\\.");
        return nameParts[0]; // Return the name without the extension
    }





    private String formatDuration(int duration) {
        int minutes = (duration / 1000) / 60;
        int seconds = (duration / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    void changeLove(View view) {
        emoji.setText("❤\uFE0F");
    }
}
