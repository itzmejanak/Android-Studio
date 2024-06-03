package com.trex.musicplus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_PROFILE_IMAGE = 1;
    private static final int PICK_COVER_IMAGE = 2;

    EditText takeName;
    ImageView takeImage, takeCoverImg;
    Button ok, back;
    String name;
    Uri profileImageUri, coverImageUri;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.UserProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sessionManager = new SessionManager(this);

        takeName = findViewById(R.id.takeName);
        takeImage = findViewById(R.id.takeProfileImg);
        takeCoverImg = findViewById(R.id.takeCoverIng);
        ok = findViewById(R.id.ok);
        back = findViewById(R.id.back);

        takeName.setOnClickListener(this);
        takeImage.setOnClickListener(this);
        takeCoverImg.setOnClickListener(this);
        ok.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.takeName) {
            name = takeName.getText().toString();
        } else if (v.getId() == R.id.takeProfileImg) {
            handleImageClick(PICK_PROFILE_IMAGE);
        } else if (v.getId() == R.id.takeCoverIng) {
            handleImageClick(PICK_COVER_IMAGE);
        } else if (v.getId() == R.id.ok) {
            handleOkButtonClick();
        } else if (v.getId() == R.id.back) {
            navigateBack();
        }
    }

    private void handleImageClick(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    private void handleOkButtonClick() {
        // Get the text from the name EditText
        String enteredName = takeName.getText().toString().trim();

        // Check if name is empty
        if (enteredName.isEmpty()) {
            // If name is empty, show a toast message and return
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if profile and cover image URIs are selected
        if (profileImageUri != null && coverImageUri != null) {
            // If all details are filled, create session and navigate to MainActivityTwo
            sessionManager.createSession(enteredName, profileImageUri.toString(), coverImageUri.toString());
            navigateToMainActivityTwo();
        } else {
            // If profile or cover image is not selected, show a toast message
            Toast.makeText(this, "Please select profile and cover images", Toast.LENGTH_SHORT).show();
        }
    }


    private void navigateToMainActivityTwo() {
        Intent intent = new Intent(UserProfile.this, MainActivityTwo.class);
        startActivity(intent);
    }

    private void navigateBack() {
        navigateToMainActivityTwo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (requestCode == PICK_PROFILE_IMAGE) {
                profileImageUri = selectedImage;
                takeImage.setImageURI(profileImageUri);
            } else if (requestCode == PICK_COVER_IMAGE) {
                coverImageUri = selectedImage;
                takeCoverImg.setImageURI(coverImageUri);
            }
        }
    }
}
