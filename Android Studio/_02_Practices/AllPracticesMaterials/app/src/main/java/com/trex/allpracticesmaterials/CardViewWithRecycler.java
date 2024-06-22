package com.trex.allpracticesmaterials;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardViewWithRecycler extends AppCompatActivity {
    RecyclerView rcv;
    ArrayList<CardViewDataModel> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.card_view_with_recycler_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        data = new ArrayList<>();
        rcv = (RecyclerView) findViewById(R.id.main);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(new CardViewAdapter(getData()));
    }

    ArrayList<CardViewDataModel> getData() {
        data.add(new CardViewDataModel("Java", "Java is platform independent", R.drawable.java));
        data.add(new CardViewDataModel("JavaScript", "JavaScript is a versatile scripting language", R.drawable.javascript));
        data.add(new CardViewDataModel("Kotlin", "Kotlin is a modern language for Android development", R.drawable.kotlin));
        data.add(new CardViewDataModel("PHP", "PHP is a popular server-side scripting language", R.drawable.php));
        data.add(new CardViewDataModel("Python", "Python is known for its simplicity and readability", R.drawable.python));
        data.add(new CardViewDataModel("Swift", "Swift is a powerful language for iOS development", R.drawable.swift));
        data.add(new CardViewDataModel("C", "C is a powerful general-purpose programming language", R.drawable.c));
        data.add(new CardViewDataModel("C#", "C# is a modern language for developing Windows applications", R.drawable.c_sharp));
        return data;
    }
}