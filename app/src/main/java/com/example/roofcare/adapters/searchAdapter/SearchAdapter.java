package com.example.roofcare.adapters.searchAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.roofcare.R;
import com.example.roofcare.activities.userProfile.UserProfileActivity;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.models.searchResponseModel.SearchResponseModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final Context mContext;
    private final List<SearchResponseModel> searchResults;

    public SearchAdapter(Context mContext, List<SearchResponseModel> searchResults) {
        this.mContext = mContext;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.adapter_search,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .error(R.drawable.ic_outline_person_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(ApiCollection.baseUrl + searchResults.get(position).getUserImage())
                .into(holder.profileImage);
        holder.fullName.setText(searchResults.get(position).getFullName());
        holder.profession.setText(searchResults.get(position).getProfessionName());
        onParentLayoutClick(holder.parentLayout, position);
    }

    private void onParentLayoutClick(ConstraintLayout parentLayout, int position) {
        parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, UserProfileActivity.class);
            intent.putExtra("Id", searchResults.get(position).getUserId());
            intent.putExtra("UserId", searchResults.get(position).getUsername());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profileImage;
        private final TextView fullName, profession;
        private final ConstraintLayout parentLayout;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            fullName = itemView.findViewById(R.id.fullName);
            profession = itemView.findViewById(R.id.profession);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
