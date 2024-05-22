package com.example.myfirstapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        TextView nextShow = findViewById(R.id.nextShow);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextShow.getVisibility() == View.VISIBLE) {
                    nextShow.setVisibility(View.INVISIBLE);
                    button.setText("More");
                } else {
                    nextShow.setVisibility(View.VISIBLE);
                    button.setText("Done");
                }
            }
        });
    }
}
