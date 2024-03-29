package com.example.homesewa.adapters.bookingsReqAdapter;

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
import com.example.homesewa.helper.dateParser.DateParser;
import com.example.homesewa.models.bookingResponse.IBooked;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IBookedReqAdapter extends RecyclerView.Adapter<IBookedReqAdapter.BookingsViewHolder> {
    private final Context context;
    private final List<IBooked> bookings;

    public IBookedReqAdapter(Context context, List<IBooked> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_i_booked, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        IBooked booked = bookings.get(position);

        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.brand_logo)
                .error(R.drawable.brand_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(context)
                .setDefaultRequestOptions(defaultOptions)
                .load(bookings.get(position).getUserImage())
                .into(holder.profileImage);
        holder.fullName.setText(bookings.get(position).getFullName());
        holder.serviceDate.setText(DateParser.formatDate(bookings.get(position).getServiceDate(), "MMM dd, yyyy"));
        holder.problemDescription.setText(bookings.get(position).getProblemDescription());

        holder.address.setText(booked.getCustomerAddress());
        holder.tvServiceType.setText(booked.getServiceType());
        holder.tvPhone.setText(booked.getUsername());
        holder.tvSendOnDate.setText(DateParser.formatDate(bookings.get(position).getBookingDate(), "MMM dd, yyyy"));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView profileImage;
        private final TextView fullName, serviceDate, problemDescription, address, tvSendOnDate, tvServiceType, tvPhone;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.otherUserImage);
            fullName = itemView.findViewById(R.id.tvName);
            serviceDate = itemView.findViewById(R.id.tvServiceOnDate);
            problemDescription = itemView.findViewById(R.id.tvDescription);
            address = itemView.findViewById(R.id.tvAddress);
            tvSendOnDate = itemView.findViewById(R.id.tvSendOnDate);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
