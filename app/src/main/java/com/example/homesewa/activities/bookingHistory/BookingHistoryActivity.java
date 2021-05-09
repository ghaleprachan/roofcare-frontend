package com.example.homesewa.activities.bookingHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.adapters.bookingHistoryAdapter.BookingHistoryAdapter;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.databinding.ActivityBookingHistoryBinding;
import com.example.homesewa.helper.userDetails.UserBasicDetails;
import com.example.homesewa.models.bookingHistory.BookingHistoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    private ActivityBookingHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackClick();
        bookingHistoryApiCall();
    }

    private void bookingHistoryApiCall() {
        try {
            binding.progress.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getBookingHistory + UserBasicDetails.getId(this),
                    response -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        ArrayList<BookingHistoryModel> bookingHistory = new Gson().fromJson(response, new TypeToken<List<BookingHistoryModel>>() {
                        }.getType());

                        setUpRecyclerView(bookingHistory);

                    },
                    error -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.GONE);
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Log.d("TAG", "bookingHistoryApiCall: " + ex.getMessage());
        }
    }

    private void setUpRecyclerView(ArrayList<BookingHistoryModel> bookingHistory) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new BookingHistoryAdapter(this, bookingHistory));
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }
}