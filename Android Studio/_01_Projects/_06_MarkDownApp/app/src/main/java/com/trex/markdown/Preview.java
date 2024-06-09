package com.trex.markdown;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import java.util.ArrayList;

public class Preview extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView listView;
    SaveData save;
    TextView no_files;
    AppCompatButton open_files;
    ArrayList<String> arrayData;
    FileArrayAdapter adapter;
    private static final int FILE_PICKER_REQUEST_CODE = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview_fragment, container, false);

        save = new SaveData(getContext());
        arrayData = save.getAllPreferences();
        listView = view.findViewById(R.id.listView);
        open_files = view.findViewById(R.id.open_files);
        no_files = view.findViewById(R.id.no_files);
        open_files.setOnClickListener(this);
        checkSize();

        // Initialize ArrayAdapter and set it to ListView
        adapter = new FileArrayAdapter(getContext(), arrayData);
        listView.setAdapter(adapter);

        // Set item click listener
        listView.setOnItemClickListener(this);

        return view;
    }

    void checkSize() {
        if (!arrayData.isEmpty()) {
            no_files.setVisibility(View.GONE);
        } else {
            no_files.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == open_files) {
            openFiles();
        }
    }

    void openFiles() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("text/markdown");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    // Get the actual file path from the URI
                    String filePath = uri.toString(); // Store the URI as string
                    save.createSession(filePath);
                    arrayData.add(filePath);
                    adapter.notifyDataSetChanged();
                    checkSize();
                } else {
                    Toast.makeText(getContext(), "Unable to get file path", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get the selected item's data path
        String selectedFilePath = arrayData.get(position);

        // Start the next activity with the selected data path
        Intent intent = new Intent(getContext(), Render.class);
        intent.putExtra("selectedFilePath", selectedFilePath);
        startActivity(intent);
    }
}
