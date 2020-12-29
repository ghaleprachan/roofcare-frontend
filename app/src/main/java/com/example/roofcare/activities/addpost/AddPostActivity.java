package com.example.roofcare.activities.addpost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.R;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityAddPostBinding;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.registerModel.RegisterModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {
    private ActivityAddPostBinding binding;
    private int YEAR = 0;
    private int MONTH = 0;
    private int DATE = 0;
    private Calendar newCalendar;
    private CharSequence charSequence;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onBackClick();
        onDateSelection();
        onPostClick();
        onSelectImageClick();
    }

    private void onSelectImageClick() {
        binding.btnAddImage.setOnClickListener(v -> selectImage());
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
                        binding.offerImage.setVisibility(View.VISIBLE);
                        binding.offerImage.setImageURI(selectedImage);
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

    private void onPostClick() {
        binding.postNow.setOnClickListener(v -> {
            if (binding.offerDesc.getText().toString().isEmpty()) {
                binding.offerDesc.requestFocus();
                binding.offerDescriptionL.setError("Please add offer description!");
            } else if (binding.validDate.getText().toString().isEmpty()) {
                binding.validDate.requestFocus();
                binding.validDateL.setError("Please add valid date!");
            } else {
                apiCall();
            }
        });
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

    private void apiCall() {
        try {
            binding.loading.setVisibility(View.VISIBLE);
            binding.postNow.setVisibility(View.GONE);
            JSONObject object = new JSONObject();
            object.put("userId", UserBasicDetails.getId(this));
            object.put("offerDescription", binding.offerDesc.getText().toString());
            object.put("validDate", DateFormat.format("yyyy-MM-dd'T'HH:mm:ss", newCalendar));
            object.put("offerImage", bitmapToString(bitmap));

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.addOffer,
                    object,
                    response -> {
                        binding.loading.setVisibility(View.GONE);
                        binding.postNow.setVisibility(View.VISIBLE);
                        try {
                            Toast.makeText(this, response.getString("Success"), Toast.LENGTH_SHORT).show();
                            this.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        binding.loading.setVisibility(View.GONE);
                        binding.postNow.setVisibility(View.VISIBLE);
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onDateSelection() {
        binding.validDate.setOnClickListener(v -> {
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
                        binding.validDate.setText(charSequence);
                    }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> this.finish());
    }
}