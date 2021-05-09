package com.example.homesewa.activities.settings.settingitems;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.databinding.ActivityChangePasswordBinding;
import com.example.homesewa.helper.userDetails.UserBasicDetails;

public class ChangePasswordActivity extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.oldPass.requestFocus();
        onBackClick();

        onChangePasswordClick();
    }

    private void onChangePasswordClick() {
        binding.changePassword.setOnClickListener(v -> {
            if (binding.oldPass.getText().toString().isEmpty()) {
                binding.oldPass.requestFocus();
                binding.oldPassL.setError("Enter your old password!");
            } else if (binding.newPass.getText().toString().isEmpty()) {
                binding.oldPassL.setError("");
                binding.newPass.requestFocus();
                binding.newPassL.setError("Enter your new password!");
            } else if (binding.newPassC.getText().toString().isEmpty()) {
                binding.newPassL.setError("");
                binding.newPassC.requestFocus();
                binding.newPassCL.setError("Enter your new password!");
            } else if (!binding.newPass.getText().toString().equals(binding.newPassC.getText().toString())) {
                binding.newPassC.requestFocus();
                binding.newPassCL.setError("Password doesn't match");
            } else {
                changePasswordApiCall();
            }
        });
    }

    private void changePasswordApiCall() {
        binding.changePassword.setEnabled(false);
        binding.progress.setVisibility(View.VISIBLE);
        try {
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    ApiCollection.changePassword + UserBasicDetails.getId(this) +
                            "&oldPassword=" + binding.oldPass.getText().toString() + "&newPassword=" + binding.newPass.getText().toString(),
                    response -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.changePassword.setEnabled(true);
                        if (response.equals("success")) {
                            Toast.makeText(this, "Password Changed", Toast.LENGTH_SHORT).show();
                            this.finish();
                        } else {
                            binding.oldPassL.setError("Invalid old password!");
                        }
                    },
                    error -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.changePassword.setEnabled(true);
                        Toast.makeText(this, "Error Message! " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onBackClick() {
        binding.backBtn.setOnClickListener(v -> this.finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}