package com.example.homesewa.adapters.bookingAdapter;

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
import com.example.homesewa.R;
import com.example.homesewa.helper.dateParser.DateParser;
import com.example.homesewa.models.bookingResponse.ImBooked;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImBookedAdapter extends RecyclerView.Adapter<ImBookedAdapter.BookingsViewHolder> {
    private final Context context;
    private final List<ImBooked> bookings;

    public ImBookedAdapter(Context context, List<ImBooked> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_im_booked_completed, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        ImBooked booked = bookings.get(position);

        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .error(R.drawable.brand_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(context)
                .setDefaultRequestOptions(defaultOptions)
                .load(bookings.get(position).getUserImage())
                .into(holder.profile);

        holder.address.setText(booked.getCustomerAddress());
        holder.tvServiceType.setText(booked.getServiceType());
        holder.tvPhone.setText(booked.getUsername());
        holder.tvSendOnDate.setText(DateParser.formatDate(bookings.get(position).getBookingDate(), "MMM dd, yyyy"));

        holder.fullName.setText(bookings.get(position).getFullName());
        holder.serviceDate.setText(DateParser.formatDate(
                bookings.get(position).getServiceDate(), "MMM dd, yyyy"
        ));
        holder.address.setText(bookings.get(position).getCustomerAddress());
        holder.problemDesc.setText(bookings.get(position).getProblemDescription());

        holder.completedStatus.setVisibility(View.GONE);
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

            totalCost.setText(bookings.get(position).getTotalCharge().toString());
            serviceCharge.setText(bookings.get(position).getServiceCharge().toString());
            discount.setText(bookings.get(position).getDiscountPercentage().toString());
            travellingCost.setText(bookings.get(position).getTravellingCost().toString());
            dialog.setCanceledOnTouchOutside(false);
            closeBtn.setOnClickListener(v1 -> dialog.dismiss());
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView profile;
        private final TextView fullName, serviceDate, address, problemDesc, tvSendOnDate, tvServiceType, tvPhone, totalPrice;
        ;
        private final Button viewBill, completedStatus;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.otherUserImage);
            fullName = itemView.findViewById(R.id.tvName);
            serviceDate = itemView.findViewById(R.id.tvServiceOnDate);
            address = itemView.findViewById(R.id.tvAddress);
            viewBill = itemView.findViewById(R.id.viewBill);
            completedStatus = itemView.findViewById(R.id.completed);
            problemDesc = itemView.findViewById(R.id.tvDescription);

            tvSendOnDate = itemView.findViewById(R.id.tvSendOnDate);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            totalPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
