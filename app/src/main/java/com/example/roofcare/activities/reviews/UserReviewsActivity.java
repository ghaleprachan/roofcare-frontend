package com.example.roofcare.activities.reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.R;
import com.example.roofcare.adapters.reviewsAdapter.ReviewsAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.userrReviews.UserReviewsResponse;
import com.example.roofcare.sshSolve.HttpsTrustManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserReviewsActivity extends AppCompatActivity {
    private LinearLayout back;
    private ProgressBar loading;
    private RecyclerView reviewsList;
    private ImageView refresh;
    private TextView failedToLoad;
    private Button addReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reviews);
        uiInitialize();
        onBackClicked();
        reviewsListApiCall();
        onRefreshClick();
        if (getUserNameIntent().equals(UserBasicDetails.getUserName(this))) {
            addReview.setVisibility(View.GONE);
        } else {
            addReview.setVisibility(View.VISIBLE);
        }
        onAddReviewClick();

    }

    private void onAddReviewClick() {
        addReview.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_review_dialog_design);
            dialog.setCancelable(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Button dialogButton = (Button) dialog.findViewById(R.id.addReview);
            EditText feedback = (EditText) dialog.findViewById(R.id.feedback);
            RatingBar rating = (RatingBar) dialog.findViewById(R.id.ratings);
            feedback.requestFocus();
            HttpsTrustManager.allowAllSSL();
            dialogButton.setOnClickListener(v1 -> {
                try {
                    dialog.setCancelable(false);
                    dialogButton.setEnabled(false);
                    JSONObject object = new JSONObject();
                    object.put("feedbackById", UserBasicDetails.getId(this));
                    object.put("feedbackToId", getId());
                    object.put("feedbackText", feedback.getText().toString());
                    object.put("rating", rating.getRating());

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            ApiCollection.postFeedback,
                            object,
                            response -> {
                                Toast.makeText(this, "Review Added", Toast.LENGTH_SHORT).show();
                                reviewsListApiCall();
                                dialog.setCancelable(true);
                                dialog.dismiss();
                                dialogButton.setEnabled(true);
                            },
                            error -> {
                                dialog.setCancelable(true);
                                dialogButton.setEnabled(true);
                                Toast.makeText(this, "Failed to add review! " + error, Toast.LENGTH_SHORT).show();
                            }
                    );
                    RequestQueue queue = Volley.newRequestQueue(this);
                    queue.add(request);

                } catch (Exception ex) {
                    dialogButton.setEnabled(true);
                    Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
                    Log.d("TAG", ex.getMessage());
                }
            });
            dialog.show();
        });
    }

    private void onRefreshClick() {
        refresh.setOnClickListener(v -> reviewsListApiCall());
    }

    private String getUserNameIntent() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getString("UserId", null);
    }

    private Integer getId() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("Id", 0);
    }

    private void reviewsListApiCall() {
        try {
            loading.setVisibility(View.VISIBLE);
            reviewsList.setVisibility(View.GONE);
            failedToLoad.setVisibility(View.GONE);
            StringRequest mRequest = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getUserReviews + getUserNameIntent(),
                    response -> {
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
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
        addReview = findViewById(R.id.addReview);
    }
}