package com.example.roofcare.activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.R;
import com.example.roofcare.activities.settings.settingitems.ChangePasswordActivity;
import com.example.roofcare.activities.settings.settingitems.SwitchAccountActivity;
import com.example.roofcare.activities.settings.settingitems.UpdateSkillsActivity;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivitySettingBinding;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.registerModel.registerresponse.RegisterResponse;
import com.example.roofcare.services.registerprofessions.ProfessionsService;
import com.google.gson.GsonBuilder;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onBackPress();
        onSwitchAccountClick();
        onChangePasswordClick();
        if (UserBasicDetails.getUserType(this).equalsIgnoreCase("Customer")) {
            binding.addSkillsCard.setVisibility(View.GONE);
        }
        onUpdateSkillsClick();
    }

    private void onUpdateSkillsClick() {
        binding.addSkills.setOnClickListener(v -> updateServiceApiCall(binding.progressSkills));
    }

    private void onChangePasswordClick() {
        binding.changePassword.setOnClickListener(v -> startActivity(new Intent(this, ChangePasswordActivity.class)));
    }

    private void onSwitchAccountClick() {
        binding.switchAccount.setOnClickListener(v -> {
            if (UserBasicDetails.getUserType(this).equalsIgnoreCase("Vendor")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ALERT");
                builder.setIcon(R.drawable.ic_baseline_notifications_active_24);
                builder.setMessage("You can't change your type, you are already an service provider.");
                builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                android.app.AlertDialog alert = builder.create();
                alert.show();
            } else {
                servicesApiCall(binding.progress);
            }
        });
    }

    private void servicesApiCall(ProgressBar progressBar) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getServices + UserBasicDetails.getId(this),
                    response -> {
                        try {
                            progressBar.setVisibility(View.GONE);
                            RegisterResponse registerResponse = new GsonBuilder().create().fromJson(response, RegisterResponse.class);
                            if (registerResponse.getSuccess()) {
                                ProfessionsService.addProfessions(registerResponse);
                                startActivity(new Intent(this, SwitchAccountActivity.class));
                            } else {
                                Toast.makeText(this, "Something went wrong try again!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateServiceApiCall(ProgressBar progressBar) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getServices + UserBasicDetails.getId(this),
                    response -> {
                        try {
                            progressBar.setVisibility(View.GONE);
                            RegisterResponse registerResponse = new GsonBuilder().create().fromJson(response, RegisterResponse.class);
                            if (registerResponse.getSuccess()) {
                                ProfessionsService.addProfessions(registerResponse);
                                startActivity(new Intent(this, UpdateSkillsActivity.class));
                            } else {
                                Toast.makeText(this, "Something went wrong try again!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onBackPress() {
        binding.back.setOnClickListener(v -> this.finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}