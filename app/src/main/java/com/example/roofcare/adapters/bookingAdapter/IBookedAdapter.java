package com.example.roofcare.adapters.bookingAdapter;

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
import com.example.roofcare.R;
import com.example.roofcare.helper.dateParser.DateParser;
import com.example.roofcare.models.bookingResponse.IBooked;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IBookedAdapter extends RecyclerView.Adapter<IBookedAdapter.BookingsViewHolder> {
    private final Context context;
    private final List<IBooked> bookings;

    public IBookedAdapter(Context context, List<IBooked> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_i_booked_completed, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        /*RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(context)
                .setDefaultRequestOptions(defaultOptions)
                .load(bookings.get(position).getUserImage())
                .into(holder.profileImage);*/
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
