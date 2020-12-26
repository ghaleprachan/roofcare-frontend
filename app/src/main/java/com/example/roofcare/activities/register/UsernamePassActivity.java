package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.activities.logIn.LogIn;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityUsernamePassBinding;
import com.example.roofcare.models.registerModel.RegisterModel;

import org.json.JSONObject;


public class UsernamePassActivity extends AppCompatActivity {
    private ActivityUsernamePassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsernamePassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackClick();
        onSignUpClick();
    }

    private void onSignUpClick() {
        binding.registerNow.setOnClickListener(v -> {
            if (binding.username.getText().toString().isEmpty()) {
                binding.usernameL.setError("Add username!");
                binding.username.requestFocus();
            } else if (binding.password.getText().toString().isEmpty()) {
                binding.usernameL.setError(null);
                binding.password.requestFocus();
                binding.passwordL.setError("Enter password!");
            } else if (binding.cPassword.getText().toString().isEmpty()) {
                binding.password.setError(null);
                binding.usernameL.setError(null);
                binding.cPassword.requestFocus();
                binding.cPasswordL.setError("Enter confirm password!");
            } else if (!binding.password.getText().toString().equals(binding.cPassword.getText().toString())) {
                binding.cPasswordL.setError("Confirm password doesn't matches!");
                binding.cPassword.requestFocus();
                binding.usernameL.setError(null);
                binding.password.setError(null);
            } else {
                registerApiCall();
            }
        });
    }

    private void registerApiCall() {
        try {
            binding.registerNow.setEnabled(false);
            binding.progress.setVisibility(View.VISIBLE);
            JSONObject object = new JSONObject();
            object.put("username", binding.username.getText().toString());
            object.put("password", binding.password.getText().toString());
            object.put("fullName", RegisterModel.getFullName());
            object.put("gender", RegisterModel.getGender());
            object.put("dob", DateFormat.format("yyyy-MM-dd'T'HH:mm:ss", RegisterModel.getDob()));
            object.put("userType", RegisterModel.getUserType());
            object.put("about", RegisterModel.getAboutUser());
            object.put("contact", RegisterModel.getContact());
            object.put("address", RegisterModel.getAddress());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.registerUser,
                    object,
                    response -> {
                        binding.registerNow.setEnabled(true);
                        binding.progress.setVisibility(View.GONE);
                        try {
                            if (response.getString("Success").equals("true")) {
                                binding.usernameL.setError(null);
                                if (RegisterModel.getUserType().equalsIgnoreCase("Vendor")) {
                                    Intent intent = new Intent(this, AddSkillsActivity.class);
                                    intent.putExtra("UserId", response.getString("UserId"));
                                    startActivity(intent);
                                } else {
                                    startActivity(new Intent(this, LogIn.class));
                                }
                            } else if (response.getString("Success").equals("false")) {
                                binding.username.requestFocus();
                                binding.usernameL.setError("Username exists!");
                            } else {
                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Exception In: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        binding.registerNow.setEnabled(true);
                        binding.progress.setVisibility(View.GONE);
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void onBackClick() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }
}