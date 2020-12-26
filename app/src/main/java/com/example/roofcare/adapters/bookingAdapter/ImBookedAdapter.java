package com.example.roofcare.adapters.bookingAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.roofcare.activities.bookingsForms.BookingAcceptFormActivity;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.helper.dateParser.DateParser;
import com.example.roofcare.models.bookingResponse.ImBooked;

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
        RequestOptions defaultOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_anim)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(context)
                .setDefaultRequestOptions(defaultOptions)
                .load(bookings.get(position).getUserImage())
                .into(holder.profile);
        holder.fullName.setText(bookings.get(position).getFullName());
        holder.serviceDate.setText("Service Date: " + DateParser.formatDate(
                bookings.get(position).getServiceDate(), "MMM dd, yyyy"
        ));
        holder.address.setText("Customer Address: " + bookings.get(position).getCustomerAddress());
        holder.problemDesc.setText(bookings.get(position).getServiceType() + "\n" +
                bookings.get(position).getProblemDescription());

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
        private final TextView fullName, serviceDate, address, problemDesc;
        private final Button viewBill, completedStatus;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.userImage);
            fullName = itemView.findViewById(R.id.fullName);
            serviceDate = itemView.findViewById(R.id.serviceDate);
            address = itemView.findViewById(R.id.serviceAddress);
            problemDesc = itemView.findViewById(R.id.problemDescription);
            viewBill = itemView.findViewById(R.id.viewBill);
            completedStatus = itemView.findViewById(R.id.completed);
        }
    }
}
