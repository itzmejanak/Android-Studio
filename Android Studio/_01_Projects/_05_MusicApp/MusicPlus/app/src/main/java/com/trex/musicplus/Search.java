package com.trex.musicplus;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private ArrayList<String> musicPaths = new ArrayList<>();
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.listview);

        musicPaths = DataSave.loadCachedMP3Paths(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, musicPaths);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userInput = newText.toLowerCase();
                ArrayList<String> filteredList = new ArrayList<>();

                for (String path : musicPaths) {
                    if (path.toLowerCase().contains(userInput)) {
                        filteredList.add(path);
                    }
                }

                adapter.clear();
                adapter.addAll(filteredList);
                return true;
            }
        });
    }
}
