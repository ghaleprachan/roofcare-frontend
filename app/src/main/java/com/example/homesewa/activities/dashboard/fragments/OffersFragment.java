package com.example.homesewa.activities.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.R;
import com.example.homesewa.adapters.offersAdapter.OffersAdapter;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.helper.userDetails.UserBasicDetails;
import com.example.homesewa.models.idModel.SavedIdResponseModel;
import com.example.homesewa.models.offerResponseModel.OfferResponseModel;
import com.example.homesewa.services.offerService.OfferService;
import com.example.homesewa.services.savedIdService.SavedIsListService;
import com.example.homesewa.sshSolve.HttpsTrustManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class OffersFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmer;
    private LinearLayout recyclerParent;
    private SwipeRefreshLayout swipeRefresh;
    private ImageView refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiInitialize(view);
        getSavedOfferIds();
        //offersApiCall();
        onSwipeRefresh();
        onRefreshClick();
    }

    private void getSavedOfferIds() {
        HttpsTrustManager.allowAllSSL();
        refresh.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
        recyclerParent.setVisibility(View.GONE);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiCollection.getSavedOfferId + UserBasicDetails.getId(requireContext()),
                response -> {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    SavedIdResponseModel responseModel = gson.fromJson(response, SavedIdResponseModel.class);
                    if (responseModel != null) {
                        SavedIsListService.addAll(responseModel.getSaveds());
                    }
                    offersApiCall();
                },
                error -> {
                    refresh.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Something went wrong." + error.toString(), Toast.LENGTH_SHORT).show();
                    shimmer.setVisibility(View.GONE);
                    shimmer.stopShimmer();
                    recyclerParent.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                }
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    private void onRefreshClick() {
        refresh.setOnClickListener(v -> offersApiCall());
    }

    private void onSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(() -> {
            getSavedOfferIds();
            swipeRefresh.setRefreshing(false);
        });
    }

    private void offersApiCall() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiCollection.getALlOffers,
                response -> {
                    shimmer.setVisibility(View.GONE);
                    shimmer.stopShimmer();
                    recyclerParent.setVisibility(View.VISIBLE);
                    try {
                        ArrayList<OfferResponseModel> offersList = new Gson().fromJson(response, new TypeToken<List<OfferResponseModel>>() {
                        }.getType());
                        if (offersList.size() == 0) {
                            Toast.makeText(getContext(), "No offers are available for now!", Toast.LENGTH_SHORT).show();
                        } else {
                            OfferService.addOffers(offersList);
                            populateRecyclerView();
                        }
                    } catch (Exception ex) {
                        refresh.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Failed to get offer.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    refresh.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Something went wrong." + error.toString(), Toast.LENGTH_SHORT).show();
                    shimmer.setVisibility(View.GONE);
                    shimmer.stopShimmer();
                    recyclerParent.setVisibility(View.GONE);
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

    private void populateRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OffersAdapter offersAdapter = new OffersAdapter(getContext(), OfferService.offers, true);
        recyclerView.setAdapter(offersAdapter);
    }

    private void uiInitialize(View view) {
        refresh = view.findViewById(R.id.refresh);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        recyclerParent = view.findViewById(R.id.recyclerParent);
        shimmer = view.findViewById(R.id.shimmer);
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}
