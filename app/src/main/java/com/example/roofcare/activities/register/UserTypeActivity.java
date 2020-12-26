package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.roofcare.R;
import com.example.roofcare.databinding.ActivityUserTypeBinding;
import com.example.roofcare.models.registerModel.RegisterModel;

public class UserTypeActivity extends AppCompatActivity {
    private ActivityUserTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackBtnClick();
        onCustomerSelect();
        RegisterModel.setContact(getIntent().getStringExtra("phone"));
    }

    private void startActivityMethod() {
        startActivity(new Intent(this, BasicDetailsFormActivity.class));
    }

    private void onCustomerSelect() {
        binding.customer.setOnClickListener(v -> {
            RegisterModel.setUserType("Customer");
            startActivityMethod();
        });
        binding.serviceProvider.setOnClickListener(v -> {
            RegisterModel.setUserType("Vendor");
            startActivityMethod();
        });
    }

    private void onBackBtnClick() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }
}