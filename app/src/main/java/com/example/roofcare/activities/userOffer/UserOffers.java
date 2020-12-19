package com.example.roofcare.activities.userOffer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.R;
import com.example.roofcare.adapters.offersAdapter.OffersAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.offerResponseModel.OfferResponseModel;
import com.example.roofcare.services.offerService.OfferService;
import com.example.roofcare.services.offerService.UserOfferService;
import com.example.roofcare.sshSolve.HttpsTrustManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UserOffers extends AppCompatActivity {
    private LinearLayout back;
    private RecyclerView postsRecyclerView;
    private ProgressBar loading;
    private ImageView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_offers);
        uiInitialize();
        onBackClick();
        postsApiCall();
        onRefreshClick();
    }

    private String getUserNameIntent() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getString("UserId", null);
    }

    private void onRefreshClick() {
        refresh.setOnClickListener(v -> postsApiCall());
    }

    private void postsApiCall() {
        try {

            loading.setVisibility(View.VISIBLE);
            postsRecyclerView.setVisibility(View.GONE);
            HttpsTrustManager.allowAllSSL();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getUserOffers + getUserNameIntent(),
                    response -> {
                        loading.setVisibility(View.GONE);
                        postsRecyclerView.setVisibility(View.VISIBLE);
                        try {
                            ArrayList<OfferResponseModel> offersList = new Gson().fromJson(response, new TypeToken<List<OfferResponseModel>>() {
                            }.getType());
                            if (offersList.size() == 0) {
                                Toast.makeText(this, "No offers are available for now!", Toast.LENGTH_SHORT).show();
                            } else {
                                UserOfferService.addOffers(offersList);
                                populateRecyclerView();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Failed to get offer.", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        loading.setVisibility(View.GONE);
                        postsRecyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void populateRecyclerView() {
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OffersAdapter offersAdapter = new OffersAdapter(this, UserOfferService.offers, false);
        postsRecyclerView.setAdapter(offersAdapter);
    }

    private void onBackClick() {
        back.setOnClickListener(v -> onBackPressed());
    }

    private void uiInitialize() {
        back = findViewById(R.id.back);
        postsRecyclerView = findViewById(R.id.postList);
        loading = findViewById(R.id.loading);
        refresh = findViewById(R.id.refresh);
    }
}