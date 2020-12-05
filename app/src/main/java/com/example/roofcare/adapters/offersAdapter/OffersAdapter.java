package com.example.roofcare.adapters.offersAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.roofcare.R;
import com.example.roofcare.helper.dateParser.DateParser;
import com.example.roofcare.models.offerResponseModel.OfferResponseModel;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {
    private final Context mContext;
    private final ArrayList<OfferResponseModel> offers;

    public OffersAdapter(Context mContext, ArrayList<OfferResponseModel> offers) {
        this.mContext = mContext;
        this.offers = offers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_offers_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(offers.get(position).getOfferImage())
                .into(holder.postImage);

        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(offers.get(position).getAddedByImage())
                .into(holder.profileImage);
        holder.profileName.setText(offers.get(position).getAddedByName());
        holder.postDate.setText(DateParser.formatDate(offers.get(position).getPostedDate(), "MMM dd 'at' h:ss a"));
        holder.postDescription.setText(offers.get(position).getOfferDescription());
        holder.moreOptions.setVisibility(View.GONE);

        onCallClick(holder.callUser, position);
    }

    private void onCallClick(ImageView callUser, int position) {
        callUser.setOnClickListener(v -> mContext.startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + offers.get(position).getAddedByContact()))));
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView profileName, postDate;
        private ImageView postImage, moreOptions, callUser, savePost;
        private ExpandableTextView postDescription;
        private LinearLayout visitProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uiInitial(itemView);
        }

        private void uiInitial(View view) {
            profileImage = view.findViewById(R.id.profileImage);
            profileName = view.findViewById(R.id.profileName);
            postDescription = view.findViewById(R.id.expand_text_view);
            postImage = view.findViewById(R.id.postImage);
            moreOptions = view.findViewById(R.id.moreOptions);
            postDate = view.findViewById(R.id.postedDate);
            visitProfile = view.findViewById(R.id.viewProfile);
            callUser = view.findViewById(R.id.call);
            savePost = view.findViewById(R.id.save);
        }
    }
}
