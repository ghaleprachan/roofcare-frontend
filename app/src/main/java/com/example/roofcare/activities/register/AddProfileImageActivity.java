package com.example.roofcare.activities.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.activities.dashboard.Dashboard;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityAddProfileImageBinding;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.registerModel.ImageUploadResponse;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class AddProfileImageActivity extends AppCompatActivity {
    private ActivityAddProfileImageBinding binding;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProfileImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onSkipClick();
        onAddImageClick();
        onSaveClick();
    }

    private void onSaveClick() {
        binding.saveImage.setOnClickListener(v -> {
            if (bitmap == null) {
                Toast.makeText(this, "Add Image or Skip.", Toast.LENGTH_SHORT).show();
            } else {
                postApiCall();
            }
        });
    }

    private void postApiCall() {
        try {
            binding.skip.setVisibility(View.GONE);
            binding.loading.setVisibility(View.VISIBLE);
            binding.saveImage.setEnabled(false);
            JSONObject object = new JSONObject();
            object.put("userId", UserBasicDetails.getId(this));
            object.put("userImage", bitmapToString(bitmap));
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    ApiCollection.addProfileImage,
                    object,
                    response -> {
                        binding.skip.setVisibility(View.VISIBLE);
                        binding.loading.setVisibility(View.GONE);
                        binding.saveImage.setEnabled(true);
                        try {
                            ImageUploadResponse uploadResponse = new GsonBuilder().create().fromJson(
                                    String.valueOf(response), ImageUploadResponse.class
                            );
                            if (uploadResponse.getSuccess()) {
                                SharedPreferences prefs = this.getSharedPreferences(
                                        "LOGIN_DETAILS", 0);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("UserImage", ApiCollection.baseUrl +
                                        uploadResponse.getImageUrl());
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                        binding.loading.setVisibility(View.GONE);
                        binding.saveImage.setEnabled(true);
                        binding.skip.setVisibility(View.VISIBLE);
                    });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onAddImageClick() {
        binding.addImage.setOnClickListener(v -> selectImage());
    }

    private void selectImage() {
        try {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 0:
                case 1:
                    if (resultCode == RESULT_OK) {
                        assert data != null;
                        Uri selectedImage = data.getData();
                        binding.profileImage.setImageURI(selectedImage);
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    }
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(this, "On Result: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String bitmapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } else {
            return null;
        }
    }

    private void onSkipClick() {
        binding.skip.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        });
    }
}