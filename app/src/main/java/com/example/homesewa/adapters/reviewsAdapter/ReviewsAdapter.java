package com.example.homesewa.adapters.reviewsAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.homesewa.R;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.helper.dateParser.DateParser;
import com.example.homesewa.models.userrReviews.Review;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private final Context mContext;
    private final List<Review> reviews;

    public ReviewsAdapter(Context mContext, List<Review> reviews) {
        this.mContext = mContext;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.review_design, parent, false);
        return new ReviewViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(ApiCollection.baseUrl + reviews.get(position).getByImage())
                .into(holder.profile);
        holder.name.setText(reviews.get(position).getByFullName());
        holder.date.setText(DateParser.formatDate(reviews.get(position).getFeedbackDate(), "MMM dd, yyyy"));
        holder.review.setText(reviews.get(position).getFeedbackText());
        holder.ratings.setText(Double.toString(reviews.get(position).getRating()));
        //Float.parseFloat(String.valueOf(profileHeading.getResult().getUserRating())));

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView profile;
        private final TextView name, ratings, date, review;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profileImage);
            name = itemView.findViewById(R.id.name);
            ratings = itemView.findViewById(R.id.rating);
            date = itemView.findViewById(R.id.addedDate);
            review = itemView.findViewById(R.id.feedback);
        }
    }
}
