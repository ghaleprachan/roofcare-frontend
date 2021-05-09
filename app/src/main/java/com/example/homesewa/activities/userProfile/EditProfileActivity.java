package com.example.homesewa.activities.userProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.homesewa.R;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.databinding.ActivityEditProfileBinding;
import com.example.homesewa.helper.dateParser.DateParser;
import com.example.homesewa.helper.userDetails.UserBasicDetails;
import com.example.homesewa.models.registerModel.ImageUploadResponse;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    private int YEAR = 0;
    private int MONTH = 0;
    private int DATE = 0;
    private Calendar newCalendar;
    private CharSequence charSequence;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        onDobClick();
        onBackClick();
        onUpdateDetailsClick();
        onChangeProfileClick();
    }

    private void onChangeProfileClick() {
        binding.changeImage.setOnClickListener(v -> {
            selectImage();
        });
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
                        if (bitmap != null) {
                            postApiCall();
                        }
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

    private void postApiCall() {
        try {
            binding.progress.setVisibility(View.VISIBLE);
            binding.update.setEnabled(false);
            JSONObject object = new JSONObject();
            object.put("userId", UserBasicDetails.getId(this));
            object.put("userImage", bitmapToString(bitmap));
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    ApiCollection.addProfileImage,
                    object,
                    response -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.update.setEnabled(true);
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
                                Intent data = new Intent();
                                data.putExtra("result", "ok");
                                setResult(RESULT_OK, data);
                                this.finish();
                            } else {
                                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                        binding.progress.setVisibility(View.GONE);
                        binding.update.setEnabled(true);
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

    private void initViews() {
        RequestOptions defaultOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        if (ProfileDetailsDataHolder.profileDetails.get(0).getUserImage() == null || ProfileDetailsDataHolder.profileDetails.get(0).getUserImage().isEmpty()) {
            binding.profileImage.setImageResource(R.drawable.ic_outline_person_24);
        } else {
            Glide.with(this)
                    .setDefaultRequestOptions(defaultOptions)
                    .load(ApiCollection.baseUrl + ProfileDetailsDataHolder.profileDetails.get(0).getUserImage())
                    .into(binding.profileImage);
        }
        binding.fullName.setText(ProfileDetailsDataHolder.profileDetails.get(0).getFullName());
        binding.dob.setText(DateParser.formatDate(ProfileDetailsDataHolder.profileDetails.get(0).getDob(), "MMM dd, yyyy"));
        binding.address.setText(ProfileDetailsDataHolder.profileDetails.get(0).getAddress());
        binding.contact.setText(ProfileDetailsDataHolder.profileDetails.get(0).getContact());
        binding.about.setText(ProfileDetailsDataHolder.profileDetails.get(0).getAbout());
        if (ProfileDetailsDataHolder.profileDetails.get(0).getGender().equalsIgnoreCase("Male")) {
            binding.gender.setSelection(0);
        }
        if (ProfileDetailsDataHolder.profileDetails.get(0).getGender().equalsIgnoreCase("Female")) {
            binding.gender.setSelection(1);
        }
        if (ProfileDetailsDataHolder.profileDetails.get(0).getGender().equalsIgnoreCase("Other")) {
            binding.gender.setSelection(2);
        }
    }

    private void onUpdateDetailsClick() {
        binding.update.setOnClickListener(v -> {
            if (binding.fullName.getText().toString().isEmpty()) {
                binding.fullName.requestFocus();
                binding.fullNameL.setError("Enter your name!");
            } else if (binding.dob.getText().toString().isEmpty()) {
                binding.dob.requestFocus();
                binding.fullNameL.setError(null);
                binding.dobL.setError("Add you date of birth!");
            } else if (binding.address.getText().toString().isEmpty()) {
                binding.address.requestFocus();
                binding.dobL.setError(null);
                binding.addressL.setError("Enter your address!");
            } else if (binding.contact.getText().toString().isEmpty() || binding.contact.getText().toString().length() != 10) {
                binding.contact.requestFocus();
                binding.contactL.setError("Invalid contact!");
                binding.addressL.setError(null);
            } else if (binding.about.getText().toString().isEmpty()) {
                binding.about.requestFocus();
                binding.contactL.setError(null);
                binding.aboutL.setError("Add something about yourself!");
            } else if (binding.fullName.getText().toString().equalsIgnoreCase(ProfileDetailsDataHolder.profileDetails.get(0).getFullName()) &&
                    binding.dob.getText().toString().equalsIgnoreCase(DateParser.formatDate(ProfileDetailsDataHolder.profileDetails.get(0).getDob(), "MMM dd, yyy")) &&
                    binding.address.getText().toString().equalsIgnoreCase(ProfileDetailsDataHolder.profileDetails.get(0).getAddress()) &&
                    binding.contact.getText().toString().equalsIgnoreCase(ProfileDetailsDataHolder.profileDetails.get(0).getContact()) &&
                    binding.about.getText().toString().equalsIgnoreCase(ProfileDetailsDataHolder.profileDetails.get(0).getAbout()) &&
                    binding.gender.getSelectedItem().toString().equalsIgnoreCase(ProfileDetailsDataHolder.profileDetails.get(0).getGender())
            ) {
                Intent data = new Intent();
                data.putExtra("result", "no");
                setResult(RESULT_OK, data);
                this.finish();
            } else {
                binding.fullNameL.setError(null);
                binding.dobL.setError(null);
                binding.addressL.setError(null);
                binding.contactL.setError(null);
                updateApiCall();
            }
        });
    }

    private void updateApiCall() {
        try {
            binding.update.setEnabled(false);
            binding.progress.setVisibility(View.VISIBLE);
            JSONObject object = new JSONObject();
            object.put("userId", UserBasicDetails.getId(this));
            object.put("name", binding.fullName.getText().toString());
            object.put("address", binding.address.getText().toString());
            object.put("gender", binding.gender.getSelectedItem());
            if (newCalendar != null) {
                object.put("dob", DateFormat.format("yyyy-MM-dd'T'HH:mm:ss", newCalendar));
            } else {
                object.put("dob", null);
            }
            object.put("about", binding.about.getText().toString());
            object.put("contact", binding.contact.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    ApiCollection.updateProfile,
                    object,
                    response -> {
                        try {
                            binding.update.setEnabled(true);
                            binding.progress.setVisibility(View.GONE);
                            UpdateResponse updateResponse = new GsonBuilder().create().fromJson(
                                    String.valueOf(response), UpdateResponse.class
                            );
                            if (updateResponse.getSuccess()) {
                                SharedPreferences prefs = this.getSharedPreferences(
                                        "LOGIN_DETAILS", 0);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("FullName", binding.fullName.getText().toString());
                                editor.apply();
                                Intent data = new Intent();
                                data.putExtra("result", "ok");
                                setResult(RESULT_OK, data);
                                this.finish();
                            } else {
                                Toast.makeText(this, "Failed to update, please try again!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            binding.update.setEnabled(true);
                            binding.progress.setVisibility(View.GONE);
                            Toast.makeText(this, "Something went wrong try again!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                        binding.update.setEnabled(true);
                        binding.progress.setVisibility(View.GONE);
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            binding.update.setEnabled(true);
            binding.progress.setVisibility(View.GONE);
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void onDobClick() {
        binding.dob.setOnClickListener(v -> {
            if (YEAR == 0 || MONTH == 0 || DATE == 0) {
                Calendar calendar = Calendar.getInstance();
                YEAR = calendar.get(Calendar.YEAR);
                MONTH = calendar.get(Calendar.MONTH);
                DATE = calendar.get(Calendar.DATE);
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, year, month, date) -> {
                        YEAR = year;
                        MONTH = month;
                        DATE = date;
                        newCalendar = Calendar.getInstance();
                        newCalendar.set(Calendar.YEAR, year);
                        newCalendar.set(Calendar.MONTH, month);
                        newCalendar.set(Calendar.DATE, date);

                        charSequence = DateFormat.format("MMM d, yyyy", newCalendar);
                        binding.dob.setText(charSequence);
                    }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });
    }
}