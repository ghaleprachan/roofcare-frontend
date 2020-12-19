package com.example.roofcare.activities.savedOffers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.adapters.savedOffer.SavedOfferAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivitySavedOfferBinding;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.savedOffer.SavedOfferResponseModel;
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
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getSavedOffers + UserBasicDetails.getId(this),
                    response -> {
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