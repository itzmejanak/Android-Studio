package com.trex.musicplus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Folders extends Fragment {
    ListView myListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folders, container, false);
        // Inflate the layout for this fragment
        ArrayList<Songs> folders = new ArrayList<>();
        folders.add(new Songs("Masti Gana", 180)); // Replace with actual icon
        folders.add(new Songs("Gods Gana", 180)); // Replace with actual icon
        folders.add(new Songs("Best Gana", 180)); // Folder example

        myListView = view.findViewById(R.id.myListViewFolders);
        MyAdapter adapter = new MyAdapter(getContext(), folders);
        myListView.setAdapter(adapter);

        return view;
    }
}