package com.example.roofcare.activities.userProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.roofcare.R;
import com.example.roofcare.activities.bookingsForms.BookRequestFormActivity;
import com.example.roofcare.activities.reviews.UserReviews;
import com.example.roofcare.activities.userOffer.UserOffers;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.helper.dateParser.DateParser;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.profileResponse.ProfileResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    private CircleImageView profileImage;
    private TextView edit, name, gender, userType, dob, skills, phoneNumber, address, rating, username;
    private Button offers, reviews, bookUserNow;
    private ExpandableTextView expandableTextView;
    private LinearLayout back;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private ImageView refresh;
    private CardView bookCard;
    private String uName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        uiInitial();
        progressBar.setVisibility(View.GONE);
        onBackClick();
        userProfileApiCall();
        onEditClick();
        onOfferClick();
        onReviewsClick();
        onBookClick();
        onRefreshClick();
        try {
            if (getUserNameIntent().equals(UserBasicDetails.getUserName(this))) {
                bookUserNow.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        onBookUserNowClick();
    }

    private void onBookUserNowClick() {
        bookUserNow.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookRequestFormActivity.class);
            intent.putExtra("Id", getId());
            startActivity(intent);
        });
    }

    private String getUserNameIntent() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getString("UserId", null);
    }

    private Integer getId() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("Id", 0);
    }

    private void onRefreshClick() {
        refresh.setOnClickListener(v -> userProfileApiCall());
    }

    private void onBookClick() {
        bookUserNow.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookRequestFormActivity.class);
            intent.putExtra("Id", getId());
            startActivity(intent);
        });
    }

    private void onReviewsClick() {
        reviews.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserReviews.class);
            intent.putExtra("Id", getId());
            intent.putExtra("UserId", uName);
            startActivity(intent);
        });
    }

    private void onOfferClick() {
        offers.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserOffers.class);
            intent.putExtra("UserId", uName);
            startActivity(intent);
        });
    }

    private void onEditClick() {
        edit.setOnClickListener(v -> {
            Toast.makeText(this, "None", Toast.LENGTH_SHORT).show();
        });
    }

    private void userProfileApiCall() {
        try {
            if (getUserNameIntent() != null) {
                uName = getUserNameIntent();
                if (!uName.equals(UserBasicDetails.getUserName(UserProfile.this))) {
                    edit.setVisibility(View.GONE);
                }
            } else {
                edit.setVisibility(View.VISIBLE);
                uName = UserBasicDetails.getUserName(UserProfile.this);
            }
            username.setText(uName);
            progressBar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            bookCard.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getUserProfile + uName,
                    response -> {
                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        bookCard.setVisibility(View.VISIBLE);
                        try {
                            Gson gson = new GsonBuilder().create();
                            ProfileResponseModel model = gson.fromJson(response, ProfileResponseModel.class);
                            if (model != null) {
                                populateData(model);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.GONE);
                        bookCard.setVisibility(View.GONE);
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Something went wrong, Try Again!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateData(ProfileResponseModel model) {
        RequestOptions defaultOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        Glide.with(this)
                .setDefaultRequestOptions(defaultOptions)
                .load(model.getUserImage())
                .into(profileImage);
        name.setText(model.getFullName());
        gender.setText(model.getGender());
        userType.setText("Type: " + model.getUserType());
        dob.setText("DOB: " + DateParser.formatDate(model.getDob(), "MMM dd, yyyy"));
        for (int i = 0; i < model.getProfessions().size(); i++) {
            String profession = "";
            profession += model.getProfessions().get(i).getProfessionName() + " \n";
            skills.append(profession);
        }
        phoneNumber.setText(model.getContact());
        address.setText(model.getAddress());
        expandableTextView.setText(model.getAbout());
        rating.setText("(4.2");
    }

    private void onBackClick() {
        back.setOnClickListener(v -> onBackPressed());
    }

    private void uiInitial() {
        profileImage = findViewById(R.id.profileImage);
        edit = findViewById(R.id.edit);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        userType = findViewById(R.id.userType);
        dob = findViewById(R.id.dob);
        skills = findViewById(R.id.skills);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        offers = findViewById(R.id.offers);
        reviews = findViewById(R.id.reviews);
        expandableTextView = findViewById(R.id.expand_text_view);
        bookUserNow = findViewById(R.id.bookUserNow);
        rating = findViewById(R.id.rating);
        username = findViewById(R.id.username);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progress);
        scrollView = findViewById(R.id.scrollView);
        refresh = findViewById(R.id.refresh);
        bookCard = findViewById(R.id.bookCard);
    }
}