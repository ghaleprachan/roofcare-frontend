package com.example.roofcare.activities.dashboard.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.roofcare.R;
import com.example.roofcare.activities.userProfile.UserProfileActivity;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.services.moreOptionService.MoreOptionsService;
import com.example.roofcare.adapters.moreOptionAdapter.MoreOptionsAdapter;
import com.example.roofcare.helper.populateMoreSection.PopulateMoreOptions;

import de.hdodenhof.circleimageview.CircleImageView;


public class MoreFragment extends Fragment {
    private RecyclerView moreOptionsRecycler;
    private CircleImageView profileImage;
    private TextView name;
    private LinearLayout visitProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uIInitial(view);
        if (MoreOptionsService.options.size() == 0)
            PopulateMoreOptions.addOptions();
        addDataToRecyclerView();
        profileIconSetup();
        onVisitProfileClick();
    }

    private void onVisitProfileClick() {
        visitProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserProfileActivity.class);
            intent.putExtra("UserId", UserBasicDetails.getUserName(requireContext()));
            startActivity(intent);
        });
    }

    private void profileIconSetup() {
        SharedPreferences preferences = requireContext().getSharedPreferences("LOGIN_DETAILS", 0);
        name.setText(preferences.getString("FullName", "Unknown"));
        RequestOptions defaultOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .error(R.drawable.ic_outline_person_24)
                .dontAnimate()
                .dontTransform();

        if (preferences.getString("UserImage", null) != null) {
            Glide.with(requireContext())
                    .setDefaultRequestOptions(defaultOptions)
                    .load(preferences.getString("UserImage", null))
                    .into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.ic_baseline_supervised_user_circle_24);
        }
    }

    private void addDataToRecyclerView() {
        moreOptionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        MoreOptionsAdapter optionsAdapter = new MoreOptionsAdapter(getContext(), MoreOptionsService.options);
        moreOptionsRecycler.setAdapter(optionsAdapter);
    }

    private void uIInitial(View view) {
        profileImage = view.findViewById(R.id.profileImage);
        name = view.findViewById(R.id.name);
        moreOptionsRecycler = view.findViewById(R.id.recyclerView);
        visitProfile = view.findViewById(R.id.visitProfile);
    }
}
