package com.example.roofcare.activities.reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.roofcare.R;

public class UserReviews extends AppCompatActivity {
    private LinearLayout back;
    private ProgressBar loading;
    private RecyclerView reviewsList;
    private ImageView refresh;

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
    }

    private void onBackClicked() {
        back.setOnClickListener(v -> onBackPressed());
    }

    private void uiInitialize() {
        back = findViewById(R.id.back);
        loading = findViewById(R.id.loading);
        reviewsList = findViewById(R.id.reviewsList);
        refresh = findViewById(R.id.refresh);
    }
}