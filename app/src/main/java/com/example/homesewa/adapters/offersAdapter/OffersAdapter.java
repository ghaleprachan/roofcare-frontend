package com.example.homesewa.adapters.offersAdapter;

import android.app.AlertDialog;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.homesewa.R;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.helper.dateParser.DateParser;
import com.example.homesewa.helper.userDetails.UserBasicDetails;
import com.example.homesewa.models.offerResponseModel.OfferResponseModel;
import com.example.homesewa.services.savedIdService.SavedIsListService;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {
    private final Context mContext;
    private final ArrayList<OfferResponseModel> offers;
    private final Boolean visible;

    public OffersAdapter(Context mContext, ArrayList<OfferResponseModel> offers, Boolean visible) {
        this.mContext = mContext;
        this.offers = offers;
        this.visible = visible;
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
        if (SavedIsListService.models != null) {
            if (SavedIsListService.isSaved(offers.get(position).getOfferId())) {
                holder.savePost.setImageResource(R.drawable.ic_baseline_bookmark_24);
            } else {
                holder.savePost.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
            }
        }
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
        if (offers.get(position).getAddedByImage() == null || offers.get(position).getAddedByImage().isEmpty()) {
            holder.profileImage.setImageResource(R.drawable.ic_outline_person_24);
        } else {
            Glide.with(mContext)
                    .setDefaultRequestOptions(defaultOptions)
                    .load(ApiCollection.baseUrl + offers.get(position).getAddedByImage())
                    .into(holder.profileImage);
        }
        holder.profileName.setText(offers.get(position).getAddedByName());
        holder.postDate.setText(DateParser.formatDate(offers.get(position).getPostedDate(), "MMM dd 'at' h:ss a"));
        holder.postDescription.setText(offers.get(position).getOfferDescription());
        onCallClick(holder.callUser, position);

        if (offers.get(position).getAddedByUsername().equals(UserBasicDetails.getUserName(mContext))) {
            holder.deletePost.setVisibility(View.VISIBLE);
        } else {
            holder.deletePost.setVisibility(View.GONE);
        }
        onDeleteClick(holder.deletePost, position);

        onSaveOfferClick(holder.savePost, position);
    }

    private void onSaveOfferClick(ImageView savePost, int position) {
        savePost.setOnClickListener(v -> {
            try {
                JSONObject object = new JSONObject();
                object.put("userId", UserBasicDetails.getId(mContext));
                object.put("offerId", offers.get(position).getOfferId());

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        ApiCollection.saveOffer,
                        object,
                        response -> {
                            savePost.setImageResource(R.drawable.ic_baseline_bookmark_24);
                            Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
                        },
                        error -> Toast.makeText(mContext, "Failed to save" + error, Toast.LENGTH_SHORT).show()
                );
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(request);
            } catch (Exception ex) {
                Log.d("TAG", "onSaveOfferClick: " + ex.getMessage());
            }
        });
    }

    private void onDeleteClick(ImageView deletePost, int position) {
        deletePost.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                    .setTitle("Delete")
                    .setIcon(R.drawable.ic_baseline_delete_24)
                    .setMessage("Are you sure to delete post?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        removeApiCall(position);
                        dialog.dismiss();
                    });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        });
    }

    private void removeApiCall(int position) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    ApiCollection.deleteOffer + offers.get(position).getOfferId(),
                    response -> {
                        Toast.makeText(mContext, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        offers.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, offers.size());
                    },
                    error -> Toast.makeText(mContext, "Failed to delete!", Toast.LENGTH_SHORT).show()
            );
            RequestQueue queue = Volley.newRequestQueue(mContext);
            queue.add(request);

        } catch (Exception ex) {
            Log.d("TAG", "onDeleteClick: " + ex.getMessage());
        }
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
