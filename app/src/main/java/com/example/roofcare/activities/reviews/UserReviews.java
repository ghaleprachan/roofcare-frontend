package com.example.roofcare.activities.reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.R;
import com.example.roofcare.adapters.reviewsAdapter.ReviewsAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.userrReviews.UserReviewsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserReviews extends AppCompatActivity {
    private LinearLayout back;
    private ProgressBar loading;
    private RecyclerView reviewsList;
    private ImageView refresh;
    private TextView failedToLoad;
    private CardView form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reviews);
        uiInitialize();
        onBackClicked();
        reviewsListApiCall();
        onRefreshClick();
    }

    private void onRefreshClick() {
        refresh.setOnClickListener(v -> reviewsListApiCall());
    }

    private void reviewsListApiCall() {
        loading.setVisibility(View.VISIBLE);
        reviewsList.setVisibility(View.GONE);
        failedToLoad.setVisibility(View.GONE);
        form.setVisibility(View.GONE);
        StringRequest mRequest = new StringRequest(
                Request.Method.GET,
                ApiCollection.getUserReviews + UserBasicDetails.getUserName(this),
                response -> {
                    form.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    reviewsList.setVisibility(View.VISIBLE);
                    failedToLoad.setVisibility(View.GONE);
                    try {
                        Gson gson = new GsonBuilder().create();
                        UserReviewsResponse reviewsResponse = gson.fromJson(response, UserReviewsResponse.class);
                        if (reviewsResponse != null) {
                            populateRecyclerView(reviewsResponse);
                        }

                    } catch (Exception exception) {
                        failedToLoad.setVisibility(View.VISIBLE);
                        failedToLoad.setText(exception.getMessage());
                    }
                },
                error -> {
                    loading.setVisibility(View.GONE);
                    reviewsList.setVisibility(View.GONE);
                    failedToLoad.setVisibility(View.VISIBLE);
                    failedToLoad.setText(error.toString());
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(mRequest);
    }

    private void populateRecyclerView(UserReviewsResponse reviewsResponse) {
        reviewsList.setLayoutManager(new LinearLayoutManager(this));
        ReviewsAdapter adapter = new ReviewsAdapter(this, reviewsResponse.getReviews());
        reviewsList.setAdapter(adapter);
    }

    private void onBackClicked() {
        back.setOnClickListener(v -> onBackPressed());
    }

    private void uiInitialize() {
        back = findViewById(R.id.back);
        loading = findViewById(R.id.progress);
        reviewsList = findViewById(R.id.reviewsList);
        refresh = findViewById(R.id.refresh);
        failedToLoad = findViewById(R.id.failedToLoad);
        form = findViewById(R.id.form);
    }
}