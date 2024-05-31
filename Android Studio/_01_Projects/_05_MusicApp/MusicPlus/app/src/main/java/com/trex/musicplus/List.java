package com.trex.musicplus;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

public class List extends Fragment {
    ListView myListView;
    ArrayList<Songs> songsArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        String name = "";
        int duration = 0;
        int icon = R.drawable.music;

        
        songsArrayList.add(new Songs(name, duration, icon));

        myListView = view.findViewById(R.id.myListView);
        MyAdapter adapter = new MyAdapter(getContext(), songsArrayList);
        myListView.setAdapter(adapter);

        return view;
    }
}
