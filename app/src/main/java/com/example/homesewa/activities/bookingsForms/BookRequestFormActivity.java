package com.example.homesewa.activities.bookingsForms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.databinding.ActivityBookRequestFormBinding;
import com.example.homesewa.helper.userDetails.UserBasicDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class BookRequestFormActivity extends AppCompatActivity {
    private ActivityBookRequestFormBinding binding;
    private int YEAR = 0;
    private int MONTH = 0;
    private int DATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookRequestFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onSetDateClick();
        onBackClick();
        onBookNowClick();
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    private Integer getId() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("Id", 0);
    }

    private void onSetDateClick() {
        binding.serviceDate.setOnClickListener(v -> {
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
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(Calendar.YEAR, year);
                        newCalendar.set(Calendar.MONTH, month);
                        newCalendar.set(Calendar.DATE, date);
                        CharSequence charSequence = DateFormat.format("MMM d, yyyy", newCalendar);
                        binding.serviceDate.setText(charSequence);
                    }, YEAR, MONTH, DATE);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }

    private void onBookNowClick() {
        binding.sendRequest.setOnClickListener(v -> {
            if (binding.serviceType.getText().toString().isEmpty()) {
                binding.serviceType.requestFocus();
                binding.serviceType.setError("Add service type, eg: plumber");
            } else if (binding.problemDescription.getText().toString().isEmpty()) {
                binding.problemDescription.requestFocus();
                binding.problemDescription.setError("Elaborate your problem.");
            } else if (binding.customerAddress.getText().toString().isEmpty()) {
                binding.customerAddress.requestFocus();
                binding.customerAddress.setError("Add your address");
            } else if (binding.serviceDate.getText().toString().isEmpty()) {
                binding.serviceDate.requestFocus();
                binding.serviceDate.setError("Select service date");
            } else {
                sendServiceRequestApi();
            }
        });
    }

    private void sendServiceRequestApi() {
        try {
            binding.sendRequest.setEnabled(false);
            binding.progress.setVisibility(View.VISIBLE);
            JSONObject object = new JSONObject();
            object.put("bookingById", UserBasicDetails.getId(this));
            object.put("bookingToId", getId());
            object.put("serviceType", binding.serviceType.getText().toString());
            object.put("serviceDate", binding.serviceDate.getText().toString());
            object.put("customerAddress", binding.customerAddress.getText().toString());
            object.put("problemDescription", binding.problemDescription.getText().toString());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.sendBookingRequest,
                    object,
                    response -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.sendRequest.setEnabled(true);
                        try {
                            Toast.makeText(this, response.getString("Success"), Toast.LENGTH_SHORT).show();
                            this.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                        binding.sendRequest.setEnabled(true);
                        binding.progress.setVisibility(View.GONE);
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Log.d("TAGRequestException", "sendServiceRequestApi: " + ex.getMessage());
        }
    }
}