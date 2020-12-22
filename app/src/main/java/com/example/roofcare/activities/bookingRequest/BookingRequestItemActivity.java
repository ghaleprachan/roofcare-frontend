package com.example.roofcare.activities.bookingRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityBookingRequestItemBinding;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.bookingResponse.BookingResponseModel;
import com.example.roofcare.services.bookingService.BookingServiceClass;
import com.google.gson.GsonBuilder;

public class BookingRequestItemActivity extends AppCompatActivity {
    private ActivityBookingRequestItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingRequestItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackClick();
        bookingRequestApiCall();
    }

    private void bookingRequestApiCall() {
        try {
            binding.bookingRequests.setVisibility(View.GONE);
            binding.loading.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getBookingAndRequests + "?userId=" + UserBasicDetails.getId(this) + "&isBooked=" + false,
                    response -> {
                        binding.bookingRequests.setVisibility(View.VISIBLE);
                        binding.loading.setVisibility(View.GONE);
                        try {
                            BookingResponseModel responseModel = new GsonBuilder().create().fromJson(response, BookingResponseModel.class);
                            if (BookingServiceClass.addBookingResponse(responseModel)) {
                                addToRecyclerView();
                            }
                        } catch (Exception ex) {
                            Log.d("TAGRequestResException", "bookingRequestApiCall: " + ex.getMessage());
                        }
                    },
                    error -> {
                        binding.bookingRequests.setVisibility(View.VISIBLE);
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

    private void addToRecyclerView() {
        
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }
}