package com.example.homesewa.activities.savedOffers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.adapters.savedOffer.SavedOfferAdapter;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.databinding.ActivitySavedOfferBinding;
import com.example.homesewa.helper.userDetails.UserBasicDetails;
import com.example.homesewa.models.savedOffer.SavedOfferResponseModel;
import com.google.gson.GsonBuilder;

public class SavedOfferActivity extends AppCompatActivity {
    private ActivitySavedOfferBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackClick();
        savedOfferApiCall();
    }

    private void savedOfferApiCall() {
        try {
            binding.progress.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getSavedOffers + UserBasicDetails.getId(this),
                    response -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        try {
                            SavedOfferResponseModel model = new GsonBuilder().create().fromJson(
                                    response, SavedOfferResponseModel.class
                            );
                            if (model != null) {
                                addToRecyclerView(model);
                            } else {
                                Toast.makeText(this, "No Saves", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        binding.progress.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Log.d("TAG", "savedOfferApiCall: " + ex.getMessage());
        }
    }

    private void addToRecyclerView(SavedOfferResponseModel model) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SavedOfferAdapter adapter = new SavedOfferAdapter(this, model.getSavedOffer());
        binding.recyclerView.setAdapter(adapter);
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }
}