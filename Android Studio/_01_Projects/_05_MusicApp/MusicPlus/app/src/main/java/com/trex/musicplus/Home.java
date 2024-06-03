package com.trex.musicplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Home extends Fragment implements View.OnClickListener, ScanMusic.ScanMusicCallback {
    public ImageView imageView7, profile;
    TextView name, textView18;
    ImageView imageView10;
    ArrayList<String> cachedPaths;
    SharedPreferences sharedPreferences;
    String totalSongs;
    MainActivityTwo position;
    int currentIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageView7 = view.findViewById(R.id.imageView7);
        imageView7.setOnClickListener(this);
        profile = view.findViewById(R.id.profile);
        profile.setOnClickListener(this);
        name = view.findViewById(R.id.name);
        imageView10 = view.findViewById(R.id.imageView10);
        imageView10.setOnClickListener(this);


        cachedPaths = DataSave.loadCachedMP3Paths(getContext());
        totalSongs = String.valueOf(cachedPaths.size());
        textView18 = view.findViewById(R.id.textView18);
        onSongChange();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUserName();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserName();
    }

    private void updateUserName() {
        sharedPreferences = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("key_session_name", "User Name");
        name.setText(userName);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView7) {
            new ScanMusic(requireActivity(), this).execute();
            Toast.makeText(requireContext(), "Scanning for music files...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.profile) {
            if(name.getText().equals("User Name")){
                Intent intent = new Intent(requireActivity(), UserProfile.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(), "Already Registered", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.imageView10){
            Intent intent = new Intent(getContext(), MainActivityThree.class);
            startActivity(intent);
        }
    }

    @Override
    public void onScanCompleted(boolean hasMusicFiles, int totalFiles) {
        // Handle scan completed
    }

    public void onSongChange() {
        position = new MainActivityTwo();
        try {
            currentIndex = 13;

        }catch (
                Exception e
        ){
            e.printStackTrace();
        }
        textView18.setText("Now Playing   " + "(" + currentIndex + "/" + totalSongs + ")");
    }
}
