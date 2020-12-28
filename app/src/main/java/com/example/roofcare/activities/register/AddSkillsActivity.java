package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityAddSkillsBinding;
import com.example.roofcare.services.registerprofessions.ProfessionsService;

public class AddSkillsActivity extends AppCompatActivity {
    private ActivityAddSkillsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSkillsBinding.inflate(getLayoutInflater());
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
            startActivity(new Intent(this, AddProfileImageActivity.class));
            finish();
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