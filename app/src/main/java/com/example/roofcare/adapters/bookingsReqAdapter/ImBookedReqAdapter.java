package com.example.roofcare.adapters.bookingsReqAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ImBookedReqAdapter extends RecyclerView.Adapter<ImBookedReqAdapter.BookingsViewHolder> {
    private final Context context;
    private final List<ImBooked> bookings;

    public ImBookedReqAdapter(Context context, List<ImBooked> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_im_booked, parent, false));
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
                .into(holder.profileImage);
        holder.fullName.setText(bookings.get(position).getFullName());
        holder.serviceDate.setText("Service Date: " + DateParser.formatDate(bookings.get(position).getServiceDate(), "MMM dd, yyyy") + "\nAt: " +
                bookings.get(position).getCustomerAddress());
        holder.problemDescription.setText(bookings.get(position).getServiceType() + "\n\n" + bookings.get(position).getProblemDescription());

        onDenyClick(holder.deny, holder.accept, position);
        onAcceptClick(holder.accept, position);
    }

    private void onAcceptClick(Button accept, int position) {
        accept.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingAcceptFormActivity.class);
            intent.putExtra("position", position);
            ((AppCompatActivity) context).startActivityForResult(intent, 1);
        });
    }

    private void onDenyClick(Button decline, Button accept, int position) {
        decline.setOnClickListener(v -> {
            accept.setEnabled(false);
            decline.setEnabled(false);
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    ApiCollection.deleteBooking + bookings.get(position).getBookingId(),
                    response -> {
                        accept.setEnabled(true);
                        decline.setEnabled(true);
                        bookings.remove(position);
                        notifyDataSetChanged();
                        notifyItemRemoved(position);
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        accept.setEnabled(true);
                        decline.setEnabled(true);
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView profileImage;
        private final TextView fullName, serviceDate, problemDescription;
        private final Button accept, deny;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.otherUserImage);
            fullName = itemView.findViewById(R.id.otherUserName);
            serviceDate = itemView.findViewById(R.id.serviceDate);
            problemDescription = itemView.findViewById(R.id.problemDescription);
            accept = itemView.findViewById(R.id.acceptRequest);
            deny = itemView.findViewById(R.id.declineRequest);
        }
    }
}
