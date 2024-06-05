package com.trex.allpracticesmaterials;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VollyLibrary extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, genderEditText;
    private Button registerButton;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volly_library);

        // Find views by their IDs
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        genderEditText = findViewById(R.id.male); // Update the ID in the XML layout too
        registerButton = findViewById(R.id.registerButton);
        message = findViewById(R.id.message);

        // Apply insets to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform registration actions here
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String gender = genderEditText.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(VollyLibrary.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
                ProgressDialog progressDialog = new ProgressDialog(VollyLibrary.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                StringRequest sr = new StringRequest(
                        Request.Method.POST,
                        "http://192.168.1.5:8080/ApiDemoOne/NewServlet",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(VollyLibrary.this, response, Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(VollyLibrary.this, error.toString(), Toast.LENGTH_SHORT).show();
                                message.setText(""+error);
                            }
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("key_name", name);
                        params.put("key_mail", email);
                        params.put("key_pass", password);
                        params.put("key_gender", gender);
                        return params;
                    }
                };
                rq.add(sr);
            }
        });
    }
}
