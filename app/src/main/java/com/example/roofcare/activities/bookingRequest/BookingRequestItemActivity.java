package com.example.roofcare.activities.bookingRequest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.adapters.bookingsReqAdapter.IBookedReqAdapter;
import com.example.roofcare.adapters.bookingsReqAdapter.ImBookedReqAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.bookingResponse.BookingResponseModel;
import com.example.roofcare.services.bookingService.BookingRequestServiceClass;
import com.google.gson.GsonBuilder;
import com.example.roofcare.databinding.ActivityBookingRequestItemBinding;

public class BookingRequestItemActivity extends AppCompatActivity {
    private ActivityBookingRequestItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingRequestItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackClick();
        bookingRequestApiCall();
        onSpinnerChange();
    }

    private void onSpinnerChange() {
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (BookingRequestServiceClass.responseModel.size() != 0) {
                    if (position == 1) {
                        imBooked();
                    } else {
                        iBooked();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (requestCode == Activity.RESULT_OK) {
                try {
                    imBooked();
                } catch (Exception ex) {
                    Log.d("TAG", "onActivityResult: "+ex.getMessage());
                }
            }
        }
    }

    private void bookingRequestApiCall() {
        try {
            binding.iBookedRequests.setVisibility(View.GONE);
            binding.loading.setVisibility(View.VISIBLE);
            binding.spinnerParent.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getBookingAndRequests + "?userId=" + UserBasicDetails.getId(this) + "&isBooked=" + false,
                    response -> {
                        binding.iBookedRequests.setVisibility(View.VISIBLE);
                        binding.loading.setVisibility(View.GONE);
                        binding.spinnerParent.setVisibility(View.VISIBLE);
                        try {
                            BookingResponseModel responseModel = new GsonBuilder().create().fromJson(response, BookingResponseModel.class);
                            if (BookingRequestServiceClass.addBookingResponse(responseModel)) {
                                iBooked();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("TAGRequestResException", "bookingRequestApiCall: " + ex.getMessage());
                        }
                    },
                    error -> {
                        binding.spinnerParent.setVisibility(View.GONE);
                        binding.iBookedRequests.setVisibility(View.VISIBLE);
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

    private void iBooked() {
        binding.iBookedRequests.setLayoutManager(new LinearLayoutManager(this));
        IBookedReqAdapter adapter = new IBookedReqAdapter(this, BookingRequestServiceClass.responseModel.get(0).getIBooked());
        binding.iBookedRequests.setAdapter(adapter);
    }

    private void imBooked() {
        try {
            binding.iBookedRequests.setLayoutManager(new LinearLayoutManager(this));
            ImBookedReqAdapter adapter = new ImBookedReqAdapter(this, BookingRequestServiceClass.responseModel.get(0).getImBooked());
            binding.iBookedRequests.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }
}