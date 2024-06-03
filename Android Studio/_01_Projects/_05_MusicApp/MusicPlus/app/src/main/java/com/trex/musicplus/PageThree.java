package com.trex.musicplus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PageThree extends Fragment implements View.OnClickListener, ScanMusic.ScanMusicCallback {
    Button buttonNext;
    TextView buttonBack;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_three, container, false);

        buttonNext = view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);
        buttonBack = view.findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(this);
        imageView = view.findViewById(R.id.imageView4);
        imageView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.BackButton) {
            getParentFragmentManager().popBackStack();
        } else if (v.getId() == R.id.buttonNext) {
            Intent intent = new Intent(getActivity(), MainActivityTwo.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageView4) {
            new ScanMusic(getActivity(), this).execute();
        }
    }

    @Override
    public void onScanCompleted(boolean hasMusicFiles, int totalFiles) {
        // Update the image view state
        if (hasMusicFiles) {
            imageView.setImageResource(R.drawable.switch_on);
            imageView.setTag(R.drawable.switch_on);
        } else {
            imageView.setImageResource(R.drawable.switch_off);
            imageView.setTag(R.drawable.switch_off);
        }
    }
}
