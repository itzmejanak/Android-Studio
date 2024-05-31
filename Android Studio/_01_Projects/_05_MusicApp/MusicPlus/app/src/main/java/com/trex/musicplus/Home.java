package com.trex.musicplus;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

public class Home extends Fragment implements View.OnClickListener {
    private static final int REQUEST_PERMISSION_CODE = 123;
    private ImageView imageView7;
    private ArrayList<String> songsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageView7 = view.findViewById(R.id.imageView7);
        imageView7.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView7) {
            Toast.makeText(getContext(), "Refresh will implemented later", Toast.LENGTH_LONG).show();
//            loadSongs();
        }
    }

}
