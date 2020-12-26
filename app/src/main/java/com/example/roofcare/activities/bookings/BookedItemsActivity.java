package com.example.roofcare.activities.bookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.adapters.bookingAdapter.IBookedAdapter;
import com.example.roofcare.adapters.bookingAdapter.ImBookedAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityBookedItemsBinding;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.bookingResponse.BookingResponseModel;
import com.example.roofcare.services.bookingService.BookingsServiceClass;
import com.google.gson.GsonBuilder;

public class BookedItemsActivity extends AppCompatActivity {
    private ActivityBookedItemsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookedItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackClick();
        bookingApiCall();
        onSpinnerChange();
    }

    private void onSpinnerChange() {
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        addToImBooked();
                    } else {
                        addToIBooked();
                    }
                } catch (Exception ex) {
                    Log.d("TAG", "onItemSelected: "+ex.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void bookingApiCall() {
        try {
            binding.bookings.setVisibility(View.GONE);
            binding.loading.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getBookingAndRequests + "?userId=" + UserBasicDetails.getId(this) + "&isBooked=" + true,
                    response -> {
                        binding.bookings.setVisibility(View.VISIBLE);
                        binding.loading.setVisibility(View.GONE);
                        try {
                            BookingResponseModel responseModel = new GsonBuilder().create().fromJson(response, BookingResponseModel.class);
                            if (BookingsServiceClass.addBookingResponse(responseModel)) {
                                addToImBooked();
                            }
                        } catch (Exception ex) {
                            Log.d("TAGRequestResException", "bookingRequestApiCall: " + ex.getMessage());
                        }
                    },
                    error -> {
                        binding.bookings.setVisibility(View.VISIBLE);
                        binding.loading.setVisibility(View.GONE);
                        Toast.makeText(this, "Error " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Log.d("TAGGetRequest", "bookingRequestApiCall: " + ex.getMessage());
        }
    }

    private void addToImBooked() {
        binding.bookings.setLayoutManager(new LinearLayoutManager(this));
        binding.bookings.setAdapter(new ImBookedAdapter(this, BookingsServiceClass.responseModel.getImBooked()));
    }

    private void addToIBooked() {
        binding.bookings.setLayoutManager(new LinearLayoutManager(this));
        binding.bookings.setAdapter(new IBookedAdapter(this, BookingsServiceClass.responseModel.getIBooked()));
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }
}