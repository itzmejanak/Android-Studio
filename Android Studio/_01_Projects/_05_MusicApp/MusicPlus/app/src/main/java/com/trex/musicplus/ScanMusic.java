package com.trex.musicplus;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ScanMusic extends AsyncTask<Void, Void, Boolean> {

    private ProgressDialog progressDialog;
    private ArrayList<String> musicPaths = new ArrayList<>();
    private Context context;
    private ScanMusicCallback callback;

    public ScanMusic(Context context, ScanMusicCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        // Show loading indicator
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Scanning Music, wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        getAllMusicPaths(Environment.getExternalStorageDirectory());
        return !musicPaths.isEmpty();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // Dismiss loading indicator
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        // Save paths in cache
        DataSave.saveGeneratedMP3Paths(context, musicPaths);

        // Update UI and show toast notification
        if (callback != null) {
            callback.onScanCompleted(result, musicPaths.size());
        }
        Toast.makeText(context, result ? "Scanned Total: " + musicPaths.size() : "No music files found.", Toast.LENGTH_SHORT).show();
    }

    private void getAllMusicPaths(File directory) {
        if (directory != null && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        getAllMusicPaths(file); // Recursively search in subdirectories
                    } else if (file.isFile() && file.getName().endsWith(".mp3")) {
                        // Check the duration of the file
                        if (isValidDuration(file.getAbsolutePath())) {
                            musicPaths.add(file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    private boolean isValidDuration(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(filePath);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int duration = durationStr != null ? Integer.parseInt(durationStr) : 0;
            return duration >= 30000; // 30 seconds = 30000 milliseconds
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                mmr.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface ScanMusicCallback {
        void onScanCompleted(boolean hasMusicFiles, int totalFiles);
    }
}
