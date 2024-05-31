package com.trex.musicplus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PageTwo extends Fragment implements View.OnClickListener {

    private static final int REQUEST_READ_MEDIA_AUDIO = 1;

    Button nextButton;
    TextView backButton;
    ImageView imageView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_two, container, false);

        nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView2.setTag(R.drawable.untik); // Set initial tag
        imageView2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nextButton) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.FrameLayoutOne, new PageThree())
                    .addToBackStack(null)
                    .commit();
        } else if (v.getId() == R.id.backButton) {
            getParentFragmentManager().popBackStack();
        } else if (v.getId() == R.id.imageView2) {
            handlePermissionAndToggle(v);
        }
    }

    private void tickUntick(View v) {
        int currentTag = (int) v.getTag();
        int newTag = (currentTag == R.drawable.untik) ? R.drawable.tick : R.drawable.tick;
        ((ImageView) v).setImageResource(newTag);
        v.setTag(newTag);
    }

    private void handlePermissionAndToggle(View v) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_MEDIA_AUDIO}, REQUEST_READ_MEDIA_AUDIO);
        } else {
            tickUntick(v);
            showToast("Permission already granted");
        }
    }

    // Method to show toast message
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
