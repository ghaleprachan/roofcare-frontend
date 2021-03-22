package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.activities.dashboard.DashboardActivity;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityUsernamePassBinding;
import com.example.roofcare.models.registerModel.RegisterModel;
import com.example.roofcare.models.registerModel.registerresponse.RegisterResponse;
import com.example.roofcare.services.registerprofessions.ProfessionsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
                            Gson gson = new GsonBuilder().create();
                            RegisterResponse registerResponse = gson.fromJson(String.valueOf(response), RegisterResponse.class);
                            if (registerResponse != null) {
                                if (registerResponse.getSuccess()) {
                                    gotoDashboard(registerResponse.getUserId());
                                    if (RegisterModel.getUserType().equalsIgnoreCase("Vendor")) {
                                        ProfessionsService.addProfessions(registerResponse);
                                        Intent intent = new Intent(this, AddSkillsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        startActivity(new Intent(this, AddProfileImageActivity.class));
                                        finish();
                                    }
                                } else {
                                    binding.username.requestFocus();
                                    binding.usernameL.setError("Username exists!");
                                }
                            }
                        } catch (Exception ex) {
                            binding.username.requestFocus();
                            binding.usernameL.setError("Username exists!");
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

    private void gotoDashboard(Integer userId) {
        SharedPreferences preferences = getSharedPreferences("LOGIN_DETAILS", 0);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("UserId", userId);
        editor.putString("Username", binding.username.getText().toString());
        editor.putString("FullName", RegisterModel.getFullName());
        editor.putString("UserImage", "https://cdn0.iconfinder.com/data/icons/set-ui-app-android/32/8-512.png");
        editor.putString("UserType", RegisterModel.getUserType());
        editor.apply();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private void onBackClick() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }
}