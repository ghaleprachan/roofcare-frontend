package com.example.roofcare.adapters.bookingHistoryAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.roofcare.R;
import com.example.roofcare.helper.dateParser.DateParser;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.bookingHistory.BookingHistoryModel;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingHistoryadapter extends RecyclerView.Adapter<BookingHistoryadapter.BookingsViewHolder> {
    private final Context context;
    private final List<BookingHistoryModel> bookingHistory;

    public BookingHistoryadapter(Context context, List<BookingHistoryModel> bookings) {
        this.context = context;
        this.bookingHistory = bookings;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingsViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_booking_history, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        holder.serviceDate.setText(DateParser.formatDate(
                bookingHistory.get(position).getServiceDate(),
                "MMM dd, yyy"));
        holder.address.setText(bookingHistory.get(position).getCustomerAddress());
        holder.problemDesc.setText(bookingHistory.get(position).getServiceType() + "\n" +
                bookingHistory.get(position).getProblemDescription());
        if (bookingHistory.get(position).getBookingById().equals(UserBasicDetails.getId(context))) {
            Glide.with(context)
                    .setDefaultRequestOptions(defaultOptions)
                    .load(bookingHistory.get(position).getBookingToUserImage())
                    .into(holder.profile);
            holder.fullName.setText(bookingHistory.get(position).getBookingToFullName());

        } else {
            Glide.with(context)
                    .setDefaultRequestOptions(defaultOptions)
                    .load(bookingHistory.get(position).getBookingByImage())
                    .into(holder.profile);
            holder.fullName.setText(bookingHistory.get(position).getBookingByName());
        }
        onViewBillClick(holder.viewBill, position);
    }

    @SuppressLint("SetTextI18n")
    private void onViewBillClick(Button viewBill, int position) {
        viewBill.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_bill_layout);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button closeBtn = dialog.findViewById(R.id.close);
            TextView totalCost = dialog.findViewById(R.id.totalCost);
            EditText serviceCharge = dialog.findViewById(R.id.serviceCharge);
            EditText discount = dialog.findViewById(R.id.discount);
            EditText travellingCost = dialog.findViewById(R.id.travellingCost);

            totalCost.setText(bookingHistory.get(position).getTotalCharge().toString());
            serviceCharge.setText(bookingHistory.get(position).getServiceCharge().toString());
            discount.setText(bookingHistory.get(position).getDiscountPercentage().toString());
            travellingCost.setText(bookingHistory.get(position).getTravellingCost().toString());
            dialog.setCanceledOnTouchOutside(false);
            closeBtn.setOnClickListener(v1 -> dialog.dismiss());
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return bookingHistory.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView profile;
        private final TextView fullName, serviceDate, address, problemDesc;
        private final Button viewBill;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.userImage);
            fullName = itemView.findViewById(R.id.fullName);
            serviceDate = itemView.findViewById(R.id.serviceDate);
            address = itemView.findViewById(R.id.serviceAddress);
            problemDesc = itemView.findViewById(R.id.problemDescription);
            viewBill = itemView.findViewById(R.id.viewBill);
        }
    }
}
