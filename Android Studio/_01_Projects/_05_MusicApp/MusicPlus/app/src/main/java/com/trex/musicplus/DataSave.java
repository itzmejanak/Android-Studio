package com.trex.musicplus;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class DataSave {

    public static void saveGeneratedMP3Paths(Context context, ArrayList<String> mp3Paths) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GeneratedMP3Paths", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear existing data before saving new paths
        editor.clear();

        editor.putInt("pathCount", mp3Paths.size());

        for (int i = 0; i < mp3Paths.size(); i++) {
            editor.putString("path_" + i, mp3Paths.get(i));
        }

        editor.apply();
    }

    public static ArrayList<String> loadCachedMP3Paths(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GeneratedMP3Paths", Context.MODE_PRIVATE);
        int pathCount = sharedPreferences.getInt("pathCount", 0);

        ArrayList<String> mp3Paths = new ArrayList<>();
        for (int i = 0; i < pathCount; i++) {
            mp3Paths.add(sharedPreferences.getString("path_" + i, null));
        }

        return mp3Paths;
    }
}
