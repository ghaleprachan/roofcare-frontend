package com.example.homesewa.activities.settings.settingitems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.R;
import com.example.homesewa.activities.logIn.LogInActivity;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.databinding.ActivitySwitchAccountBinding;
import com.example.homesewa.services.registerprofessions.ProfessionsService;

public class SwitchAccountActivity extends AppCompatActivity {
    private ActivitySwitchAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwitchAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.profession.requestFocus();
        onBackClick();
        initHint();
        binding.done.setEnabled(false);
        onAddClick();
        onDoneClick();
    }

    private void onDoneClick() {
        binding.done.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.setTitle("ALERT");
            builder.setIcon(R.drawable.ic_baseline_notifications_active_24);
            builder.setMessage("Your account has been switched, To complete account switching please log in again!");
            builder.setPositiveButton("Ok", (dialog, which) -> {
                SharedPreferences preferences = this.getSharedPreferences("LOGIN_DETAILS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                dialog.dismiss();
            });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        });
    }

    private void onAddClick() {
        binding.add.setOnClickListener(v -> {
            if (binding.profession.getText().toString().isEmpty()) {
                binding.profession.requestFocus();
                binding.professionLayout.setError("Enter skills, eg: Plumber..");
            } else {
                if (ProfessionsService.getProfessionId(binding.profession.getText().toString()) != -1) {
                    binding.professionLayout.setError(null);
                    addApiCall();
                } else {
                    binding.professionLayout.setError("Profession still not available!");
                }
            }
        });
    }

    private void addApiCall() {
        try {
            binding.add.setEnabled(false);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    ApiCollection.addUserProfession + "userId=" +
                            ProfessionsService.professions.get(0).getUserId() +
                            "&professionId=" +
                            ProfessionsService.getProfessionId(binding.profession.getText().toString()),
                    response -> {
                        binding.add.setEnabled(true);
                        binding.done.setEnabled(true);
                        if (response.equals("true")) {
                            binding.profession.setText("");
                            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("false")) {
                            Toast.makeText(this, "Skill already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                        binding.add.setEnabled(true);
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Ex: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initHint() {
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ProfessionsService.getProfessions());
        binding.profession.setAdapter(countryAdapter);
    }

    private void onBackClick() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }
}