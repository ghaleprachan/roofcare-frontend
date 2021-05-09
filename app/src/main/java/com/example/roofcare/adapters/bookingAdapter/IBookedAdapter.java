package com.example.roofcare.adapters.bookingAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.roofcare.R;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.helper.dateParser.DateParser;
import com.example.roofcare.models.bookingResponse.IBooked;
import com.example.roofcare.models.bookingResponse.ImBooked;

import java.util.List;
import java.util.Objects;

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
        return new BookingsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_im_booked_completed, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        IBooked booked = bookings.get(position);

        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(context)
                .setDefaultRequestOptions(defaultOptions)
                .load(bookings.get(position).getUserImage())
                .error(R.drawable.brand_logo)
                .into(holder.profile);

        holder.fullName.setText(bookings.get(position).getFullName());
        holder.serviceDate.setText(DateParser.formatDate(bookings.get(position).getServiceDate(), "MMM dd, yyyy"));
        holder.address.setText(bookings.get(position).getCustomerAddress());
        holder.problemDesc.setText(bookings.get(position).getProblemDescription());

        holder.address.setText(booked.getCustomerAddress());
        holder.tvServiceType.setText(booked.getServiceType());
        holder.tvPhone.setText(booked.getUsername());
        holder.tvSendOnDate.setText(DateParser.formatDate(bookings.get(position).getBookingDate(), "MMM dd, yyyy"));
        holder.totalPrice.setText("Rs. " + booked.getTotalCharge().toString());

        holder.completedStatus.setVisibility(View.VISIBLE);
        onCompletedClick(holder.completedStatus, holder.viewBill, position);
        onViewBillClick(holder.viewBill, position);
    }

    private void onCompletedClick(Button completedStatus, Button viewBill, int position) {
        completedStatus.setOnClickListener(v -> {
            completedStatus.setEnabled(false);
            viewBill.setEnabled(false);
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    ApiCollection.markBookingCompleted + bookings.get(position).getBookingId(),
                    response -> {
                        completedStatus.setEnabled(true);
                        viewBill.setEnabled(true);
                        if (response.equalsIgnoreCase("true")) {
                            bookings.remove(position);
                            notifyDataSetChanged();
                            notifyItemChanged(position);
                        }
                    },
                    error -> {
                        completedStatus.setEnabled(true);
                        viewBill.setEnabled(true);
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);
        });
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
            try {
                totalCost.setText(bookings.get(position).getTotalCharge().toString());
                serviceCharge.setText(bookings.get(position).getServiceCharge().toString());
                discount.setText(bookings.get(position).getDiscountPercentage().toString());
                travellingCost.setText(bookings.get(position).getTravellingCost().toString());
            } catch (Exception ex) {
                Log.d("TAG", "onViewBillClick: " + ex.getMessage());
            }
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
        private final Button viewBill, completedStatus;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.otherUserImage);
            fullName = itemView.findViewById(R.id.tvName);
            serviceDate = itemView.findViewById(R.id.tvServiceOnDate);
            problemDesc = itemView.findViewById(R.id.tvDescription);

            viewBill = itemView.findViewById(R.id.viewBill);
            completedStatus = itemView.findViewById(R.id.completed);

            address = itemView.findViewById(R.id.tvAddress);
            tvSendOnDate = itemView.findViewById(R.id.tvSendOnDate);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            totalPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
