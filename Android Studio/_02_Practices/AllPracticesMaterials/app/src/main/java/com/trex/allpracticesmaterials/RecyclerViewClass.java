package com.trex.allpracticesmaterials;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class RecyclerViewClass extends AppCompatActivity {
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_activity);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Apply window insets to adjust padding based on system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sample data: Array of programming languages
        String[] data = {
                "Java",
                "Kotlin",
                "Python",
                "C++",
                "Swift",
                "JavaScript",
                "Ruby",
                "Go",
                "PHP",
                "Rust",
                "TypeScript",
                "Scala"
        };

        rcv = findViewById(R.id.main);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter rcvAdapter = new RecyclerViewAdapter(data);
        rcv.setAdapter(rcvAdapter);
    }
}
