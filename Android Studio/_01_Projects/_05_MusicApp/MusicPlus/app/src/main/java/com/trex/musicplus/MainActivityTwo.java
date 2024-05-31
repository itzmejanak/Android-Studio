package com.trex.musicplus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

public class MainActivityTwo extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private final int REQUEST_MEDIA_CODE = 1;
    private int selectedTabPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_two);

        // Apply window insets listener for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize tab layout and frame layout
        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.frameLayout);

//        // Set up tabs
//        TabLayout.Tab tab1 = tabLayout.newTab().setText("Home").setIcon(R.drawable.home);
//        TabLayout.Tab tab2 = tabLayout.newTab().setText("List").setIcon(R.drawable.list_icon);
//        TabLayout.Tab tab3 = tabLayout.newTab().setText("Folders").setIcon(R.drawable.folder);
//
//        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_text_color));
//        tabLayout.addTab(tab1);
//        tabLayout.addTab(tab2);
//        tabLayout.addTab(tab3);

        // Set the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();

        // Set tab selection listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTabPosition = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();
                        break;
                    case 1:
                    case 2:
                        listSongs();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Temp()).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void listSongs() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have permission", Toast.LENGTH_SHORT).show();
            loadFragmentForSelectedTab();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_AUDIO}, REQUEST_MEDIA_CODE);
        }
    }

    private void loadFragmentForSelectedTab() {
        if (selectedTabPosition == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new List()).commit();
        } else if (selectedTabPosition == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Folders()).commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_MEDIA_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with listing songs
                loadFragmentForSelectedTab();
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Permission denied to read media", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
