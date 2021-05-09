package com.example.homesewa.adapters.savedOffer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.homesewa.R;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.helper.dateParser.DateParser;
import com.example.homesewa.models.savedOffer.SavedOffer;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavedOfferAdapter extends RecyclerView.Adapter<SavedOfferAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<SavedOffer> offers;

    public SavedOfferAdapter(Context mContext, List<SavedOffer> offers) {
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
        holder.savePost.setImageResource(R.drawable.ic_baseline_bookmark_24);
        holder.deletePost.setVisibility(View.GONE);
        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        if (offers.get(position).getOfferImage() == null || offers.get(position).getOfferImage().isEmpty()) {
            holder.postImage.setVisibility(View.GONE);
        } else {
            Glide.with(mContext)
                    .setDefaultRequestOptions(defaultOptions)
                    .load(ApiCollection.baseUrl + offers.get(position).getOfferImage())
                    .into(holder.postImage);
        }
        if (offers.get(position).getOfferUserImage() == null || offers.get(position).getOfferUserImage().isEmpty()) {
            holder.profileImage.setVisibility(View.GONE);
        } else {
            Glide.with(mContext)
                    .setDefaultRequestOptions(defaultOptions)
                    .load(ApiCollection.baseUrl + offers.get(position).getOfferUserImage())
                    .into(holder.profileImage);
        }

        holder.profileName.setText(offers.get(position).getOfferFullName());
        holder.postDate.setText(DateParser.formatDate(offers.get(position).getPostedDate(), "MMM dd 'at' h:ss a"));
        holder.postDescription.setText(offers.get(position).getOfferDescription());
        onCallClick(holder.callUser, position);
        removeFromSaved(holder.savePost, position);
    }

    private void removeFromSaved(ImageView savePost, int position) {
        savePost.setOnClickListener(v -> {
            try {
                /*TODO
                 *  action for removing user*/
                StringRequest request = new StringRequest(
                        Request.Method.DELETE,
                        ApiCollection.removeSaved + offers.get(position).getSavedOfferId(),
                        response -> {
                            offers.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, offers.size());
                            Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        },
                        error -> Toast.makeText(mContext, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show()
                );
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(request);
            } catch (Exception ex) {
                Log.d("TAG", "onSaveOfferClick: " + ex.getMessage());
            }
        });
    }

    private void onCallClick(ImageView callUser, int position) {
        callUser.setOnClickListener(v -> mContext.startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + offers.get(position).getOfferPhoneNum()))));
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView profileName, postDate;
        private ImageView postImage, deletePost, callUser, savePost;
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
            deletePost = view.findViewById(R.id.deletePost);
            postDate = view.findViewById(R.id.postedDate);
            visitProfile = view.findViewById(R.id.viewProfile);
            callUser = view.findViewById(R.id.call);
            savePost = view.findViewById(R.id.save);
        }
    }
}
