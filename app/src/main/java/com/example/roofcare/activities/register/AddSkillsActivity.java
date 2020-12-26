package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roofcare.databinding.ActivityAddSkillsBinding;

public class AddSkillsActivity extends AppCompatActivity {
    private ActivityAddSkillsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSkillsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onBackClick();
    }

    private void onBackClick() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }
}